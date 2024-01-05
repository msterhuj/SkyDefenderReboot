package net.msterhuj.skydefenderreboot.core.teams;


import lombok.Data;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Random;
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

    public Location getRandomHighestSafeLocation(World world, int minX, int maxX, int minZ, int maxZ) {
        Random random = new Random();
        int x = random.nextInt(minX, maxX);
        int z = random.nextInt(minZ, maxZ);
        Location location = world.getHighestBlockAt(x, z).getLocation();
        if (location.getBlock().isLiquid()) {
            return getRandomHighestSafeLocation(world, minX, maxX, minZ, maxZ);
        }
        return location;
    }

    public void spreadPlayers() {
        //Player[] attackers = getPlayers(TeamType.ATTACKER);
        //Player[] defenders = getPlayers(TeamType.DEFENDER);
        //Player[] spectators = getPlayers(TeamType.SPECTATOR);


    }

    private Player[] getPlayers(TeamType teamType) {
        return teamPlayers.stream()
                .filter(teamPlayer -> teamPlayer.getTeamType().equals(teamType))
                .map(TeamPlayer::getPlayerByUUID)
                .toArray(Player[]::new);
    }

    public TeamPlayer getTeamPlayer(Player player) {
        return teamPlayers.stream().filter(teamPlayer -> teamPlayer.getUuid().equals(player.getUniqueId())).findFirst().orElse(null);
    }
}
