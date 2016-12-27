package me.brainmix.itemapi.api.events;

import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;

public class ItemClickEvent extends ItemEvent {

    private Action action;

    public ItemClickEvent(Player player, ItemStack item, int delay, Action action) {
        super(player, item, delay);
        this.action = action;
    }

    public Action getAction() {
        return action;
    }

}
