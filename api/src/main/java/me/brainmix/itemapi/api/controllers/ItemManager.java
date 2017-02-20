package me.brainmix.itemapi.api.controllers;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import me.brainmix.itemapi.api.ItemRegister;
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

import me.brainmix.itemapi.api.CustomItem;
import me.brainmix.itemapi.api.ItemOptions;
import me.brainmix.itemapi.api.events.*;
import me.brainmix.itemapi.api.events.ItemLeftClickEntityEvent.DamageData;
import me.brainmix.itemapi.api.interfaces.Clickable;
import me.brainmix.itemapi.api.interfaces.EntityClickable;
import me.brainmix.itemapi.api.utils.Actionbar;
import me.brainmix.itemapi.api.utils.Interactables;
import me.brainmix.itemapi.api.utils.ItemUtils;

public class ItemManager extends AbstractItemManager implements Listener {

    public static int WAIT_FOR_DAMAGEEVENT_TICKS = 2;
    public static int BETWEEN_INTERACT_AT_ENTITY = 3;

    private Set<CustomItem> items = new HashSet<>();
    private Set<CustomItem> isHandleAfter = new HashSet<>();
    private Map<Player, Long> rightClicked = new HashMap<>();
    private Set<Player> leftClicked = new HashSet<>();

    private InteractConsumer leftClickConsumer = (ev, i) -> {
        ItemLeftClickEvent e = new ItemLeftClickEvent(ev.getPlayer(), ev.getPlayer().getItemInHand(), i.getDelayManager().getTimeLeft(ev.getPlayer()), ev.getAction());
        getRegister().getEventManager().callEvent(i, e);
        if(e.isCancelled()) ev.setCancelled(true);
        if(i.getDelayManager().getTimeLeft(ev.getPlayer()) == -1 && getRegister().getEventManager().hasWithoutDelay(i.getId(), ItemLeftClickEvent.class)) handleAfter(i, ev.getPlayer(), ev.getPlayer().getItemInHand(), i.getOptions());
    };

    private InteractConsumer clickConsumer = (ev, i) -> {
        if((getRegister().getEventManager().hasWith(i.getId(), ItemRightClickEntityEvent.class) || getRegister().getEventManager().hasWith(i.getId(), ItemRightClickPlayerEvent.class)) && rightClicked.containsKey(ev.getPlayer()) && System.currentTimeMillis() - rightClicked.get(ev.getPlayer()) <= BETWEEN_INTERACT_AT_ENTITY) return;
        ItemClickEvent e = new ItemClickEvent(ev.getPlayer(), ev.getPlayer().getItemInHand(), i.getDelayManager().getTimeLeft(ev.getPlayer()), ev.getAction());
        getRegister().getEventManager().callEvent(i, e);
        if(e.isCancelled()) e.setCancelled(true);
        if(i.getDelayManager().getTimeLeft(ev.getPlayer()) == -1 && getRegister().getEventManager().hasWithoutDelay(i.getId(), ItemClickEvent.class)) handleAfter(i, ev.getPlayer(), ev.getPlayer().getItemInHand(), i.getOptions());
    };

    public ItemManager(ItemRegister register, Set<CustomItem> items) {
        super(register);
        this.items = items;
        Bukkit.getPluginManager().registerEvents(this, register.getPlugin());
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
                getRegister().getEventManager().callEvent(item, e);
                if(e.isCancelled()) event.setCancelled(true);
            }
        });
        Bukkit.getOnlinePlayers().stream().map(getRegister().getUserManager()::getUser).forEach(u -> {
            if(u.isFreezed(event.getItemDrop().getItemStack())) event.setCancelled(true);
        });
    }

    @EventHandler
    public void onMove(InventoryClickEvent event) {
        items.forEach(item -> {
            if((item.compare(event.getCurrentItem()) || item.compare(event.getCursor())) && !item.getOptions().isMove()) event.setCancelled(true);
        });
        Bukkit.getOnlinePlayers().stream().map(getRegister().getUserManager()::getUser).forEach(u -> {
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
                boolean called = getRegister().getEventManager().callEvent(item, e);

                if(event.getEntity() instanceof Player) {
                    ItemLeftClickPlayerEvent e1 = new ItemLeftClickPlayerEvent(damager, damager.getItemInHand(), delay, (Player) event.getEntity(), event.getCause());
                    boolean called1 = getRegister().getEventManager().callEvent(item, e1);
                }

                if(leftClicked.contains(damager)) leftClicked.remove(damager);

                DamageData damageData = e.getDamageData();
                if(damageData != null) {
                    if(damageData.getModifier() != null) event.setDamage(damageData.getModifier(), damageData.getDamage());
                    else event.setDamage(damageData.getDamage());
                }

                if(called) event.setCancelled(e.isCancelled());
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
                boolean called = getRegister().getEventManager().callEvent(item, e);
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
                boolean called = getRegister().getEventManager().callEvent(item, e);

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
                getRegister().getEventManager().callEvent(item, e);

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

        if(action != Action.PHYSICAL && getRegister().getEventManager().hasWith(id, ItemClickEvent.class)) {
            if(item instanceof EntityClickable && getRegister().getEventManager().hasWith(id, ItemLeftClickEntityEvent.class) && (action == Action.LEFT_CLICK_BLOCK || action == Action.LEFT_CLICK_AIR)) {
                leftClicked.add(player);
                Bukkit.getScheduler().scheduleSyncDelayedTask(getRegister().getPlugin(), () -> {
                    if(leftClicked.contains(player)) {
                        clickConsumer.handle(event, item);
                        leftClicked.remove(player);
                    }
                }, WAIT_FOR_DAMAGEEVENT_TICKS);
            } else {
                clickConsumer.handle(event, item);
            }
        }

        if((action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) && getRegister().getEventManager().hasWith(id, ItemRightClickEvent.class)) {
            /* Avoid handling ItemRightClickEvent if ItemRightClickEntityEvent gets called */
            if(getRegister().getEventManager().hasWith(id, ItemRightClickEntityEvent.class) && rightClicked.containsKey(player) && System.currentTimeMillis() - rightClicked.get(player) <= BETWEEN_INTERACT_AT_ENTITY) return;
            ItemRightClickEvent e = new ItemRightClickEvent(player, clickedItem, delay, action);
            getRegister().getEventManager().callEvent(item, e);
            if(e.isCancelled()) event.setCancelled(true);
            if(delay == -1 && getRegister().getEventManager().hasWithoutDelay(id, ItemRightClickEvent.class)) handleAfter(item, player, clickedItem, options);
        }

        if((action == Action.LEFT_CLICK_BLOCK || action == Action.LEFT_CLICK_AIR) && getRegister().getEventManager().hasWith(id, ItemLeftClickEvent.class)) {
            /* if player left clicked an entity already the leftClick code below wont handle
            * playerinteract event gets called before entitydamage event, so put the code into a consumer,
            * wait some ticks and if the entitydamage event didn't get called, handle the normal leftclickevent */
            if(item instanceof EntityClickable && getRegister().getEventManager().hasWith(id, ItemLeftClickEntityEvent.class)) {
                leftClicked.add(player);
                Bukkit.getScheduler().scheduleSyncDelayedTask(getRegister().getPlugin(), () -> {
                    if(leftClicked.contains(player)) {
                        leftClickConsumer.handle(event, item);
                        leftClicked.remove(player);
                    }
                }, WAIT_FOR_DAMAGEEVENT_TICKS);
            } else {
                leftClickConsumer.handle(event, item);
            }
        }

        if((action == Action.RIGHT_CLICK_BLOCK || action == Action.LEFT_CLICK_BLOCK) && getRegister().getEventManager().hasWith(id, ItemClickBlockEvent.class)) {
            ItemClickBlockEvent e = new ItemClickBlockEvent(player, clickedItem, delay, action, block, face);
            getRegister().getEventManager().callEvent(item, e);
            if(e.isCancelled()) event.setCancelled(true);
            if(delay == -1 && getRegister().getEventManager().hasWithoutDelay(id, ItemClickBlockEvent.class)) handleAfter(item, player, clickedItem, options);
        }


        if(action == Action.LEFT_CLICK_BLOCK && getRegister().getEventManager().hasWith(id, ItemLeftClickBlockEvent.class)) {
            ItemLeftClickBlockEvent e = new ItemLeftClickBlockEvent(player, clickedItem, delay, action, block, face);
            getRegister().getEventManager().callEvent(item, e);
            if(e.isCancelled()) event.setCancelled(true);
            if(delay == -1 && getRegister().getEventManager().hasWithoutDelay(id, ItemLeftClickBlockEvent.class)) handleAfter(item, player, clickedItem, options);
        }

        if(action == Action.RIGHT_CLICK_BLOCK && getRegister().getEventManager().hasWith(id, ItemRightClickBlockEvent.class)) {
            ItemRightClickBlockEvent e = new ItemRightClickBlockEvent(player, clickedItem, delay, action, block, face);
            getRegister().getEventManager().callEvent(item, e);
            if(e.isCancelled()) event.setCancelled(true);
            if(delay == -1 && getRegister().getEventManager().hasWithoutDelay(id, ItemRightClickBlockEvent.class)) handleAfter(item, player, clickedItem, options);
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
