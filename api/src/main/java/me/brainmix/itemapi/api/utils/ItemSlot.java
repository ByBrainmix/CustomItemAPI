package me.brainmix.itemapi.api.utils;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ItemSlot {

    private int slot;
    private Player player;
    private ItemStack item;

    public ItemSlot(int slot, Player player, ItemStack item) {
        this.slot = slot;
        this.player = player;
        this.item = item;
    }

    public int getSlot() {
        return slot;
    }

    public Player getPlayer() {
        return player;
    }

    public ItemStack getItem() {
        return item;
    }

    public boolean hasItem() {
        return item != null;
    }

    public void setItem(ItemStack item) {
        if(!isValid()) return;
        this.item = item;

        player.getInventory().setItem(slot, item);
    }

    public boolean isValid() {
        return slot >= 0;
    }

}
