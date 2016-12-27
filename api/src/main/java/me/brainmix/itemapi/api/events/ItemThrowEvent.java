package me.brainmix.itemapi.api.events;

import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ItemThrowEvent extends ItemEvent {

    private Item thrownItem;

    public ItemThrowEvent(Player player, ItemStack item, int delay, Item thrownItem) {
        super(player, item, delay);
        this.thrownItem = thrownItem;
    }

    public Item getThrownItem() {
        return thrownItem;
    }

}
