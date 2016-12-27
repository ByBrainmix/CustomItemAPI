package me.brainmix.itemapi.api.events;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public abstract class ItemClickEntityEvent extends ItemEvent {

    private Entity entity;

    public ItemClickEntityEvent(Player player, ItemStack item, int delay, Entity entity) {
        super(player, item, delay);
        this.entity = entity;
    }

    public Entity getEntity() {
        return entity;
    }
}
