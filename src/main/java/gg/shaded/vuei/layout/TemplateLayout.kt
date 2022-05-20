package gg.shaded.vuei.layout

import gg.shaded.vuei.Renderable
import io.reactivex.rxjava3.core.Observable

class TemplateLayout: Layout {
    override fun allocate(context: LayoutContext): Observable<List<Renderable>> {
        throw UnsupportedOperationException()
    }
}