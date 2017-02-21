package me.brainmix.customitemapi;

import me.brainmix.customitemapi.items.*;
import me.brainmix.itemapi.api.CustomItem;
import me.brainmix.itemapi.api.utils.CustomItemEnum;
import org.bukkit.inventory.ItemStack;

public enum MainItem implements CustomItemEnum {

    KNOCKBACKSTICK(new ItemKnockbackstick()),
    SMOKE(new ItemSmoke()),
    PISTOL(new ItemPistol()),
    DELAYTEST(new ItemDelayTest()),
    SHOOTER(new ItemShooter()),
    ULTRABOW(new ItemBow()),
    TEST(new ItemTest());

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
