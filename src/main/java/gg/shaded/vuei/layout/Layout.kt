package gg.shaded.vuei.layout

import gg.shaded.vuei.Component
import gg.shaded.vuei.Element
import gg.shaded.vuei.Renderable
import gg.shaded.vuei.unwrap
import io.reactivex.rxjava3.core.Observable
import org.graalvm.polyglot.Context
import org.graalvm.polyglot.HostAccess
import org.graalvm.polyglot.Value
import java.util.*


interface Layout {
    fun allocate(context: LayoutContext): Observable<List<Renderable>>
}

interface LayoutContext {
    val superContext: LayoutContext?

    val element: Element

    val parent: Renderable

    val bindings: Map<String, Any>

    val components: Map<String, Component>

    val slots: Map<String, List<Element>>

    fun copy(
        superContext: LayoutContext? = null,
        element: Element? = null,
        parent: Renderable? = null,
        bindings: Map<String, Any>? = null,
        components: Map<String, Component>? = null,
        slots: Map<String, List<Element>>? = null,
    ): LayoutContext

    fun getAttributeBinding(key: String): Any? {
        val binding = element.bindings[key]

        if(binding != null) {
            return getBinding(binding) ?:
                throw IllegalStateException("No binding found for $key=$binding")
        }

        return element.values[key]?.let { Observable.just(it) }
    }

    fun getBinding(binding: String): Any? {
        // TODO: Dispose context (maybe use one context per LayoutContext?)
        val context = Context.newBuilder("js")
            .allowAllAccess(true)
            .allowHostAccess(
                HostAccess.newBuilder(HostAccess.ALL)
                    .targetTypeMapping(
                        Value::class.java,
                        Any::class.java,
                        { v -> v.hasArrayElements() }
                    ) { v -> v.`as`(List::class.java) }
                    .build()
            )
            .build()

        val jsBindings = context.getBindings("js")

        this.bindings.forEach { (key, binding) ->
            jsBindings.putMember(key, binding)
        }

        val result = try {
            context.eval("js", binding)
        } catch(e: Exception) {
            throw RuntimeException("Error in $binding", e)
        }

        return result.unwrap()
    }
}

class SimpleLayoutContext(
    override val superContext: LayoutContext?,
    override val element: Element,
    override val parent: Renderable,
    override val bindings: Map<String, Any>,
    override val components: Map<String, Component>,
    override val slots: Map<String, List<Element>>
): LayoutContext {
    override fun copy(
        superContext: LayoutContext?,
        element: Element?,
        parent: Renderable?,
        bindings: Map<String, Any>?,
        components: Map<String, Component>?,
        slots: Map<String, List<Element>>?
    ): LayoutContext {
        return SimpleLayoutContext(
            superContext ?: this.superContext,
            element ?: this.element,
            parent ?: this.parent,
            bindings ?: this.bindings,
            components ?: this.components,
            slots ?: this.slots
        )
    }
}