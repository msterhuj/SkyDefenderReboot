package net.msterhuj.skydefenderreboot.core.teams;


import lombok.Data;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

@Data
public class TeamManager {

    Set<TeamPlayer> teamPlayers;

    public TeamManager() {
        this.teamPlayers = new HashSet<>();
    }
    public boolean registerPlayer(Player player) {
        // add player to teamPlayers if not already in
        if (teamPlayers.stream().noneMatch(teamPlayer -> teamPlayer.getUuid().equals(player.getUniqueId()))) {
            TeamPlayer teamPlayer = new TeamPlayer();
            teamPlayer.setUuid(player.getUniqueId());
            teamPlayer.setPseudo(player.getName());
            teamPlayer.setTeamType(TeamType.SPECTATOR);
            teamPlayers.add(teamPlayer);
            return true;
        }
        return false;
    }

    public void addPlayerToTeam(Player player, TeamType teamType) {
        TeamPlayer teamPlayer = getTeamPlayer(player);
        if (teamPlayer != null) {
            teamPlayer.setTeamType(teamType);
        }
    }

    public void removePlayerFromTeam(Player player) {
        TeamPlayer teamPlayer = getTeamPlayer(player);
        if (teamPlayer != null) {
            teamPlayer.setTeamType(TeamType.SPECTATOR);
        }
    }

    public TeamPlayer getTeamPlayer(Player player) {
        return teamPlayers.stream().filter(teamPlayer -> teamPlayer.getUuid().equals(player.getUniqueId())).findFirst().orElse(null);
    }
}
