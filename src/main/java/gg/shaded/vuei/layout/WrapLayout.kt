package gg.shaded.vuei.layout

import gg.shaded.vuei.Renderable
import gg.shaded.vuei.SimpleRenderable
import gg.shaded.vuei.allocate
import io.reactivex.rxjava3.core.Observable

class WrapLayout: Layout {
    override fun allocate(context: LayoutContext): Observable<List<Renderable>> {
        return context.element.children.allocate(context)
            .map { children ->
                var x = 0
                var y = 0

                val wrapped = children.map { child ->
                    SimpleRenderable(
                        x = x,
                        y = y,
                        width = child.width,
                        height = child.height,
                        element = child.element,
                        children = child.children,
                        item = child.item,
                        onClick = child.onClick
                    ).also {
                        x += child.width

                        if(x >= context.parent.width) {
                            y++
                        }
                    }
                }

                SimpleRenderable(
                    width = children.maxOfOrNull { it.width + it.x } ?: 0,
                    height = children.maxOfOrNull { it.height + it.y } ?: 0,
                    element = context.element,
                    children = wrapped
                ).toList()
            }
    }
}