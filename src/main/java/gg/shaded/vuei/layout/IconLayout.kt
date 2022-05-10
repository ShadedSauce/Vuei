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
    override fun allocate(element: Element, parent: Renderable): Observable<Renderable> {
        val bindings = element.bindings as Map<String, Observable<String>>

        val type = bindings["type"]
            ?: throw IllegalStateException("Icon has no type.")

        println("is $type")

        return type.map { name ->
            val item = itemFactory.create(name, "", listOf())

            SimpleRenderable(
                x = 0,
                y = 0,
                width = 1,
                height = 1,
                element = element,
                item = item
            )
        }
    }
}