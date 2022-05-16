package gg.shaded.vuei.layout

import gg.shaded.vuei.Renderable
import gg.shaded.vuei.SimpleRenderable
import gg.shaded.vuei.allocate
import rx.Observable

class SpanLayout: Layout {
    override fun allocate(context: LayoutContext): Observable<List<Renderable>> {
        return context.element.children.allocate(context)
            .map { children ->
                var x = 0

                children.map { child ->
                    SimpleRenderable(
                        x = x,
                        width = child.width,
                        height = child.height,
                        element = child.element,
                        children = child.children,
                        item = child.item,
                        onClick = child.onClick
                    ).also { x += it.width }
                }
            }
    }
}