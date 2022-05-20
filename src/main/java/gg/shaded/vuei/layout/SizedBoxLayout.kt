package gg.shaded.vuei.layout

import gg.shaded.vuei.Renderable
import gg.shaded.vuei.SimpleRenderable
import gg.shaded.vuei.allocate
import io.reactivex.rxjava3.core.Observable

class SizedBoxLayout: Layout {
    override fun allocate(context: LayoutContext): Observable<List<Renderable>> {
        val width = context.getAttributeBinding("w")?.observe() as? Observable<String>
            ?: throw IllegalStateException("SizedBox requires width attribute.")
        val height = context.getAttributeBinding("h")?.observe() as? Observable<String>
            ?: throw IllegalStateException("SizedBox requires height attribute.")

        return Observable.combineLatest(
            width,
            height
        ) { w, h ->
            val bounds = SimpleRenderable(
                width = w.toInt(),
                height = h.toInt(),
                element = context.element,
            )

            context.element.children.allocate(
                SimpleLayoutContext(
                    context.element,
                    bounds,
                    context.bindings,
                    context.components,
                    context.slots
                )
            )
                .map { children ->
                    SimpleRenderable(
                        width = bounds.width,
                        height = bounds.height,
                        element = context.element,
                        children = children
                    ).toList()
                }
        }
            .flatMap { it }
    }
}