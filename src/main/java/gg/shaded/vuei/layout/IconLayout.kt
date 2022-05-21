package gg.shaded.vuei.layout

import gg.shaded.vuei.ItemFactory
import gg.shaded.vuei.Renderable
import gg.shaded.vuei.SimpleRenderable
import gg.shaded.vuei.observe
import io.reactivex.rxjava3.core.Observable
import org.bukkit.inventory.ItemStack

class IconLayout(
    private val itemFactory: ItemFactory
): Layout {
    override fun allocate(context: LayoutContext): Observable<List<Renderable>> {
        val type = context.getAttributeBinding("type")?.observe() as? Observable<Any>
            ?: throw IllegalStateException("Icon has no type.")

        val name = context.getAttributeBinding("name")?.observe() as? Observable<String>
            ?: Observable.just("")

        val description = context.getAttributeBinding("description")?.observe() as? Observable<List<String>>
            ?: Observable.just(ArrayList())

        return Observable.combineLatest(
            type, name, description
        )
        { t, n, d ->
            val item = when(t) {
                is ItemStack -> itemFactory.create(
                    t,
                    n.takeIf { it.isNotEmpty() },
                    d.takeIf { it.isNotEmpty() }
                )
                is String -> itemFactory.create(
                    t,
                    n.takeIf { it.isNotEmpty() },
                    d.takeIf { it.isNotEmpty() }
                )
                else -> throw IllegalStateException("Icon type must be Material or ItemStack.")
            }

            SimpleRenderable(
                x = 0,
                y = 0,
                width = 1,
                height = 1,
                element = context.element,
                item = item
            ).toList()
        }
    }
}