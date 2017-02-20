package me.brainmix.itemapi.api.events;

import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.ItemStack;

public class ItemProjectileHitPlayerEvent<T extends Projectile> extends ItemProjectileHitEntityEvent<T> {

    public ItemProjectileHitPlayerEvent(Player player, ItemStack item, int delay, T projectile, Class<T> type, Player entity, DamageCause cause) {
        super(player, item, delay, projectile, type, entity, cause);
    }

    @Override
    public Player getEntity() {
        return (Player) super.getEntity();
    }
}
