package gg.shaded.vuei

import gg.shaded.vuei.layout.SimpleLayoutContext
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.Cancellable
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryDragEvent
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryView
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.Plugin
import org.bukkit.plugin.PluginManager
import rx.Scheduler
import rx.Subscription
import rx.schedulers.Schedulers
import java.util.concurrent.TimeUnit

open class ComponentWindow(
    private val plugin: Plugin,
    pluginManager: PluginManager,
    private val inventoryProvider: InventoryProvider,
    private val renderer: Renderer,
    private val scheduler: Scheduler = Schedulers.from { r -> Bukkit.getScheduler().runTask(plugin, r) },
    private val root: Component
): Window, Listener {
    private var document: Element = root.template.element

    protected open var renderable: Renderable? = null
        set(value) {
            field = value

            if(value != null) {
                renderer.render(value)

                ignoreClose = true
                viewers.forEach { it.openInventory(inventory) }
                ignoreClose = false
            }
        }

    private val inventory: Inventory
        get() {
            val layout = this.renderable ?: throw IllegalStateException("Layout is not defined.")

            return inventoryProvider.getInventory(layout)
        }

    private val viewers = ArrayList<Player>()

    private var subscription: Subscription? = null
    private var ignoreClose: Boolean = false

    init {
        pluginManager.registerEvents(this, plugin)
    }

    override fun show(player: Player) {
        viewers.add(player)

        if(renderable == null) {
            start()
        }
        else {
            player.openInventory(this.inventory)
        }
    }

    private fun start() {
        subscription = document.layout.allocate(
            SimpleLayoutContext(
                document,
                SimpleRenderable(
                    width = 9,
                    height = 6,
                    element = document
                ),
                root.setup(),
                root.imports,
                slots = HashMap()
            )
        )
            .debounce(100, TimeUnit.MILLISECONDS) // 1 tick
            .map { it.first() }
            .observeOn(scheduler)
            .subscribe { renderable -> this.renderable = renderable }
    }

    private fun triggerClick(
        player: Player,
        rawSlot: Int,
        clickedInventory: Inventory?,
        currentItem: ItemStack?,
        view: InventoryView,
        isShiftClick: Boolean,
        cancellable: Cancellable
    ) {
        var slot = rawSlot
        val inventory = this.inventory
        val layout = this.renderable ?: return

        if(slot < 0) {
            return
        }

        if(view.topInventory != inventory) {
            return
        }

        if(clickedInventory != inventory) {
            if(isShiftClick) {
                slot = inventory.first(currentItem ?: ItemStack(Material.AIR))
                    .let { if(it == -1) inventory.firstEmpty() else it }
            } else {
                return
            }
        }

        val x = slot % 9
        val y = slot / 9

        val context = ClickContext(
            relX = x,
            relY = y,
            absX = x,
            absY = y,
            player = player,
            item = currentItem,
            inventory = inventory,
            rawSlot = slot,
            renderable = layout
        )

        cancellable.isCancelled = true

        layout.click(x, y, context)
    }

    private fun dispose() {
        subscription?.unsubscribe()
    }

    @EventHandler
    fun onClick(event: InventoryClickEvent) {
        triggerClick(
            player = event.whoClicked as Player,
            rawSlot = event.rawSlot,
            clickedInventory = event.clickedInventory,
            currentItem = event.currentItem,
            view = event.view,
            isShiftClick = event.isShiftClick,
            cancellable = event
        )
    }

    @EventHandler
    fun onDrag(event: InventoryDragEvent) {
        val inventory = this.inventory

        event.newItems.forEach { (rawSlot, item) ->
            val clicked = if(rawSlot >= inventory.size) event.view.bottomInventory
            else inventory

            triggerClick(
                player = event.whoClicked as Player,
                rawSlot = rawSlot,
                clickedInventory = clicked,
                currentItem = item,
                view = event.view,
                isShiftClick = false,
                cancellable = event
            )
        }
    }

    @EventHandler
    fun onClose(event: InventoryCloseEvent) {
        if(event.inventory !== inventory || ignoreClose) {
            return
        }

        // 0 after close
        if(event.viewers.size == 1) {
            dispose()
        }

        viewers.remove(event.player)
    }
}