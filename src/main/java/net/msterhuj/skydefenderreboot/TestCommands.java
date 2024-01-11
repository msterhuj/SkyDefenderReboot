package net.msterhuj.skydefenderreboot;

import net.msterhuj.skydefenderreboot.core.teams.TeamManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TestCommands implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        SkyDefenderReboot plugin = SkyDefenderReboot.getInstance();
        plugin.getLogger().info("Test command executed but no body knows what it does :)");
        Player player = (Player) commandSender;
        int minSpreadDistanceFromSpawn = plugin.getConfig().getInt("spread_distance_from_spawn.min");
        int maxSpreadDistanceFromSpawn = plugin.getConfig().getInt("spread_distance_from_spawn.max");
        TeamManager teamManager = SkyDefenderReboot.getData().getTeamManager();
        player.teleport(teamManager.getRandomHighestSafeLocation(SkyDefenderReboot.getData().getSpawnLocation().getLocation(),
                minSpreadDistanceFromSpawn, maxSpreadDistanceFromSpawn));
        return true;
    }
}
