package me.brainmix.itemapi.api.events;

import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.inventory.ItemStack;

public class ItemProjectileHitEvent<T extends Projectile> extends ItemShootEvent<T> {

    public ItemProjectileHitEvent(Player player, ItemStack item, int delay, T projectile, Class<T> type) {
        super(player, item, delay, projectile, type);
    }

}
