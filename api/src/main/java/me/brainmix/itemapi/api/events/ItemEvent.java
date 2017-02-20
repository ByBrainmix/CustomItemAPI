package me.brainmix.itemapi.api.events;

import me.brainmix.itemapi.api.ItemUser;
import me.brainmix.itemapi.api.controllers.ItemUserManager;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public abstract class ItemEvent {

    private static ItemUserManager userManager;
    private Player player;
    private ItemStack item;
    private int delay;
    private boolean cancelled;

    public ItemEvent(Player player, ItemStack item, int delay) {
        this.player = player;
        this.item = item;
        this.delay = delay;
    }

    public static void setUserManager(ItemUserManager userManager) {
        ItemEvent.userManager = userManager;
    }

    public int getTimeLeft() {
        return delay;
    }

    public Player getPlayer() {
        return player;
    }

    public ItemUser getUser() {
        return userManager.getUser(player);
    }

    public ItemStack getItem() {
        return item;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        if(this instanceof NotCancellable) {
            throw new RuntimeException(getClass().getSimpleName() + " is not cancellable!");
        }
        this.cancelled = cancelled;
    }

}
