package me.brainmix.itemapi.api.events;

import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ItemHitGroundEvent extends ItemThrowEvent {

    public ItemHitGroundEvent(Player player, ItemStack item, int delay, Item thrownItem) {
        super(player, item, delay, thrownItem);
    }

}
