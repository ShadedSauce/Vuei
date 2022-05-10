package gg.shaded.vuei.layout

import gg.shaded.vuei.Element
import gg.shaded.vuei.Renderable
import gg.shaded.vuei.SimpleRenderable
import rx.Observable

class IfLayout(
    private val layout: Layout
): Layout {
    override fun allocate(element: Element, parent: Renderable): Observable<Renderable> {
        val condition = element.bindings["if"] as? Observable<Boolean>
            ?: Observable.just(true)

        return condition.switchMap { visible ->
            if(visible) layout.allocate(element, parent)
            else Observable.just(
                SimpleRenderable(
                    width = 0,
                    height = 0,
                    element = element
                )
            )
        }
    }
}