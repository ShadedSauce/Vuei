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

        var childContext: LayoutContext? = null

        return condition.distinctUntilChanged().switchMap { visible ->
            if(visible) layout.allocate(
                context.copy().also { childContext = it }
            )
            else {
                childContext?.close()
                Observable.just(emptyList())
            }
        }
    }

    override fun toString(): String {
        return "IfLayout(layout=$layout)"
    }
}