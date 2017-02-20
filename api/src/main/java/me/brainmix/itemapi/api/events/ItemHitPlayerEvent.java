package me.brainmix.itemapi.api.events;

import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ItemHitPlayerEvent extends ItemHitEntityEvent {

    public ItemHitPlayerEvent(Player player, ItemStack item, int delay, Item thrownItem, Player entity) {
        super(player, item, delay, thrownItem, entity);
    }

    @Override
    public Player getEntity() {
        return (Player) super.getEntity();
    }
}
