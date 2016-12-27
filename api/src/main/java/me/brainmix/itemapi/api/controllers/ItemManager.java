package me.brainmix.itemapi.api.controllers;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import me.brainmix.itemapi.api.CustomItem;
import me.brainmix.itemapi.api.ItemOptions;
import me.brainmix.itemapi.api.events.*;
import me.brainmix.itemapi.api.events.ItemLeftClickEntityEvent.DamageData;
import me.brainmix.itemapi.api.interfaces.Clickable;
import me.brainmix.itemapi.api.interfaces.EntityClickable;
import me.brainmix.itemapi.api.utils.Actionbar;
import me.brainmix.itemapi.api.utils.Interactables;
import me.brainmix.itemapi.api.utils.ItemUtils;

public class ItemManager implements Listener {

    public static int WAIT_FOR_DAMAGEEVENT_TICKS = 2;
    public static int BETWEEN_INTERACT_AT_ENTITY = 3;

    private ItemEventManager eventManager;
    private Set<CustomItem> items = new HashSet<>();
    private Set<CustomItem> isHandleAfter = new HashSet<>();
    private Map<Player, Long> rightClicked = new HashMap<>();
    private Set<Player> leftClicked = new HashSet<>();

    private InteractConsumer leftClickConsumer = (ev, i) -> {
        ItemLeftClickEvent e = new ItemLeftClickEvent(ev.getPlayer(), ev.getPlayer().getItemInHand(), i.getDelayManager().getTimeLeft(ev.getPlayer()), ev.getAction());
        eventManager.callEvent(i, e);
        if(e.isCancelled()) ev.setCancelled(true);
        if(i.getDelayManager().getTimeLeft(ev.getPlayer()) == -1 && eventManager.hasWithoutDelay(i.getId(), ItemLeftClickEvent.class)) handleAfter(i, ev.getPlayer(), ev.getPlayer().getItemInHand(), i.getOptions());
    };

    private InteractConsumer clickConsumer = (ev, i) -> {
        if(eventManager.hasWith(i.getId(), ItemRightClickEntityEvent.class) && rightClicked.containsKey(ev.getPlayer()) && System.currentTimeMillis() - rightClicked.get(ev.getPlayer()) <= BETWEEN_INTERACT_AT_ENTITY) return;
        ItemClickEvent e = new ItemClickEvent(ev.getPlayer(), ev.getPlayer().getItemInHand(), i.getDelayManager().getTimeLeft(ev.getPlayer()), ev.getAction());
        eventManager.callEvent(i, e);
        if(e.isCancelled()) e.setCancelled(true);
        if(i.getDelayManager().getTimeLeft(ev.getPlayer()) == -1 && eventManager.hasWithoutDelay(i.getId(), ItemClickEvent.class)) handleAfter(i, ev.getPlayer(), ev.getPlayer().getItemInHand(), i.getOptions());
    };

    private Plugin plugin;

    public ItemManager(Plugin plugin, ItemEventManager eventManager, Set<CustomItem> items) {
        this.eventManager = eventManager;
        this.items = items;
        this.plugin = plugin;
    }

    public void setItems(Set<CustomItem> items) {
        this.items = items;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {

        Player player = event.getPlayer();
        ItemStack clickedItem = player.getItemInHand();

        items.forEach(item -> {
            if(item.compare(clickedItem) && item instanceof Clickable) handleClickableItem(event, item);
        });

    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        items.forEach(item -> {
            if(item.compare(event.getItemDrop().getItemStack())){
                if(!item.getOptions().isDrop()) event.setCancelled(true);

                ItemDropEvent e = new ItemDropEvent(event.getPlayer(), event.getItemDrop().getItemStack(), item.getDelayManager().getTimeLeft(event.getPlayer()), event.getItemDrop());
                eventManager.callEvent(item, e);
                if(e.isCancelled()) event.setCancelled(true);
            }
        });
        Bukkit.getOnlinePlayers().stream().map(UserManager.a()::getUser).forEach(u -> {
            if(u.isFreezed(event.getItemDrop().getItemStack())) event.setCancelled(true);
        });
    }

    @EventHandler
    public void onMove(InventoryClickEvent event) {
        items.forEach(item -> {
            if((item.compare(event.getCurrentItem()) || item.compare(event.getCursor())) && !item.getOptions().isMove()) event.setCancelled(true);
        });
        Bukkit.getOnlinePlayers().stream().map(UserManager.a()::getUser).forEach(u -> {
            if(u.isFreezed(event.getCursor()) || u.isFreezed(event.getCurrentItem())) event.setCancelled(true);
        });
    }

    @EventHandler
    public void onPlayerItemHeld(PlayerItemHeldEvent event) {
        items.forEach(item -> {
            if(item.compare(event.getPlayer().getInventory().getItem(event.getPreviousSlot()))) {
                if(item.getDelayManager().hasDelay(event.getPlayer())) {
                    Actionbar.send(event.getPlayer(), "");
                }
            }
        });
    }

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        if(!(event.getDamager() instanceof Player)) return;
        Player damager = (Player) event.getDamager();
        items.forEach(item -> {
            if(damager.getItemInHand() != null && item.compare(damager.getItemInHand()) && item instanceof EntityClickable) {

                if(item.getOptions().isDisabled()) return;
                int delay = item.getDelayManager().getTimeLeft(damager);
                if(item.getOptions().isCancellDefaults()) event.setCancelled(true);

                ItemLeftClickEntityEvent e = new ItemLeftClickEntityEvent(damager, damager.getItemInHand(), delay, event.getEntity(), event.getCause());
                eventManager.callEvent(item, e);

                if(leftClicked.contains(damager)) leftClicked.remove(damager);

                DamageData damageData = e.getDamageData();
                if(damageData != null) {
                    if(damageData.getModifier() != null) event.setDamage(damageData.getModifier(), damageData.getDamage());
                    else event.setDamage(damageData.getDamage());
                }

                if(e.isCancelled()) event.setCancelled(true);
            }
        });
    }

    @EventHandler
    public void onEntityInteract(PlayerInteractAtEntityEvent event) {
        Player player = event.getPlayer();
        items.forEach(item -> {
            if(player.getItemInHand() != null && item.compare(player.getItemInHand()) && item instanceof EntityClickable) {

                if(item.getOptions().isDisabled()) return;
                int delay = item.getDelayManager().getTimeLeft(player);
                if(item.getOptions().isCancellDefaults()) event.setCancelled(true);

                ItemRightClickEntityEvent e = new ItemRightClickEntityEvent(player, player.getItemInHand(), delay, event.getRightClicked(), event.getClickedPosition());
                boolean called = eventManager.callEvent(item, e);
                if(called) rightClicked.put(player, System.currentTimeMillis());

                if(e.isCancelled()) event.setCancelled(true);
            }
        });
    }

    @EventHandler
    public void onPlayerPickupItem(PlayerPickupItemEvent event) {
        Item itemEntity = event.getItem();
        Player player = event.getPlayer();
        items.forEach(item -> {
            if(item.compare(itemEntity.getItemStack())) {
                int delay = item.getDelayManager().getTimeLeft(player);
                if(item.getOptions().isCancellDefaults()) event.setCancelled(true);

                ItemPickupEvent e = new ItemPickupEvent(player, itemEntity.getItemStack(), delay, itemEntity, event.getRemaining());
                boolean called = eventManager.callEvent(item, e);

                if(e.isCancelled()) event.setCancelled(true);
            }
        });
    }

    @EventHandler
    public void onItemDespawn(org.bukkit.event.entity.ItemDespawnEvent event) {
        Item itemEntity = event.getEntity();
        items.forEach(item -> {
            if(item.compare(itemEntity.getItemStack())) {
                if(item.getOptions().isCancellDefaults()) event.setCancelled(true);
                if(!item.getOptions().isDespawnable()) event.setCancelled(true);

                ItemDespawnEvent e = new ItemDespawnEvent(itemEntity.getItemStack(), itemEntity, event.getLocation());
                eventManager.callEvent(item, e);

                if(e.isCancelled()) event.setCancelled(true);
            }
        });
    }

    private void handleClickableItem(PlayerInteractEvent event, CustomItem item) {

        Player player = event.getPlayer();
        ItemStack clickedItem = player.getItemInHand();
        Action action = event.getAction();
        Block block =  event.getClickedBlock();
        BlockFace face = event.getBlockFace();
        ItemOptions options = item.getOptions();
        String id = item.getId();

        if(isHandleAfter.contains(item)) isHandleAfter.remove(item);

        if(options.isDisabled()) return;

        int delay = item.getDelayManager().getTimeLeft(player);

        if(options.isInteractWithOthers() && action == Action.RIGHT_CLICK_BLOCK && Interactables.get().contains(block.getType())) return;
        if(options.isCancellDefaults()) event.setCancelled(true);

        if(action != Action.PHYSICAL && eventManager.hasWith(id, ItemClickEvent.class)) {
            if(item instanceof EntityClickable && eventManager.hasWith(id, ItemLeftClickEntityEvent.class) && (action == Action.LEFT_CLICK_BLOCK || action == Action.LEFT_CLICK_AIR)) {
                leftClicked.add(player);
                Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                    if(leftClicked.contains(player)) {
                        clickConsumer.handle(event, item);
                        leftClicked.remove(player);
                    }
                }, WAIT_FOR_DAMAGEEVENT_TICKS);
            } else {
                clickConsumer.handle(event, item);
            }
        }

        if((action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) && eventManager.hasWith(id, ItemRightClickEvent.class)) {
            /* Avoid handling ItemRightClickEvent if ItemRightClickEntityEvent gets called */
            if(eventManager.hasWith(id, ItemRightClickEntityEvent.class) && rightClicked.containsKey(player) && System.currentTimeMillis() - rightClicked.get(player) <= BETWEEN_INTERACT_AT_ENTITY) return;
            ItemRightClickEvent e = new ItemRightClickEvent(player, clickedItem, delay, action);
            eventManager.callEvent(item, e);
            if(e.isCancelled()) event.setCancelled(true);
            if(delay == -1 && eventManager.hasWithoutDelay(id, ItemRightClickEvent.class)) handleAfter(item, player, clickedItem, options);
        }

        if((action == Action.LEFT_CLICK_BLOCK || action == Action.LEFT_CLICK_AIR) && eventManager.hasWith(id, ItemLeftClickEvent.class)) {
            /* if player left clicked an entity already the leftClick code below wont handle
            * playerinteract event gets called before entitydamage event, so put the code into a consumer,
            * wait some ticks and if the entitydamage event didn't get called, handle the normal leftclickevent */
            if(item instanceof EntityClickable && eventManager.hasWith(id, ItemLeftClickEntityEvent.class)) {
                leftClicked.add(player);
                Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                    if(leftClicked.contains(player)) {
                        leftClickConsumer.handle(event, item);
                        leftClicked.remove(player);
                    }
                }, WAIT_FOR_DAMAGEEVENT_TICKS);
            } else {
                leftClickConsumer.handle(event, item);
            }
        }

        if((action == Action.RIGHT_CLICK_BLOCK || action == Action.LEFT_CLICK_BLOCK) && eventManager.hasWith(id, ItemClickBlockEvent.class)) {
            ItemClickBlockEvent e = new ItemClickBlockEvent(player, clickedItem, delay, action, block, face);
            eventManager.callEvent(item, e);
            if(e.isCancelled()) event.setCancelled(true);
            if(delay == -1 && eventManager.hasWithoutDelay(id, ItemClickBlockEvent.class)) handleAfter(item, player, clickedItem, options);
        }


        if(action == Action.LEFT_CLICK_BLOCK && eventManager.hasWith(id, ItemLeftClickBlockEvent.class)) {
            ItemLeftClickBlockEvent e = new ItemLeftClickBlockEvent(player, clickedItem, delay, action, block, face);
            eventManager.callEvent(item, e);
            if(e.isCancelled()) event.setCancelled(true);
            if(delay == -1 && eventManager.hasWithoutDelay(id, ItemLeftClickBlockEvent.class)) handleAfter(item, player, clickedItem, options);
        }

        if(action == Action.RIGHT_CLICK_BLOCK && eventManager.hasWith(id, ItemRightClickBlockEvent.class)) {
            ItemRightClickBlockEvent e = new ItemRightClickBlockEvent(player, clickedItem, delay, action, block, face);
            eventManager.callEvent(item, e);
            if(e.isCancelled()) event.setCancelled(true);
            if(delay == -1 && eventManager.hasWithoutDelay(id, ItemRightClickBlockEvent.class)) handleAfter(item, player, clickedItem, options);
        }

    }

    private void handleAfter(CustomItem item, Player player, ItemStack clickedItem, ItemOptions options) {

        if(isHandleAfter.contains(item)) return;
        isHandleAfter.add(item);

        if(options.getClickSound() != null) {
            options.getClickSound().play(player);
        }

        if(options.getItemAfterUse() != null) {
            if(item.compare(player.getItemInHand())) {
                player.setItemInHand(options.getItemAfterUse());
            } else {
                ItemUtils.replaceItem(player, clickedItem, options.getItemAfterUse());
            }
        }

        if(options.getRemoveAfterUse() != 0) {
            int amount = clickedItem.getAmount();
            amount -= options.getRemoveAfterUse();
            if(amount <= 0) {
                player.setItemInHand(new ItemStack(Material.AIR));
            } else {
                clickedItem.setAmount(amount);
                player.setItemInHand(clickedItem);
            }
        }

        if(options.getAutoItemDelay() != null) {
            item.getDelayManager().startDelay(player, options.getAutoItemDelay());
        }

    }

    private interface InteractConsumer {
        void handle(PlayerInteractEvent event, CustomItem item);
    }

}
