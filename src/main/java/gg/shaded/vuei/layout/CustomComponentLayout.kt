package gg.shaded.vuei.layout

import gg.shaded.vuei.OptionalProp
import gg.shaded.vuei.Renderable
import gg.shaded.vuei.SimpleSetupContext
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject
import java.lang.IllegalStateException

class CustomComponentLayout(
    private val name: String
): Layout {
    override fun allocate(context: LayoutContext): Observable<List<Renderable>> {
        val component = context.components[name]
            ?: throw IllegalStateException("$name has no layout.")
        val elements = component.template.elements

        val defaultSlot = context.element.children.filterNot {
            it.values.containsKey("slot")
        }

        val namedSlots = context.element.children.filter {
            it.values["slot"] as? String != null
        }
            .associate { it.values["slot"] as String to it.children }

        val props = component.props
            .plus(
                context.element.bindings.keys.filter { it.startsWith("emit:") }
                    .map { OptionalProp(it) }
            )
            .associate { prop ->
                val binding = context.getAttributeBinding(prop.name)

                val validated = try {
                    prop.validate(
                        context.getAttributeBinding(prop.name)
                    ) ?: binding
                } catch (t: Throwable) {
                    throw RuntimeException("Validation failed for $component", t)
                }

                prop.name to validated
            }
            .plus(
                context.element.values.mapValues { Observable.just(it.value) }
            )

        return component.setupWithQueue(SimpleSetupContext(props, PublishSubject.create()))
            .map { bindings ->
                elements.map { element ->
                    context.copy(
                        superContext = context,
                        element = element,
                        bindings = bindings.plus(props),
                        components = component.imports,
                        slots = namedSlots.let {
                            if(defaultSlot.isNotEmpty()) it.plus("default" to defaultSlot)
                            else it
                        }
                    ).let(element.layout::allocate)
                }
            }
            .switchMap { allocations ->
                Observable.combineLatest(allocations) { renderables ->
                    renderables
                        .map { it as List<Renderable> }
                        .flatten()
                }
            }
    }
}