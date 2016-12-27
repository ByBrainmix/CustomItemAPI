package me.brainmix.itemapi.api;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import me.brainmix.itemapi.api.delay.DelayManager;
import me.brainmix.itemapi.api.utils.ItemUtils;

public abstract class CustomItem {

    private ItemOptions options;
    private final String id;
    private DelayManager delayManager;

    public CustomItem() {
        this.id = getClass().getSimpleName().substring(0, 1).toLowerCase() + getClass().getSimpleName().substring(1);
        this.options = new ItemOptions();
        options.setItemStack(ItemUtils.getItemStack(Material.STICK, "&aCustom Item"));
    }

    protected abstract void init(ItemOptions options);

    protected void setDelayManager(DelayManager delayManager) {
        this.delayManager = delayManager;
    }

    public DelayManager getDelayManager() {
        return delayManager;
    }

    public String getId() {
        return id;
    }

    public ItemOptions getOptions() {
        return options;
    }

    public boolean compare(ItemStack item) {
        return ItemUtils.compare(options.getItemStack(), item);
    }

}
