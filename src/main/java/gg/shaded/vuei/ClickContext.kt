package gg.shaded.vuei

import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

data class ClickContext(
    val relX: Int,
    val relY: Int,
    val absX: Int,
    val absY: Int,
    val player: Player,
    val item: ItemStack?,
    val inventory: Inventory,
    val rawSlot: Int,
    val renderable: Renderable
) {
    val isOwnInventory: Boolean
        get() = rawSlot >= inventory.size
}