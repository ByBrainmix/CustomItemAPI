package me.brainmix.itemapi.api.events;

import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.inventory.ItemStack;

public class ItemProjectileFlyTickEvent<T extends Projectile> extends ItemShootEvent<T> {

    private int timeInAir;

    public ItemProjectileFlyTickEvent(Player player, ItemStack item, int delay, T projectile, Class<T> type, int timeInAir) {
        super(player, item, delay, projectile, type);
        this.timeInAir = timeInAir;
    }

    public int getTimeInAir() {
        return timeInAir;
    }

}
