package me.brainmix.itemapi.api.delay;

import me.brainmix.itemapi.api.CustomItem;
import me.brainmix.itemapi.api.utils.ItemUtils;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ItemDelayAmount extends ItemDelay implements DelayDisplay {

    private CustomItem customItem;
    private boolean canMove;
    private boolean canDrop;

    public ItemDelayAmount(int delay, CustomItem customItem) {
        super(delay);
        this.customItem = customItem;
        this.canMove = customItem.getOptions().isMove();
        this.canDrop = customItem.getOptions().isDrop();
    }

    @Override
    public void display(Player player, int timeLeft) {
        if(timeLeft == 0) {
            customItem.getOptions().setMove(canMove);
            customItem.getOptions().setDrop(canDrop);
            ItemUtils.replaceItem(player, customItem.getOptions().getItemStack(), customItem.getOptions().getItemStack());
            return;
        }

        customItem.getOptions().setMove(false);
        customItem.getOptions().setDrop(false);

        ItemStack item = customItem.getOptions().getItemStack().clone();
        item.setAmount((timeLeft / 20) + 2);
        ItemUtils.replaceItem(player, customItem.getOptions().getItemStack(), item);
    }

}
