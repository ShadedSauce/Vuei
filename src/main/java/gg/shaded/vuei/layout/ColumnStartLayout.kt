package gg.shaded.vuei.layout

import gg.shaded.vuei.Renderable
import gg.shaded.vuei.SimpleRenderable
import gg.shaded.vuei.allocate
import io.reactivex.rxjava3.core.Observable

class ColumnStartLayout: Layout {
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
                var y = 0

                val childrenLayout = children.map { child ->
                    SimpleRenderable(
                        y = y,
                        width = child.width,
                        height = child.height,
                        element = child.element,
                        item = child.item,
                        children = child.children,
                        onClick = child.onClick
                    ).also { y += it.height }
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