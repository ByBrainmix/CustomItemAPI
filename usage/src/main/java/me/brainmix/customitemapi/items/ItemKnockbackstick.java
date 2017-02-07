package me.brainmix.customitemapi.items;

import me.brainmix.itemapi.api.CustomItem;
import me.brainmix.itemapi.api.ItemOptions;
import me.brainmix.itemapi.api.controllers.ItemHandler;
import me.brainmix.itemapi.api.delay.ItemDelayChar;
import me.brainmix.itemapi.api.events.ItemDelayEndEvent;
import me.brainmix.itemapi.api.events.ItemDropEvent;
import me.brainmix.itemapi.api.events.ItemLeftClickEntityEvent;
import me.brainmix.itemapi.api.events.ItemLeftClickEvent;
import me.brainmix.itemapi.api.interfaces.Clickable;
import me.brainmix.itemapi.api.interfaces.EntityClickable;
import me.brainmix.itemapi.api.utils.ItemUtils;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.util.Vector;

public class ItemKnockbackstick extends CustomItem implements EntityClickable, Clickable {

    @Override
    protected void init(ItemOptions options) {
        options.setItemStack(ItemUtils.addEnchant(ItemUtils.getItemStack(Material.STICK, "&6Knockbackstick"), Enchantment.KNOCKBACK, 5));
        options.setItemDelay(new ItemDelayChar(60));
    }

    @ItemHandler
    public void onDelayEnd(ItemDelayEndEvent event) {
        // add knockback enchantment to item
        ItemUtils.replaceItem(event.getPlayer(), getOptions().getItemStack(), ItemUtils.addEnchant(getOptions().getItemStack(), Enchantment.KNOCKBACK, 5));
    }

    @ItemHandler
    public void onDrop(ItemDropEvent event) {
        // don't drop, if item-delay is running
        if(event.getUser().isDelayRunning(this)) event.setCancelled(true);
    }

    @ItemHandler
    public void onEntityClick(ItemLeftClickEntityEvent event) {
        // remove knockback enchantment from item
        ItemUtils.replaceItem(event.getPlayer(), getOptions().getItemStack(), ItemUtils.removeEnchant(getOptions().getItemStack(), Enchantment.KNOCKBACK));

        // start delay
        event.getUser().startDelay(this);

        Vector vel = event.getPlayer().getLocation().getDirection().multiply(2);
        vel.setY(0.5);
        event.getEntity().setVelocity(vel);
        event.setCancelled(true);
    }

    @ItemHandler
    public void onClick(ItemLeftClickEvent event) {
        // remove knockback enchantment from item
        ItemUtils.replaceItem(event.getPlayer(), getOptions().getItemStack(), ItemUtils.removeEnchant(getOptions().getItemStack(), Enchantment.KNOCKBACK));
        event.getUser().startDelay(this);
    }

}
