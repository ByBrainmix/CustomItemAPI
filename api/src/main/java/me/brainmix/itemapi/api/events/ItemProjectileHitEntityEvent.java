package me.brainmix.itemapi.api.events;

import me.brainmix.itemapi.api.events.ItemLeftClickEntityEvent.DamageData;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.ItemStack;

public class ItemProjectileHitEntityEvent<T extends Projectile> extends ItemShootEvent<T> {

    private Entity entity;
    private DamageCause cause;
    private DamageData damageData;

    public ItemProjectileHitEntityEvent(Player player, ItemStack item, int delay, T projectile, Class<T> type, Entity entity, DamageCause cause) {
        super(player, item, delay, projectile, type);
        this.entity = entity;
        this.cause = cause;
    }

    public Entity getEntity() {
        return entity;
    }

    public DamageCause getCause() {
        return cause;
    }

    public DamageData getDamageData() {
        return damageData;
    }

    public double getDamage() {
        return damageData.getDamage();
    }

    public void setDamage(double damage) {
        this.damageData = new DamageData(damage, null);
    }

    public void setDamage(EntityDamageEvent.DamageModifier modifier, double damage) {
        this.damageData = new DamageData(damage, modifier);
    }

}
