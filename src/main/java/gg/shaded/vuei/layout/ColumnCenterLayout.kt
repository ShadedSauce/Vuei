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
            SimpleLayoutContext(
                context.element,
                container,
                context.bindings,
                context.components,
                context.slots
            )
        )
            .map { children ->
                val used = children.sumOf { it.height }
                val start = max(0, context.parent.height / 2 - used / 2)
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
                    element = context.element,
                    children = childrenLayout
                ).toList()
            }
    }
}