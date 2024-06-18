package fr.elowyr.lobby.utils;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Arrays;
import java.util.List;

public class ItemBuilder {

    private ItemStack is;

    private ItemMeta meta;

    public ItemBuilder(ItemStack stack) {
        this.is = stack;
        this.meta = stack.getItemMeta();
    }

    public void lore(List<String> lore) {
        this.meta.setLore(lore);
    }

    public ItemBuilder(Material m) {

        this(m, 1);
    }

    public ItemBuilder(Material m, int amount) {

        is = new ItemStack(m, amount);
    }

    public ItemBuilder(Material m, int amount, short meta) {

        is = new ItemStack(m, amount, meta);
    }

    public void addFlags(ItemFlag[] flags) {
        this.meta.addItemFlags(flags);
    }

    public ItemBuilder clone() {

        return new ItemBuilder(is);
    }

    public ItemBuilder setName(String name) {
        ItemMeta im = this.is.getItemMeta();
        im.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
        this.is.setItemMeta(im);
        return this;
    }

    public ItemBuilder addEnchant(Enchantment ench, int level) {

        ItemMeta im = is.getItemMeta();
        im.addEnchant(ench, level, true);
        is.setItemMeta(im);
        return this;
    }

    public ItemBuilder setLore(String... lore) {

        ItemMeta im = is.getItemMeta();
        im.setLore(Utils.colorAll(Arrays.asList(lore)));
        is.setItemMeta(im);
        return this;
    }

    public void setColor(Color color) {
        if (this.meta instanceof LeatherArmorMeta)
            ((LeatherArmorMeta)this.meta).setColor(color);
    }

    public ItemBuilder owner(String player) {
        if (this.meta instanceof SkullMeta)
            ((SkullMeta)this.meta).setOwner(player);
        return this;
    }

    public ItemStack toItemStack() {

        return is;
    }
}
