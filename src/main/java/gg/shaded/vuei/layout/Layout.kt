package gg.shaded.vuei.layout

import gg.shaded.vuei.Component
import gg.shaded.vuei.Element
import gg.shaded.vuei.Renderable
import rx.Observable
import java.lang.IllegalStateException

interface Layout {
    fun allocate(context: LayoutContext): Observable<List<Renderable>>
}

interface LayoutContext {
    val element: Element

    val parent: Renderable

    val bindings: Map<String, Observable<out Any>>

    val components: Map<String, Component>

    val slots: Map<String, List<Element>>

    fun getBinding(key: String): Observable<out Any>? {
        val binding = element.bindings[key]

        if(binding != null) {
            println("binding2: ${bindings[binding]}")
            return bindings[binding]
                ?: throw IllegalStateException("No binding found for $key=$binding.")
        }

        return element.values[key]?.let { Observable.just(it) }
    }
}

class SimpleLayoutContext(
    override val element: Element,
    override val parent: Renderable,
    override val bindings: Map<String, Observable<out Any>>,
    override val components: Map<String, Component>,
    override val slots: Map<String, List<Element>>
): LayoutContext