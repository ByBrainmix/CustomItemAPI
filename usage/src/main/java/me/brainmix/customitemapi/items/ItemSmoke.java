package me.brainmix.customitemapi.items;

import me.brainmix.customitemapi.MainPlugin;
import me.brainmix.customitemapi.utils.BParticle;
import me.brainmix.itemapi.api.ClickSound;
import me.brainmix.itemapi.api.CustomItem;
import me.brainmix.itemapi.api.ItemOptions;
import me.brainmix.itemapi.api.controllers.ItemHandler;
import me.brainmix.itemapi.api.events.ItemHitGroundEvent;
import me.brainmix.itemapi.api.events.ItemRightClickEvent;
import me.brainmix.itemapi.api.interfaces.Clickable;
import me.brainmix.itemapi.api.interfaces.Throwable;
import me.brainmix.itemapi.api.utils.ItemUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class ItemSmoke extends CustomItem implements Clickable, Throwable {

    private int smokeTime = 80;
    private float smokeRadius = 2;

    @Override
    protected void init(ItemOptions options) {
        options.setItemStack(ItemUtils.getItemStack(Material.FIREWORK_CHARGE, "&8Smoke"));
        options.setClickSound(new ClickSound(Sound.SHOOT_ARROW, 1, 0.3f));
        options.setRemoveAfterUse(1);
    }

    @ItemHandler
    public void onClick(ItemRightClickEvent event) {
        event.getUser().throwItem(this);
    }

    @ItemHandler
    public void onHitGround(ItemHitGroundEvent event) {
        Item item = event.getThrownItem();
        createSmoke(item.getLocation());
        new ClickSound(Sound.EXPLODE, 1, 1).playAt(item.getLocation());
        item.remove();
    }

    private void createSmoke(Location loc) {
        new BukkitRunnable() {
            int c = 0;

            @Override
            public void run() {

                BParticle.EXPLOSION_NORMAL.playAll(loc, true, smokeRadius, smokeRadius, smokeRadius, 0, 100);
                BParticle.EXPLOSION_HUGE.playAll(loc, true, smokeRadius - 0.5, smokeRadius - 0.5, smokeRadius - 0.5, 0, 1);

                float radius = smokeRadius + 1;
                for (Entity e : loc.getWorld().getNearbyEntities(loc, radius, radius, radius)) {
                    if (!(e instanceof Player)) continue;
                    Player p = (Player) e;
                    p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 60, 0));
                    p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 60, 3));
                }


                if (c == smokeTime) {
                    this.cancel();
                }
                c++;

            }
        }.runTaskTimer(MainPlugin.INSTANCE, 0, 1);
    }

}