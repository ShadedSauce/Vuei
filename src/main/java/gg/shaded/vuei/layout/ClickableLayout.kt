package gg.shaded.vuei.layout

import gg.shaded.vuei.*
import io.reactivex.rxjava3.core.Observable

class ClickableLayout(
    private val layout: Layout
): Layout {
    override fun allocate(context: LayoutContext): Observable<List<Renderable>> {
        val onClick = context.getAttributeBinding("click")?.observe() as? Observable<Any>
            ?: return layout.allocate(context)

        return Observable.combineLatest(
            onClick,
            layout.allocate(context),
        ) { callback, children ->
            children.map { child ->
                child.copy { context ->
                    callback.invoke(context)
                }
            }
        }
    }
}