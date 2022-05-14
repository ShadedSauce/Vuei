package gg.shaded.vuei.layout

import gg.shaded.vuei.Element
import gg.shaded.vuei.Renderable
import gg.shaded.vuei.SimpleRenderable
import gg.shaded.vuei.allocate
import rx.Observable

class RowSpaceBetweenLayout: Layout {
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
                val free = container.width - used
                val dividers = children.count() - 1
                val divider = if(dividers > 0) free / dividers else 0
                var lastX = 0

                val childrenLayout = children.mapIndexed { i, child ->
                    val renderable = SimpleRenderable(
                        x = if(i > 0) lastX + divider else 0,
                        width = child.width,
                        height = child.height,
                        element = child.element,
                        item = child.item,
                        children = child.children
                    )

                    lastX = renderable.x + renderable.width
                    return@mapIndexed renderable
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