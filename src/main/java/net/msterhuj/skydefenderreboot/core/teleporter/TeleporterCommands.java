package net.msterhuj.skydefenderreboot.core.teleporter;

import net.msterhuj.skydefenderreboot.SkyDefenderReboot;
import net.msterhuj.skydefenderreboot.core.GameStatus;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TeleporterCommands {
    public boolean run(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        Player player = (Player) commandSender;

        // disable command if game is started
        if (SkyDefenderReboot.getData().getGameStatus() == GameStatus.STARTED) {
            commandSender.sendMessage("§cYou can't do this now");
            return true;
        }

        if (strings.length == 1) {
            commandSender.sendMessage("§cUse §e/skydefenderreboot §cfor help");
            return true;
        }

        // reset
        if (strings[1].equalsIgnoreCase("reset")) {
            // resettp <name>
            if (strings.length == 3) {
                SkyDefenderReboot.getData().getTeleporterManager().resetTeleporter(strings[2]);
                SkyDefenderReboot.getInstance().saveData();
                player.sendMessage("§aTeleporter reset!");
            } else {
                player.sendMessage("§cUse §e/skydefenderreboot  §cfor help");
            }
            return true;
        }

        // setin
        if (strings[1].equalsIgnoreCase("setin")) {
            // settpin <name>
            if (strings.length == 2) {
                player.sendMessage("§cUse §e/skydefenderreboot  §cfor help");
                return true;
            }
            SkyDefenderReboot.getData().getTeleporterManager().addTeleporter(player, strings[2], TeleporterType.INPUT);
            SkyDefenderReboot.getInstance().saveData();
            player.sendMessage("§aTeleporter set!");
            return true;
        }

        // setout
        if (strings[0].equalsIgnoreCase("setout")) {
            if (strings.length == 2) {
                player.sendMessage("§cUse §e/skydefenderreboot  §cfor help");
                return true;
            }
            // settpout <name>
            SkyDefenderReboot.getData().getTeleporterManager().addTeleporter(player, strings[2], TeleporterType.OUTPUT);
            SkyDefenderReboot.getInstance().saveData();
            player.sendMessage("§aTeleporter set!");
            return true;
        }
        return false;
    }

    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        List<String> list = new ArrayList<>();

        if (strings.length == 2) {
            list.add("reset");
            list.add("setin");
            list.add("setout");
            return list.stream().filter(stream -> stream.startsWith(strings[1])).collect(Collectors.toList());
        }

        if (strings.length == 3) {
            if (strings[1].equalsIgnoreCase("reset")) {
                list.addAll(SkyDefenderReboot.getData().getTeleporterManager().getTeleporters().keySet());
                return list.stream().filter(stream -> stream.startsWith(strings[2])).collect(Collectors.toList());
            }

            if (strings[1].equalsIgnoreCase("setin") || strings[1].equalsIgnoreCase("setout")) {
                list.addAll(SkyDefenderReboot.getData().getTeleporterManager().getTeleporters().keySet());
                List<String> filtered = list.stream()
                        .filter(stream -> stream.startsWith(strings[2])).collect(Collectors.toList());
                filtered.add("<name>");
                return filtered;
            }
        }
        return list;
    }
}