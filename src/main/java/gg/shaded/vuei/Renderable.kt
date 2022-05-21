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
    val onClick: ((ClickContext) -> Unit)?

    fun click(
        x: Int,
        y: Int,
        context: ClickContext,
    ): Boolean

    fun copy(
        x: Int? = null,
        y: Int? = null,
        width: Int? = null,
        height: Int? = null,
        element: Element? = null,
        children: List<Renderable>? = null,
        item: ItemStack? = null,
        title: String? = null,
        onClick: ((ClickContext) -> Unit)? = null
    ): Renderable

    fun toList(): List<Renderable> = listOf(this)
}

data class SimpleRenderable(
    override val x: Int = 0,
    override val y: Int = 0,
    override val width: Int,
    override val height: Int,
    override val element: Element,
    override val children: List<Renderable> = ArrayList(),
    override val item: ItemStack? = null,
    override val title: String? = null,
    override val onClick: ((ClickContext) -> Unit)? = null,
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
            onClick?.invoke(context)
            return true
        }

        return false
    }

    override fun copy(
        x: Int?,
        y: Int?,
        width: Int?,
        height: Int?,
        element: Element?,
        children: List<Renderable>?,
        item: ItemStack?,
        title: String?,
        onClick: ((ClickContext) -> Unit)?
    ) = SimpleRenderable(
        x ?: this.x,
        y ?: this.y,
        width ?: this.width,
        height ?: this.height,
        element ?: this.element,
        children ?: this.children,
        item ?: this.item,
        title ?: this.title,
        onClick ?: this.onClick
    )
}