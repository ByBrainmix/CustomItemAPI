package me.brainmix.itemapi.api.events;

import org.bukkit.Location;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.brainmix.itemapi.api.ItemUser;

public class ItemDespawnEvent extends ItemEvent {

    private Item itemEntity;
    private Location location;

    public ItemDespawnEvent( ItemStack item, Item itemEntity, Location location) {
        super(null, item, -1);
        this.itemEntity = itemEntity;
        this.location = location;
    }

    @Deprecated
    @Override
    public Player getPlayer() throws RuntimeException {
        throw new RuntimeException("Can't access getPlayer() in ItemDespawnEvent");
    }

    @Deprecated
    @Override
    public int getTimeLeft() throws RuntimeException {
        throw new RuntimeException("Can't access getTimeLeft() in ItemDespawnEvent");
    }

    @Deprecated
    @Override
    public ItemUser getUser() throws RuntimeException {
        throw new RuntimeException("Can't access getUser() in ItemDespawnEvent");
    }

    public Item getItemEntity() {
        return itemEntity;
    }

    public Location getLocation() {
        return location;
    }
}
