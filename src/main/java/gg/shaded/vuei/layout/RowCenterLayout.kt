package gg.shaded.vuei.layout

import gg.shaded.vuei.Element
import gg.shaded.vuei.Renderable
import gg.shaded.vuei.SimpleRenderable
import gg.shaded.vuei.allocate
import io.reactivex.rxjava3.core.Observable
import kotlin.math.max

class RowCenterLayout: Layout {
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
                val used = children.sumOf { it.width }
                val start = max(0, context.parent.width / 2 - used / 2)
                var lastX = start

                val childrenLayout = children.map { child ->
                    child.copy(x = lastX).also {
                        lastX = it.x + it.width
                    }
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