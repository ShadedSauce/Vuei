package gg.shaded.vuei.layout

import gg.shaded.vuei.I18n
import gg.shaded.vuei.ItemFactory
import gg.shaded.vuei.Renderable
import io.reactivex.rxjava3.core.Observable

class FillLayout(
    private val itemFactory: ItemFactory,
    private val i18n: I18n
): Layout {
    override fun allocate(context: LayoutContext): Observable<List<Renderable>> {
        return IconLayout(itemFactory, i18n)
            .allocate(context)
            .map { it.first() }
            .map { icon ->
                (0 until context.parent.width)
                    .flatMap { x ->
                        (0 until context.parent.height).map { y ->
                            icon.copy(x = x, y = y)
                        }
                    }
            }
    }
}