package me.brainmix.itemapi.api.events;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;

public class ItemClickEvent extends ItemEvent {

    private Action action;
    private Block block;
    private BlockFace blockFace;

    public ItemClickEvent(Player player, ItemStack item, int delay, Action action, Block block, BlockFace blockFace) {
        super(player, item, delay);
        this.action = action;
        this.block = block;
        this.blockFace = blockFace;
    }

    public Action getAction() {
        return action;
    }

    public Block getClickedBlock() {
        return block;
    }

    public BlockFace getBlockFace() {
        return blockFace;
    }

}
