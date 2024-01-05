package net.msterhuj.skydefenderreboot;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import net.msterhuj.skydefenderreboot.core.teleporter.TeleporterType;

import java.util.Arrays;

public class Commands implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        SkyDefenderReboot plugin = SkyDefenderReboot.getInstance();
        plugin.getLogger().info(Arrays.toString(strings));

        // sky
        if (strings.length == 0) {
            commandSender.sendMessage("§cSkyDefenderReboot v" + plugin.getDescription().getVersion() + " by Msterhuj");
            commandSender.sendMessage("§cUse §e/skydefenderreboot help §cfor help");
            return true;
        }

        Player player = (Player) commandSender;

        // sky settout
        if (strings[0].equalsIgnoreCase("settpout")) {
            // sky settpout <name>
            if (strings.length >= 2) {
                SkyDefenderReboot.getData().getTeleporterManager().addTeleporter(player, strings[1], TeleporterType.OUTPUT);
                SkyDefenderReboot.getInstance().saveData();
                return true;
            }
        }
        // sky settpin
        if (strings[0].equalsIgnoreCase("settpin")) {
            // sky settpin <name>
            if (strings.length >= 2) {
                SkyDefenderReboot.getData().getTeleporterManager().addTeleporter(player, strings[1], TeleporterType.INPUT);
                SkyDefenderReboot.getInstance().saveData();
                return true;
            }
        }
        // sky resettp
        if (strings[0].equalsIgnoreCase("resettp")) {
            // sky resettp <name>
            if (strings.length > 2) {
                SkyDefenderReboot.getData().getTeleporterManager().resetTeleporter(strings[1]);
                SkyDefenderReboot.getInstance().saveData();
                return true;
            }
        }



        return true;
    }
}
