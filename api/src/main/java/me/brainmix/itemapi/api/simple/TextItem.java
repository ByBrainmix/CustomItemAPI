package me.brainmix.itemapi.api.simple;

import me.brainmix.itemapi.api.ClickSound;
import me.brainmix.itemapi.api.utils.ItemUtils;
import org.bukkit.Sound;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

public class TextItem extends SimpleItem {

    public TextItem(ItemStack item, ClickSound clickSound, String... text) {
        super(item, e -> Arrays.asList(text).stream().map(ItemUtils::format).forEach(e.getPlayer()::sendMessage), clickSound);
    }

    public TextItem(ItemStack item, Sound clickSound, String... text) {
        super(item, e -> Arrays.asList(text).stream().map(ItemUtils::format).forEach(e.getPlayer()::sendMessage), clickSound);
    }

    public TextItem(ItemStack item, String... text) {
        super(item, e -> Arrays.asList(text).stream().map(ItemUtils::format).forEach(e.getPlayer()::sendMessage));
    }

    public TextItem(ItemStack item, ClickSound clickSound, List<String> text) {
        super(item, e -> text.stream().map(ItemUtils::format).forEach(e.getPlayer()::sendMessage), clickSound);
    }

    public TextItem(ItemStack item, Sound clickSound, List<String> text) {
        super(item, e ->text.stream().map(ItemUtils::format).forEach(e.getPlayer()::sendMessage), clickSound);
    }

    public TextItem(ItemStack item, List<String> text) {
        super(item, e -> text.stream().map(ItemUtils::format).forEach(e.getPlayer()::sendMessage));
    }

}
