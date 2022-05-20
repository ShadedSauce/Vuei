package gg.shaded.vuei.layout

import gg.shaded.vuei.Renderable
import io.reactivex.rxjava3.core.Observable

class IfLayout(
    private val layout: Layout
): Layout {
    override fun allocate(context: LayoutContext): Observable<List<Renderable>> {
        val condition = context.getAttributeBinding("if")?.observe() as? Observable<Boolean>
            ?: Observable.just(true)

        println("resubbing")
        return condition.switchMap { visible ->
            println("if became: $visible")

            if(visible) layout.allocate(context)
            else Observable.just(ArrayList())
        }
    }
}