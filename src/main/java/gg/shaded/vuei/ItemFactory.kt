package gg.shaded.vuei

import gg.shaded.vuei.util.ItemBuilder
import org.bukkit.Material
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack

interface ItemFactory {
    fun create(type: String, name: String?, description: List<String>?): ItemStack
}

class ItemBuilderItemFactory: ItemFactory {
    override fun create(type: String, name: String?, description: List<String>?): ItemStack {
        val material = Material.matchMaterial(type)
            ?: throw IllegalStateException("$type is not a Material.")

        return ItemBuilder.of(material)
            .flags(*ItemFlag.values())
            .also { builder ->
                if(name != null) {
                    builder.name(name)
                }

                if(description != null) {
                    builder.lore(description)
                }
            }
            .build()
    }
}

class TestItemFactory: ItemFactory {
    override fun create(type: String, name: String?, description: List<String>?): ItemStack {
        val material = Material.matchMaterial(type)
            ?: throw IllegalStateException("$type is not a Material.")

        return TestItemStack(material)
    }
}

class TestItemStack(private val material: Material): ItemStack(material) {
    override fun toString(): String {
        return "{type=$material}"
    }

    override fun isSimilar(stack: ItemStack?): Boolean {
        return false
    }
}