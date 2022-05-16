package gg.shaded.vuei.layout

import gg.shaded.vuei.ClickContext
import gg.shaded.vuei.Renderable
import gg.shaded.vuei.SimpleRenderable
import rx.Observable
import rx.subjects.PublishSubject

class ClickableLayout(
    private val layout: Layout
): Layout {
    override fun allocate(context: LayoutContext): Observable<List<Renderable>> {
        println("allocating clickabnle")

        val onClick = context.getBinding("click") as? PublishSubject<ClickContext>
            ?: return layout.allocate(context)

        println("has click $onClick")

        return layout.allocate(context)
            .map { children ->
                children.map { child ->
                    SimpleRenderable(
                        child.x,
                        child.y,
                        child.width,
                        child.height,
                        child.element,
                        child.children,
                        child.item,
                        child.title,
                    ) { context ->
                        println("Calling click on $child")
                        onClick.onNext(context)
                    }
                }
            }
    }
}