package net.msterhuj.skydefenderreboot.core;

import net.msterhuj.skydefenderreboot.SkyDefenderReboot;
import net.msterhuj.skydefenderreboot.core.teams.TeamManager;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GameCommands {
    public boolean run(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        SkyDefenderReboot plugin = SkyDefenderReboot.getInstance();
        GameData gameData = SkyDefenderReboot.getData();

        // game
        if (strings.length == 1) {
            commandSender.sendMessage("§cUse §e/skydefenderreboot game start §cfor help");
            return true;
        }

        // start
        if (strings[1].equalsIgnoreCase("start")) {
            if (gameData.getGameStatus() != GameStatus.WAITING) {
                commandSender.sendMessage("§cYou can't do this now");
                return true;
            }
            gameData.setGameStatus(GameStatus.STARTING);

            // check if spawn is set
            if (gameData.getSpawnLocation() == null) {
                commandSender.sendMessage("§cYou need to set spawn first");
                gameData.setGameStatus(GameStatus.WAITING);
                return true;
            }

            // check if there is at least 2 players in each team
            TeamManager teamManager = gameData.getTeamManager();
            if (!teamManager.isReady()) {
                commandSender.sendMessage("§cYou need at least 1 players in attacker and defender teams online to start the game");
                gameData.setGameStatus(GameStatus.WAITING);
                return true;
            }

            teamManager.spreadPlayers();


            gameData.setGameStatus(GameStatus.STARTED);
            plugin.saveData();
            return true;
        }

        // reset
        if (strings[1].equalsIgnoreCase("reset")) {
            TeamManager teamManager = gameData.getTeamManager();
            teamManager.resetTeams();
            gameData.setGameStatus(GameStatus.WAITING);
            plugin.saveData();
            commandSender.sendMessage("§aGame reset");
            return true;
        }

        return false;
    }

    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        List<String> list = new ArrayList<>();

        if (strings.length == 2) {
            list.add("start");
            list.add("reset");
            return list.stream().filter(stream -> stream.startsWith(strings[1])).collect(Collectors.toList());
        }

        return list;
    }
}
