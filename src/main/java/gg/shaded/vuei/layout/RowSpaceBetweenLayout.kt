package gg.shaded.vuei.layout

import gg.shaded.vuei.Element
import gg.shaded.vuei.Renderable
import gg.shaded.vuei.SimpleRenderable
import gg.shaded.vuei.allocate
import io.reactivex.rxjava3.core.Observable

class RowSpaceBetweenLayout: Layout {
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
                val free = container.width - used
                val dividers = children.count() - 1
                val divider = if(dividers > 0) free / dividers else 0
                var lastX = 0

                val childrenLayout = children.mapIndexed { i, child ->
                    child.copy(
                        x = if(i > 0) lastX + divider else 0
                    ).also {
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