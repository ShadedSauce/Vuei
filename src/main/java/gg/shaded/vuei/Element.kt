package gg.shaded.vuei

import gg.shaded.vuei.layout.For
import gg.shaded.vuei.layout.Layout
import rx.Observable
import rx.subjects.Subject

interface Element {
    val children: List<Element>

    val layout: Layout

    val bindings: Map<String, Observable<out Any>>

    val loop: For?

    fun onClick(context: ClickContext)
}

class SimpleElement(
    override val children: List<Element>,
    override val layout: Layout,
    override val bindings: Map<String, Observable<out Any>>,
    override val loop: For?
): Element {
    override fun onClick(context: ClickContext) {
        val handler = bindings["click"] as Subject<Any, Any>

        handler.onNext(context)
    }
}

fun List<Element>.allocate(parent: Renderable) =
    Observable.combineLatest(
        this.map { child -> child.layout.allocate(child, parent) }
    ) { children ->
        children
            .map { it as Renderable }
            .filterNot { it.height == 0 && it.width == 0 }
            .toList()
    }