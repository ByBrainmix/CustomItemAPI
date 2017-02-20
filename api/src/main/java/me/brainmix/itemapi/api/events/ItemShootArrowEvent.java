package me.brainmix.itemapi.api.events;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ItemShootArrowEvent extends ItemShootEvent<Arrow> {

    private float force;

    public ItemShootArrowEvent(Player player, ItemStack item, int delay, Arrow projectile, float force) {
        super(player, item, delay, projectile, Arrow.class);
        this.force = force;
    }

    public Arrow getArrow() {
        return getProjectile();
    }

    public float getForce() {
        return force;
    }

}
