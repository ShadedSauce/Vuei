package gg.shaded.vuei

import gg.shaded.vuei.layout.For
import gg.shaded.vuei.layout.Layout
import gg.shaded.vuei.layout.LayoutContext
import gg.shaded.vuei.layout.SimpleLayoutContext
import rx.Observable
import rx.subjects.Subject
import java.lang.IllegalStateException

interface Element {
    val children: List<Element>

    val layout: Layout

    val bindings: Map<String, String>

    val values: Map<String, String>

    val loop: For?

    fun onClick(context: ClickContext)
}

class SimpleElement(
    override val children: List<Element>,
    override val layout: Layout,
    override val bindings: Map<String, String>,
    override val values: Map<String, String>,
    override val loop: For?,
): Element {
    override fun onClick(context: ClickContext) {
        val handler = bindings["click"] as? Subject<Any, Any>

        handler?.onNext(context)
    }
}

fun List<Element>.allocate(
    context: LayoutContext,
    bindings: Map<String, Observable<out Any>>? = null
) = Observable.combineLatest(
        this.map { child ->
            child.layout.allocate(
                SimpleLayoutContext(
                    child,
                    context.parent,
                    context.bindings.plus(bindings ?: HashMap()),
                    context.components,
                    context.slots
                )
            )
        }
    ) { children ->
        children
            .flatMap { it as List<Renderable> }
            .toList()
    }