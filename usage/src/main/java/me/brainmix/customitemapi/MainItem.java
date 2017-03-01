package me.brainmix.customitemapi;

import me.brainmix.customitemapi.items.*;
import me.brainmix.itemapi.api.CustomItem;
import me.brainmix.itemapi.api.simple.TextItem;
import me.brainmix.itemapi.api.utils.CustomItemEnum;
import me.brainmix.itemapi.api.utils.ItemUtils;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.inventory.ItemStack;

public enum MainItem implements CustomItemEnum {

    KNOCKBACKSTICK(new ItemKnockbackstick()),
    SMOKE(new ItemSmoke()),
    PISTOL(new ItemPistol()),
    DELAYTEST(new ItemDelayTest()),
    SHOOTER(new ItemShooter()),
    ULTRABOW(new ItemBow()),
    TEST(new ItemTest()),
    KNIFE(new ItemKnife()),
    TOGGLETEST(new ItemToggleTest());

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
