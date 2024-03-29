package gg.shaded.vuei.layout

import gg.shaded.vuei.*
import gg.shaded.vuei.theme.WindowTheme
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.core.Single
import org.graalvm.polyglot.Context
import kotlin.collections.HashSet


interface Layout {
    fun allocate(context: LayoutContext): Observable<List<Renderable>>
}

interface LayoutContext: AutoCloseable {
    val superContext: LayoutContext?

    val jsContext: Context

    val element: Element

    val parent: Renderable

    val bindings: Map<String, Any?>

    val components: Map<String, Component>

    val slots: Map<String, List<Element>>

    val uiScheduler: Scheduler

    val backgroundScheduler: Scheduler

    val errorHandler: ErrorHandler

    val theme: WindowTheme

    fun copy(
        superContext: LayoutContext? = null,
        jsContext: Context? = null,
        element: Element? = null,
        parent: Renderable? = null,
        bindings: Map<String, Any?>? = null,
        components: Map<String, Component>? = null,
        slots: Map<String, List<Element>>? = null,
        uiScheduler: Scheduler? = null,
        backgroundScheduler: Scheduler? = null,
        errorHandler: ErrorHandler? = null,
        theme: WindowTheme? = null
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
    override val jsContext: Context,
    override val element: Element,
    override val parent: Renderable,
    override val bindings: Map<String, Any?>,
    override val components: Map<String, Component>,
    override val slots: Map<String, List<Element>>,
    override val uiScheduler: Scheduler,
    override val backgroundScheduler: Scheduler,
    override val errorHandler: ErrorHandler,
    override val theme: WindowTheme
): LayoutContext {
    private val closeables = HashSet<AutoCloseable>()
    private val children = HashSet<LayoutContext>()

    override fun close() {
        closeables.plus(children)
            .forEach(AutoCloseable::close)
    }

    override fun copy(
        superContext: LayoutContext?,
        jsContext: Context?,
        element: Element?,
        parent: Renderable?,
        bindings: Map<String, Any?>?,
        components: Map<String, Component>?,
        slots: Map<String, List<Element>>?,
        uiScheduler: Scheduler?,
        backgroundScheduler: Scheduler?,
        errorHandler: ErrorHandler?,
        theme: WindowTheme?
    ): LayoutContext {
        return SimpleLayoutContext(
            superContext ?: this.superContext,
            jsContext ?: this.jsContext,
            element ?: this.element,
            parent ?: this.parent,
            bindings ?: this.bindings,
            components ?: this.components,
            slots ?: this.slots,
            uiScheduler ?: this.uiScheduler,
            backgroundScheduler ?: this.backgroundScheduler,
            errorHandler ?: this.errorHandler,
            theme ?: this.theme
        ).also { children.add(it) }
    }

    override fun getBinding(binding: String): Any? {
        // TODO: Should probably have an interface for executing scripts
        val result = try {
            val bindings = this.bindings.filterKeys { !it.contains(":") }
                .plus("theme" to theme)

            // Graal doesn't initialze it for some reason.
            Class.forName(
                "net.kyori.adventure.text.Component",
                true,
                Thread.currentThread().contextClassLoader
            )

            val callable = jsContext.eval("js", """
                var Component = Java.type('net.kyori.adventure.text.Component');
                
                (${bindings.keys.joinToString()}) => $binding
            """)

            callable.execute(*bindings.values.toTypedArray()).unwrap()
        } catch(e: Exception) {
            throw RuntimeException("Error in $binding", e)
        }

        return when (result) {
            is Observable<*> -> result.observeOn(uiScheduler)
            is Maybe<*> -> result.observeOn(uiScheduler)
            is Single<*> -> result.observeOn(uiScheduler)
            is Completable -> result.observeOn(uiScheduler)
            else -> result
        }
    }
}