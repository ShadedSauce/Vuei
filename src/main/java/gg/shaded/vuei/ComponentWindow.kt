package gg.shaded.vuei

import gg.shaded.vuei.layout.LayoutContext
import gg.shaded.vuei.layout.SimpleLayoutContext
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.Cancellable
import org.bukkit.event.EventHandler
import org.bukkit.event.HandlerList
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryDragEvent
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryView
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.Plugin
import org.bukkit.plugin.PluginManager
import org.graalvm.polyglot.Engine
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import kotlin.concurrent.thread

open class ComponentWindow(
    private val plugin: Plugin,
    pluginManager: PluginManager = Bukkit.getPluginManager(),
    private val inventoryProvider: InventoryProvider = CachedInventoryProvider(),
    private val renderer: Renderer = InventoryRenderer(inventoryProvider),
    private val scheduler: Scheduler = Schedulers.from { r -> Bukkit.getScheduler().runTask(plugin, r) },
    private val errorHandler: ErrorHandler,
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

    protected open val inventory: Inventory
        get() {
            val layout = this.renderable ?: throw IllegalStateException("Layout is not defined.")

            return inventoryProvider.getInventory(layout)
        }

    private val viewers = ArrayList<Player>()

    private var subscription: Disposable? = null
    private var contexts: MutableSet<LayoutContext> = HashSet()
    private var ignoreClose: Boolean = false

    private val contextScheduler = Schedulers.from(
        Executors.newSingleThreadExecutor { r ->
            thread(
                contextClassLoader = ClassLoader.getSystemClassLoader(),
                start = false
            ) {
                r.run()
            }
        }
    )

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
        subscription = Observable.defer { root.setup(SimpleSetupContext(HashMap())) }
            .switchMap { bindings ->
                document.layout.allocate(createLayoutContext(bindings))
            }
            .subscribeOn(contextScheduler)
            .debounce(50, TimeUnit.MILLISECONDS, contextScheduler) // 1 tick
            .map { it.first() }
            .observeOn(scheduler)
            .subscribe(
                { renderable -> this.renderable = renderable },
                { t ->
                    errorHandler.handle(t)
                    // Prevent CME
                    ArrayList(viewers).forEach { it.closeInventory() }
                }
            )
    }

    private fun createLayoutContext(bindings: Map<String, Any?>) =
        SimpleLayoutContext(
            superContext = null,
            Engine.create(),
            document,
            SimpleRenderable(
                width = 9,
                height = 6,
                element = document
            ),
            bindings,
            root.imports,
            slots = HashMap()
        ).also { contexts.add(it) }

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

        Completable.fromAction { layout.click(x, y, context) }
            .subscribeOn(contextScheduler)
            .subscribe(
                {},
                errorHandler::handle
            )
    }

    protected open fun dispose() {
        contexts.forEach(AutoCloseable::close)
        subscription?.dispose()
        HandlerList.unregisterAll(this)
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