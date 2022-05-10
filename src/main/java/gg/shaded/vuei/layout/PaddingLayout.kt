package gg.shaded.vuei.layout

import gg.shaded.vuei.Element
import gg.shaded.vuei.Renderable
import gg.shaded.vuei.SimpleRenderable
import gg.shaded.vuei.allocate
import rx.Observable

class PaddingLayout: Layout {
    override fun allocate(element: Element, parent: Renderable): Observable<Renderable> {
        val bindings = element.bindings as Map<String, Observable<String>>
        
        val left = bindings["l"]
            ?: bindings["h"]
            ?: bindings["a"]
            ?: Observable.just("0")
        val top = bindings["t"]
            ?: bindings["v"]
            ?: bindings["a"]
            ?: Observable.just("0")
        val right = bindings["r"]
            ?: bindings["h"]
            ?: bindings["a"]
            ?: Observable.just("0")
        val bottom = bindings["b"]
            ?: bindings["v"]
            ?: bindings["a"]
            ?: Observable.just("0")

        val container = SimpleRenderable(
            width = parent.width,
            height = 0,
            element = element
        )

        return element.children.allocate(container)
            .first()
            .flatMap { children ->
                Observable.combineLatest(left, top, right, bottom) { l, t, r, b ->
                    Padding(l.toInt(), t.toInt(), r.toInt(), b.toInt())
                }
                    .flatMap { padding ->
                        val width = children.maxOf { it.width + it.x }
                        val height = children.maxOf { it.height + it.y }

                        val bounds = SimpleRenderable(
                            width = width - padding.l - padding.r,
                            height = height - padding.t - padding.b,
                            element = element
                        )

                        element.children.allocate(bounds)
                            .map { children ->
                                children.map { child ->
                                    SimpleRenderable(
                                        x = padding.l,
                                        y = padding.t,
                                        width = child.width,
                                        height = child.height,
                                        element = child.element,
                                        item = child.item,
                                        children = child.children
                                    )
                                }
                            }
                            .map { children ->
                                SimpleRenderable(
                                    width = container.width,
                                    height = height + padding.t + padding.b,
                                    element = element,
                                    children = children
                                )
                            }
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