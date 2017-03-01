package me.brainmix.itemapi.api.controllers;

import me.brainmix.itemapi.api.CustomItem;
import me.brainmix.itemapi.api.ItemRegister;
import me.brainmix.itemapi.api.ItemUser;
import me.brainmix.itemapi.api.events.*;
import me.brainmix.itemapi.api.interfaces.Throwable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Collection;

public class ItemThrowManager extends AbstractItemManager {

    public static double NEARBY_ENTITIES_OFFSET = 0.3;

    public ItemThrowManager(ItemRegister register) {
        super(register);
    }

    public Item throwItem(CustomItem item, ItemUser user) {
        Player player = user.getPlayer();

        Item thrownItem = player.getWorld().dropItemNaturally(player.getEyeLocation(), item.getOptions().getItemStack());
        thrownItem.setVelocity(player.getLocation().getDirection());
        thrownItem.setPickupDelay(1000000);

        if(!(item instanceof Throwable)) return thrownItem;

        ItemThrowEvent event = new ItemThrowEvent(player, item.getOptions().getItemStack(), item.getDelayManager().getTimeLeft(player), thrownItem);
        getRegister().getEventManager().callEvent(item, event);

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
                        getRegister().getEventManager().callEvent(item, itemHitEntityEvent);
                        if(itemHitEntityEvent.isCancelled()) {
                            this.cancel();
                            return;
                        }

                        if(entity instanceof Player) {
                            ItemHitPlayerEvent itemHitPlayerEvent = new ItemHitPlayerEvent(player, item.getOptions().getItemStack(), item.getDelayManager().getTimeLeft(player), thrownItem, (Player) entity);
                            getRegister().getEventManager().callEvent(item, itemHitPlayerEvent);
                            if(itemHitPlayerEvent.isCancelled()) {
                                this.cancel();
                                return;
                            }
                        }
                    }
                }

                if(thrownItem.isOnGround()) {
                    ItemHitGroundEvent hitGroundEvent = new ItemHitGroundEvent(player, item.getOptions().getItemStack(), item.getDelayManager().getTimeLeft(player), thrownItem);
                    getRegister().getEventManager().callEvent(item, hitGroundEvent);
                    thrownItem.setPickupDelay(0);
                    this.cancel();
                }

                ItemFlyTickEvent event = new ItemFlyTickEvent(player, item.getOptions().getItemStack(), item.getDelayManager().getTimeLeft(player), thrownItem, timeInAir);
                getRegister().getEventManager().callEvent(item, event);
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
        }.runTaskTimer(getRegister().getPlugin(), 0, 0);



        return thrownItem;
    }

}
