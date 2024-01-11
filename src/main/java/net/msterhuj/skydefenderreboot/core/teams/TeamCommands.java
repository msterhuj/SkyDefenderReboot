package net.msterhuj.skydefenderreboot.core.teams;

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

public class TeamCommands { // todo update commands to permit to admin to add player to a team and console to use this command without crashing x)
    public boolean run(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        SkyDefenderReboot plugin = SkyDefenderReboot.getInstance();

        // disable command if game is started
        if (SkyDefenderReboot.getGameManager().getGameStatus() == GameStatus.RUNNING) {
            commandSender.sendMessage("§cYou can't do this now");
            return true;
        }

        TeamManager teamManager = SkyDefenderReboot.getGameManager().getTeamManager();
        Player player = (Player) commandSender; // I see you crashing by console
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
                // player is in a team
                if (teamPlayer != null && teamPlayer.getTeamType() == teamType) {
                    commandSender.sendMessage("§a- " + teamType.getName());
                    for (TeamPlayer inTeam : teamManager.getTeamPlayers()) {
                        if (inTeam.getTeamType() == teamType) {
                            commandSender.sendMessage("§7-> " + inTeam.getPseudo());
                        }
                    }
                    continue;
                }
                // player is not in that's team
                commandSender.sendMessage("§c- " + teamType.getName());
                for (TeamPlayer inTeam : teamManager.getTeamPlayers()) {
                    if (inTeam.getTeamType() == teamType) {
                        commandSender.sendMessage("§7-> " + inTeam.getPseudo());
                    }
                }
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
            teamManager.addPlayerToTeam(player.getUniqueId(), teamType);
            plugin.saveGameManager();
            commandSender.sendMessage("§aYou joined the team " + teamType.getName());
            return true;
        }

        if (strings[1].equalsIgnoreCase("leave")) {
            if (teamPlayer.getTeamType() == TeamType.SPECTATOR) {
                commandSender.sendMessage("§cYou are already a spectator");
                return true;
            }
            teamManager.removePlayerFromTeam(player.getUniqueId());
            commandSender.sendMessage("§aYou left your team (you are now a spectator)");
            plugin.saveGameManager();
            return true;
        }

        return false;
    }

    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        List<String> list = new ArrayList<>();

        if (strings.length == 2) {
            list.add("list");
            list.add("join");
            list.add("leave");
            return list.stream().filter(stream -> stream.startsWith(strings[1])).collect(java.util.stream.Collectors.toList());
        }

        if (strings.length == 3) {

            if (strings[1].equalsIgnoreCase("join")) {
                list = Arrays.asList(TeamType.values())
                        .stream()
                        .map(TeamType::getName)
                        .collect(Collectors.toList());
                return list.stream().filter(stream -> stream.startsWith(strings[2])).collect(java.util.stream.Collectors.toList());
            }
        }
        return list;
    }
}
