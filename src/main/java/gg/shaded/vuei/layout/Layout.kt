package gg.shaded.vuei.layout

import gg.shaded.vuei.Element
import gg.shaded.vuei.Renderable
import rx.Observable

interface Layout {
    fun allocate(element: Element, parent: Renderable): Observable<Renderable>
}