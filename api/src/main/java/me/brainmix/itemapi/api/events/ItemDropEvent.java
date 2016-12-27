package me.brainmix.itemapi.api.events;

import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ItemDropEvent extends ItemEvent{

    private Item itemDrop;

    public ItemDropEvent(Player player, ItemStack item, int delay, Item itemDrop) {
        super(player, item, delay);
        this.itemDrop = itemDrop;
    }

    public Item getItemDrop() {
        return itemDrop;
    }
}
