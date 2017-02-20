package me.brainmix.customitemapi.items;

import me.brainmix.customitemapi.utils.BParticle;
import me.brainmix.itemapi.api.ClickSound;
import me.brainmix.itemapi.api.CustomItem;
import me.brainmix.itemapi.api.ItemOptions;
import me.brainmix.itemapi.api.controllers.ItemHandler;
import me.brainmix.itemapi.api.events.ItemProjectileFlyTickEvent;
import me.brainmix.itemapi.api.events.ItemProjectileHitEntityEvent;
import me.brainmix.itemapi.api.events.ItemProjectileHitEvent;
import me.brainmix.itemapi.api.events.ItemRightClickEvent;
import me.brainmix.itemapi.api.interfaces.Clickable;
import me.brainmix.itemapi.api.interfaces.Shootable;
import me.brainmix.itemapi.api.utils.ItemUtils;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Snowball;
import org.bukkit.util.Vector;

public class ItemShooter extends CustomItem implements Clickable, Shootable {

    @Override
    protected void init(ItemOptions options) {
        options.setItemStack(ItemUtils.getItemStack(Material.GOLD_INGOT, "&6Shooter"));
        options.setClickSound(new ClickSound(Sound.FIREWORK_BLAST, 1, 1));
        options.setInteractWithOthers(true);
    }

    @ItemHandler
    public void onClick(ItemRightClickEvent event) {
        event.getUser().shootProjectile(this, Snowball.class);
    }

    @ItemHandler
    public void onFly(ItemProjectileFlyTickEvent event) {
        BParticle.CRIT_MAGIC.playAll(event.getProjectile().getLocation());
    }

    @ItemHandler
    public void onHit(ItemProjectileHitEvent event) {
        BParticle.SMOKE_LARGE.playAll(event.getProjectile().getLocation(), true, 0.5, 0.5, 0.5, 0.1f, 30);
    }

    @ItemHandler
    public void onHitEntity(ItemProjectileHitEntityEvent event) {
        event.getEntity().setVelocity(event.getEntity().getVelocity().add(new Vector(0, 0.8, 0)));
        event.setCancelled(true);
    }

}
