package gg.shaded.vuei.layout

import gg.shaded.vuei.Renderable
import rx.Observable

class IfLayout(
    private val layout: Layout
): Layout {
    override fun allocate(context: LayoutContext): Observable<List<Renderable>> {
        val condition = context.getBinding("if") as? Observable<Boolean>
            ?: Observable.just(true)

        println("resubbing")
        return condition.switchMap { visible ->
            println("if became: $visible")

            if(visible) layout.allocate(context)
            else Observable.just(ArrayList())
        }
    }
}