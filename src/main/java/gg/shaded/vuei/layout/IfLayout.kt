package gg.shaded.vuei.layout

import gg.shaded.vuei.Renderable
import gg.shaded.vuei.observe
import io.reactivex.rxjava3.core.Observable

class IfLayout(
    private val layout: Layout
): Layout {
    override fun allocate(context: LayoutContext): Observable<List<Renderable>> {
        val condition = context.getAttributeBinding("if")?.observe() as? Observable<Boolean>
            ?: Observable.just(true)

        return Observable.combineLatest(
            layout.allocate(context),
            condition
        ) { children, visible ->
            if(visible) children
            else emptyList()
        }
    }
}