package net.msterhuj.skydefenderreboot.core;

import net.msterhuj.skydefenderreboot.SkyDefenderReboot;
import net.msterhuj.skydefenderreboot.core.teams.TeamManager;
import net.msterhuj.skydefenderreboot.core.teams.TeamPlayer;
import net.msterhuj.skydefenderreboot.core.teams.TeamType;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.*;


public class GameListener implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void onPlayerDeath(PlayerDeathEvent event) {
        GameManager.getInstance().getTeamManager().handlePlayerDeath(event);
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerQuit(PlayerQuitEvent event) {
        // todo handle in player leave function
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerKick(PlayerKickEvent event) {
        // todo handle in player leave function
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerRespawnEvent(PlayerRespawnEvent event) {
        GameManager.getInstance().getTeamManager().handlePlayerRespawn(event);
    }

    @EventHandler(ignoreCancelled = true)
    public void onBlockPlace(BlockPlaceEvent event) {
        if (GameManager.getInstance().isGameStatus(GameStatus.LOBBY, GameStatus.PAUSED) && !event.getPlayer().isOp()) {
            if (event instanceof Player) event.getPlayer().sendMessage("§cYou can't place blocks now");
            event.setCancelled(true);
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerJoin(PlayerJoinEvent event) {
        GameManager.getInstance().getTeamManager().handlePlayerJoin(event);
    }

    @EventHandler(ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent event) {
        // todo add a method to check if the player can break the block by checking the game status
        if (GameManager.getInstance().isGameStatus(GameStatus.LOBBY, GameStatus.PAUSED) && !event.getPlayer().isOp()) {
            event.setCancelled(true);
        }

        // todo move this to team manager on a method called "checkIfTeamWins"
        // check if it's the banner
        Location bannerLocation = SkyDefenderReboot.getGameManager().getBannerLocation().getLocation();
        TeamManager teamManager = SkyDefenderReboot.getGameManager().getTeamManager();
        if (event.getBlock().getLocation().equals(bannerLocation)) {
            if (teamManager.getTeamPlayer(event.getPlayer()).isTeam(TeamType.DEFENDER)) {
                event.setCancelled(true);
                event.getPlayer().sendMessage("§cYou can't break the banner!");
                return;
            }
            // attacker team wins
            Bukkit.broadcastMessage("§aAttacker team wins!");
            SkyDefenderReboot.getGameManager().setGameStatus(GameStatus.FINISH);
            SkyDefenderReboot.getInstance().saveGameManager();
        }
    }

    /*
     * when player right-clicks on a banner and has the metadata "setup_banner"
     * if the game is in WAITING status
     * set the banner location in the game data
     */
    @EventHandler(ignoreCancelled = true)
    public void onPlayerInteract(PlayerInteractEvent event) {
        GameManager.getInstance().getBannerLocation().handleSetup(event);
    }
}

