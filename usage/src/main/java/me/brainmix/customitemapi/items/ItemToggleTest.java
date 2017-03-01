package me.brainmix.customitemapi.items;

import me.brainmix.itemapi.api.ClickSound;
import me.brainmix.itemapi.api.ItemOptions;
import me.brainmix.itemapi.api.events.ItemRightClickEvent;
import me.brainmix.itemapi.api.simple.ToggleItem;
import me.brainmix.itemapi.api.utils.ItemUtils;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.inventory.ItemStack;

public class ItemToggleTest extends ToggleItem {

    @Override
    protected void onInit(ItemOptions options) {
        options.setClickSound(new ClickSound(Sound.CLICK, 1, 1));
        options.setCancellDefaults(true);
    }

    @Override
    public ItemStack getItemTrue() {
        return ItemUtils.getItemStack(Material.EMERALD, "&aenabled");
    }

    @Override
    public ItemStack getItemFalse() {
        return ItemUtils.getItemStack(Material.REDSTONE, "&cdisabled");
    }

    @Override
    protected void fromTrueToFalse(ItemRightClickEvent event) {
        event.getUser().sendFormatted("&cnow disabled!");
    }

    @Override
    protected void fromFalseToTrue(ItemRightClickEvent event) {
        event.getUser().sendFormatted("&anow enabled!");
    }

}
