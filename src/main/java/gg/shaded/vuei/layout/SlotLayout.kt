package gg.shaded.vuei.layout

import gg.shaded.vuei.Renderable
import gg.shaded.vuei.allocate
import rx.Observable

class SlotLayout: Layout {
    override fun allocate(context: LayoutContext): Observable<List<Renderable>> {
        val name = context.element.values["name"] ?: "default"
        println("slot ${context.slots}")
        val templates = context.slots[name] ?: return Observable.just(ArrayList())

        return templates.allocate(context)
    }
}