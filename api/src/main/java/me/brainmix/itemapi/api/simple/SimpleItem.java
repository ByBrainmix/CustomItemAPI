package me.brainmix.itemapi.api.simple;

import me.brainmix.itemapi.api.ClickSound;
import me.brainmix.itemapi.api.CustomItem;
import me.brainmix.itemapi.api.ItemOptions;
import me.brainmix.itemapi.api.controllers.ItemHandler;
import me.brainmix.itemapi.api.events.ItemRightClickEvent;
import me.brainmix.itemapi.api.interfaces.Clickable;
import org.bukkit.Sound;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;

public class SimpleItem extends CustomItem implements Clickable {

    private Consumer<ItemRightClickEvent> clickHandler;

    public SimpleItem(ItemStack item, Consumer<ItemRightClickEvent> clickHandler, ClickSound clickSound) {
        this.clickHandler = clickHandler;
        getOptions().setItemStack(item);
        getOptions().setInteractWithOthers(true);
        if(clickSound != null) getOptions().setClickSound(clickSound);
    }

    public SimpleItem(ItemStack item, Consumer<ItemRightClickEvent> clickHandler, Sound clickSound) {
        this(item, clickHandler, new ClickSound(clickSound, 1, 1));
    }

    public SimpleItem(ItemStack item, Consumer<ItemRightClickEvent> clickHandler) {
        this(item, clickHandler, (ClickSound) null);
    }

    public SimpleItem(ItemStack item) {
        this(item, null);
    }

    @Override
    protected void init(ItemOptions options) {
    }

    @ItemHandler
    public void onClick(ItemRightClickEvent event) {
        if(clickHandler != null) clickHandler.accept(event);
    }

}
