package gg.shaded.vuei.layout

import gg.shaded.vuei.Element
import gg.shaded.vuei.Renderable
import gg.shaded.vuei.SimpleRenderable
import gg.shaded.vuei.allocate
import rx.Observable
import kotlin.math.max

class RowCenterLayout: Layout {
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
                val used = children.sumOf { it.width }
                val start = max(0, context.parent.width / 2 - used / 2)
                var lastX = start

                val childrenLayout = children.map { child ->
                    val renderable = SimpleRenderable(
                        x = lastX,
                        width = child.width,
                        height = child.height,
                        element = child.element,
                        item = child.item,
                        children = child.children,
                        onClick = child.onClick
                    )

                    lastX = renderable.x + renderable.width
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