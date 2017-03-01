package me.brainmix.customitemapi.items;

import me.brainmix.itemapi.api.CustomItem;
import me.brainmix.itemapi.api.ItemOptions;
import me.brainmix.itemapi.api.controllers.ItemHandler;
import me.brainmix.itemapi.api.events.ItemRightClickEvent;
import me.brainmix.itemapi.api.interfaces.Clickable;
import me.brainmix.itemapi.api.interfaces.Shootable;
import me.brainmix.itemapi.api.utils.ItemUtils;
import org.bukkit.Material;
import org.bukkit.entity.Snowball;
import org.bukkit.scheduler.BukkitRunnable;

public class ItemSplashGun extends CustomItem implements Clickable, Shootable {

    @Override
    protected void init(ItemOptions options) {
        options.setItemStack(ItemUtils.getItemStack(Material.FIREBALL, "&cSplashGun"));
    }

    @ItemHandler
    public void onClick(ItemRightClickEvent event) {
        CustomItem self = this;
        new BukkitRunnable() {
            @Override
            public void run() {
                event.getUser().shootProjectile(self, Snowball.class);
            }
        }.runTaskTimer(getRegister().getPlugin(), 0, 1);

    }


}
