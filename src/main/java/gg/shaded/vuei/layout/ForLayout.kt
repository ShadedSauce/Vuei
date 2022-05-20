package gg.shaded.vuei.layout

import gg.shaded.vuei.*
import io.reactivex.rxjava3.core.Observable

class ForLayout(
    private val layout: Layout
): Layout {
    override fun allocate(context: LayoutContext): Observable<List<Renderable>> {
        val loop = context.element.loop ?: return layout.allocate(context)

        println("loop: $loop")
        return context.getBinding(loop.binding)?.observe()
            ?.switchMap { bindings ->
                if((bindings as Iterable<Any>).toList().isEmpty()) {
                    return@switchMap Observable.just(listOf())
                }

                Observable.combineLatest(
                    bindings.map { binding ->
                        layout.allocate(
                            SimpleLayoutContext(
                                context.element,
                                context.parent,
                                context.bindings.plus(mapOf(loop.variable to binding)),
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