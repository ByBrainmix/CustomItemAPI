package me.brainmix.itemapi.api.events;

import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ItemPickupEvent extends ItemEvent{

    private Item itemEntity;
    private int remaining;

    public ItemPickupEvent(Player player, ItemStack item, int delay, Item itemEntity, int remaining) {
        super(player, item, delay);
        this.itemEntity = itemEntity;
        this.remaining = remaining;
    }

    public Item getItemEntity() {
        return itemEntity;
    }

    public int getRemaining() {
        return remaining;
    }

}
