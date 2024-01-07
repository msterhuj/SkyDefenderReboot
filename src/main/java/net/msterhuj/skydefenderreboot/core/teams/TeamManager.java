package net.msterhuj.skydefenderreboot.core.teams;


import lombok.Data;
import net.msterhuj.skydefenderreboot.SkyDefenderReboot;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.*;


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

    public Location getRandomHighestSafeLocation(Location startLocation, int spreadDistanceFromLocation) {
        Random random = new Random();
        int x = startLocation.getBlockX() + random.nextInt(-spreadDistanceFromLocation, spreadDistanceFromLocation);
        int z = startLocation.getBlockZ() + random.nextInt(-spreadDistanceFromLocation, spreadDistanceFromLocation);
        Location destLocation = startLocation.getWorld().getHighestBlockAt(x, z).getLocation();
        if (destLocation.getBlock().isLiquid()) {
            return getRandomHighestSafeLocation(startLocation, spreadDistanceFromLocation);
        }
        return destLocation.add(0.5, 1.2, 0.5);
    }

    /*
     * Teleport attackers to random locations around spawn
     * Teleport defenders and spectator to spawn
     */
    public void spreadPlayers() {
        SkyDefenderReboot plugin = SkyDefenderReboot.getInstance();
        int spreadDistanceFromSpawn = plugin.getConfig().getInt("spread_distance_from_spawn");
        for (Player attacker : getOnlineTeamPlayers(TeamType.ATTACKER)) {
            attacker.teleport(getRandomHighestSafeLocation(SkyDefenderReboot.getData().getSpawnLocation().getLocation(), spreadDistanceFromSpawn));
            attacker.setGameMode(GameMode.SURVIVAL);
            attacker.getInventory().clear();
        }
        Location spawnLocation = SkyDefenderReboot.getData().getSpawnLocation().getLocation();
        for (Player defender : getOnlineTeamPlayers(TeamType.DEFENDER)) {
            defender.teleport(spawnLocation);
            defender.setGameMode(GameMode.SURVIVAL);
            defender.getInventory().clear();
        }
        for (Player spectator : getOnlineTeamPlayers(TeamType.SPECTATOR)) {
            spectator.teleport(spawnLocation);
            spectator.setGameMode(GameMode.SPECTATOR);
            spectator.getInventory().clear();
        }
    }

    private Player[] getTeamPlayers(TeamType teamType) {
        return teamPlayers.stream()
                .filter(teamPlayer -> teamPlayer.getTeamType().equals(teamType))
                .map(TeamPlayer::getPlayerByUUID)
                .toArray(Player[]::new);
    }

    private Player[] getOnlineTeamPlayers(TeamType teamType) {
        return teamPlayers.stream()
                .filter(teamPlayer -> teamPlayer.getTeamType().equals(teamType) && teamPlayer.isOnline())
                .map(TeamPlayer::getPlayerByUUID)
                .toArray(Player[]::new);
    }

    public TeamPlayer getTeamPlayer(Player player) {
        return teamPlayers.stream().filter(teamPlayer -> teamPlayer.getUuid().equals(player.getUniqueId())).findFirst().orElse(null);
    }

    /*
     * Check if there is at least 1 player in each defender and attacker team online
     */
    public boolean isReady() {
        Bukkit.broadcastMessage("defender online" + getOnlineTeamPlayers(TeamType.DEFENDER).length);
        for (Player defender : getOnlineTeamPlayers(TeamType.DEFENDER)) {
            Bukkit.broadcastMessage(defender.getName());
        }
        Bukkit.broadcastMessage("attacker online" + getOnlineTeamPlayers(TeamType.ATTACKER).length);
        for (Player attacker : getOnlineTeamPlayers(TeamType.ATTACKER)) {
            Bukkit.broadcastMessage(attacker.getName());
        }
        return getOnlineTeamPlayers(TeamType.ATTACKER).length >= 1 && getOnlineTeamPlayers(TeamType.DEFENDER).length >= 1;
    }

    public void resetTeams() {
        teamPlayers.forEach(teamPlayer -> teamPlayer.setTeamType(TeamType.SPECTATOR));
    }
}
