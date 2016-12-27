package me.brainmix.itemapi.api.events;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class ItemRightClickEntityEvent extends ItemClickEntityEvent {

    private Vector clickedPosition;

    public ItemRightClickEntityEvent(Player player, ItemStack item, int delay, Entity entity, Vector clickedPosition) {
        super(player, item, delay, entity);
        this.clickedPosition = clickedPosition;
    }

    public Vector getClickedPosition() {
        return clickedPosition;
    }
}
