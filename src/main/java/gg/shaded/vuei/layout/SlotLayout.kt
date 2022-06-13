package gg.shaded.vuei.layout

import gg.shaded.vuei.Renderable
import gg.shaded.vuei.allocate
import io.reactivex.rxjava3.core.Observable

class SlotLayout: Layout {
    override fun allocate(context: LayoutContext): Observable<List<Renderable>> {
        val name = context.element.values["name"] ?: "default"
        println("found slot: ${context.slots[name]}")
        val templates = context.slots[name] ?: return Observable.just(ArrayList())
        val superContext = context.superContext ?: context

        return templates.allocate(
            context.copy(
                bindings = superContext.bindings
            )
        )
    }
}