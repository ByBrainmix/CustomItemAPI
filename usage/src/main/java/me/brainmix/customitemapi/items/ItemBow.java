package me.brainmix.customitemapi.items;

import me.brainmix.customitemapi.MainPlugin;
import me.brainmix.customitemapi.utils.BParticle;
import me.brainmix.itemapi.api.CustomItem;
import me.brainmix.itemapi.api.ItemOptions;
import me.brainmix.itemapi.api.controllers.ItemHandler;
import me.brainmix.itemapi.api.events.ItemProjectileFlyTickEvent;
import me.brainmix.itemapi.api.events.ItemProjectileHitEntityEvent;
import me.brainmix.itemapi.api.events.ItemProjectileHitEvent;
import me.brainmix.itemapi.api.events.ItemShootArrowEvent;
import me.brainmix.itemapi.api.interfaces.Shootable;
import me.brainmix.itemapi.api.utils.ItemUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;

public class ItemBow extends CustomItem implements Shootable {

    @Override
    protected void init(ItemOptions options) {
        options.setItemStack(ItemUtils.getItemStack(Material.BOW, "&dUltraBow"));
        options.setInteractWithOthers(true);
    }

    @ItemHandler
    public void onShoot(ItemShootArrowEvent event) {
        BParticle.SMOKE_LARGE.playAll(event.getProjectile().getLocation(), true, 0.5, 0.5, 0.5, 0.1f, 10);
    }

    @ItemHandler
    public void onFly(ItemProjectileFlyTickEvent event) {
        BParticle.FLAME.playAll(event.getProjectile().getLocation());
    }

    @ItemHandler
    public void onHit(ItemProjectileHitEvent event) {
        Bukkit.getScheduler().scheduleSyncDelayedTask(MainPlugin.INSTANCE, () -> event.getProjectile().remove(), 1);
    }
    @ItemHandler
    public void onHitEntity(ItemProjectileHitEntityEvent event) {
        event.getUser().sendValue("hit", event.getEntity().getType());
        BParticle.FLAME.playAll(event.getProjectile().getLocation(), true, 0.5, 0.5, 0.5, 0.1f, 30);
        event.getEntity().setFireTicks(40);
        event.setCancelled(true);
    }


}
