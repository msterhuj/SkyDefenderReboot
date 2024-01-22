package net.msterhuj.skydefenderreboot;

import net.msterhuj.skydefenderreboot.core.GameManager;
import net.msterhuj.skydefenderreboot.core.world.WorldManager;
import net.msterhuj.skydefenderreboot.utils.GameConfig;
import org.bukkit.GameRule;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class TestCommands implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        SkyDefenderReboot plugin = SkyDefenderReboot.getInstance();
        GameConfig gameConfig = SkyDefenderReboot.getGameConfig();
        plugin.getLogger().info("Test command executed but no body knows what it does :)");
        commandSender.sendMessage("Game state: " + String.valueOf(GameManager.getInstance().getGameStatus().name()));
        commandSender.sendMessage("Daylight cycle: " + String.valueOf(WorldManager.getWorld().getGameRuleValue(GameRule.DO_DAYLIGHT_CYCLE)));
        commandSender.sendMessage("Day: " + String.valueOf(WorldManager.getDay()));
        commandSender.sendMessage("Time: " + String.valueOf(WorldManager.getWorld().getTime()));
        commandSender.sendMessage("Full Time: " + String.valueOf(WorldManager.getWorld().getFullTime()));
        commandSender.sendMessage("Weather: " + String.valueOf(WorldManager.getWorld().hasStorm() ? "Stormy" : "Clear"));
        commandSender.sendMessage("Number of players: " + String.valueOf(WorldManager.getWorld().getPlayers().size()));
        commandSender.sendMessage("Difficulty: " + String.valueOf(WorldManager.getWorld().getDifficulty()));

        for (int day = gameConfig.getWorldborderStartReduceAtDay()-2; day <= gameConfig.getWorldborderFinishReduceAtDay()+2; day++) {
            commandSender.sendMessage("Border Day " + day + ": Radius " + WorldManager.getBorderSizeForDay(day));
        }
        return true;
    }
}
