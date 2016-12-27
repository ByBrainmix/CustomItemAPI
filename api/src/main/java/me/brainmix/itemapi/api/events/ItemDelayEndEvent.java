package me.brainmix.itemapi.api.events;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ItemDelayEndEvent extends ItemEvent implements NotCancellable {
    public ItemDelayEndEvent(Player player, ItemStack item, int delay) {
        super(player, item, delay);
    }
}
