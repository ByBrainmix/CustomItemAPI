package me.brainmix.itemapi.api.controllers;

import java.util.Collection;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import me.brainmix.itemapi.api.CustomItem;
import me.brainmix.itemapi.api.ItemUser;
import me.brainmix.itemapi.api.events.ItemFlyTickEvent;
import me.brainmix.itemapi.api.events.ItemHitEntityEvent;
import me.brainmix.itemapi.api.events.ItemHitGroundEvent;
import me.brainmix.itemapi.api.events.ItemThrowEvent;
import me.brainmix.itemapi.api.interfaces.Throwable;

public class ThrownItemManager {

    public static double NEARBY_ENTITIES_OFFSET = 0.3;

    private Plugin plugin;
    private ItemEventManager eventManager;

    public ThrownItemManager(Plugin plugin, ItemEventManager eventManager) {
        this.plugin = plugin;
        this.eventManager = eventManager;
    }

    public Item throwItem(CustomItem item, ItemUser user) {
        Player player = user.getPlayer();

        Item thrownItem = player.getWorld().dropItemNaturally(player.getEyeLocation(), item.getOptions().getItemStack());
        thrownItem.setVelocity(player.getLocation().getDirection());
        thrownItem.setPickupDelay(1000000);

        if(!(item instanceof Throwable)) return thrownItem;

        ItemThrowEvent event = new ItemThrowEvent(player, item.getOptions().getItemStack(), item.getDelayManager().getTimeLeft(player), thrownItem);
        eventManager.callEvent(item, event);

        if(event.isCancelled()) {
            thrownItem.remove();
            return thrownItem;
        }


        new BukkitRunnable() {
            int timeInAir = 0;
            @Override
            public void run() {


                Collection<Entity> entities = player.getWorld().getNearbyEntities(thrownItem.getLocation(), NEARBY_ENTITIES_OFFSET, NEARBY_ENTITIES_OFFSET, NEARBY_ENTITIES_OFFSET);
                if(!entities.isEmpty()) {
                    for(Entity entity : entities) {
                        ItemHitEntityEvent itemHitEntityEvent = new ItemHitEntityEvent(player, item.getOptions().getItemStack(), item.getDelayManager().getTimeLeft(player), thrownItem, entity);
                        eventManager.callEvent(item, itemHitEntityEvent);
                        if(itemHitEntityEvent.isCancelled()) {
                            this.cancel();
                            return;
                        }
                    }
                }

                if(thrownItem.isOnGround()) {
                    ItemHitGroundEvent hitGroundEvent = new ItemHitGroundEvent(player, item.getOptions().getItemStack(), item.getDelayManager().getTimeLeft(player), thrownItem);
                    eventManager.callEvent(item, hitGroundEvent);
                    if(hitGroundEvent.isCancelled()) {
                        this.cancel();
                        return;
                    }
                }

                ItemFlyTickEvent event = new ItemFlyTickEvent(player, item.getOptions().getItemStack(), item.getDelayManager().getTimeLeft(player), thrownItem, timeInAir);
                eventManager.callEvent(item, event);
                if(event.isCancelled()) {
                    this.cancel();
                    return;
                }

                if(!thrownItem.isValid() || thrownItem.isDead()) {
                    this.cancel();
                    return;
                }


                timeInAir++;
            }
        }.runTaskTimer(plugin, 0, 0);



        return thrownItem;
    }

}
