package gg.shaded.vuei

import gg.shaded.vuei.util.ItemBuilder
import org.bukkit.Material
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack

interface ItemFactory {
    fun create(type: String, name: String, description: List<String>): ItemStack
}

class ItemBuilderItemFactory: ItemFactory {
    override fun create(type: String, name: String, description: List<String>): ItemStack {
        val material = Material.matchMaterial(type)
            ?: throw IllegalStateException("$type is not a Material.")

        return ItemBuilder.of(material)
            .flags(*ItemFlag.values())
            .build()
    }
}