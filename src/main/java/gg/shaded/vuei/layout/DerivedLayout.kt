package gg.shaded.vuei.layout

import gg.shaded.vuei.Element
import gg.shaded.vuei.I18n
import gg.shaded.vuei.ItemFactory
import gg.shaded.vuei.Renderable
import gg.shaded.vuei.layout.ColumnCenterLayout
import io.reactivex.rxjava3.core.Observable

class DerivedLayout(
    private val layout: String,
    itemFactory: ItemFactory,
    i18n: I18n
): Layout {
    private val layouts = mapOf(
        "window" to DocumentLayout(),
        "span" to SpanLayout(),
        "row-s" to RowStartLayout(),
        "row-sb" to RowSpaceBetweenLayout(),
        "row-c" to RowCenterLayout(),
        "col-s" to ColumnStartLayout(),
        "col-c" to ColumnCenterLayout(),
        "icon" to IconLayout(itemFactory, i18n),
        "fill" to FillLayout(itemFactory, i18n),
        "padding" to PaddingLayout(),
        "slot" to SlotLayout(),
        "template" to TemplateLayout(),
        "wrap" to WrapLayout(),
        "sized-box" to SizedBoxLayout(),
    )

    override fun allocate(context: LayoutContext): Observable<List<Renderable>> {
        val layout = layouts[this.layout] ?: CustomComponentLayout(this.layout)

        return layout.allocate(context)
    }

    override fun toString(): String {
        return "DerivedLayout(name=$layout)"
    }
}