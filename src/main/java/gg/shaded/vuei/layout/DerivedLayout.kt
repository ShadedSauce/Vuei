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
        "span" to SpanLayout(),
        "row-sb" to RowSpaceBetweenLayout(),
        "row-c" to RowCenterLayout(),
        "col-c" to ColumnCenterLayout(),
        "col-s" to ColumnStartLayout(),
        "icon" to IconLayout(itemFactory),
        "padding" to PaddingLayout(),
        "slot" to SlotLayout(),
        "template" to TemplateLayout()
    )

    override fun allocate(context: LayoutContext): Observable<List<Renderable>> {
        val layout =
            layouts[this.layout] ?: CustomComponentLayout(this.layout)

        return layout.allocate(context)
    }
}