package gg.shaded.vuei.layout

import gg.shaded.vuei.Renderable
import gg.shaded.vuei.SimpleRenderable
import gg.shaded.vuei.allocate
import io.reactivex.rxjava3.core.Observable

class SpanLayout: Layout {
    override fun allocate(context: LayoutContext): Observable<List<Renderable>> {
        return context.element.children.allocate(context)
            .map { children ->
                var x = 0

                val spanned = children.map { child ->
                    child.copy(
                        x = x,
                    ).also { x += it.width }
                }

                SimpleRenderable(
                    width = spanned.maxOfOrNull { it.width + it.x } ?: 0,
                    height = spanned.maxOfOrNull { it.height + it.y } ?: 0,
                    children = spanned,
                    element = context.element
                ).toList()
            }
    }
}