package gg.shaded.vuei.layout

import gg.shaded.vuei.Renderable
import gg.shaded.vuei.SimpleSetupContext
import io.reactivex.rxjava3.core.Observable
import java.lang.IllegalStateException

class CustomComponentLayout(
    private val name: String
): Layout {
    override fun allocate(context: LayoutContext): Observable<List<Renderable>> {
        val component = context.components[name]
            ?: throw IllegalStateException("$name has no layout.")
        val element = component.template.element

        val defaultSlot = context.element.children.filterNot {
            it.values.containsKey("slot")
        }

        val namedSlots = context.element.children.filter {
            it.values["slot"] as? String != null
        }
            .associate { it.values["slot"] as String to it.children }

        val props = context.element.bindings
            .mapValues { entry -> context.getAttributeBinding(entry.key) }
            .filterValues { it != null }
            .mapValues { it.value!! }
            .plus(
                context.element.values.mapValues { Observable.just(it.value) }
            )

        return component.setup(SimpleSetupContext(props))
            .map { bindings ->
                context.copy(
                    superContext = context,
                    element = element,
                    bindings = bindings.plus(props),
                    components = component.imports,
                    slots = namedSlots.plus("default" to defaultSlot)
                )
            }
            .switchMap { ctx -> element.layout.allocate(ctx) }
    }
}