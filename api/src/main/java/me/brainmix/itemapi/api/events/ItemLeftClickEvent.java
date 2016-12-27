package me.brainmix.itemapi.api.events;

import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;

public class ItemLeftClickEvent extends ItemClickEvent {
    public ItemLeftClickEvent(Player player, ItemStack item, int delay, Action action) {
        super(player, item, delay, action);
    }
}
