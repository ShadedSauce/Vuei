package gg.shaded.vuei.layout

import gg.shaded.vuei.*
import io.reactivex.rxjava3.core.Observable
import org.graalvm.polyglot.Engine
import org.graalvm.polyglot.Value
import kotlin.collections.HashSet


interface Layout {
    fun allocate(context: LayoutContext): Observable<List<Renderable>>
}

interface LayoutContext: AutoCloseable {
    val superContext: LayoutContext?

    val engine: Engine

    val element: Element

    val parent: Renderable

    val bindings: Map<String, Any?>

    val components: Map<String, Component>

    val slots: Map<String, List<Element>>

    fun copy(
        superContext: LayoutContext? = null,
        engine: Engine? = null,
        element: Element? = null,
        parent: Renderable? = null,
        bindings: Map<String, Any?>? = null,
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

    fun getBinding(binding: String): Any?
}

class SimpleLayoutContext(
    override val superContext: LayoutContext?,
    override val engine: Engine,
    override val element: Element,
    override val parent: Renderable,
    override val bindings: Map<String, Any?>,
    override val components: Map<String, Component>,
    override val slots: Map<String, List<Element>>
): LayoutContext {
    private val closeables = HashSet<AutoCloseable>()
    private val children = HashSet<LayoutContext>()

    override fun close() {
        closeables.plus(children)
            .forEach(AutoCloseable::close)
    }

    override fun copy(
        superContext: LayoutContext?,
        engine: Engine?,
        element: Element?,
        parent: Renderable?,
        bindings: Map<String, Any?>?,
        components: Map<String, Component>?,
        slots: Map<String, List<Element>>?
    ): LayoutContext {
        return SimpleLayoutContext(
            superContext ?: this.superContext,
            engine ?: this.engine,
            element ?: this.element,
            parent ?: this.parent,
            bindings ?: this.bindings,
            components ?: this.components,
            slots ?: this.slots
        ).also { children.add(it) }
    }

    override fun getBinding(binding: String): Any? {
        println("creating context")
        val context = createJavaScriptContext(Engine.create())
        println("created context")
        val jsBindings = context.getBindings("js")

        this.bindings.forEach { (key, binding) ->
            jsBindings.putMember(key, binding)
        }

        val result = try {
            println("eval: $binding")
            context.eval("js", binding).unwrap()
        } catch(e: Exception) {
            throw RuntimeException("Error in $binding", e)
        }

        if(result is Observable<*>) {
            return result.doOnDispose { context.close() }
        }

        if(result !is Value || !result.canExecute()) {
            context.close()
        }
        else {
            closeables.add(context)
        }

        return result
    }
}