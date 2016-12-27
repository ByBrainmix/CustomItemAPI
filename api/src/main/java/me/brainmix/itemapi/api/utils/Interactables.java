package me.brainmix.itemapi.api.utils;

import org.bukkit.Material;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Interactables {

    private static Set<Material> interactables = new HashSet<>(Arrays.asList(
            Material.ACACIA_DOOR,
            Material.ACACIA_FENCE_GATE,
            Material.ANVIL,
            Material.BEACON,
            Material.BED,
            Material.BIRCH_DOOR,
            Material.BIRCH_FENCE_GATE,
            Material.BOAT,
            Material.BREWING_STAND,
            Material.COMMAND,
            Material.CHEST,
            Material.DARK_OAK_DOOR,
            Material.DARK_OAK_FENCE_GATE,
            Material.DAYLIGHT_DETECTOR,
            Material.DAYLIGHT_DETECTOR_INVERTED,
            Material.DISPENSER,
            Material.DROPPER,
            Material.ENCHANTMENT_TABLE,
            Material.ENDER_CHEST,
            Material.FENCE_GATE,
            Material.FURNACE,
            Material.HOPPER,
            Material.HOPPER_MINECART,
            Material.ITEM_FRAME,
            Material.JUNGLE_DOOR,
            Material.JUNGLE_FENCE_GATE,
            Material.LEVER,
            Material.MINECART,
            Material.NOTE_BLOCK,
            Material.POWERED_MINECART,
            Material.REDSTONE_COMPARATOR,
            Material.REDSTONE_COMPARATOR_OFF,
            Material.REDSTONE_COMPARATOR_ON,
            Material.SIGN,
            Material.SIGN_POST,
            Material.STORAGE_MINECART,
            Material.TRAP_DOOR,
            Material.TRAPPED_CHEST,
            Material.WALL_SIGN,
            Material.WORKBENCH,
            Material.WOOD_BUTTON,
            Material.WOOD_DOOR,
            Material.WOODEN_DOOR,
            Material.ACACIA_DOOR,
            Material.ACACIA_FENCE,
            Material.ACACIA_FENCE_GATE,
            Material.DARK_OAK_DOOR,
            Material.DARK_OAK_FENCE,
            Material.DARK_OAK_FENCE_GATE,
            Material.BIRCH_DOOR,
            Material.BIRCH_FENCE,
            Material.BIRCH_FENCE_GATE,
            Material.SPRUCE_DOOR,
            Material.SPRUCE_FENCE,
            Material.SPRUCE_FENCE_GATE,
            Material.JUNGLE_DOOR,
            Material.JUNGLE_FENCE,
            Material.JUNGLE_FENCE_GATE,
            Material.STONE_BUTTON,
            Material.WOOD_BUTTON,
            Material.DIODE,
            Material.DIODE_BLOCK_OFF,
            Material.DIODE_BLOCK_ON
    ));

    public static Set<Material> get() {
        return interactables;
    }

}
