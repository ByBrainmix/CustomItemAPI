package me.brainmix.itemapi.api.simple;

import me.brainmix.itemapi.api.CustomItem;
import me.brainmix.itemapi.api.ItemOptions;
import me.brainmix.itemapi.api.controllers.ItemHandler;
import me.brainmix.itemapi.api.events.ItemRightClickEvent;
import me.brainmix.itemapi.api.interfaces.Clickable;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;

public abstract class ToggleItem extends CustomItem implements Clickable {

    private ToggleItemFalse toggleItemFalse;

    @Override
    protected final void init(ItemOptions options) {
        toggleItemFalse = new ToggleItemFalse(this);
        options.setItemStack(getItemTrue());
        options.setItemAfterUse(getItemFalse());
        options.setInteractWithOthers(true);
        onInit(options);
        getRegister().register(toggleItemFalse);
    }

    protected void onInit(ItemOptions options) {}

    public abstract ItemStack getItemTrue();

    public abstract ItemStack getItemFalse();

    protected abstract void fromTrueToFalse(ItemRightClickEvent event);

    protected abstract void fromFalseToTrue(ItemRightClickEvent event);

    @ItemHandler
    public final void onClick(ItemRightClickEvent event) {
        fromTrueToFalse(event);
    }

    private static class ToggleItemFalse extends CustomItem implements Clickable {

        private ToggleItem toggleItem;

        private ToggleItemFalse(ToggleItem toggleItem) {
            this.toggleItem = toggleItem;
        }

        @Override
        protected void init(ItemOptions options) {
            options.setItemStack(toggleItem.getItemFalse());
            options.setItemAfterUse(toggleItem.getItemTrue());
            options.setInteractWithOthers(true);
            toggleItem.onInit(options);
        }

        @ItemHandler
        public void onClick(ItemRightClickEvent event) {
            toggleItem.fromFalseToTrue(event);
        }

    }

}
