package gg.shaded.vuei.layout

import gg.shaded.vuei.Element
import gg.shaded.vuei.ItemFactory
import gg.shaded.vuei.Renderable
import gg.shaded.vuei.layout.ColumnCenterLayout
import rx.Observable

class DerivedLayout(
    private val layout: String,
    itemFactory: ItemFactory,
): Layout {
    private val layouts = mapOf(
        "window" to DocumentLayout(),
        "row-sb" to RowSpaceBetweenLayout(),
        "row-c" to RowCenterLayout(),
        "col-c" to ColumnCenterLayout(),
        "icon" to IconLayout(itemFactory),
        "padding" to PaddingLayout()
    )

    override fun allocate(element: Element, parent: Renderable): Observable<Renderable> {
        val layout =
            layouts[this.layout] ?: throw IllegalArgumentException("${this.layout} has no layout.")

        return layout.allocate(element, parent)
    }
}