package gg.shaded.vuei

import org.bukkit.Bukkit
import org.bukkit.inventory.Inventory
import kotlin.math.max

interface InventoryProvider {
    fun getInventory(renderable: Renderable): Inventory
}

class CachedInventoryProvider: InventoryProvider {
    private val inventories = HashMap<String, Inventory>()

    override fun getInventory(renderable: Renderable) =
        inventories.computeIfAbsent("${renderable.height}:${renderable.title}") {
            Bukkit.createInventory(
                null, max(9, 9 * renderable.height), renderable.title ?: "Window"
            )
        }
}