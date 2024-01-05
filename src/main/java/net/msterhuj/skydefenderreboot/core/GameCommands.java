package net.msterhuj.skydefenderreboot.core;

import net.msterhuj.skydefenderreboot.SkyDefenderReboot;
import net.msterhuj.skydefenderreboot.core.teams.TeamManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

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
            if (teamManager.isReady()) {
                commandSender.sendMessage("§cYou need at least 2 players in attacker and defender teams to start the game");
                gameData.setGameStatus(GameStatus.WAITING);
                return true;
            }
            teamManager.spreadPlayers();


            gameData.setGameStatus(GameStatus.STARTED);
            plugin.saveData();
            return true;
        }

        return false;
    }
}
