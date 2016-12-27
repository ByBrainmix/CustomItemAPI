package me.brainmix.itemapi.api.events;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ItemDelayTickEvent extends ItemEvent {
    public ItemDelayTickEvent(Player player, ItemStack item, int timeLeft) {
        super(player, item, timeLeft);
    }

}
