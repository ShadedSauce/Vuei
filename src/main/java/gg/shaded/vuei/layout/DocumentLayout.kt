package gg.shaded.vuei.layout

import gg.shaded.vuei.Element
import gg.shaded.vuei.Renderable
import gg.shaded.vuei.SimpleRenderable
import gg.shaded.vuei.allocate
import rx.Observable

class DocumentLayout: Layout {
    override fun allocate(element: Element, parent: Renderable): Observable<Renderable> {
        val title = element.bindings["title"] as Observable<String>

        return Observable.combineLatest(
            element.children.allocate(parent),
            title
        )
        { children, t ->
            val height = children.maxOf { it.height + it.y }

            SimpleRenderable(
                width = parent.width,
                height = height,
                children = children,
                title = t,
                element = element
            )
        }
    }
}