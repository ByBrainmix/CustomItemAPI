package me.brainmix.itemapi.api.events;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDamageEvent.DamageModifier;
import org.bukkit.inventory.ItemStack;

public class ItemLeftClickEntityEvent extends ItemClickEntityEvent {

    private DamageCause cause;
    private DamageData damageData;

    public ItemLeftClickEntityEvent(Player player, ItemStack item, int delay, Entity entity, DamageCause cause) {
        super(player, item, delay, entity);
        this.cause = cause;
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

    public void setDamage(DamageModifier modifier, double damage) {
        this.damageData = new DamageData(damage, modifier);
    }

    public static class DamageData {
        private double damage;
        private DamageModifier modifier;

        public DamageData(double damage, DamageModifier modifier) {
            this.damage = damage;
            this.modifier = modifier;

        }

        public double getDamage() {
            return damage;
        }

        public void setDamage(double damage) {
            this.damage = damage;
        }

        public DamageModifier getModifier() {
            return modifier;
        }

        public void setModifier(DamageModifier modifier) {
            this.modifier = modifier;
        }


    }

}
