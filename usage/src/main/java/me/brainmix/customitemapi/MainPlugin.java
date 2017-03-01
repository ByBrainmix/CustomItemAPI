package me.brainmix.customitemapi;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import me.brainmix.itemapi.api.ItemRegister;

public class MainPlugin extends JavaPlugin {

    public static MainPlugin INSTANCE;

    @Override
    public void onEnable() {
        INSTANCE = this;

        ItemRegister itemRegister = new ItemRegister(this);
        itemRegister.registerAll(MainItem.class);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender instanceof Player && cmd.getName().equalsIgnoreCase("getitems")) {
            Player player = (Player) sender;

            add(player, MainItem.KNOCKBACKSTICK);
            add(player, MainItem.SMOKE);
            add(player, MainItem.PISTOL);
            add(player, MainItem.DELAYTEST);
            add(player, MainItem.SHOOTER);
            add(player, MainItem.ULTRABOW);
            add(player, MainItem.TEST);
            add(player, MainItem.KNIFE);
            add(player, MainItem.TOGGLETEST);

        }
        return true;
    }

    private void add(Player player, MainItem item) {
        player.getInventory().addItem(item.get());
    }

}
