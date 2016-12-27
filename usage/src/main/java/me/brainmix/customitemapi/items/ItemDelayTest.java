package me.brainmix.customitemapi.items;

import me.brainmix.itemapi.api.ClickSound;
import me.brainmix.itemapi.api.CustomItem;
import me.brainmix.itemapi.api.ItemOptions;
import me.brainmix.itemapi.api.ItemUser;
import me.brainmix.itemapi.api.controllers.ItemHandler;
import me.brainmix.itemapi.api.delay.ItemDelay;
import me.brainmix.itemapi.api.events.ItemDelayEndEvent;
import me.brainmix.itemapi.api.events.ItemDelayTickEvent;
import me.brainmix.itemapi.api.events.ItemLeftClickEvent;
import me.brainmix.itemapi.api.events.ItemRightClickEvent;
import me.brainmix.itemapi.api.interfaces.Clickable;
import me.brainmix.itemapi.api.utils.ItemUtils;
import org.bukkit.Material;
import org.bukkit.Sound;

/**
 * class to demonstrate, how to use the delays
 */
public class ItemDelayTest extends CustomItem implements Clickable {

    @Override
    protected void init(ItemOptions options) {
        options.setItemStack(ItemUtils.getItemStack(Material.EMERALD, "&aDelay Test"));
        options.setInteractWithOthers(true);
        options.setClickSound(new ClickSound(Sound.CLICK, 1, 1));
        options.setItemDelay(new ItemDelay(60));
    }

    @ItemHandler
    public void onRightClick(ItemRightClickEvent e) {
        ItemUser user = e.getUser();
        user.sendFormatted("§7right clicked");
        user.startDelay(this);
        user.setUnmoveable();
    }

    @ItemHandler(delay = true)
    public void onLeftClick(ItemLeftClickEvent e) {
        e.getUser().stopDelay(this);
    }

    @ItemHandler
    public void onDelayTick(ItemDelayTickEvent e) {
        ItemUser user = e.getUser();
        int timeLeft= e.getTimeLeft();
        if(!user.hasItemInHand(this)) return;
        if(timeLeft % 15 == 0) user.sendFormatted("§8time left: §e" + timeLeft + " §8ticks");
    }

    @ItemHandler
    public void onDelayEnd(ItemDelayEndEvent e) {
        ItemUser user = e.getUser();
        if(user.hasItemInHand(this)) user.sendFormatted("§cdelay ended!");
        user.playSound(Sound.CLICK);
        user.setMoveable();
    }


}