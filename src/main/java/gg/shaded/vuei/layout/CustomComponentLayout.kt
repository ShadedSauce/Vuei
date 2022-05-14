package gg.shaded.vuei.layout

import gg.shaded.vuei.Renderable
import rx.Observable
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
            it.values.containsKey("slot")
        }
            .associate { it.values["slot"]!! to it.children }

        return element.layout.allocate(
            SimpleLayoutContext(
                element,
                context.parent,
                component.setup(),
                component.imports,
                namedSlots.plus("default" to defaultSlot)
            )
        )
    }
}