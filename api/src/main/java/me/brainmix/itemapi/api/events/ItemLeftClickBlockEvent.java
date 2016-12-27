package me.brainmix.itemapi.api.events;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;

public class ItemLeftClickBlockEvent extends ItemClickBlockEvent {
    public ItemLeftClickBlockEvent(Player player, ItemStack item, int delay, Action action, Block block, BlockFace blockFace) {
        super(player, item, delay, action, block, blockFace);
    }
}
