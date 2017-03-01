package me.brainmix.itemapi.api.events;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;

public class ItemRightClickReleaseEvent extends ItemRightClickEvent {

    public ItemRightClickReleaseEvent(Player player, ItemStack item, int delay, Action action, Block block, BlockFace blockFace, long lastClicked, boolean holdRightClick) {
        super(player, item, delay, action, block, blockFace, lastClicked, holdRightClick);
    }

}
