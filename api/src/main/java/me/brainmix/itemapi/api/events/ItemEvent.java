package me.brainmix.itemapi.api.events;

import me.brainmix.itemapi.api.ItemUser;
import me.brainmix.itemapi.api.controllers.UserManager;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public abstract class ItemEvent {

    private Player player;
    private ItemStack item;
    private int delay;
    private boolean cancelled;

    public ItemEvent(Player player, ItemStack item, int delay) {
        this.player = player;
        this.item = item;
        this.delay = delay;
    }

    public int getTimeLeft() {
        return delay;
    }

    public Player getPlayer() {
        return player;
    }

    public ItemUser getUser() {
        return UserManager.a().getUser(player);
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
