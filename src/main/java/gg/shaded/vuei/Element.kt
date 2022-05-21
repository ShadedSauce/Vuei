package gg.shaded.vuei

import gg.shaded.vuei.layout.For
import gg.shaded.vuei.layout.Layout
import gg.shaded.vuei.layout.LayoutContext
import gg.shaded.vuei.layout.SimpleLayoutContext
import io.reactivex.rxjava3.core.Observable

interface Element {
    val children: List<Element>

    val layout: Layout

    val bindings: Map<String, String>

    val values: Map<String, Any>

    val loop: For?
}

class SimpleElement(
    override val children: List<Element>,
    override val layout: Layout,
    override val bindings: Map<String, String>,
    override val values: Map<String, Any>,
    override val loop: For?,
): Element

fun List<Element>.allocate(
    context: LayoutContext,
    bindings: Map<String, Observable<out Any>>? = null
) = Observable.combineLatest(
        this.map { child ->
            child.layout.allocate(
                context.copy(
                    element = child,
                    bindings = context.bindings.plus(bindings ?: HashMap())
                )
            )
        }
    ) { children ->
        children
            .flatMap { it as List<Renderable> }
            .toList()
    }