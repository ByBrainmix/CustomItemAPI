package me.brainmix.itemapi.api.events;

import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.inventory.ItemStack;

public class ItemShootEvent<T extends Projectile> extends ItemEvent {

    private T projectile;
    private Class<T> type;

    public ItemShootEvent(Player player, ItemStack item, int delay, T projectile, Class<T> type) {
        super(player, item, delay);
        this.projectile = projectile;
        this.type = type;
    }

    public T getProjectile() {
        return projectile;
    }

    public Class<T> getType() {
        return type;
    }
}
