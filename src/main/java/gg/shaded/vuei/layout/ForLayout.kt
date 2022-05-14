package gg.shaded.vuei.layout

import gg.shaded.vuei.*
import rx.Observable
import rx.subjects.ReplaySubject
import rx.subjects.Subject

class ForLayout(
    private val layout: Layout
): Layout {
    override fun allocate(context: LayoutContext): Observable<List<Renderable>> {
        val loop = context.element.loop ?: return layout.allocate(context)

        return context.getBinding(loop.binding)
            ?.switchMap { bindings ->
                Observable.combineLatest(
                    (bindings as Iterable<Any>).map { binding ->
                        layout.allocate(
                            SimpleLayoutContext(
                                context.element,
                                context.parent,
                                mapOf(loop.variable to Observable.just(binding)),
                                context.components,
                                context.slots
                            )
                        )
                    }
                ) { results ->
                    results.flatMap { it as List<Renderable> }
                }
            } ?: return layout.allocate(context)
    }
}

data class For(
    val binding: String,
    val variable: String,
)