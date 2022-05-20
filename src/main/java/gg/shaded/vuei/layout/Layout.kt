package gg.shaded.vuei.layout

import gg.shaded.vuei.Component
import gg.shaded.vuei.Element
import gg.shaded.vuei.Renderable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject
import io.reactivex.rxjava3.subjects.Subject
import org.graalvm.polyglot.Context

interface Layout {
    fun allocate(context: LayoutContext): Observable<List<Renderable>>
}

interface LayoutContext {
    val element: Element

    val parent: Renderable

    val bindings: Map<String, Any>

    val components: Map<String, Component>

    val slots: Map<String, List<Element>>

    fun getAttributeBinding(key: String): Any? {
        val binding = element.bindings[key]

        if(binding != null) {
            return getBinding(binding) ?:
                throw IllegalStateException("No binding found for $key=$binding")
        }

        return element.values[key]?.let { Observable.just(it) }
    }

    fun getBinding(binding: String): Any? {
        // TODO: Dispose context
        val context = Context.newBuilder("js")
            .allowAllAccess(true)
            .build()

        val jsBindings = context.getBindings("js")

        this.bindings.forEach { (key, binding) ->
            jsBindings.putMember(key, binding)
        }

        val result = context.eval("js", binding)

        println("result: $binding, $result")

        if(result.isNull) {
            return null
        }

        if(result.isHostObject) {
            return result.asHostObject<Any>()
        }

        if(result.isString) {
            return result.asString()
        }

        return result
    }
}

class SimpleLayoutContext(
    override val element: Element,
    override val parent: Renderable,
    override val bindings: Map<String, Any>,
    override val components: Map<String, Component>,
    override val slots: Map<String, List<Element>>
): LayoutContext

fun Any.observe() = if(this is Observable<*>) this else Observable.just(this)