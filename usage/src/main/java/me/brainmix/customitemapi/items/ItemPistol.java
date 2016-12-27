package me.brainmix.customitemapi.items;

import me.brainmix.customitemapi.utils.BParticle;
import me.brainmix.itemapi.api.ClickSound;
import me.brainmix.itemapi.api.CustomItem;
import me.brainmix.itemapi.api.ItemOptions;
import me.brainmix.itemapi.api.controllers.ItemHandler;
import me.brainmix.itemapi.api.delay.ItemDelayChar;
import me.brainmix.itemapi.api.events.ItemFlyTickEvent;
import me.brainmix.itemapi.api.events.ItemHitEntityEvent;
import me.brainmix.itemapi.api.events.ItemHitGroundEvent;
import me.brainmix.itemapi.api.events.ItemRightClickEvent;
import me.brainmix.itemapi.api.interfaces.Clickable;
import me.brainmix.itemapi.api.interfaces.Throwable;
import me.brainmix.itemapi.api.utils.ItemUtils;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ItemPistol extends CustomItem implements Clickable, Throwable {

    private double speed = 3.5;

    @Override
    protected void init(ItemOptions options) {
        options.setItemStack(ItemUtils.getItemStack(Material.IRON_BARDING, "&7Pistol"));
        options.setClickSound(new ClickSound(Sound.FIREWORK_BLAST, 1, 1));
        options.setAutoItemDelay(new ItemDelayChar(40));
        options.setInteractWithOthers(true);
    }

    @ItemHandler
    public void onRightClick(ItemRightClickEvent event) {
        Player player = event.getPlayer();


        Item bullet = event.getUser().throwItem(this);
        bullet.setItemStack(new ItemStack(Material.FIREWORK_CHARGE));
        bullet.setVelocity(player.getLocation().getDirection().multiply(speed));
    }

    @ItemHandler
    public void onFlyTick(ItemFlyTickEvent event) {
        BParticle.EXPLOSION_NORMAL.playAll(event.getThrownItem().getLocation(), true);
    }

    @ItemHandler
    public void onHitEntity(ItemHitEntityEvent event) {
        if(!(event.getEntity() instanceof Player)) return;
        Player target = (Player) event.getEntity();
        if(target == event.getPlayer()) return;

        target.setHealth(0);

        event.getThrownItem().remove();
    }

    @ItemHandler
    public void onHitGround(ItemHitGroundEvent event) {
        event.getThrownItem().remove();
    }

}
