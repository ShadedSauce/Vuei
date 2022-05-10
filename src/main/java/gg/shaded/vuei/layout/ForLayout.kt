package gg.shaded.vuei.layout

import gg.shaded.vuei.*
import rx.Observable

class ForLayout(
    private val layout: Layout
): Layout {
    override fun allocate(element: Element, parent: Renderable): Observable<Renderable> {
        if(element.children.isEmpty()) {
            return layout.allocate(element, parent)
        }

        return Observable.combineLatest(
            element.children.map { child ->
                (child.bindings["for"] as? Observable<Iterable<out Any>>)
                    ?.map { bindings ->
                        bindings.map { binding ->
                            SimpleElement(
                                child.children,
                                child.layout,
                                child.bindings.mapValues { entry ->
                                    return@mapValues if(entry.value == child.loop?.reference)
                                        Observable.just(binding)
                                        else entry.value
                                },
                                child.loop
                            )
                        }
                    }
                    ?: Observable.just(listOf(child))
            }
        ) { results ->
            SimpleElement(
                children = results.map { it as List<Element> }.flatten(),
                layout,
                element.bindings,
                element.loop
            )
        }
            .flatMap { self -> layout.allocate(self, parent) }
    }
}

data class For(
    val binding: String,
    val variable: String,
    val reference: Observable<out Any>
)