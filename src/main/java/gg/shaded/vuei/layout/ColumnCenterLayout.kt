package gg.shaded.vuei.layout

import gg.shaded.vuei.Element
import gg.shaded.vuei.Renderable
import gg.shaded.vuei.SimpleRenderable
import gg.shaded.vuei.allocate
import rx.Observable
import java.lang.Integer.max

class ColumnCenterLayout: Layout {
    override fun allocate(element: Element, parent: Renderable): Observable<Renderable> {
        val container = SimpleRenderable(
            width = parent.width,
            height = 0,
            element = element
        )

        return element.children.allocate(container)
            .map { children ->
                val used = children.sumOf { it.height }
                val start = max(0, parent.height / 2 - used / 2)
                var lastY = start

                val childrenLayout = children.map { child ->
                    val renderable = SimpleRenderable(
                        y = lastY,
                        width = child.width,
                        height = child.height,
                        element = child.element,
                        item = child.item,
                        children = child.children
                    )

                    lastY = renderable.y + renderable.height
                    return@map renderable
                }

                val height = childrenLayout.maxOf { it.height + it.y }

                SimpleRenderable(
                    width = container.width,
                    height = height,
                    element = element,
                    children = childrenLayout
                )
            }
    }
}