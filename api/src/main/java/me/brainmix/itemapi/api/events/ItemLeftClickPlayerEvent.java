package me.brainmix.itemapi.api.events;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;

public class ItemLeftClickPlayerEvent extends ItemLeftClickEntityEvent {

    public ItemLeftClickPlayerEvent(Player player, ItemStack item, int delay, Player entity, EntityDamageEvent.DamageCause cause) {
        super(player, item, delay, entity, cause);
    }

    @Override
    public Player getEntity() {
        return (Player) super.getEntity();
    }
}
