package me.brainmix.itemapi.api.utils;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

public final class ItemUtils {

    private ItemUtils() {}

    public static ItemStack getItemStack(Material material, String displayName) {
        ItemStack item = new ItemStack(material);
        ItemMeta im = item.getItemMeta();
        im.setDisplayName(format(displayName));
        item.setItemMeta(im);
        return item;
    }

    public static int getSlot(Player player, ItemStack item) {
        PlayerInventory inv = player.getInventory();

        int slot = -1;
        for(int i = 0; i  < inv.getContents().length; i++) {
            if(compare(inv.getContents()[i], item)) slot = i;
        }

        return slot;
    }

    public static boolean hasItem(Player player, ItemStack item) {
        return getSlot(player, item) != -1;
    }

    public static void replaceItem(Player player, ItemStack replace, ItemStack replacor) {
        int slot = getSlot(player, replace);
        if(slot == -1) return;
        player.getInventory().setItem(slot, replacor);
    }

    public static void clearItem(Player player, ItemStack item) {
        replaceItem(player, item, new ItemStack(Material.AIR));
    }

    public static boolean compare(ItemStack item1, ItemStack item2) {

        return item1 != null && item2 != null
                && item1.getType() == item2.getType()
                && item1.hasItemMeta() && item2.hasItemMeta()
                && item1.getItemMeta().hasDisplayName() && item2.getItemMeta().hasDisplayName()
                && item1.getItemMeta().getDisplayName().equals(item2.getItemMeta().getDisplayName());

    }

    public static ItemStack addEnchant(ItemStack item, Enchantment enchantment, int level) {
        ItemMeta im = item.getItemMeta();
        im.addEnchant(enchantment, level, false);
        item.setItemMeta(im);
        return item;
    }

    public static ItemStack removeEnchant(ItemStack item, Enchantment enchantment) {
        ItemMeta im = item.getItemMeta();
        im.removeEnchant(enchantment);
        item.setItemMeta(im);
        return item;
    }

    public static String format(String text, Object... replace) {

        String output = text;
        List<String> r = new ArrayList<>();
        List<String> o = new ArrayList<>();

        for (int i = 0; i < replace.length; i++) {
            if (i % 2 == 0) {
                r.add(String.valueOf(replace[i]));
            } else {
                o.add(String.valueOf(replace[i]));
            }
        }

        for (String s : r) {
            while (output.contains(s)) {
                output = output.replace(s, ChatColor.translateAlternateColorCodes('&', o.get(r.indexOf(s))));
            }
        }

        return ChatColor.translateAlternateColorCodes('&', output);
    }

}
