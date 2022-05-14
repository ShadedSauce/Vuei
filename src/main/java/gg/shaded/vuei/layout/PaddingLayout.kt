package gg.shaded.vuei.layout

import gg.shaded.vuei.Element
import gg.shaded.vuei.Renderable
import gg.shaded.vuei.SimpleRenderable
import gg.shaded.vuei.allocate
import rx.Observable

class PaddingLayout: Layout {
    override fun allocate(context: LayoutContext): Observable<List<Renderable>> {
        val left = context.getBinding("l")
            ?: context.getBinding("h")
            ?: context.getBinding("a")
            ?: Observable.just(0)

        val top = context.getBinding("t")
            ?: context.getBinding("v")
            ?: context.getBinding("a")
            ?: Observable.just(0)

        val right = context.getBinding("r")
            ?: context.getBinding("h")
            ?: context.getBinding("a")
            ?: Observable.just(0)

        val bottom = context.getBinding("b")
            ?: context.getBinding("v")
            ?: context.getBinding("a")
            ?: Observable.just(0)

        val container = SimpleRenderable(
            width = context.parent.width,
            height = 0,
            element = context.element
        )

        return Observable.combineLatest(left, top, right, bottom) { l, t, r, b ->
            Padding(
                l.toString().toInt(),
                t.toString().toInt(),
                r.toString().toInt(),
                b.toString().toInt()
            )
        }
            .switchMap { padding ->
                val bounds = SimpleRenderable(
                    width = context.parent.width - padding.l - padding.r,
                    height = 0,
                    element = context.element
                )

                context.element.children.allocate(
                    SimpleLayoutContext(
                        context.element,
                        bounds,
                        context.bindings,
                        context.components,
                        context.slots
                    )
                )
                    .map { children ->
                        val height = children.maxOf { it.height + it.y }

                        SimpleRenderable(
                            x = padding.l,
                            y = padding.t,
                            width = container.width,
                            height = height + padding.b,
                            element = context.element,
                            children = children
                        ).toList()
                    }
            }
    }
}

private data class Padding(
    val l: Int,
    val t: Int,
    val r: Int,
    val b: Int
)