package gg.shaded.vuei.layout

import gg.shaded.vuei.Element
import gg.shaded.vuei.Renderable
import gg.shaded.vuei.SimpleRenderable
import gg.shaded.vuei.allocate
import io.reactivex.rxjava3.core.Observable
import java.lang.Integer.max

class ColumnCenterLayout: Layout {
    override fun allocate(context: LayoutContext): Observable<List<Renderable>> {
        val container = SimpleRenderable(
            width = context.parent.width,
            height = 0,
            element = context.element
        )

        return context.element.children.allocate(
            context.copy(parent = container)
        )
            .map { children ->
                val used = children.sumOf { it.height }
                val start = max(0, context.parent.height / 2 - used / 2)
                var lastY = start

                val childrenLayout = children.map { child ->
                    child.copy(y = lastY).also { lastY = it.y + it.height }
                }

                val height = childrenLayout.maxOf { it.height + it.y }

                SimpleRenderable(
                    width = container.width,
                    height = height,
                    element = context.element,
                    children = childrenLayout
                ).toList()
            }
    }
}