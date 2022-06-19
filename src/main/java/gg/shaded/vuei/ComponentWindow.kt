package gg.shaded.vuei

import gg.shaded.vuei.layout.LayoutContext
import gg.shaded.vuei.layout.SimpleLayoutContext
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject
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
import org.graalvm.polyglot.Context
import org.graalvm.polyglot.Engine
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import kotlin.concurrent.thread

open class ComponentWindow(
    private val plugin: Plugin,
    pluginManager: PluginManager = Bukkit.getPluginManager(),
    private val inventoryProvider: InventoryProvider = CachedInventoryProvider(),
    private val renderer: Renderer = InventoryRenderer(inventoryProvider),
    private val mainScheduler: Scheduler = Schedulers.from { r -> Bukkit.getScheduler().runTask(plugin, r) },
    private val errorHandler: ErrorHandler,
    private val root: Component
): Window, Listener {
    private var document: Element = root.template.elements.first()

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
    private var jsContext: Context? = null
    private var ignoreClose: Boolean = false
    private val contexts = HashSet<LayoutContext>()

    private val uiScheduler = Schedulers.from(
        Executors.newSingleThreadExecutor { r ->
            thread(
                contextClassLoader = ClassLoader.getSystemClassLoader(),
                name = "Component Window UI Thread",
                start = false
            ) {
                r.run()
            }
        }
    )

    private val backgroundScheduler = Schedulers.from(
        Executors.newSingleThreadExecutor()
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
        subscription = Observable.defer {
            root.setupWithQueue(
                SimpleSetupContext(
                    HashMap(),
                    PublishSubject.create(),
                    uiScheduler,
                    backgroundScheduler
                )
            )
        }
            .switchMap { bindings ->
                document.layout.allocate(createLayoutContext(
                    this.jsContext ?: createJavaScriptContext(Engine.create())
                        .also { this.jsContext = it },
                    bindings
                ))
            }
            .subscribeOn(uiScheduler)
            .debounce(50, TimeUnit.MILLISECONDS, uiScheduler) // 1 tick
            .map { it.first() }
            .observeOn(mainScheduler)
            .subscribe(
                { renderable -> this.renderable = renderable },
                { t ->
                    errorHandler.handle(t, viewers)
                    // Prevent CME
                    ArrayList(viewers).forEach { it.closeInventory() }
                    dispose()
                }
            )
    }

    private fun createLayoutContext(jsContext: Context, bindings: Map<String, Any?>) =
        SimpleLayoutContext(
            superContext = null,
            jsContext,
            document,
            SimpleRenderable(
                width = 9,
                height = 6,
                element = document
            ),
            bindings,
            root.imports,
            slots = HashMap(),
            uiScheduler,
            backgroundScheduler
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
        val layout = this.renderable ?: return
        val inventory = this.inventory

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

        scheduleClick(context, layout, x, y)
    }

    protected open fun scheduleClick(
        context: ClickContext,
        renderable: Renderable,
        x: Int,
        y: Int
    ) {
        Completable.fromAction { renderable.click(x, y, context) }
            .subscribeOn(uiScheduler)
            .subscribe(
                {},
                { t -> errorHandler.handle(t, viewers) }
            )
    }

    protected open fun dispose() {
        contexts.forEach(AutoCloseable::close)
        jsContext?.close()
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
        if(this.renderable == null) return
        if(event.inventory !== inventory || ignoreClose) return

        // 0 after close
        if(event.viewers.size == 1) {
            dispose()
        }

        viewers.remove(event.player)
    }
}