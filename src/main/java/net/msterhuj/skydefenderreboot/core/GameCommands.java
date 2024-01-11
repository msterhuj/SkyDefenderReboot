package net.msterhuj.skydefenderreboot.core;

import net.msterhuj.skydefenderreboot.SkyDefenderReboot;
import net.msterhuj.skydefenderreboot.core.teams.TeamManager;
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
        GameManager gameManager = SkyDefenderReboot.getGameManager();

        // game
        if (strings.length == 1) {
            commandSender.sendMessage("§cUse §e/skydefenderreboot game start §cfor help");
            return true;
        }

        // start
        if (strings[1].equalsIgnoreCase("start")) {
            // move all check for checking if game is ready to game manager
            if (gameManager.getGameStatus() != GameStatus.LOBBY) {
                commandSender.sendMessage("§cYou can't do this now");
                return true;
            }
            gameManager.setGameStatus(GameStatus.STARTING);

            // check if spawn is set
            if (gameManager.getSpawnLocation() == null) {
                commandSender.sendMessage("§cYou need to set spawn first");
                gameManager.setGameStatus(GameStatus.LOBBY);
                return true;
            }

            // check if there is at least 2 players in each team
            TeamManager teamManager = gameManager.getTeamManager();
            if (!teamManager.isReady()) {
                commandSender.sendMessage("§cYou need at least 1 players in attacker and defender teams online to start the game");
                gameManager.setGameStatus(GameStatus.LOBBY);
                return true;
            }

            // todo add multi thread for teleporting players and add countdown
            teamManager.spreadPlayers();


            gameManager.setGameStatus(GameStatus.RUNNING);
            plugin.saveGameManager();
            return true;
        }

        // reset
        if (strings[1].equalsIgnoreCase("reset")) {
            // move this to game manager reset method
            TeamManager teamManager = gameManager.getTeamManager();
            teamManager.resetTeams();
            gameManager.setGameStatus(GameStatus.LOBBY);
            plugin.saveGameManager();
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
