package me.brainmix.itemapi.api.controllers;

import me.brainmix.itemapi.api.CustomItem;
import me.brainmix.itemapi.api.ItemRegister;
import me.brainmix.itemapi.api.ItemUser;
import me.brainmix.itemapi.api.events.*;
import me.brainmix.itemapi.api.interfaces.Shootable;
import org.bukkit.Bukkit;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;

public class ItemProjectileManager extends AbstractItemManager implements Listener {

    public static int TIME_UNTIL_REMOVE_ARROW = 2;

    private Map<Integer, ItemShootEvent> projectiles = new HashMap<>();
    private Map<Integer, CustomItem> items = new HashMap<>();

    public ItemProjectileManager(ItemRegister register) {
        super(register);
        Bukkit.getPluginManager().registerEvents(this, register.getPlugin());
    }

    public <T extends Projectile> T shoot(CustomItem item, ItemUser user, Class<T> type) {
        Player player = user.getPlayer();

        T snowball = player.launchProjectile(type);
        snowball.setVelocity(player.getLocation().getDirection());

        if(!(item instanceof Shootable)) return snowball;

        ItemShootEvent shootEvent = new ItemShootEvent(player, item.getOptions().getItemStack(), item.getDelayManager().getTimeLeft(player), snowball, type);
        getRegister().getEventManager().callEvent(item, shootEvent);

        if(shootEvent.isCancelled()) {
            snowball.remove();
            return snowball;
        }

        projectiles.put(snowball.getEntityId(), shootEvent);
        items.put(snowball.getEntityId(), item);

        new BukkitRunnable() {
            int timeInAir = 0;
            @Override
            public void run() {

                ItemProjectileFlyTickEvent event = new ItemProjectileFlyTickEvent(player, item.getOptions().getItemStack(), item.getDelayManager().getTimeLeft(player), snowball, type, timeInAir);
                getRegister().getEventManager().callEvent(item, event);
                if(event.isCancelled()) {
                    this.cancel();
                    return;
                }

                if(!snowball.isValid() || snowball.isDead()) {
                    this.cancel();
                    return;
                }
                timeInAir++;
            }
        }.runTaskTimer(getRegister().getPlugin(), 0, 0);

        return snowball;
    }

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent event) {
        if(projectiles.containsKey(event.getEntity().getEntityId())) {
            ItemShootEvent shootEvent = projectiles.get(event.getEntity().getEntityId());

            CustomItem item = items.get(event.getEntity().getEntityId());
            Player player = shootEvent.getPlayer();

            ItemProjectileHitEvent hitGroundEvent = new ItemProjectileHitEvent(player, shootEvent.getItem(), shootEvent.getTimeLeft(), shootEvent.getProjectile(), shootEvent.getType());
            getRegister().getEventManager().callEvent(items.get(event.getEntity().getEntityId()), hitGroundEvent);
            if(hitGroundEvent.isCancelled()) {
                return;
            }

            if(event.getEntity() instanceof Arrow) {
                Bukkit.getScheduler().scheduleSyncDelayedTask(getRegister().getPlugin(), () -> {
                    items.remove(event.getEntity().getEntityId());
                    projectiles.remove(event.getEntity().getEntityId());
                }, TIME_UNTIL_REMOVE_ARROW);
            } else {
                items.remove(event.getEntity().getEntityId());
                projectiles.remove(event.getEntity().getEntityId());
            }
        }
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if(event.getDamager() instanceof Projectile) {

            Projectile projectile = (Projectile) event.getDamager();
            if(!projectiles.containsKey(projectile.getEntityId())) return;
            Entity entity = event.getEntity();
            ItemShootEvent shootEvent = projectiles.get(projectile.getEntityId());
            Player player = shootEvent.getPlayer();
            CustomItem item = items.get(projectile.getEntityId());


            ItemProjectileHitEntityEvent itemHitEntityEvent = new ItemProjectileHitEntityEvent(player, shootEvent.getItem(), shootEvent.getTimeLeft(), projectile, shootEvent.getType(), entity, event.getCause());
            getRegister().getEventManager().callEvent(item, itemHitEntityEvent);
            if(itemHitEntityEvent.isCancelled()) {
                event.setCancelled(true);
            }

            if(entity instanceof Player) {
                ItemProjectileHitPlayerEvent itemHitPlayerEvent = new ItemProjectileHitPlayerEvent(player, shootEvent.getItem(), shootEvent.getTimeLeft(), projectile, shootEvent.getType(), (Player) entity, event.getCause());
                getRegister().getEventManager().callEvent(item, itemHitPlayerEvent);
                if(itemHitPlayerEvent.isCancelled()) {
                    event.setCancelled(true);
                }
            }

            projectiles.remove(projectile.getEntityId());
            items.remove(projectile.getEntityId());
        }
    }

    @EventHandler
    public void onShoot(EntityShootBowEvent event) {
        ItemStack itemStack = event.getBow();
        CustomItem item = getRegister().getItems().stream().filter(i -> i.compare(itemStack)).findFirst().orElse(null);
        Arrow arrow = event.getProjectile() instanceof Arrow ? (Arrow) event.getProjectile() : null;
        if(item == null) return;
        if(arrow == null) return;
        if(!(item instanceof Shootable)) return;
        if(!(event.getEntity() instanceof Player)) return;

        Player player = (Player) event.getEntity();
        ItemShootEvent shootEvent = new ItemShootEvent(player, item.getOptions().getItemStack(), item.getDelayManager().getTimeLeft(player), arrow, Arrow.class);
        getRegister().getEventManager().callEvent(item, shootEvent);

        if(shootEvent.isCancelled()) {
            arrow.remove();
            return;
        }

        ItemShootArrowEvent shootArrowEvent = new ItemShootArrowEvent(player, itemStack, item.getDelayManager().getTimeLeft(player), arrow, event.getForce());
        getRegister().getEventManager().callEvent(item, shootArrowEvent);

        if(shootArrowEvent.isCancelled()) {
            arrow.remove();
            return;
        }

        projectiles.put(arrow.getEntityId(), shootEvent);
        items.put(arrow.getEntityId(), item);

        new BukkitRunnable() {
            int timeInAir = 0;
            @Override
            public void run() {

                ItemProjectileFlyTickEvent event = new ItemProjectileFlyTickEvent(player, item.getOptions().getItemStack(), item.getDelayManager().getTimeLeft(player), arrow, Arrow.class, timeInAir);
                getRegister().getEventManager().callEvent(item, event);
                if(event.isCancelled()) {
                    this.cancel();
                    return;
                }

                if(!arrow.isValid() || arrow.isDead()) {
                    this.cancel();
                    return;
                }
                timeInAir++;
            }
        }.runTaskTimer(getRegister().getPlugin(), 0, 0);

    }

}
