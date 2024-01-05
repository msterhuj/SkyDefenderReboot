package net.msterhuj.skydefenderreboot;


import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class TestCommands implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        SkyDefenderReboot plugin = SkyDefenderReboot.getInstance();
        plugin.getLogger().info("Test command executed");

        Random random = new Random();

        Player player = Bukkit.getPlayer("MsterHuj");
        int x = random.nextInt(-255, 255);
        int z = random.nextInt(-255, 255);
        Location destinationLocation = player.getWorld().getHighestBlockAt(x, z).getLocation();
        player.sendMessage(destinationLocation.getBlock().getType().name());
        if (destinationLocation.getBlock().isLiquid()) {
            player.sendMessage("§cYou can't teleport to a liquid block");
        }
        destinationLocation.add(0, 1, 0);
        player.teleport(destinationLocation);

        return true;
    }
}
