package gg.shaded.vuei.layout

import gg.shaded.vuei.Element
import gg.shaded.vuei.ItemFactory
import gg.shaded.vuei.Renderable
import gg.shaded.vuei.SimpleRenderable
import gg.shaded.vuei.util.ItemBuilder
import org.bukkit.Material
import org.bukkit.inventory.ItemFlag
import rx.Observable

class IconLayout(
    private val itemFactory: ItemFactory
): Layout {
    override fun allocate(context: LayoutContext): Observable<List<Renderable>> {
        val type = context.getBinding("type") as? Observable<String>
            ?: throw IllegalStateException("Icon has no type.")

        if(type.first().toBlocking().first() == "DIAMOND") {
            println("allocating")
        }

        return type.map { name ->
            val item = itemFactory.create(name, "", listOf())

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