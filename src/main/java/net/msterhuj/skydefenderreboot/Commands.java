package net.msterhuj.skydefenderreboot;

import net.msterhuj.skydefenderreboot.core.GameCommands;
import net.msterhuj.skydefenderreboot.core.GameStatus;
import net.msterhuj.skydefenderreboot.core.SpawnLocation;
import net.msterhuj.skydefenderreboot.core.teams.TeamCommands;
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

        // settout
        if (strings[0].equalsIgnoreCase("settpout")) {
            if (strings.length == 1) {
                player.sendMessage("§cUse §e/skydefenderreboot settpout <name> §cfor help");
                return true;
            }
            // settpout <name>
            SkyDefenderReboot.getData().getTeleporterManager().addTeleporter(player, strings[1], TeleporterType.OUTPUT);
            SkyDefenderReboot.getInstance().saveData();
            player.sendMessage("§aTeleporter set!");
            return true;

        }
        // settpin
        if (strings[0].equalsIgnoreCase("settpin")) {
            // settpin <name>
            if (strings.length == 1) {
                player.sendMessage("§cUse §e/skydefenderreboot settpin <name> §cfor help");
                return true;
            }
            SkyDefenderReboot.getData().getTeleporterManager().addTeleporter(player, strings[1], TeleporterType.INPUT);
            SkyDefenderReboot.getInstance().saveData();
            player.sendMessage("§aTeleporter set!");
            return true;
        }
        // resettp
        if (strings[0].equalsIgnoreCase("resettp")) {
            // resettp <name>
            if (strings.length == 2) {
                SkyDefenderReboot.getData().getTeleporterManager().resetTeleporter(strings[1]);
                SkyDefenderReboot.getInstance().saveData();
                player.sendMessage("§aTeleporter reset!");
            } else {
                player.sendMessage("§cUse §e/skydefenderreboot resettp <name> §cfor help");
            }
            return true;
        }

        if (strings[0].equalsIgnoreCase("team"))
            return (new TeamCommands()).run(commandSender, command, s, strings);

        if (strings[0].equalsIgnoreCase("game"))
            return (new GameCommands()).run(commandSender, command, s, strings);

        return false;
    }
}
