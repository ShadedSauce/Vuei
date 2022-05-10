package gg.shaded.vuei

import org.bukkit.inventory.ItemStack

interface Renderable {
    val x: Int
    val y: Int
    val width: Int
    val height: Int
    val element: Element
    val children: List<Renderable>
    val item: ItemStack?
    val title: String?

    fun click(
        x: Int,
        y: Int,
        context: ClickContext,
    ): Boolean
}

data class SimpleRenderable(
    override val x: Int = 0,
    override val y: Int = 0,
    override val width: Int,
    override val height: Int,
    override val element: Element,
    override val children: List<Renderable> = ArrayList(),
    override val item: ItemStack? = null,
    override val title: String? = null
): Renderable {
    override fun click(
        x: Int,
        y: Int,
        context: ClickContext,
    ): Boolean {
        val consumed = children.filter {
            x >= it.x && x < it.x + it.width
                && y >= it.y && y < it.y + it.height
        }
            .any {
                val relX = x - it.x
                val relY = y - it.y

                it.click(
                    relX,
                    relY,
                    context.copy(
                        renderable = it,
                        relX = relX,
                        relY = relY
                    ),
                )
            }

        if(!consumed) {
            element.onClick(context)
            return true
        }

        return false
    }
}