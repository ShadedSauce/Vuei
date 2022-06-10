package gg.shaded.vuei

import com.destroystokyo.paper.ClientOption
import com.destroystokyo.paper.Title
import com.destroystokyo.paper.block.TargetBlockInfo
import com.destroystokyo.paper.entity.TargetEntityInfo
import gg.shaded.vuei.*
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Scheduler
import net.kyori.adventure.audience.MessageType
import net.kyori.adventure.identity.Identity
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
import net.md_5.bungee.api.chat.BaseComponent
import org.bukkit.*
import org.bukkit.advancement.Advancement
import org.bukkit.advancement.AdvancementProgress
import org.bukkit.attribute.Attribute
import org.bukkit.attribute.AttributeInstance
import org.bukkit.block.Block
import org.bukkit.block.BlockFace
import org.bukkit.block.PistonMoveReaction
import org.bukkit.block.Sign
import org.bukkit.block.data.BlockData
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.conversations.Conversation
import org.bukkit.conversations.ConversationAbandonedEvent
import org.bukkit.entity.*
import org.bukkit.entity.memory.MemoryKey
import org.bukkit.event.Event
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.entity.CreatureSpawnEvent
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.inventory.*
import org.bukkit.event.inventory.InventoryType.SlotType
import org.bukkit.event.player.PlayerKickEvent
import org.bukkit.event.player.PlayerResourcePackStatusEvent
import org.bukkit.event.player.PlayerTeleportEvent
import org.bukkit.generator.BiomeProvider
import org.bukkit.generator.ChunkGenerator
import org.bukkit.inventory.*
import org.bukkit.map.MapView
import org.bukkit.metadata.MetadataValue
import org.bukkit.permissions.Permissible
import org.bukkit.permissions.Permission
import org.bukkit.permissions.PermissionAttachment
import org.bukkit.permissions.PermissionAttachmentInfo
import org.bukkit.persistence.PersistentDataContainer
import org.bukkit.plugin.*
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import org.bukkit.profile.PlayerProfile
import org.bukkit.scoreboard.Scoreboard
import org.bukkit.util.BoundingBox
import org.bukkit.util.RayTraceResult
import org.bukkit.util.Vector
import java.io.File
import java.io.InputStream
import java.net.InetSocketAddress
import java.util.*
import java.util.logging.Logger

class TestPlugin: Plugin {
    override fun onTabComplete(
        p0: CommandSender,
        p1: Command,
        p2: String,
        p3: Array<out String>
    ): MutableList<String>? {
        TODO("Not yet implemented")
    }

    override fun onCommand(
        p0: CommandSender,
        p1: Command,
        p2: String,
        p3: Array<out String>
    ): Boolean {
        TODO("Not yet implemented")
    }

    override fun getDataFolder(): File {
        TODO("Not yet implemented")
    }

    override fun getDescription(): PluginDescriptionFile {
        TODO("Not yet implemented")
    }

    override fun getConfig(): FileConfiguration {
        TODO("Not yet implemented")
    }

    override fun getResource(p0: String): InputStream? {
        TODO("Not yet implemented")
    }

    override fun saveConfig() {
        TODO("Not yet implemented")
    }

    override fun saveDefaultConfig() {
        TODO("Not yet implemented")
    }

    override fun saveResource(p0: String, p1: Boolean) {
        TODO("Not yet implemented")
    }

    override fun reloadConfig() {
        TODO("Not yet implemented")
    }

    override fun getPluginLoader(): PluginLoader {
        TODO("Not yet implemented")
    }

    override fun getServer(): Server {
        TODO("Not yet implemented")
    }

    override fun isEnabled(): Boolean {
        TODO("Not yet implemented")
    }

    override fun onDisable() {
        TODO("Not yet implemented")
    }

    override fun onLoad() {
        TODO("Not yet implemented")
    }

    override fun onEnable() {
        TODO("Not yet implemented")
    }

    override fun isNaggable(): Boolean {
        TODO("Not yet implemented")
    }

    override fun setNaggable(p0: Boolean) {
        TODO("Not yet implemented")
    }

    override fun getDefaultWorldGenerator(p0: String, p1: String?): ChunkGenerator? {
        TODO("Not yet implemented")
    }

    override fun getDefaultBiomeProvider(p0: String, p1: String?): BiomeProvider? {
        TODO("Not yet implemented")
    }

    override fun getLogger(): Logger {
        TODO("Not yet implemented")
    }

    override fun getName(): String {
        return "Test"
    }
}

class TestPluginManager: PluginManager {
    override fun registerInterface(p0: Class<out PluginLoader>) {
        TODO("Not yet implemented")
    }

    override fun getPlugin(p0: String): Plugin? {
        TODO("Not yet implemented")
    }

    override fun getPlugins(): Array<Plugin> {
        TODO("Not yet implemented")
    }

    override fun isPluginEnabled(p0: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun isPluginEnabled(p0: Plugin?): Boolean {
        TODO("Not yet implemented")
    }

    override fun loadPlugin(p0: File): Plugin? {
        TODO("Not yet implemented")
    }

    override fun loadPlugins(p0: File): Array<Plugin> {
        TODO("Not yet implemented")
    }

    override fun disablePlugins() {
        TODO("Not yet implemented")
    }

    override fun clearPlugins() {
        TODO("Not yet implemented")
    }

    override fun callEvent(event: Event) {
        listeners.forEach { listener ->
            listener.javaClass.methods
                .filter { it.parameterTypes.firstOrNull() == event.javaClass }
                .forEach { it.invoke(listener, event) }
        }
    }

    private val listeners = ArrayList<Listener>()

    override fun registerEvents(listener: Listener, p1: Plugin) {
        listeners.add(listener)
    }

    override fun registerEvent(
        p0: Class<out Event>,
        p1: Listener,
        p2: EventPriority,
        p3: EventExecutor,
        p4: Plugin
    ) {
        TODO("Not yet implemented")
    }

    override fun registerEvent(
        p0: Class<out Event>,
        p1: Listener,
        p2: EventPriority,
        p3: EventExecutor,
        p4: Plugin,
        p5: Boolean
    ) {
        TODO("Not yet implemented")
    }

    override fun enablePlugin(p0: Plugin) {
        TODO("Not yet implemented")
    }

    override fun disablePlugin(p0: Plugin) {
        TODO("Not yet implemented")
    }

    override fun getPermission(p0: String): Permission? {
        TODO("Not yet implemented")
    }

    override fun addPermission(p0: Permission) {
        TODO("Not yet implemented")
    }

    override fun removePermission(p0: Permission) {
        TODO("Not yet implemented")
    }

    override fun removePermission(p0: String) {
        TODO("Not yet implemented")
    }

    override fun getDefaultPermissions(p0: Boolean): MutableSet<Permission> {
        TODO("Not yet implemented")
    }

    override fun recalculatePermissionDefaults(p0: Permission) {
        TODO("Not yet implemented")
    }

    override fun subscribeToPermission(p0: String, p1: Permissible) {
        TODO("Not yet implemented")
    }

    override fun unsubscribeFromPermission(p0: String, p1: Permissible) {
        TODO("Not yet implemented")
    }

    override fun getPermissionSubscriptions(p0: String): MutableSet<Permissible> {
        TODO("Not yet implemented")
    }

    override fun subscribeToDefaultPerms(p0: Boolean, p1: Permissible) {
        TODO("Not yet implemented")
    }

    override fun unsubscribeFromDefaultPerms(p0: Boolean, p1: Permissible) {
        TODO("Not yet implemented")
    }

    override fun getDefaultPermSubscriptions(p0: Boolean): MutableSet<Permissible> {
        TODO("Not yet implemented")
    }

    override fun getPermissions(): MutableSet<Permission> {
        TODO("Not yet implemented")
    }

    override fun useTimings(): Boolean {
        TODO("Not yet implemented")
    }

}

class TestInventoryProvider: InventoryProvider {
    private val cache = HashMap<String, TestInventory>()

    override fun getInventory(renderable: Renderable): TestInventory {
        return cache.getOrPut("${renderable.height}:${renderable.title}") {
            TestInventory(renderable)
        }
    }
}

class TestInventory(
    private val renderable: Renderable
): Inventory {
    val items: Array<ItemStack?> = (0 until renderable.height * 9)
        .map { null }
        .toTypedArray()

    val title: String?
        get() = renderable.title

    override fun iterator(): MutableListIterator<ItemStack> {
        TODO("Not yet implemented")
    }

    override fun iterator(p0: Int): MutableListIterator<ItemStack> {
        TODO("Not yet implemented")
    }

    override fun getSize(): Int {
        return renderable.height * 9
    }

    override fun getMaxStackSize(): Int {
        TODO("Not yet implemented")
    }

    override fun setMaxStackSize(p0: Int) {
        TODO("Not yet implemented")
    }

    override fun getItem(p0: Int): ItemStack? {
        return items[p0]
    }

    override fun setItem(p0: Int, p1: ItemStack?) {
        items[p0] = p1
    }

    override fun addItem(vararg p0: ItemStack): HashMap<Int, ItemStack> {
        TODO("Not yet implemented")
    }

    override fun removeItem(vararg p0: ItemStack): HashMap<Int, ItemStack> {
        TODO("Not yet implemented")
    }

    override fun removeItemAnySlot(vararg p0: ItemStack): HashMap<Int, ItemStack> {
        TODO("Not yet implemented")
    }

    override fun getContents(): Array<ItemStack> {
        return items.map {
            it ?: TestItemStack(Material.AIR)
        }
            .toTypedArray()
    }

    override fun setContents(p0: Array<out ItemStack?>?) {
        TODO("Not yet implemented")
    }

    override fun getStorageContents(): Array<ItemStack> {
        TODO("Not yet implemented")
    }

    override fun setStorageContents(p0: Array<out ItemStack?>?) {
        TODO("Not yet implemented")
    }

    override fun contains(p0: Material): Boolean {
        TODO("Not yet implemented")
    }

    override fun contains(p0: ItemStack?): Boolean {
        TODO("Not yet implemented")
    }

    override fun contains(p0: Material, p1: Int): Boolean {
        TODO("Not yet implemented")
    }

    override fun contains(p0: ItemStack?, p1: Int): Boolean {
        TODO("Not yet implemented")
    }

    override fun containsAtLeast(p0: ItemStack?, p1: Int): Boolean {
        TODO("Not yet implemented")
    }

    override fun all(p0: Material): HashMap<Int, out ItemStack> {
        TODO("Not yet implemented")
    }

    override fun all(p0: ItemStack?): HashMap<Int, out ItemStack> {
        TODO("Not yet implemented")
    }

    override fun first(p0: Material): Int {
        TODO("Not yet implemented")
    }

    override fun first(p0: ItemStack): Int {
        TODO("Not yet implemented")
    }

    override fun firstEmpty(): Int {
        TODO("Not yet implemented")
    }

    override fun isEmpty(): Boolean {
        TODO("Not yet implemented")
    }

    override fun remove(p0: Material) {
        TODO("Not yet implemented")
    }

    override fun remove(p0: ItemStack) {
        TODO("Not yet implemented")
    }

    override fun clear(p0: Int) {
        TODO("Not yet implemented")
    }

    override fun clear() {
        TODO("Not yet implemented")
    }

    override fun close(): Int {
        TODO("Not yet implemented")
    }

    override fun getViewers(): MutableList<HumanEntity> {
        TODO("Not yet implemented")
    }

    override fun getType(): InventoryType {
        TODO("Not yet implemented")
    }

    override fun getHolder(): InventoryHolder? {
        TODO("Not yet implemented")
    }

    override fun getHolder(p0: Boolean): InventoryHolder? {
        TODO("Not yet implemented")
    }

    override fun getLocation(): Location? {
        TODO("Not yet implemented")
    }
}

class TestPlayer: Player {
    override fun getAttribute(p0: Attribute): AttributeInstance? {
        TODO("Not yet implemented")
    }

    override fun registerAttribute(p0: Attribute) {
        TODO("Not yet implemented")
    }

    override fun setMetadata(p0: String, p1: MetadataValue) {
        TODO("Not yet implemented")
    }

    override fun getMetadata(p0: String): MutableList<MetadataValue> {
        TODO("Not yet implemented")
    }

    override fun hasMetadata(p0: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun removeMetadata(p0: String, p1: Plugin) {
        TODO("Not yet implemented")
    }

    override fun isOp(): Boolean {
        TODO("Not yet implemented")
    }

    override fun setOp(p0: Boolean) {
        TODO("Not yet implemented")
    }

    override fun isPermissionSet(p0: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun isPermissionSet(p0: Permission): Boolean {
        TODO("Not yet implemented")
    }

    override fun hasPermission(p0: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun hasPermission(p0: Permission): Boolean {
        TODO("Not yet implemented")
    }

    override fun addAttachment(p0: Plugin, p1: String, p2: Boolean): PermissionAttachment {
        TODO("Not yet implemented")
    }

    override fun addAttachment(p0: Plugin): PermissionAttachment {
        TODO("Not yet implemented")
    }

    override fun addAttachment(
        p0: Plugin,
        p1: String,
        p2: Boolean,
        p3: Int
    ): PermissionAttachment? {
        TODO("Not yet implemented")
    }

    override fun addAttachment(p0: Plugin, p1: Int): PermissionAttachment? {
        TODO("Not yet implemented")
    }

    override fun removeAttachment(p0: PermissionAttachment) {
        TODO("Not yet implemented")
    }

    override fun recalculatePermissions() {
        TODO("Not yet implemented")
    }

    override fun getEffectivePermissions(): MutableSet<PermissionAttachmentInfo> {
        TODO("Not yet implemented")
    }


    override fun sendMessage(message: net.kyori.adventure.text.Component) {
        println("sendMessage: $message")
    }

    override fun sendActionBar(p0: String) {
        TODO("Not yet implemented")
    }

    override fun sendActionBar(p0: Char, p1: String) {
        TODO("Not yet implemented")
    }

    override fun sendActionBar(vararg p0: BaseComponent) {
        TODO("Not yet implemented")
    }

    override fun showTitle(p0: Array<out BaseComponent?>?) {
        TODO("Not yet implemented")
    }

    override fun showTitle(p0: BaseComponent?) {
        TODO("Not yet implemented")
    }

    override fun showTitle(p0: Array<out BaseComponent?>?, p1: Array<out BaseComponent?>?, p2: Int, p3: Int, p4: Int) {
        TODO("Not yet implemented")
    }

    override fun showTitle(p0: BaseComponent?, p1: BaseComponent?, p2: Int, p3: Int, p4: Int) {
        TODO("Not yet implemented")
    }

    override fun sendMessage(p0: String) {
        TODO("Not yet implemented")
    }

    override fun sendMessage(vararg p0: String) {
        TODO("Not yet implemented")
    }

    override fun sendMessage(p0: UUID?, p1: String) {
        TODO("Not yet implemented")
    }

    override fun sendMessage(p0: UUID?, vararg p1: String) {
        TODO("Not yet implemented")
    }

    override fun getServer(): Server {
        TODO("Not yet implemented")
    }

    override fun getName(): String {
        TODO("Not yet implemented")
    }

    override fun spigot(): Player.Spigot {
        TODO("Not yet implemented")
    }

    override fun name(): net.kyori.adventure.text.Component {
        TODO("Not yet implemented")
    }

    override fun customName(): net.kyori.adventure.text.Component? {
        TODO("Not yet implemented")
    }

    override fun customName(p0: net.kyori.adventure.text.Component?) {
        TODO("Not yet implemented")
    }

    override fun getCustomName(): String? {
        TODO("Not yet implemented")
    }

    override fun setCustomName(p0: String?) {
        TODO("Not yet implemented")
    }

    override fun getPersistentDataContainer(): PersistentDataContainer {
        TODO("Not yet implemented")
    }

    override fun getLocation(): Location {
        TODO("Not yet implemented")
    }

    override fun getLocation(p0: Location?): Location? {
        TODO("Not yet implemented")
    }

    override fun setVelocity(p0: Vector) {
        TODO("Not yet implemented")
    }

    override fun getVelocity(): Vector {
        TODO("Not yet implemented")
    }

    override fun getHeight(): Double {
        TODO("Not yet implemented")
    }

    override fun getWidth(): Double {
        TODO("Not yet implemented")
    }

    override fun getBoundingBox(): BoundingBox {
        TODO("Not yet implemented")
    }

    override fun isOnGround(): Boolean {
        TODO("Not yet implemented")
    }

    override fun isInWater(): Boolean {
        TODO("Not yet implemented")
    }

    override fun getWorld(): World {
        TODO("Not yet implemented")
    }

    override fun setRotation(p0: Float, p1: Float) {
        TODO("Not yet implemented")
    }

    override fun teleport(p0: Location): Boolean {
        TODO("Not yet implemented")
    }

    override fun teleport(p0: Location, p1: PlayerTeleportEvent.TeleportCause): Boolean {
        TODO("Not yet implemented")
    }

    override fun teleport(p0: Entity): Boolean {
        TODO("Not yet implemented")
    }

    override fun teleport(p0: Entity, p1: PlayerTeleportEvent.TeleportCause): Boolean {
        TODO("Not yet implemented")
    }

    override fun getNearbyEntities(p0: Double, p1: Double, p2: Double): MutableList<Entity> {
        TODO("Not yet implemented")
    }

    override fun getEntityId(): Int {
        TODO("Not yet implemented")
    }

    override fun getFireTicks(): Int {
        TODO("Not yet implemented")
    }

    override fun getMaxFireTicks(): Int {
        TODO("Not yet implemented")
    }

    override fun setFireTicks(p0: Int) {
        TODO("Not yet implemented")
    }

    override fun setVisualFire(p0: Boolean) {
        TODO("Not yet implemented")
    }

    override fun isVisualFire(): Boolean {
        TODO("Not yet implemented")
    }

    override fun getFreezeTicks(): Int {
        TODO("Not yet implemented")
    }

    override fun getMaxFreezeTicks(): Int {
        TODO("Not yet implemented")
    }

    override fun setFreezeTicks(p0: Int) {
        TODO("Not yet implemented")
    }

    override fun isFrozen(): Boolean {
        TODO("Not yet implemented")
    }

    override fun isFreezeTickingLocked(): Boolean {
        TODO("Not yet implemented")
    }

    override fun lockFreezeTicks(p0: Boolean) {
        TODO("Not yet implemented")
    }

    override fun remove() {
        TODO("Not yet implemented")
    }

    override fun isDead(): Boolean {
        TODO("Not yet implemented")
    }

    override fun isValid(): Boolean {
        TODO("Not yet implemented")
    }

    override fun isPersistent(): Boolean {
        TODO("Not yet implemented")
    }

    override fun setPersistent(p0: Boolean) {
        TODO("Not yet implemented")
    }

    override fun getPassenger(): Entity? {
        TODO("Not yet implemented")
    }

    override fun setPassenger(p0: Entity): Boolean {
        TODO("Not yet implemented")
    }

    override fun getPassengers(): MutableList<Entity> {
        TODO("Not yet implemented")
    }

    override fun addPassenger(p0: Entity): Boolean {
        TODO("Not yet implemented")
    }

    override fun removePassenger(p0: Entity): Boolean {
        TODO("Not yet implemented")
    }

    override fun isEmpty(): Boolean {
        TODO("Not yet implemented")
    }

    override fun eject(): Boolean {
        TODO("Not yet implemented")
    }

    override fun getFallDistance(): Float {
        TODO("Not yet implemented")
    }

    override fun setFallDistance(p0: Float) {
        TODO("Not yet implemented")
    }

    override fun setLastDamageCause(p0: EntityDamageEvent?) {
        TODO("Not yet implemented")
    }

    override fun getLastDamageCause(): EntityDamageEvent? {
        TODO("Not yet implemented")
    }

    private val uuid = UUID.randomUUID()

    override fun getUniqueId(): UUID {
        return uuid
    }

    override fun getTicksLived(): Int {
        TODO("Not yet implemented")
    }

    override fun setTicksLived(p0: Int) {
        TODO("Not yet implemented")
    }

    override fun playEffect(p0: Location, p1: Effect, p2: Int) {
        TODO("Not yet implemented")
    }

    override fun <T: Any?> playEffect(p0: Location, p1: Effect, p2: T?) {
        TODO("Not yet implemented")
    }

    override fun playEffect(p0: EntityEffect) {
        TODO("Not yet implemented")
    }

    override fun getType(): EntityType {
        TODO("Not yet implemented")
    }

    override fun isInsideVehicle(): Boolean {
        TODO("Not yet implemented")
    }

    override fun leaveVehicle(): Boolean {
        TODO("Not yet implemented")
    }

    override fun getVehicle(): Entity? {
        TODO("Not yet implemented")
    }

    override fun setCustomNameVisible(p0: Boolean) {
        TODO("Not yet implemented")
    }

    override fun isCustomNameVisible(): Boolean {
        TODO("Not yet implemented")
    }

    override fun setGlowing(p0: Boolean) {
        TODO("Not yet implemented")
    }

    override fun isGlowing(): Boolean {
        TODO("Not yet implemented")
    }

    override fun setInvulnerable(p0: Boolean) {
        TODO("Not yet implemented")
    }

    override fun isInvulnerable(): Boolean {
        TODO("Not yet implemented")
    }

    override fun isSilent(): Boolean {
        TODO("Not yet implemented")
    }

    override fun setSilent(p0: Boolean) {
        TODO("Not yet implemented")
    }

    override fun hasGravity(): Boolean {
        TODO("Not yet implemented")
    }

    override fun setGravity(p0: Boolean) {
        TODO("Not yet implemented")
    }

    override fun getPortalCooldown(): Int {
        TODO("Not yet implemented")
    }

    override fun setPortalCooldown(p0: Int) {
        TODO("Not yet implemented")
    }

    override fun getScoreboardTags(): MutableSet<String> {
        TODO("Not yet implemented")
    }

    override fun addScoreboardTag(p0: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun removeScoreboardTag(p0: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun getPistonMoveReaction(): PistonMoveReaction {
        TODO("Not yet implemented")
    }

    override fun getFacing(): BlockFace {
        TODO("Not yet implemented")
    }

    override fun getPose(): Pose {
        TODO("Not yet implemented")
    }

    override fun getSpawnCategory(): SpawnCategory {
        TODO("Not yet implemented")
    }

    override fun teamDisplayName(): net.kyori.adventure.text.Component {
        TODO("Not yet implemented")
    }

    override fun getOrigin(): Location? {
        TODO("Not yet implemented")
    }

    override fun fromMobSpawner(): Boolean {
        TODO("Not yet implemented")
    }

    override fun getEntitySpawnReason(): CreatureSpawnEvent.SpawnReason {
        TODO("Not yet implemented")
    }

    override fun isInRain(): Boolean {
        TODO("Not yet implemented")
    }

    override fun isInBubbleColumn(): Boolean {
        TODO("Not yet implemented")
    }

    override fun isInWaterOrRain(): Boolean {
        TODO("Not yet implemented")
    }

    override fun isInWaterOrBubbleColumn(): Boolean {
        TODO("Not yet implemented")
    }

    override fun isInWaterOrRainOrBubbleColumn(): Boolean {
        TODO("Not yet implemented")
    }

    override fun isInLava(): Boolean {
        TODO("Not yet implemented")
    }

    override fun isTicking(): Boolean {
        TODO("Not yet implemented")
    }

    override fun getTrackedPlayers(): MutableSet<Player> {
        TODO("Not yet implemented")
    }

    override fun spawnAt(p0: Location, p1: CreatureSpawnEvent.SpawnReason): Boolean {
        TODO("Not yet implemented")
    }

    override fun isInPowderedSnow(): Boolean {
        TODO("Not yet implemented")
    }

    override fun damage(p0: Double) {
        TODO("Not yet implemented")
    }

    override fun damage(p0: Double, p1: Entity?) {
        TODO("Not yet implemented")
    }

    override fun getHealth(): Double {
        TODO("Not yet implemented")
    }

    override fun setHealth(p0: Double) {
        TODO("Not yet implemented")
    }

    override fun getAbsorptionAmount(): Double {
        TODO("Not yet implemented")
    }

    override fun setAbsorptionAmount(p0: Double) {
        TODO("Not yet implemented")
    }

    override fun getMaxHealth(): Double {
        TODO("Not yet implemented")
    }

    override fun setMaxHealth(p0: Double) {
        TODO("Not yet implemented")
    }

    override fun resetMaxHealth() {
        TODO("Not yet implemented")
    }

    override fun <T: Projectile?> launchProjectile(p0: Class<out T>): T {
        TODO("Not yet implemented")
    }

    override fun <T: Projectile?> launchProjectile(p0: Class<out T>, p1: Vector?): T {
        TODO("Not yet implemented")
    }

    override fun getEyeHeight(): Double {
        TODO("Not yet implemented")
    }

    override fun getEyeHeight(p0: Boolean): Double {
        TODO("Not yet implemented")
    }

    override fun getEyeLocation(): Location {
        TODO("Not yet implemented")
    }

    override fun getLineOfSight(p0: MutableSet<Material>?, p1: Int): MutableList<Block> {
        TODO("Not yet implemented")
    }

    override fun getTargetBlock(p0: MutableSet<Material>?, p1: Int): Block {
        TODO("Not yet implemented")
    }

    override fun getTargetBlock(p0: Int, p1: TargetBlockInfo.FluidMode): Block? {
        TODO("Not yet implemented")
    }

    override fun getTargetBlockFace(p0: Int, p1: TargetBlockInfo.FluidMode): BlockFace? {
        TODO("Not yet implemented")
    }

    override fun getTargetBlockInfo(p0: Int, p1: TargetBlockInfo.FluidMode): TargetBlockInfo? {
        TODO("Not yet implemented")
    }

    override fun getTargetEntity(p0: Int, p1: Boolean): Entity? {
        TODO("Not yet implemented")
    }

    override fun getTargetEntityInfo(p0: Int, p1: Boolean): TargetEntityInfo? {
        TODO("Not yet implemented")
    }

    override fun getLastTwoTargetBlocks(p0: MutableSet<Material>?, p1: Int): MutableList<Block> {
        TODO("Not yet implemented")
    }

    override fun getTargetBlockExact(p0: Int): Block? {
        TODO("Not yet implemented")
    }

    override fun getTargetBlockExact(p0: Int, p1: FluidCollisionMode): Block? {
        TODO("Not yet implemented")
    }

    override fun rayTraceBlocks(p0: Double): RayTraceResult? {
        TODO("Not yet implemented")
    }

    override fun rayTraceBlocks(p0: Double, p1: FluidCollisionMode): RayTraceResult? {
        TODO("Not yet implemented")
    }

    override fun getRemainingAir(): Int {
        TODO("Not yet implemented")
    }

    override fun setRemainingAir(p0: Int) {
        TODO("Not yet implemented")
    }

    override fun getMaximumAir(): Int {
        TODO("Not yet implemented")
    }

    override fun setMaximumAir(p0: Int) {
        TODO("Not yet implemented")
    }

    override fun getArrowCooldown(): Int {
        TODO("Not yet implemented")
    }

    override fun setArrowCooldown(p0: Int) {
        TODO("Not yet implemented")
    }

    override fun getArrowsInBody(): Int {
        TODO("Not yet implemented")
    }

    override fun setArrowsInBody(p0: Int) {
        TODO("Not yet implemented")
    }

    override fun getBeeStingerCooldown(): Int {
        TODO("Not yet implemented")
    }

    override fun setBeeStingerCooldown(p0: Int) {
        TODO("Not yet implemented")
    }

    override fun getBeeStingersInBody(): Int {
        TODO("Not yet implemented")
    }

    override fun setBeeStingersInBody(p0: Int) {
        TODO("Not yet implemented")
    }

    override fun getMaximumNoDamageTicks(): Int {
        TODO("Not yet implemented")
    }

    override fun setMaximumNoDamageTicks(p0: Int) {
        TODO("Not yet implemented")
    }

    override fun getLastDamage(): Double {
        TODO("Not yet implemented")
    }

    override fun setLastDamage(p0: Double) {
        TODO("Not yet implemented")
    }

    override fun getNoDamageTicks(): Int {
        TODO("Not yet implemented")
    }

    override fun setNoDamageTicks(p0: Int) {
        TODO("Not yet implemented")
    }

    override fun getKiller(): Player? {
        TODO("Not yet implemented")
    }

    override fun setKiller(p0: Player?) {
        TODO("Not yet implemented")
    }

    override fun addPotionEffect(p0: PotionEffect): Boolean {
        TODO("Not yet implemented")
    }

    override fun addPotionEffect(p0: PotionEffect, p1: Boolean): Boolean {
        TODO("Not yet implemented")
    }

    override fun addPotionEffects(p0: MutableCollection<PotionEffect>): Boolean {
        TODO("Not yet implemented")
    }

    override fun hasPotionEffect(p0: PotionEffectType): Boolean {
        TODO("Not yet implemented")
    }

    override fun getPotionEffect(p0: PotionEffectType): PotionEffect? {
        TODO("Not yet implemented")
    }

    override fun removePotionEffect(p0: PotionEffectType) {
        TODO("Not yet implemented")
    }

    override fun getActivePotionEffects(): MutableCollection<PotionEffect> {
        TODO("Not yet implemented")
    }

    override fun hasLineOfSight(p0: Entity): Boolean {
        TODO("Not yet implemented")
    }

    override fun hasLineOfSight(p0: Location): Boolean {
        TODO("Not yet implemented")
    }

    override fun getRemoveWhenFarAway(): Boolean {
        TODO("Not yet implemented")
    }

    override fun setRemoveWhenFarAway(p0: Boolean) {
        TODO("Not yet implemented")
    }

    override fun getEquipment(): EntityEquipment {
        TODO("Not yet implemented")
    }

    override fun setCanPickupItems(p0: Boolean) {
        TODO("Not yet implemented")
    }

    override fun getCanPickupItems(): Boolean {
        TODO("Not yet implemented")
    }

    override fun isLeashed(): Boolean {
        TODO("Not yet implemented")
    }

    override fun getLeashHolder(): Entity {
        TODO("Not yet implemented")
    }

    override fun setLeashHolder(p0: Entity?): Boolean {
        TODO("Not yet implemented")
    }

    override fun isGliding(): Boolean {
        TODO("Not yet implemented")
    }

    override fun setGliding(p0: Boolean) {
        TODO("Not yet implemented")
    }

    override fun isSwimming(): Boolean {
        TODO("Not yet implemented")
    }

    override fun setSwimming(p0: Boolean) {
        TODO("Not yet implemented")
    }

    override fun isRiptiding(): Boolean {
        TODO("Not yet implemented")
    }

    override fun isSleeping(): Boolean {
        TODO("Not yet implemented")
    }

    override fun isClimbing(): Boolean {
        TODO("Not yet implemented")
    }

    override fun setAI(p0: Boolean) {
        TODO("Not yet implemented")
    }

    override fun hasAI(): Boolean {
        TODO("Not yet implemented")
    }

    override fun attack(p0: Entity) {
        TODO("Not yet implemented")
    }

    override fun swingMainHand() {
        TODO("Not yet implemented")
    }

    override fun swingOffHand() {
        TODO("Not yet implemented")
    }

    override fun setCollidable(p0: Boolean) {
        TODO("Not yet implemented")
    }

    override fun isCollidable(): Boolean {
        TODO("Not yet implemented")
    }

    override fun getCollidableExemptions(): MutableSet<UUID> {
        TODO("Not yet implemented")
    }

    override fun <T: Any?> getMemory(p0: MemoryKey<T>): T? {
        TODO("Not yet implemented")
    }

    override fun <T: Any?> setMemory(p0: MemoryKey<T>, p1: T?) {
        TODO("Not yet implemented")
    }

    override fun getCategory(): EntityCategory {
        TODO("Not yet implemented")
    }

    override fun setInvisible(p0: Boolean) {
        TODO("Not yet implemented")
    }

    override fun isInvisible(): Boolean {
        TODO("Not yet implemented")
    }

    override fun getArrowsStuck(): Int {
        TODO("Not yet implemented")
    }

    override fun setArrowsStuck(p0: Int) {
        TODO("Not yet implemented")
    }

    override fun getShieldBlockingDelay(): Int {
        TODO("Not yet implemented")
    }

    override fun setShieldBlockingDelay(p0: Int) {
        TODO("Not yet implemented")
    }

    override fun getActiveItem(): ItemStack? {
        TODO("Not yet implemented")
    }

    override fun clearActiveItem() {
        TODO("Not yet implemented")
    }

    override fun getItemUseRemainingTime(): Int {
        TODO("Not yet implemented")
    }

    override fun getHandRaisedTime(): Int {
        TODO("Not yet implemented")
    }

    override fun getInventory(): PlayerInventory {
        TODO("Not yet implemented")
    }

    override fun getEnderChest(): Inventory {
        TODO("Not yet implemented")
    }

    override fun getMainHand(): MainHand {
        TODO("Not yet implemented")
    }

    override fun setWindowProperty(p0: InventoryView.Property, p1: Int): Boolean {
        TODO("Not yet implemented")
    }

    override fun getOpenInventory(): InventoryView {
        TODO("Not yet implemented")
    }

    var currentInventory: TestInventory? = null
    var inventoryCallback: ((TestInventory) -> Unit)? = null

    override fun openInventory(p0: Inventory): InventoryView? {
        if(p0 is TestInventory) {
            this.currentInventory = p0
            inventoryCallback?.invoke(p0)
        }

        return null
    }

    override fun openInventory(p0: InventoryView) {
        TODO("Not yet implemented")
    }

    override fun openWorkbench(p0: Location?, p1: Boolean): InventoryView? {
        TODO("Not yet implemented")
    }

    override fun openEnchanting(p0: Location?, p1: Boolean): InventoryView? {
        TODO("Not yet implemented")
    }

    override fun openMerchant(p0: Villager, p1: Boolean): InventoryView? {
        TODO("Not yet implemented")
    }

    override fun openMerchant(p0: Merchant, p1: Boolean): InventoryView? {
        TODO("Not yet implemented")
    }

    override fun openAnvil(p0: Location?, p1: Boolean): InventoryView? {
        TODO("Not yet implemented")
    }

    override fun openCartographyTable(p0: Location?, p1: Boolean): InventoryView? {
        TODO("Not yet implemented")
    }

    override fun openGrindstone(p0: Location?, p1: Boolean): InventoryView? {
        TODO("Not yet implemented")
    }

    override fun openLoom(p0: Location?, p1: Boolean): InventoryView? {
        TODO("Not yet implemented")
    }

    override fun openSmithingTable(p0: Location?, p1: Boolean): InventoryView? {
        TODO("Not yet implemented")
    }

    override fun openStonecutter(p0: Location?, p1: Boolean): InventoryView? {
        TODO("Not yet implemented")
    }

    override fun closeInventory() {
        TODO("Not yet implemented")
    }

    override fun closeInventory(p0: InventoryCloseEvent.Reason) {
        TODO("Not yet implemented")
    }

    override fun getItemInHand(): ItemStack {
        TODO("Not yet implemented")
    }

    override fun setItemInHand(p0: ItemStack?) {
        TODO("Not yet implemented")
    }

    override fun getItemOnCursor(): ItemStack {
        TODO("Not yet implemented")
    }

    override fun setItemOnCursor(p0: ItemStack?) {
        TODO("Not yet implemented")
    }

    override fun hasCooldown(p0: Material): Boolean {
        TODO("Not yet implemented")
    }

    override fun getCooldown(p0: Material): Int {
        TODO("Not yet implemented")
    }

    override fun setCooldown(p0: Material, p1: Int) {
        TODO("Not yet implemented")
    }

    override fun isDeeplySleeping(): Boolean {
        TODO("Not yet implemented")
    }

    override fun getSleepTicks(): Int {
        TODO("Not yet implemented")
    }

    override fun getPotentialBedLocation(): Location? {
        TODO("Not yet implemented")
    }

    override fun sleep(p0: Location, p1: Boolean): Boolean {
        TODO("Not yet implemented")
    }

    override fun wakeup(p0: Boolean) {
        TODO("Not yet implemented")
    }

    override fun getBedLocation(): Location {
        TODO("Not yet implemented")
    }

    override fun getGameMode(): GameMode {
        TODO("Not yet implemented")
    }

    override fun setGameMode(p0: GameMode) {
        TODO("Not yet implemented")
    }

    override fun isBlocking(): Boolean {
        TODO("Not yet implemented")
    }

    override fun isHandRaised(): Boolean {
        TODO("Not yet implemented")
    }

    override fun getHandRaised(): EquipmentSlot {
        TODO("Not yet implemented")
    }

    override fun isJumping(): Boolean {
        TODO("Not yet implemented")
    }

    override fun setJumping(p0: Boolean) {
        TODO("Not yet implemented")
    }

    override fun playPickupItemAnimation(p0: Item, p1: Int) {
        TODO("Not yet implemented")
    }

    override fun getHurtDirection(): Float {
        TODO("Not yet implemented")
    }

    override fun setHurtDirection(p0: Float) {
        TODO("Not yet implemented")
    }

    override fun getItemInUse(): ItemStack? {
        TODO("Not yet implemented")
    }

    override fun getExpToLevel(): Int {
        TODO("Not yet implemented")
    }

    override fun releaseLeftShoulderEntity(): Entity? {
        TODO("Not yet implemented")
    }

    override fun releaseRightShoulderEntity(): Entity? {
        TODO("Not yet implemented")
    }

    override fun getAttackCooldown(): Float {
        TODO("Not yet implemented")
    }

    override fun discoverRecipe(p0: NamespacedKey): Boolean {
        TODO("Not yet implemented")
    }

    override fun discoverRecipes(p0: MutableCollection<NamespacedKey>): Int {
        TODO("Not yet implemented")
    }

    override fun undiscoverRecipe(p0: NamespacedKey): Boolean {
        TODO("Not yet implemented")
    }

    override fun undiscoverRecipes(p0: MutableCollection<NamespacedKey>): Int {
        TODO("Not yet implemented")
    }

    override fun hasDiscoveredRecipe(p0: NamespacedKey): Boolean {
        TODO("Not yet implemented")
    }

    override fun getDiscoveredRecipes(): MutableSet<NamespacedKey> {
        TODO("Not yet implemented")
    }

    override fun getShoulderEntityLeft(): Entity? {
        TODO("Not yet implemented")
    }

    override fun setShoulderEntityLeft(p0: Entity?) {
        TODO("Not yet implemented")
    }

    override fun getShoulderEntityRight(): Entity? {
        TODO("Not yet implemented")
    }

    override fun setShoulderEntityRight(p0: Entity?) {
        TODO("Not yet implemented")
    }

    override fun dropItem(p0: Boolean): Boolean {
        TODO("Not yet implemented")
    }

    override fun getExhaustion(): Float {
        TODO("Not yet implemented")
    }

    override fun setExhaustion(p0: Float) {
        TODO("Not yet implemented")
    }

    override fun getSaturation(): Float {
        TODO("Not yet implemented")
    }

    override fun setSaturation(p0: Float) {
        TODO("Not yet implemented")
    }

    override fun getFoodLevel(): Int {
        TODO("Not yet implemented")
    }

    override fun setFoodLevel(p0: Int) {
        TODO("Not yet implemented")
    }

    override fun getSaturatedRegenRate(): Int {
        TODO("Not yet implemented")
    }

    override fun setSaturatedRegenRate(p0: Int) {
        TODO("Not yet implemented")
    }

    override fun getUnsaturatedRegenRate(): Int {
        TODO("Not yet implemented")
    }

    override fun setUnsaturatedRegenRate(p0: Int) {
        TODO("Not yet implemented")
    }

    override fun getStarvationRate(): Int {
        TODO("Not yet implemented")
    }

    override fun setStarvationRate(p0: Int) {
        TODO("Not yet implemented")
    }

    override fun isConversing(): Boolean {
        TODO("Not yet implemented")
    }

    override fun acceptConversationInput(p0: String) {
        TODO("Not yet implemented")
    }

    override fun beginConversation(p0: Conversation): Boolean {
        TODO("Not yet implemented")
    }

    override fun abandonConversation(p0: Conversation) {
        TODO("Not yet implemented")
    }

    override fun abandonConversation(p0: Conversation, p1: ConversationAbandonedEvent) {
        TODO("Not yet implemented")
    }

    override fun sendRawMessage(p0: String) {
        TODO("Not yet implemented")
    }

    override fun sendRawMessage(p0: UUID?, p1: String) {
        TODO("Not yet implemented")
    }

    override fun serialize(): MutableMap<String, Any> {
        TODO("Not yet implemented")
    }

    override fun isOnline(): Boolean {
        TODO("Not yet implemented")
    }

    override fun getPlayerProfile(): com.destroystokyo.paper.profile.PlayerProfile {
        TODO("Not yet implemented")
    }

    override fun isBanned(): Boolean {
        TODO("Not yet implemented")
    }

    override fun isWhitelisted(): Boolean {
        TODO("Not yet implemented")
    }

    override fun setWhitelisted(p0: Boolean) {
        TODO("Not yet implemented")
    }

    override fun getPlayer(): Player? {
        TODO("Not yet implemented")
    }

    override fun getFirstPlayed(): Long {
        TODO("Not yet implemented")
    }

    override fun getLastPlayed(): Long {
        TODO("Not yet implemented")
    }

    override fun hasPlayedBefore(): Boolean {
        TODO("Not yet implemented")
    }

    override fun getBedSpawnLocation(): Location? {
        TODO("Not yet implemented")
    }

    override fun getLastLogin(): Long {
        TODO("Not yet implemented")
    }

    override fun getLastSeen(): Long {
        TODO("Not yet implemented")
    }

    override fun incrementStatistic(p0: Statistic) {
        TODO("Not yet implemented")
    }

    override fun incrementStatistic(p0: Statistic, p1: Int) {
        TODO("Not yet implemented")
    }

    override fun incrementStatistic(p0: Statistic, p1: Material) {
        TODO("Not yet implemented")
    }

    override fun incrementStatistic(p0: Statistic, p1: Material, p2: Int) {
        TODO("Not yet implemented")
    }

    override fun incrementStatistic(p0: Statistic, p1: EntityType) {
        TODO("Not yet implemented")
    }

    override fun incrementStatistic(p0: Statistic, p1: EntityType, p2: Int) {
        TODO("Not yet implemented")
    }

    override fun decrementStatistic(p0: Statistic) {
        TODO("Not yet implemented")
    }

    override fun decrementStatistic(p0: Statistic, p1: Int) {
        TODO("Not yet implemented")
    }

    override fun decrementStatistic(p0: Statistic, p1: Material) {
        TODO("Not yet implemented")
    }

    override fun decrementStatistic(p0: Statistic, p1: Material, p2: Int) {
        TODO("Not yet implemented")
    }

    override fun decrementStatistic(p0: Statistic, p1: EntityType) {
        TODO("Not yet implemented")
    }

    override fun decrementStatistic(p0: Statistic, p1: EntityType, p2: Int) {
        TODO("Not yet implemented")
    }

    override fun setStatistic(p0: Statistic, p1: Int) {
        TODO("Not yet implemented")
    }

    override fun setStatistic(p0: Statistic, p1: Material, p2: Int) {
        TODO("Not yet implemented")
    }

    override fun setStatistic(p0: Statistic, p1: EntityType, p2: Int) {
        TODO("Not yet implemented")
    }

    override fun getStatistic(p0: Statistic): Int {
        TODO("Not yet implemented")
    }

    override fun getStatistic(p0: Statistic, p1: Material): Int {
        TODO("Not yet implemented")
    }

    override fun getStatistic(p0: Statistic, p1: EntityType): Int {
        TODO("Not yet implemented")
    }

    override fun sendPluginMessage(p0: Plugin, p1: String, p2: ByteArray) {
        TODO("Not yet implemented")
    }

    override fun getListeningPluginChannels(): MutableSet<String> {
        TODO("Not yet implemented")
    }

    override fun getDisplayName(): String {
        TODO("Not yet implemented")
    }

    override fun setDisplayName(p0: String?) {
        TODO("Not yet implemented")
    }

    override fun playerListName(p0: net.kyori.adventure.text.Component?) {
        TODO("Not yet implemented")
    }

    override fun playerListName(): net.kyori.adventure.text.Component {
        TODO("Not yet implemented")
    }

    override fun playerListHeader(): net.kyori.adventure.text.Component? {
        TODO("Not yet implemented")
    }

    override fun playerListFooter(): net.kyori.adventure.text.Component? {
        TODO("Not yet implemented")
    }

    override fun getPlayerListName(): String {
        TODO("Not yet implemented")
    }

    override fun setPlayerListName(p0: String?) {
        TODO("Not yet implemented")
    }

    override fun getPlayerListHeader(): String? {
        TODO("Not yet implemented")
    }

    override fun getPlayerListFooter(): String? {
        TODO("Not yet implemented")
    }

    override fun setPlayerListHeader(p0: String?) {
        TODO("Not yet implemented")
    }

    override fun setPlayerListFooter(p0: String?) {
        TODO("Not yet implemented")
    }

    override fun setPlayerListHeaderFooter(p0: String?, p1: String?) {
        TODO("Not yet implemented")
    }

    override fun setPlayerListHeaderFooter(p0: Array<out BaseComponent?>?, p1: Array<out BaseComponent?>?) {
        TODO("Not yet implemented")
    }

    override fun setPlayerListHeaderFooter(p0: BaseComponent?, p1: BaseComponent?) {
        TODO("Not yet implemented")
    }

    override fun setCompassTarget(p0: Location) {
        TODO("Not yet implemented")
    }

    override fun getCompassTarget(): Location {
        TODO("Not yet implemented")
    }

    override fun getAddress(): InetSocketAddress? {
        TODO("Not yet implemented")
    }

    override fun getProtocolVersion(): Int {
        TODO("Not yet implemented")
    }

    override fun getVirtualHost(): InetSocketAddress? {
        TODO("Not yet implemented")
    }

    override fun displayName(): net.kyori.adventure.text.Component {
        TODO("Not yet implemented")
    }

    override fun displayName(p0: net.kyori.adventure.text.Component?) {
        TODO("Not yet implemented")
    }

    override fun kickPlayer(p0: String?) {
        TODO("Not yet implemented")
    }

    override fun kick() {
        TODO("Not yet implemented")
    }

    override fun kick(p0: net.kyori.adventure.text.Component?) {
        TODO("Not yet implemented")
    }

    override fun kick(p0: net.kyori.adventure.text.Component?, p1: PlayerKickEvent.Cause) {
        TODO("Not yet implemented")
    }

    override fun chat(p0: String) {
        TODO("Not yet implemented")
    }

    override fun performCommand(p0: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun isSneaking(): Boolean {
        TODO("Not yet implemented")
    }

    override fun setSneaking(p0: Boolean) {
        TODO("Not yet implemented")
    }

    override fun isSprinting(): Boolean {
        TODO("Not yet implemented")
    }

    override fun setSprinting(p0: Boolean) {
        TODO("Not yet implemented")
    }

    override fun saveData() {
        TODO("Not yet implemented")
    }

    override fun loadData() {
        TODO("Not yet implemented")
    }

    override fun setSleepingIgnored(p0: Boolean) {
        TODO("Not yet implemented")
    }

    override fun isSleepingIgnored(): Boolean {
        TODO("Not yet implemented")
    }

    override fun setBedSpawnLocation(p0: Location?) {
        TODO("Not yet implemented")
    }

    override fun setBedSpawnLocation(p0: Location?, p1: Boolean) {
        TODO("Not yet implemented")
    }

    override fun playNote(p0: Location, p1: Byte, p2: Byte) {
        TODO("Not yet implemented")
    }

    override fun playNote(p0: Location, p1: Instrument, p2: Note) {
        TODO("Not yet implemented")
    }

    override fun playSound(p0: Location, p1: Sound, p2: Float, p3: Float) {
        TODO("Not yet implemented")
    }

    override fun playSound(p0: Location, p1: String, p2: Float, p3: Float) {
        TODO("Not yet implemented")
    }

    override fun playSound(p0: Location, p1: Sound, p2: SoundCategory, p3: Float, p4: Float) {
        TODO("Not yet implemented")
    }

    override fun playSound(p0: Location, p1: String, p2: SoundCategory, p3: Float, p4: Float) {
        TODO("Not yet implemented")
    }

    override fun playSound(p0: Entity, p1: Sound, p2: Float, p3: Float) {
        TODO("Not yet implemented")
    }

    override fun playSound(p0: Entity, p1: Sound, p2: SoundCategory, p3: Float, p4: Float) {
        TODO("Not yet implemented")
    }

    override fun stopSound(p0: Sound) {
        TODO("Not yet implemented")
    }

    override fun stopSound(p0: String) {
        TODO("Not yet implemented")
    }

    override fun stopSound(p0: Sound, p1: SoundCategory?) {
        TODO("Not yet implemented")
    }

    override fun stopSound(p0: String, p1: SoundCategory?) {
        TODO("Not yet implemented")
    }

    override fun stopAllSounds() {
        TODO("Not yet implemented")
    }

    override fun breakBlock(p0: Block): Boolean {
        TODO("Not yet implemented")
    }

    override fun sendBlockChange(p0: Location, p1: Material, p2: Byte) {
        TODO("Not yet implemented")
    }

    override fun sendBlockChange(p0: Location, p1: BlockData) {
        TODO("Not yet implemented")
    }

    override fun sendBlockDamage(p0: Location, p1: Float) {
        TODO("Not yet implemented")
    }

    override fun sendMultiBlockChange(p0: MutableMap<Location, BlockData>, p1: Boolean) {
        TODO("Not yet implemented")
    }

    override fun sendEquipmentChange(p0: LivingEntity, p1: EquipmentSlot, p2: ItemStack) {
        TODO("Not yet implemented")
    }

    override fun sendSignChange(
        p0: Location,
        p1: MutableList<net.kyori.adventure.text.Component>?,
        p2: DyeColor,
        p3: Boolean
    ) {
        TODO("Not yet implemented")
    }

    override fun sendSignChange(p0: Location, p1: Array<out String?>?) {
        TODO("Not yet implemented")
    }

    override fun sendSignChange(p0: Location, p1: Array<out String?>?, p2: DyeColor) {
        TODO("Not yet implemented")
    }

    override fun sendSignChange(p0: Location, p1: Array<out String?>?, p2: DyeColor, p3: Boolean) {
        TODO("Not yet implemented")
    }

    override fun sendMap(p0: MapView) {
        TODO("Not yet implemented")
    }

    override fun setTitleTimes(p0: Int, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }

    override fun setSubtitle(p0: Array<out BaseComponent>?) {
        TODO("Not yet implemented")
    }

    override fun setSubtitle(p0: BaseComponent?) {
        TODO("Not yet implemented")
    }

    override fun sendTitle(p0: Title) {
        TODO("Not yet implemented")
    }

    override fun updateInventory() {
        TODO("Not yet implemented")
    }

    override fun getPreviousGameMode(): GameMode? {
        TODO("Not yet implemented")
    }

    override fun setPlayerTime(p0: Long, p1: Boolean) {
        TODO("Not yet implemented")
    }

    override fun getPlayerTime(): Long {
        TODO("Not yet implemented")
    }

    override fun getPlayerTimeOffset(): Long {
        TODO("Not yet implemented")
    }

    override fun isPlayerTimeRelative(): Boolean {
        TODO("Not yet implemented")
    }

    override fun resetPlayerTime() {
        TODO("Not yet implemented")
    }

    override fun setPlayerWeather(p0: WeatherType) {
        TODO("Not yet implemented")
    }

    override fun getPlayerWeather(): WeatherType? {
        TODO("Not yet implemented")
    }

    override fun resetPlayerWeather() {
        TODO("Not yet implemented")
    }

    override fun giveExp(p0: Int) {
        TODO("Not yet implemented")
    }

    override fun giveExp(p0: Int, p1: Boolean) {
        TODO("Not yet implemented")
    }

    override fun applyMending(p0: Int): Int {
        TODO("Not yet implemented")
    }

    override fun giveExpLevels(p0: Int) {
        TODO("Not yet implemented")
    }

    override fun getExp(): Float {
        TODO("Not yet implemented")
    }

    override fun setExp(p0: Float) {
        TODO("Not yet implemented")
    }

    override fun getLevel(): Int {
        TODO("Not yet implemented")
    }

    override fun setLevel(p0: Int) {
        TODO("Not yet implemented")
    }

    override fun getTotalExperience(): Int {
        TODO("Not yet implemented")
    }

    override fun setTotalExperience(p0: Int) {
        TODO("Not yet implemented")
    }

    override fun sendExperienceChange(p0: Float) {
        TODO("Not yet implemented")
    }

    override fun sendExperienceChange(p0: Float, p1: Int) {
        TODO("Not yet implemented")
    }

    override fun getAllowFlight(): Boolean {
        TODO("Not yet implemented")
    }

    override fun setAllowFlight(p0: Boolean) {
        TODO("Not yet implemented")
    }

    override fun hidePlayer(p0: Player) {
        TODO("Not yet implemented")
    }

    override fun hidePlayer(p0: Plugin, p1: Player) {
        TODO("Not yet implemented")
    }

    override fun showPlayer(p0: Player) {
        TODO("Not yet implemented")
    }

    override fun showPlayer(p0: Plugin, p1: Player) {
        TODO("Not yet implemented")
    }

    override fun canSee(p0: Player): Boolean {
        TODO("Not yet implemented")
    }

    override fun canSee(p0: Entity): Boolean {
        TODO("Not yet implemented")
    }

    override fun hideEntity(p0: Plugin, p1: Entity) {
        TODO("Not yet implemented")
    }

    override fun showEntity(p0: Plugin, p1: Entity) {
        TODO("Not yet implemented")
    }

    override fun isFlying(): Boolean {
        TODO("Not yet implemented")
    }

    override fun setFlying(p0: Boolean) {
        TODO("Not yet implemented")
    }

    override fun setFlySpeed(p0: Float) {
        TODO("Not yet implemented")
    }

    override fun setWalkSpeed(p0: Float) {
        TODO("Not yet implemented")
    }

    override fun getFlySpeed(): Float {
        TODO("Not yet implemented")
    }

    override fun getWalkSpeed(): Float {
        TODO("Not yet implemented")
    }

    override fun setTexturePack(p0: String) {
        TODO("Not yet implemented")
    }

    override fun setResourcePack(p0: String) {
        TODO("Not yet implemented")
    }

    override fun setResourcePack(p0: String, p1: ByteArray?) {
        TODO("Not yet implemented")
    }

    override fun setResourcePack(p0: String, p1: ByteArray?, p2: String?) {
        TODO("Not yet implemented")
    }

    override fun setResourcePack(p0: String, p1: ByteArray?, p2: Boolean) {
        TODO("Not yet implemented")
    }

    override fun setResourcePack(p0: String, p1: ByteArray?, p2: String?, p3: Boolean) {
        TODO("Not yet implemented")
    }

    override fun setResourcePack(p0: String, p1: ByteArray?, p2: net.kyori.adventure.text.Component?, p3: Boolean) {
        TODO("Not yet implemented")
    }

    override fun setResourcePack(p0: String, p1: String) {
        TODO("Not yet implemented")
    }

    override fun setResourcePack(p0: String, p1: String, p2: Boolean) {
        TODO("Not yet implemented")
    }

    override fun setResourcePack(p0: String, p1: String, p2: Boolean, p3: net.kyori.adventure.text.Component?) {
        TODO("Not yet implemented")
    }

    override fun getScoreboard(): Scoreboard {
        TODO("Not yet implemented")
    }

    override fun setScoreboard(p0: Scoreboard) {
        TODO("Not yet implemented")
    }

    override fun getWorldBorder(): WorldBorder? {
        TODO("Not yet implemented")
    }

    override fun setWorldBorder(p0: WorldBorder?) {
        TODO("Not yet implemented")
    }

    override fun isHealthScaled(): Boolean {
        TODO("Not yet implemented")
    }

    override fun setHealthScaled(p0: Boolean) {
        TODO("Not yet implemented")
    }

    override fun setHealthScale(p0: Double) {
        TODO("Not yet implemented")
    }

    override fun getHealthScale(): Double {
        TODO("Not yet implemented")
    }

    override fun sendHealthUpdate(p0: Double, p1: Int, p2: Float) {
        TODO("Not yet implemented")
    }

    override fun sendHealthUpdate() {
        TODO("Not yet implemented")
    }

    override fun getSpectatorTarget(): Entity? {
        TODO("Not yet implemented")
    }

    override fun setSpectatorTarget(p0: Entity?) {
        TODO("Not yet implemented")
    }

    override fun sendTitle(p0: String?, p1: String?) {
        TODO("Not yet implemented")
    }

    override fun sendTitle(p0: String?, p1: String?, p2: Int, p3: Int, p4: Int) {
        TODO("Not yet implemented")
    }

    override fun updateTitle(p0: Title) {
        TODO("Not yet implemented")
    }

    override fun hideTitle() {
        TODO("Not yet implemented")
    }

    override fun resetTitle() {
        TODO("Not yet implemented")
    }

    override fun spawnParticle(p0: Particle, p1: Location, p2: Int) {
        TODO("Not yet implemented")
    }

    override fun spawnParticle(p0: Particle, p1: Double, p2: Double, p3: Double, p4: Int) {
        TODO("Not yet implemented")
    }

    override fun <T: Any?> spawnParticle(p0: Particle, p1: Location, p2: Int, p3: T?) {
        TODO("Not yet implemented")
    }

    override fun <T: Any?> spawnParticle(
        p0: Particle,
        p1: Double,
        p2: Double,
        p3: Double,
        p4: Int,
        p5: T?
    ) {
        TODO("Not yet implemented")
    }

    override fun spawnParticle(
        p0: Particle,
        p1: Location,
        p2: Int,
        p3: Double,
        p4: Double,
        p5: Double
    ) {
        TODO("Not yet implemented")
    }

    override fun spawnParticle(
        p0: Particle,
        p1: Double,
        p2: Double,
        p3: Double,
        p4: Int,
        p5: Double,
        p6: Double,
        p7: Double
    ) {
        TODO("Not yet implemented")
    }

    override fun <T: Any?> spawnParticle(
        p0: Particle,
        p1: Location,
        p2: Int,
        p3: Double,
        p4: Double,
        p5: Double,
        p6: T?
    ) {
        TODO("Not yet implemented")
    }

    override fun <T: Any?> spawnParticle(
        p0: Particle,
        p1: Double,
        p2: Double,
        p3: Double,
        p4: Int,
        p5: Double,
        p6: Double,
        p7: Double,
        p8: T?
    ) {
        TODO("Not yet implemented")
    }

    override fun spawnParticle(
        p0: Particle,
        p1: Location,
        p2: Int,
        p3: Double,
        p4: Double,
        p5: Double,
        p6: Double
    ) {
        TODO("Not yet implemented")
    }

    override fun spawnParticle(
        p0: Particle,
        p1: Double,
        p2: Double,
        p3: Double,
        p4: Int,
        p5: Double,
        p6: Double,
        p7: Double,
        p8: Double
    ) {
        TODO("Not yet implemented")
    }

    override fun <T: Any?> spawnParticle(
        p0: Particle,
        p1: Location,
        p2: Int,
        p3: Double,
        p4: Double,
        p5: Double,
        p6: Double,
        p7: T?
    ) {
        TODO("Not yet implemented")
    }

    override fun <T: Any?> spawnParticle(
        p0: Particle,
        p1: Double,
        p2: Double,
        p3: Double,
        p4: Int,
        p5: Double,
        p6: Double,
        p7: Double,
        p8: Double,
        p9: T?
    ) {
        TODO("Not yet implemented")
    }

    override fun getAdvancementProgress(p0: Advancement): AdvancementProgress {
        TODO("Not yet implemented")
    }

    override fun getClientViewDistance(): Int {
        TODO("Not yet implemented")
    }

    override fun locale(): Locale {
        TODO("Not yet implemented")
    }

    override fun getPing(): Int {
        TODO("Not yet implemented")
    }

    override fun getLocale(): String {
        TODO("Not yet implemented")
    }

    override fun getAffectsSpawning(): Boolean {
        TODO("Not yet implemented")
    }

    override fun setAffectsSpawning(p0: Boolean) {
        TODO("Not yet implemented")
    }

    override fun getViewDistance(): Int {
        TODO("Not yet implemented")
    }

    override fun setViewDistance(p0: Int) {
        TODO("Not yet implemented")
    }

    override fun getSimulationDistance(): Int {
        TODO("Not yet implemented")
    }

    override fun setSimulationDistance(p0: Int) {
        TODO("Not yet implemented")
    }

    override fun getNoTickViewDistance(): Int {
        TODO("Not yet implemented")
    }

    override fun setNoTickViewDistance(p0: Int) {
        TODO("Not yet implemented")
    }

    override fun getSendViewDistance(): Int {
        TODO("Not yet implemented")
    }

    override fun setSendViewDistance(p0: Int) {
        TODO("Not yet implemented")
    }

    override fun updateCommands() {
        TODO("Not yet implemented")
    }

    override fun openBook(p0: ItemStack) {
        TODO("Not yet implemented")
    }

    override fun openSign(p0: Sign) {
        TODO("Not yet implemented")
    }

    override fun showDemoScreen() {
        TODO("Not yet implemented")
    }

    override fun isAllowingServerListings(): Boolean {
        TODO("Not yet implemented")
    }

    override fun getResourcePackStatus(): PlayerResourcePackStatusEvent.Status? {
        TODO("Not yet implemented")
    }

    override fun getResourcePackHash(): String? {
        TODO("Not yet implemented")
    }

    override fun hasResourcePack(): Boolean {
        TODO("Not yet implemented")
    }

    override fun setPlayerProfile(p0: com.destroystokyo.paper.profile.PlayerProfile) {
        TODO("Not yet implemented")
    }

    override fun getCooldownPeriod(): Float {
        TODO("Not yet implemented")
    }

    override fun getCooledAttackStrength(p0: Float): Float {
        TODO("Not yet implemented")
    }

    override fun resetCooldown() {
        TODO("Not yet implemented")
    }

    override fun <T : Any?> getClientOption(p0: ClientOption<T>): T {
        TODO("Not yet implemented")
    }

    override fun boostElytra(p0: ItemStack): Firework? {
        TODO("Not yet implemented")
    }

    override fun sendOpLevel(p0: Byte) {
        TODO("Not yet implemented")
    }

    override fun getClientBrandName(): String? {
        TODO("Not yet implemented")
    }
}

class TestInventoryView(
    private val inventory: Inventory,
    private val player: Player
): InventoryView() {
    override fun getTopInventory(): Inventory {
        return inventory
    }

    override fun getBottomInventory(): Inventory {
        return inventory
    }

    override fun getPlayer(): HumanEntity {
        return player
    }

    override fun getType(): InventoryType {
        return InventoryType.CHEST
    }

    override fun getTitle(): String {
        TODO("Not yet implemented")
    }
}

class TestWindow(
    plugin: Plugin = TestPlugin(),
    private val pluginManager: PluginManager = TestPluginManager(),
    inventoryProvider: InventoryProvider = TestInventoryProvider(),
    renderer: Renderer = InventoryRenderer(inventoryProvider),
    scheduler: Scheduler,
    root: Component,
    errorHandler: ErrorHandler = ErrorHandler { it.printStackTrace() },
    private val callback: ((TestWindow, Inventory, Renderable) -> Unit)? = null
): ComponentWindow(
    plugin,
    pluginManager,
    inventoryProvider,
    renderer,
    scheduler,
    errorHandler,
    root,
) {
    override var renderable: Renderable?
        get() = super.renderable
        set(value) {
            super.renderable = value

            if(value != null) {
                callback?.invoke(this, inventory, value)
            }
        }

    public override val inventory: Inventory
        get() = super.inventory

    fun click(player: Player, slot: Int) {
        pluginManager.callEvent(
            InventoryClickEvent(
                TestInventoryView(inventory, player),
                SlotType.CONTAINER,
                slot,
                ClickType.LEFT,
                InventoryAction.NOTHING
            )
        )
    }

    fun awaitRedraw(timeout: Long = 1000L) {
        val old = this.renderable
        var elapsed = 0L

        while(this.renderable == old) {
            if(elapsed >= timeout) {
                throw InterruptedException("Redraw took too long.")
            }

            Thread.sleep(50) // 1 tick
            elapsed += 50
        }
    }

    override fun toString(): String {
        val height = this.renderable?.height ?: return "Empty window"

        return (0 until height)
            .joinToString("\n") { y ->
                inventory.contents?.toList()
                    ?.subList(y * 9, y * 9 + 9)
                    ?.toString() ?: "empty"
            }
    }
}

fun Component.extend(mapper: (Observable<Map<String, Any?>>) -> Observable<Map<String, Any?>>) = object : Component {
    override val template
        get() = this@extend.template

    override val props: List<Prop>
        get() = this@extend.props

    override val imports: Map<String, Component>
        get() = this@extend.imports

    override fun setup(context: SetupContext): Observable<Map<String, Any?>> {
        return this@extend.setup(context)
            .compose { mapper(it) }
    }
}