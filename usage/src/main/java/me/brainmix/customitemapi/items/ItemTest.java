package me.brainmix.customitemapi.items;

import me.brainmix.itemapi.api.CustomItem;
import me.brainmix.itemapi.api.ItemOptions;
import me.brainmix.itemapi.api.controllers.ItemHandler;
import me.brainmix.itemapi.api.events.ItemRightClickHoldEvent;
import me.brainmix.itemapi.api.events.ItemRightClickReleaseEvent;
import me.brainmix.itemapi.api.interfaces.Clickable;
import me.brainmix.itemapi.api.utils.ItemUtils;
import org.bukkit.Material;

public class ItemTest extends CustomItem implements Clickable {

    @Override
    protected void init(ItemOptions options) {
        options.setItemStack(ItemUtils.getItemStack(Material.NETHER_STAR, "&dTestItem"));
    }

    @ItemHandler
    public void onHold(ItemRightClickHoldEvent event) {
        event.getUser().sendFormatted("§aholding rightclick");
    }

    @ItemHandler
    public void onRelease(ItemRightClickReleaseEvent event) {
        event.getUser().sendFormatted("§crelease rightclick");
    }

}
