package me.brainmix.itemapi.api.events;

import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ItemFlyTickEvent extends ItemThrowEvent {

    private int timeInAir;

    public ItemFlyTickEvent(Player player, ItemStack item, int delay, Item thrownItem, int timeInAir) {
        super(player, item, delay, thrownItem);
        this.timeInAir = timeInAir;
    }

    public int getTimeInAir() {
        return timeInAir;
    }
}
