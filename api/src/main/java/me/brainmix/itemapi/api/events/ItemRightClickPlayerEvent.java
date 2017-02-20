package me.brainmix.itemapi.api.events;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class ItemRightClickPlayerEvent extends ItemRightClickEntityEvent {

    public ItemRightClickPlayerEvent(Player player, ItemStack item, int delay, Player entity, Vector clickedPosition) {
        super(player, item, delay, entity, clickedPosition);
    }

    @Override
    public Player getEntity() {
        return (Player) super.getEntity();
    }
}
