package gg.shaded.vuei.layout

import gg.shaded.vuei.ClickContext
import gg.shaded.vuei.Renderable
import gg.shaded.vuei.SimpleRenderable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject
import org.graalvm.polyglot.Value

class ClickableLayout(
    private val layout: Layout
): Layout {
    override fun allocate(context: LayoutContext): Observable<List<Renderable>> {
        println("allocating clickabnle")

        val onClick = context.getAttributeBinding("click")?.observe() as? Observable<Any>
            ?: return layout.allocate(context)

        println("has click $onClick")

        return Observable.combineLatest(
            onClick,
            layout.allocate(context),
        ) { callback, children ->
            children.map { child ->
                SimpleRenderable(
                    child.x,
                    child.y,
                    child.width,
                    child.height,
                    child.element,
                    child.children,
                    child.item,
                    child.title,
                ) { context ->
                    when(callback) {
                        is Function1<*, *> -> (callback as (ClickContext) -> Unit)(context)
                        is Value -> {
                            println("callback: $callback, ${callback.canExecute()}")
                            callback.executeVoid(context)
                        }
                        else -> throw IllegalStateException("Callback not callable.")
                    }
                }
            }
        }
    }
}