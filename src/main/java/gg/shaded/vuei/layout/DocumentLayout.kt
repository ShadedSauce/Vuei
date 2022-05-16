package gg.shaded.vuei.layout

import gg.shaded.vuei.*
import rx.Observable

class DocumentLayout: Layout {
    override fun allocate(context: LayoutContext): Observable<List<Renderable>> {
        val title = context.getBinding("title") as? Observable<String>
            ?: Observable.just("Untitled")

        return Observable.combineLatest(
            title,
            context.element.children.allocate(context),
        )
        { t, children ->
            val height = children.maxOfOrNull { it.height + it.y } ?: 0

            SimpleRenderable(
                width = context.parent.width,
                height = height,
                children = children,
                title = t,
                element = context.element
            ).toList()
        }
    }
}