package me.brainmix.itemapi.api.events;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;

public class ItemClickBlockEvent extends ItemClickEvent {

    private Block block;
    private BlockFace blockFace;

    public ItemClickBlockEvent(Player player, ItemStack item, int delay, Action action, Block block, BlockFace blockFace) {
        super(player, item, delay, action);
        this.block = block;
        this.blockFace = blockFace;
    }

    public Block getBlock() {
        return block;
    }

    public BlockFace getBlockFace() {
        return blockFace;
    }
}
