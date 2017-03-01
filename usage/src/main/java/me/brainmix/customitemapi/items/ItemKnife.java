package me.brainmix.customitemapi.items;

import me.brainmix.customitemapi.utils.BParticle;
import me.brainmix.itemapi.api.CustomItem;
import me.brainmix.itemapi.api.ItemOptions;
import me.brainmix.itemapi.api.controllers.ItemHandler;
import me.brainmix.itemapi.api.events.ItemFlyTickEvent;
import me.brainmix.itemapi.api.events.ItemPickupEvent;
import me.brainmix.itemapi.api.events.ItemRightClickEvent;
import me.brainmix.itemapi.api.interfaces.Clickable;
import me.brainmix.itemapi.api.interfaces.Throwable;
import me.brainmix.itemapi.api.utils.ItemUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Item;

public class ItemKnife extends CustomItem implements Clickable, Throwable {

    @Override
    protected void init(ItemOptions options) {
        options.setItemStack(ItemUtils.getItemStack(Material.GOLD_SWORD, "&6Knife"));
        options.setItemAfterUse(ItemUtils.getItemStack(Material.COMPASS, "&7KnifeFinder"));
        options.setMove(false);
        options.setDrop(false);
    }

    @ItemHandler
    public void onClick(ItemRightClickEvent event) {
        Item item = event.getUser().throwItem(this);
        event.getPlayer().setCompassTarget(item.getLocation());
    }

    @ItemHandler
    public void onFly(ItemFlyTickEvent event) {
        BParticle.SMOKE_LARGE.playAll(event.getThrownItem().getLocation());
        event.getPlayer().setCompassTarget(event.getThrownItem().getLocation());
    }

    @ItemHandler
    public void onPickup(ItemPickupEvent event) {
        Bukkit.broadcastMessage("removing item...");
        ItemUtils.replaceItem(event.getPlayer(), getOptions().getItemAfterUse(), getOptions().getItemStack());
        event.setCancelled(true);
        event.getItemEntity().remove();
    }

}
