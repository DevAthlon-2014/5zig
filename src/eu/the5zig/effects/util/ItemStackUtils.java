package eu.the5zig.effects.util;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class ItemStackUtils {

	private ItemStack is;

	private ItemStackUtils(ItemStack is) {
		this.is = is;
	}

	public static ItemStackUtils newItemStack(Material material) {
		return new ItemStackUtils(new ItemStack(material));
	}

	public static ItemStackUtils newItemStack(Material material, int amount) {
		return new ItemStackUtils(new ItemStack(material, amount));
	}

	public static ItemStackUtils newSkull(String name) {
		ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
		SkullMeta meta = (SkullMeta) skull.getItemMeta();
		meta.setOwner(name);
		skull.setItemMeta(meta);
		return new ItemStackUtils(skull);
	}

	public ItemStackUtils name(String name) {
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(name);
		is.setItemMeta(im);
		return this;
	}

	public ItemStackUtils lore(String... lore) {
		ItemMeta im = is.getItemMeta();
		im.setLore(Arrays.asList(lore));
		is.setItemMeta(im);
		return this;
	}

	public ItemStackUtils enchant(Enchantment enchantment, int level) {
		is.addEnchantment(enchantment, level);
		return this;
	}

	public ItemStack getItemStack() {
		return is;
	}

}
