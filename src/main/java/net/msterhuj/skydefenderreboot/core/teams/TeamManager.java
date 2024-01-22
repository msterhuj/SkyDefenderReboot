package net.msterhuj.skydefenderreboot.core.teams;


import com.destroystokyo.paper.event.player.PlayerPostRespawnEvent;
import lombok.Data;
import net.msterhuj.skydefenderreboot.SkyDefenderReboot;
import net.msterhuj.skydefenderreboot.core.GameManager;
import net.msterhuj.skydefenderreboot.core.GameStatus;
import net.msterhuj.skydefenderreboot.core.world.WorldManager;
import net.msterhuj.skydefenderreboot.utils.GameConfig;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.scoreboard.Team;

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

    public void addPlayerToTeam(UUID uuid, TeamType teamType) {
        TeamPlayer teamPlayer = getTeamPlayer(uuid);
        if (teamPlayer != null) {
            teamPlayer.setTeamType(teamType);
        }
    }

    public void removePlayerFromTeam(UUID uuid) {
        TeamPlayer teamPlayer = getTeamPlayer(uuid);
        if (teamPlayer != null) {
            teamPlayer.setTeamType(TeamType.SPECTATOR);
        }
    }


    public Location getRandomHighestSafeLocation(Location startLocation, int minSpreadDistanceFromLocation, int maxSpreadDistanceFromLocation) {
        Random random = new Random();

        int newX, newZ;
        Location destLocation;

        do {
            newX = startLocation.getBlockX() + random.nextInt(-maxSpreadDistanceFromLocation, maxSpreadDistanceFromLocation);
            newZ = startLocation.getBlockZ() + random.nextInt(-maxSpreadDistanceFromLocation, maxSpreadDistanceFromLocation);

            // todo load chunk in other thread to avoid lag
            destLocation = startLocation.getWorld().getHighestBlockAt(newX, newZ).getLocation();
        } while (destLocation.getBlock().isLiquid() || destLocation.distance(startLocation) < minSpreadDistanceFromLocation);

        return destLocation.add(0.5, 1.2, 0.5);
    }

    /*
     * Teleport attackers to random locations around spawn
     * Teleport defenders and spectator to spawn
     */
    public void spreadPlayers() {
        // todo add check if worldborder if to small compared to teleporter radius and warn in console
        GameConfig gameConfig = SkyDefenderReboot.getGameConfig();
        int minSpreadDistanceFromSpawn = gameConfig.getSpreadDistanceFromSpawnMin();
        int maxSpreadDistanceFromSpawn = gameConfig.getSpreadDistanceFromSpawnMax();
        for (Player attacker : getOnlinePlayersByTeam(TeamType.ATTACKER)) {
            attacker.teleport(getRandomHighestSafeLocation(SkyDefenderReboot.getGameManager().getSpawnLocation().getLocation(),
                    minSpreadDistanceFromSpawn, maxSpreadDistanceFromSpawn));
            attacker.setGameMode(GameMode.SURVIVAL);
            attacker.getInventory().clear();
        }
        Location spawnLocation = SkyDefenderReboot.getGameManager().getSpawnLocation().getLocation();
        for (Player defender : getOnlinePlayersByTeam(TeamType.DEFENDER)) {
            defender.teleport(spawnLocation);
            defender.setGameMode(GameMode.SURVIVAL);
            defender.getInventory().clear();
        }
        for (Player spectator : getOnlinePlayersByTeam(TeamType.SPECTATOR)) {
            spectator.teleport(spawnLocation);
            spectator.setGameMode(GameMode.SPECTATOR);
            spectator.getInventory().clear();
        }
    }

    private TeamPlayer[] getTeamPlayersByTeam(TeamType teamType) {
        return teamPlayers.stream()
                .filter(teamPlayer -> teamPlayer.getTeamType().equals(teamType))
                .toArray(TeamPlayer[]::new);
    }

    private Player[] getOnlinePlayersByTeam(TeamType teamType) {
        return teamPlayers.stream()
                .filter(teamPlayer -> teamPlayer.getTeamType().equals(teamType) && teamPlayer.isOnline())
                .map(TeamPlayer::getPlayerByUUID)
                .toArray(Player[]::new);
    }

    public TeamPlayer getTeamPlayer(Player player) {
        return teamPlayers.stream().filter(teamPlayer -> teamPlayer.getUuid().equals(player.getUniqueId())).findFirst().orElse(null);
    }

    public TeamPlayer getTeamPlayer(UUID uuid) {
        return teamPlayers.stream().filter(teamPlayer -> teamPlayer.getUuid().equals(uuid)).findFirst().orElse(null);
    }

    /*
     * Check if there is at least 1 player in each defender and attacker team online
     */
    public boolean isReady() {
        return getOnlinePlayersByTeam(TeamType.ATTACKER).length >= 1 && getOnlinePlayersByTeam(TeamType.DEFENDER).length >= 1;
    }

    public void handlePlayerDeath(PlayerDeathEvent event) {
        // death
        if (GameManager.getInstance().isGameStatus(
                GameStatus.LOBBY,
                GameStatus.FINISH,
                GameStatus.PAUSED)) {
            event.setCancelled(true);
            return;
        }
        TeamPlayer teamPlayer = getTeamPlayer(event.getPlayer());
        if (teamPlayer.isTeam(TeamType.DEFENDER)) return;
        teamPlayer.setAlive(false);
        SkyDefenderReboot.getInstance().saveGameManager();

        // todo move this code to other function
        // todo move this to team manager on a method called "checkIfTeamWins"
        // todo add a check when attacker is not online but its alive create a timer before defender team wins
        // todo if defender team is not online the game automatically pause after 5 minutes
        // check if game win
        TeamManager teamManager = SkyDefenderReboot.getGameManager().getTeamManager();
        if (SkyDefenderReboot.getGameManager().isGameStatus(GameStatus.RUNNING)) {
            if (teamPlayer.getTeamType() == TeamType.ATTACKER) {
                // check if he's the last one in the attacker team
                if (teamManager.getTeamPlayers().stream().filter(teamPlayer1 -> teamPlayer1.getTeamType() == TeamType.ATTACKER).count() == 1) { // todo redo this cause it's not logic :sob:
                    // defender team wins
                    Bukkit.broadcastMessage("§aDefender team wins!");
                    SkyDefenderReboot.getGameManager().setGameStatus(GameStatus.FINISH);
                    SkyDefenderReboot.getInstance().saveGameManager();
                }
            }
        }
    }

    public void handlePlayerRespawn(PlayerRespawnEvent event) {

        Player player = event.getPlayer();
        TeamPlayer teamPlayer = getTeamPlayer(player);

        switch (GameManager.getInstance().getGameStatus()) {
            case LOBBY:
                player.setGameMode(GameMode.ADVENTURE);

            case RUNNING:
                if (teamPlayer.isAlive()) player.setGameMode(GameMode.SURVIVAL);
                else player.setGameMode(GameMode.SPECTATOR);

                event.setRespawnLocation(GameManager.getInstance().getSpawnLocation().getLocation());

            default:
                player.setGameMode(GameMode.SPECTATOR);
                SkyDefenderReboot.getInstance().getLogger()
                        .warning("PlayerRespawn > cant handle respawn for player " + player.getName() +  " gamemode set to spectator");
        }
    }

    public void handlePlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        Server server = SkyDefenderReboot.getInstance().getServer();
        TeamManager teamManager = GameManager.getInstance().getTeamManager();
        if (teamManager.registerPlayer(player)) SkyDefenderReboot.getInstance().saveGameManager();

        switch (SkyDefenderReboot.getGameManager().getGameStatus()) {
            case LOBBY:
                server.broadcastMessage("§aWaiting for players...");
                if (!player.isOp()) {
                    player.setGameMode(GameMode.ADVENTURE);
                    if (GameManager.getInstance().getSpawnLocation() == null) {
                        player.sendMessage("§cSpawn location not set!");
                        SkyDefenderReboot.getInstance().getLogger().warning("Spawn location not set player " + player.getName() + " cannot be teleported to spawn!");
                    } else player.teleport(SkyDefenderReboot.getGameManager().getSpawnLocation().getLocation().add(0.5, 0.2, 0.5));
                }
                break;

            case RUNNING:
                server.broadcastMessage("§aGame started!");
                TeamPlayer teamPlayer = teamManager.getTeamPlayer(player);
                if (teamPlayer.isTeam(TeamType.SPECTATOR)) player.setGameMode(GameMode.SPECTATOR);
                if (!teamPlayer.isAlive()) player.setGameMode(GameMode.SPECTATOR);
                break;

            case PAUSED:
                server.broadcastMessage("§aGame paused!");
                break;

            case FINISH:
                server.broadcastMessage("§aGame finished!");
                player.setGameMode(GameMode.SPECTATOR);
                break;
        }
    }

    public boolean checkIfTeamWins() {
        // todo
        return false;
    }

    public void resetTeams() {
        teamPlayers.forEach(teamPlayer -> teamPlayer.setTeamType(TeamType.SPECTATOR));
    }

    public boolean isBannersSet() {
        return GameManager.getInstance().getBannerLocation().getLocation().getBlock().getType().name().contains("BANNER");
    }
}
