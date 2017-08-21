package me.lt_col_bodo.bodo_mystery_box;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class mystery_box extends JavaPlugin
{
    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] arg) {
        if(cmd.getName().equalsIgnoreCase("hallo") && sender instanceof Player) {
            Player player = (Player) sender;

            player.sendMessage("Hallo, " + player.getName() + "!");

            return true;
        }

        return false;
    }


}

