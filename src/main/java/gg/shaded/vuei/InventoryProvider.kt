package gg.shaded.vuei

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer
import org.bukkit.Bukkit
import org.bukkit.inventory.Inventory
import kotlin.math.max

interface InventoryProvider {
    fun getInventory(renderable: Renderable): Inventory
}

class CachedInventoryProvider: InventoryProvider {
    private val inventories = HashMap<String, Inventory>()

    override fun getInventory(renderable: Renderable): Inventory {
        val title = renderable.title?.run {
            PlainTextComponentSerializer.plainText().serialize(this)
        }

        return inventories.computeIfAbsent("${renderable.height}:$title") {
            Bukkit.createInventory(
                null, max(9, 9 * renderable.height),
                renderable.title ?: Component.translatable("Untitled")
            )
        }
    }

}