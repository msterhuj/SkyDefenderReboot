package net.msterhuj.skydefenderreboot;

import net.msterhuj.skydefenderreboot.core.GameCommands;
import net.msterhuj.skydefenderreboot.core.GameStatus;
import net.msterhuj.skydefenderreboot.core.locations.SpawnLocation;
import net.msterhuj.skydefenderreboot.core.teams.TeamCommands;
import net.msterhuj.skydefenderreboot.core.teleporter.TeleporterCommands;
import net.msterhuj.skydefenderreboot.core.world.WorldManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Commands implements CommandExecutor, TabCompleter {
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
            WorldManager.setupBorderCenter(player.getLocation());
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

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        List<String> list = new ArrayList<>();

        commandSender.sendMessage(Arrays.toString(strings));

        if (strings.length == 1) {
            list.add("setspawn");
            list.add("setbanner");
            list.add("teleporter");
            list.add("team");
            list.add("game");
            return list.stream().filter(stream -> stream.startsWith(strings[0])).collect(Collectors.toList());
        }

        if (strings.length >= 2) {
            if (strings[0].equalsIgnoreCase("teleporter")) {
                return (new TeleporterCommands()).onTabComplete(commandSender, command, s, strings);
            }
            if (strings[0].equalsIgnoreCase("team")) {
                return (new TeamCommands()).onTabComplete(commandSender, command, s, strings);
            }
            if (strings[0].equalsIgnoreCase("game")) {
                return (new GameCommands()).onTabComplete(commandSender, command, s, strings);
            }
        }

        return list;
    }
}
