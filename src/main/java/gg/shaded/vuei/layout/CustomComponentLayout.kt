package gg.shaded.vuei.layout

import gg.shaded.vuei.Renderable
import gg.shaded.vuei.SimpleSetupContext
import gg.shaded.vuei.observe
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject
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

        val props = component.props
            .associate { prop ->
                val binding = context.getAttributeBinding(prop.name)

                val validated = prop.validate(
                    context.getAttributeBinding(prop.name)
                ) ?: binding

                prop.name to validated
            }
            .plus(
                context.element.values.mapValues { Observable.just(it.value) }
            )

        return component.setupWithQueue(SimpleSetupContext(props, PublishSubject.create()))
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