package net.msterhuj.skydefenderreboot;

import net.msterhuj.skydefenderreboot.core.GameCommands;
import net.msterhuj.skydefenderreboot.core.GameStatus;
import net.msterhuj.skydefenderreboot.core.locations.SpawnLocation;
import net.msterhuj.skydefenderreboot.core.teams.TeamCommands;
import net.msterhuj.skydefenderreboot.core.teleporter.TeleporterCommands;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.jetbrains.annotations.NotNull;
import net.msterhuj.skydefenderreboot.core.teleporter.TeleporterType;

import java.util.Arrays;

public class Commands implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        SkyDefenderReboot plugin = SkyDefenderReboot.getInstance();
        plugin.getLogger().info(Arrays.toString(strings));

        if (SkyDefenderReboot.getData().getGameStatus() == GameStatus.STARTING) {
            commandSender.sendMessage("§cYou can't do this now (game is starting)");
            return true;
        }

        //
        if (strings.length == 0) {
            commandSender.sendMessage("§cSkyDefenderReboot v" + plugin.getDescription().getVersion() + " by Msterhuj");
            commandSender.sendMessage("§cUse §e/skydefenderreboot help §cfor help");
            return true;
        }

        Player player = (Player) commandSender;

        // setspawn
        if (strings[0].equalsIgnoreCase("setspawn")) {
            SkyDefenderReboot.getData().setSpawnLocation(new SpawnLocation(player));
            SkyDefenderReboot.getInstance().saveData();
            player.getWorld().setSpawnLocation(player.getLocation());
            player.sendMessage("§aWorldSpawn set!");
            return true;
        }

        // serbanner
        if (strings[0].equalsIgnoreCase("setbanner")) {
            Player p = (Player) commandSender;
            player.setMetadata("setup_banner", new FixedMetadataValue(plugin, true));
            player.sendMessage("§aRight click on the banner");
            return true;
        }

        if (strings[0].equalsIgnoreCase("teleporter")) {
            return (new TeleporterCommands()).run(commandSender, command, s, strings);
        }

        if (strings[0].equalsIgnoreCase("team"))
            return (new TeamCommands()).run(commandSender, command, s, strings);

        if (strings[0].equalsIgnoreCase("game"))
            return (new GameCommands()).run(commandSender, command, s, strings);

        return false;
    }
}
