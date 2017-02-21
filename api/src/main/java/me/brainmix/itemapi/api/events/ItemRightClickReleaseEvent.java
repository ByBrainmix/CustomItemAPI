package me.brainmix.itemapi.api.events;

import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;

public class ItemRightClickReleaseEvent extends ItemRightClickEvent {

    public ItemRightClickReleaseEvent(Player player, ItemStack item, int delay, Action action, long lastClicked) {
        super(player, item, delay, action, lastClicked, false);
    }

}
