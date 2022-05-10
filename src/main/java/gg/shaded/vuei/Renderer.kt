package gg.shaded.vuei

import org.bukkit.inventory.Inventory

interface Renderer {
    fun render(renderable: Renderable)
}

class InventoryRenderer(
    private val inventoryProvider: InventoryProvider
): Renderer {
    override fun render(renderable: Renderable) {
        val inventory = inventoryProvider.getInventory(renderable)
        val affectedSlots = render(inventory, renderable, 0, 0)

        for(slot in 0 until inventory.size) {
            if(!affectedSlots.contains(slot) && inventory.getItem(slot) != null) {
                inventory.setItem(slot, null)
            }
        }
    }

    private fun render(inventory: Inventory, renderable: Renderable, x: Int, y: Int): Set<Int> {
        val affectedSlots = HashSet<Int>()

        for(relX in 0 until renderable.width) {
            for(relY in 0 until renderable.height) {
                val absX = x + relX + renderable.x
                val absY = y + relY + renderable.y
                val slot = absY * 9 + absX

                if(renderable.item != null) {
                    if(inventory.getItem(slot)
                            ?.isSimilar(renderable.item) != true
                    ) {
                        inventory.setItem(slot, renderable.item)
                    }

                    affectedSlots.add(slot)
                }
            }
        }

        val childX = x + renderable.x
        val childY = y + renderable.y

        affectedSlots.addAll(renderable.children.flatMap {
            render(inventory, it, childX, childY)
        })

        return affectedSlots
    }
}