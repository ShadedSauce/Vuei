package gg.shaded.vuei.util;

import com.google.common.base.Preconditions;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * A utility class to assist in the creation of ItemStacks in the confines of a single line.
 *
 * @author Parker Hawke - 2008Choco
 */
public final class ItemBuilder {

    private static final Set<Material> ILLEGAL_TYPES = EnumSet.of(Material.AIR /*,Material.CAVE_AIR, Material.VOID_AIR*/); // TODO: uncomment in 1.13

    private final Map<Integer, Consumer<ItemBuilder>> consumers = new TreeMap<>(Comparator.comparingInt(i -> i));

    private final ItemStack item;
    private final ItemMeta meta;

    private ItemBuilder(Material type, int amount) {
        Preconditions.checkArgument(type != null, "Cannot create ItemBuilder for null Material");
        Preconditions.checkArgument(!ILLEGAL_TYPES.contains(type), "Illegal material!");
        Preconditions.checkArgument(amount > 0 && amount <= type.getMaxStackSize(), "Amount must be between 0 - &d", type.getMaxStackSize());

        this.item = new ItemStack(type);
        this.meta = item.getItemMeta();
    }

    private ItemBuilder(ItemStack item) {
        Preconditions.checkArgument(item != null, "Cannot modify a null item");
        Preconditions.checkArgument(!ILLEGAL_TYPES.contains(item.getType()), "Illegal material!");

        this.item = item.clone();
        this.meta = item.getItemMeta();
    }

    /**
     * Get a new instance of an ItemBuilder given a (non-null and non-air) {@link Material} and
     * a quantity greater than 0 and less than or equal to {@link Material#getMaxStackSize()}.
     *
     * @param type   the type of item to build
     * @param amount the item amount
     * @return the ItemBuilder instance for the provided values
     */
    public static ItemBuilder of(Material type, int amount) {
        return new ItemBuilder(type, amount);
    }

    /**
     * Get a new instance of an ItemBuilder given a (non-null and non-air) {@link Material}.
     *
     * @param type the type of item to build
     * @return the ItemBuilder instance for the provided material
     */
    public static ItemBuilder of(Material type) {
        return new ItemBuilder(type, 1);
    }

    public static ItemBuilder of(String type) {
        return of(Material.matchMaterial(type));
    }

    public static ItemBuilder empty() {
        return of(Material.STONE);
    }

    /**
     * Get a new instance of ItemBuilder to modify an existing {@link ItemStack}. The
     * ItemStack passed will be cloned, therefore the passed reference will not be modified,
     * but rather a copy of it. The result of {@link #build()} will be a separate item with
     * the changes applied from this builder instance. The provided item acts as a base for
     * the values in this builder.
     *
     * @param item the item to build
     * @return the ItemBuilder instance for the provided item
     */
    public static ItemBuilder modify(ItemStack item) {
        return new ItemBuilder(item);
    }

    /**
     * Check whether the specified type of ItemMeta is supported by this ItemBuilder.
     *
     * @param type the type of meta to check
     * @return true if supported, false otherwise or if null
     */
    public boolean isSupportedMeta(Class<? extends ItemMeta> type) {
        return type != null && type.isInstance(meta);
    }

    /**
     * Apply a method from a more specific type of ItemMeta to this ItemBuilder instance.
     * If the type provided is unsupported by this ItemBuilder (according to
     * {@link #isSupportedMeta(Class)}), this method will throw an exception, therefore it
     * is recommended that it be checked before invoking this method if you are unsure as to
     * what is and is not supported.
     *
     * @param type    the type of ItemMeta to apply
     * @param applier the function to apply to the ItemMeta instance
     * @param <T>     The ItemMeta type to be applied in the consumer function
     * @return this instance. Allows for chained method calls
     */
    public <T extends ItemMeta> ItemBuilder useItemMeta(Class<T> type, Consumer<T> applier) {
        Preconditions.checkArgument(type != null, "Cannot apply meta for type null");
        Preconditions.checkArgument(isSupportedMeta(type), "The specified ItemMeta type is not supported by this ItemBuilder instance");
        Preconditions.checkArgument(applier != null, "Application function must not be null");

        applier.accept(type.cast(meta));
        return this;
    }

    public ItemBuilder transform(Function<ItemBuilder, ItemBuilder> transformer) {
        return transformer.apply(this);
    }

    public ItemBuilder type(Material type) {
        ItemStack item = build();

        item.setType(type);
        return ItemBuilder.modify(item);
    }

    /**
     * Set the item name.
     *
     * @param name the name to set
     * @return this instance. Allows for chained method calls
     * @see ItemMeta#setDisplayName(String)
     */
    public ItemBuilder name(String name) {
        this.meta.setDisplayName(name);
        return this;
    }

    public ItemBuilder name(Component name) {
        this.meta.displayName(name);
        return this;
    }

    public ItemBuilder name(Function<String, String> nameApplier) {
        String name = meta.hasDisplayName() ? meta.getDisplayName() : "";

        return name(nameApplier.apply(name));
    }

    /**
     * Set the item lore in the form of varargs.
     *
     * @param lore the lore to set
     * @return this instance. Allows for chained method calls
     * @see ItemBuilder#lore(List)
     * @see ItemMeta#setLore(List)
     */
    public ItemBuilder lore(String... lore) {
        return lore(Arrays.asList(lore));
    }

    /**
     * Set the item lore in the form of a {@literal List<String>}.
     *
     * @param lore the lore to set
     * @return this instance. Allows for chained method calls
     * @see ItemBuilder#lore(String...)
     * @see ItemMeta#setLore(List)
     */
    public ItemBuilder lore(List<String> lore) {
        meta.setLore(lore);
        return this;
    }

    public ItemBuilder description(List<Component> components) {
        meta.lore(components);
        return this;
    }

    public ItemBuilder addLore(List<String> lore) {
        List<String> current = meta.getLore();

        if (current == null) {
            current = new ArrayList<>();
        }

        current.addAll(lore);
        return lore(current);
    }

    public ItemBuilder addLore(String... lore) {
        return addLore(Arrays.asList(lore));
    }

    public ItemBuilder insertLore(int index, List<String> lore) {
        List<String> current = meta.getLore();

        if (current == null) {
            current = new ArrayList<>();
        }

        current.addAll(index, lore);
        return lore(current);
    }

    public ItemBuilder insertLore(int index, String... lore) {
        return insertLore(index, Arrays.asList(lore));
    }

    /**
     * Set the item damage. Some items may not display damage or accept the damage attribute at
     * all, in which case this method will simply fail silently.
     *
     * @param damage the damage to set
     * @return this instance. Allows for chained method calls
     * <p>
     * //TODO: @see Damageable#setDamage(int)
     */
    public ItemBuilder damage(int damage) {
        item.setDurability((short) damage);
        // TODO: 1.13
//        ((Damageable) meta).setDamage(damage);
        return this;
    }

    /**
     * Set the item amount. This damage must range between 1 and {@link Material#getMaxStackSize()}
     * according to the type being built in this ItemBuilder instance.
     *
     * @param amount the amount to set
     * @return this instance. Allows for chained method calls
     * @see ItemStack#setAmount(int)
     */
    public ItemBuilder amount(int amount) {
        this.item.setAmount(amount);
        return this;
    }

    /**
     * Apply an enchantment with the specified level to the item. This method does not respect the
     * level limitations of an enchantment (i.e. Sharpness VI may be applied if desired).
     *
     * @param enchantment the enchantment to add
     * @param level       the enchantment level to set
     * @return this instance. Allows for chained method calls
     * @see ItemMeta#addEnchant(Enchantment, int, boolean)
     */
    public ItemBuilder enchantment(Enchantment enchantment, int level) {
        this.meta.addEnchant(enchantment, level, true);
        return this;
    }

    public ItemBuilder enchantments(Map<Enchantment, Integer> enchantments) {
        enchantments.forEach(this::enchantment);
        return this;
    }

    /**
     * Apply flags to the item.
     *
     * @param flags the flags to set
     * @return this instance. Allows for chained method calls
     * @see ItemMeta#addItemFlags(ItemFlag...)
     */
    public ItemBuilder flags(ItemFlag... flags) {
        if (flags.length > 0) {
            this.meta.addItemFlags(flags);
        }

        return this;
    }

    /**
     * Set the unbreakable state of this item to true.
     *
     * @return this instance. Allows for chained method calls
     * @see ItemMeta#setUnbreakable(boolean)
     */
    public ItemBuilder unbreakable() {
        this.meta.setUnbreakable(true);
        return this;
    }

    public ItemBuilder data(MaterialData data) {
        this.item.setData(data);
        return this;
    }

    /**
     * Set the item's localized name.
     *
     * @param name the localized name to set
     * @return this instance. Allows for chained method calls
     * @see ItemMeta#setLocalizedName(String)
     */
    public ItemBuilder localizedName(String name) {
        this.meta.setLocalizedName(name);
        return this;
    }

    public ItemBuilder reorder(int position, Consumer<ItemBuilder> consumer) {
        consumers.put(position, consumer);
        return this;
    }

    public ItemBuilder model(int model) {
        this.meta.setCustomModelData(model);
        return this;
    }

    private void order() {
        consumers.forEach((key, value) -> value.accept(this));
    }

    /**
     * Complete the building of this ItemBuilder and return the resulting ItemStack.
     *
     * @return the completed {@link ItemStack} instance built by this builder
     */
    public ItemStack build() {
        order();
        this.item.setItemMeta(meta);
        return item;
    }
}