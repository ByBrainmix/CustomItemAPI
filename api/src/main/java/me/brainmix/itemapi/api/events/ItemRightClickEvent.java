package me.brainmix.itemapi.api.events;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;

public class ItemRightClickEvent extends ItemClickEvent {

    private long lastClicked;
    private boolean holdRightClick;

    public ItemRightClickEvent(Player player, ItemStack item, int delay, Action action, Block block, BlockFace blockFace, long lastClicked, boolean holdRightClick) {
        super(player, item, delay, action, block, blockFace);
        this.lastClicked = lastClicked;
        this.holdRightClick = holdRightClick;
    }

    public long getLastClicked() {
        return lastClicked;
    }

    public boolean isHoldingRightClick() {
        return holdRightClick;
    }
}
