package net.msterhuj.skydefenderreboot;


import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TestCommands implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        SkyDefenderReboot plugin = SkyDefenderReboot.getInstance();
        plugin.getLogger().info("Test command executed");

        Player player = (Player) commandSender;
        World world = player.getWorld();
        player.sendMessage("Time: " + world.getTime()); // 0 - 24000 (0 = sunrise, 1000 = day, 6000 = noon, 12000 = sunset, 13000 = night, 18000 = midnight)
        player.sendMessage("Full Time: " + world.getFullTime()); // all time passed to get the day divide by 24000 (0 = day 1, 1 = day 2, 2 = day 3, etc.)
        player.sendMessage("Day: " + world.getFullTime() / 24000); // day number
        player.sendMessage("Time: " + world.getFullTime() % 24000); // time in the day

        return true;
    }
}
