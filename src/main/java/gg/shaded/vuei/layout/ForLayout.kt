package gg.shaded.vuei.layout

import gg.shaded.vuei.*
import io.reactivex.rxjava3.core.Observable

class ForLayout(
    private val layout: Layout
): Layout {
    override fun allocate(context: LayoutContext): Observable<List<Renderable>> {
        val loop = context.element.loop ?: return layout.allocate(context)

        return context.getBinding(loop.binding)?.observe()
            ?.distinctUntilChanged()
            ?.switchMap { bindings ->
                if((bindings as Iterable<Any>).toList().isEmpty()) {
                    return@switchMap Observable.just(listOf())
                }

                Observable.combineLatest(
                    bindings.map { binding ->
                        layout.allocate(
                            context.copy(
                                bindings = context.bindings.plus(mapOf(loop.variable to binding))
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