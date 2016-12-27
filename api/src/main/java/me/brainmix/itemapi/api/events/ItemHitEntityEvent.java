package me.brainmix.itemapi.api.events;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ItemHitEntityEvent extends ItemThrowEvent {

    private Entity entity;

    public ItemHitEntityEvent(Player player, ItemStack item, int delay, Item thrownItem, Entity entity) {
        super(player, item, delay, thrownItem);
        this.entity = entity;
    }

    public Entity getEntity() {
        return entity;
    }
}
