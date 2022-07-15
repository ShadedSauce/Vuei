package gg.shaded.vuei.layout

import gg.shaded.vuei.*
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject

class ClickableLayout(
    private val layout: Layout
): Layout {
    override fun allocate(context: LayoutContext): Observable<List<Renderable>> {
        val onClick = context.getAttributeBinding("emit:click")?.observe() as? Observable<Any>
            ?: return layout.allocate(context)

        return Observable.combineLatest(
            onClick,
            layout.allocate(context),
        ) { callback, children ->
            children.map { child ->
                child.copy { click ->
                    try {
                        callback.invoke(click)
                    } catch (t: Throwable) {
                        throw RuntimeException(
                            "An error occurred while processing click: " +
                                "${context.element.bindings["emit:click"]}",
                            t
                        )
                    }
                }
            }
        }
    }

    override fun toString(): String {
        return "IfLayout(layout=$layout)"
    }
}