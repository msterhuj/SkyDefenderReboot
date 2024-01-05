package net.msterhuj.skydefenderreboot.core.teams;

import net.msterhuj.skydefenderreboot.SkyDefenderReboot;
import net.msterhuj.skydefenderreboot.core.GameStatus;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TeamCommands {
    public boolean run(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        SkyDefenderReboot plugin = SkyDefenderReboot.getInstance();

        // disable command if game is started
        if (SkyDefenderReboot.getData().getGameStatus() == GameStatus.STARTED) {
            commandSender.sendMessage("§cYou can't do this now");
            return true;
        }

        TeamManager teamManager = SkyDefenderReboot.getData().getTeamManager();
        Player player = (Player) commandSender;
        TeamPlayer teamPlayer = teamManager.getTeamPlayer(player);

        // team
        if (strings.length == 1) {
            commandSender.sendMessage("§cUse §e/skydefenderreboot team list §cor §e/skydefenderreboot team join <team> §cfor help");
            return true;
        }

        // team list
        if (strings[1].equalsIgnoreCase("list")) {
            commandSender.sendMessage("§cList of teams:");
            for (TeamType teamType : TeamType.values()) {
                if (teamPlayer != null && teamPlayer.getTeamType() == teamType) {
                    commandSender.sendMessage("§a- " + teamType.getName());
                    continue;
                }
                commandSender.sendMessage("§c- " + teamType.getName());
            }
            return true;
        }

        // team join
        if (strings[1].equalsIgnoreCase("join")) {

            // team join
            if (strings.length == 2) {
                commandSender.sendMessage("§cUse §e/skydefenderreboot team join <team> §cfor help");
                return true;
            }

            // team join <team>
            TeamType teamType = TeamType.getTeamType(strings[2]);
            if (teamType == null) {
                commandSender.sendMessage("§cTeam not found");
                return true;
            }
            teamManager.addPlayerToTeam(player, teamType);
            plugin.saveData();
            commandSender.sendMessage("§aYou joined the team " + teamType.getName());
        }

        if (strings[1].equalsIgnoreCase("leave")) {
            if (teamPlayer.getTeamType() == TeamType.SPECTATOR) {
                commandSender.sendMessage("§cYou are already a spectator");
                return true;
            }
            teamManager.removePlayerFromTeam(player);
            commandSender.sendMessage("§aYou left your team (you are now a spectator)");
            plugin.saveData();
            return true;
        }

        return false;
    }
}
