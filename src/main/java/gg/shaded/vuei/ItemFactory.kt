package gg.shaded.vuei

import gg.shaded.vuei.util.ItemBuilder
import org.bukkit.Material
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack

interface ItemFactory {
    fun create(
        type: String,
        name: net.kyori.adventure.text.Component?,
        description: List<net.kyori.adventure.text.Component>?
    ): ItemStack

    fun create(
        item: ItemStack,
        name: net.kyori.adventure.text.Component?,
        description: List<net.kyori.adventure.text.Component>?
    ): ItemStack
}

class ItemBuilderItemFactory: ItemFactory {
    override fun create(
        type: String,
        name: net.kyori.adventure.text.Component?,
        description: List<net.kyori.adventure.text.Component>?
    ): ItemStack {
        val material = Material.matchMaterial(type)
            ?: throw IllegalStateException("$type is not a Material.")

        return create(ItemStack(material), name, description)
    }

    override fun create(
        item: ItemStack,
        name: net.kyori.adventure.text.Component?,
        description: List<net.kyori.adventure.text.Component>?
    ): ItemStack {
        return ItemBuilder.modify(item)
            .flags(*ItemFlag.values())
            .also { builder ->
                if(name != null) {
                    builder.name(name)
                }

                if(description != null) {
                    builder.description(description)
                }
            }
            .build()
    }
}

class TestItemFactory: ItemFactory {
    override fun create(
        type: String,
        name: net.kyori.adventure.text.Component?,
        description: List<net.kyori.adventure.text.Component>?
    ): ItemStack {
        val material = Material.matchMaterial(type)
            ?: throw IllegalStateException("$type is not a Material.")

        return TestItemStack(material)
    }

    override fun create(
        item: ItemStack,
        name: net.kyori.adventure.text.Component?,
        description: List<net.kyori.adventure.text.Component>?
    ): ItemStack {
        return item
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