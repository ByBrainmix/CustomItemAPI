package me.brainmix.customitemapi;

import me.brainmix.customitemapi.items.ItemDelayTest;
import me.brainmix.customitemapi.items.ItemKnockbackstick;
import me.brainmix.customitemapi.items.ItemPistol;
import me.brainmix.customitemapi.items.ItemSmoke;
import me.brainmix.itemapi.api.CustomItem;
import me.brainmix.itemapi.api.utils.CustomItemEnum;
import org.bukkit.inventory.ItemStack;

public enum MainItem implements CustomItemEnum {

    KNOCKBACKSTICK(new ItemKnockbackstick()),
    SMOKE(new ItemSmoke()),
    PISTOL(new ItemPistol()),
    DELAYTEST(new ItemDelayTest());

    private CustomItem item;

    MainItem(CustomItem item) {
        this.item = item;
    }

    @Override
    public CustomItem getCustomItem() {
        return item;
    }

    public ItemStack get() {
        return item.getOptions().getItemStack();
    }

}
