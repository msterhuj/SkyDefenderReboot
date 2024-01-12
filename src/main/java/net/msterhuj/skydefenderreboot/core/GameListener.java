package net.msterhuj.skydefenderreboot.core;

import net.msterhuj.skydefenderreboot.SkyDefenderReboot;
import net.msterhuj.skydefenderreboot.core.locations.BannerLocation;
import net.msterhuj.skydefenderreboot.core.teams.TeamManager;
import net.msterhuj.skydefenderreboot.core.teams.TeamPlayer;
import net.msterhuj.skydefenderreboot.core.teams.TeamType;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import javax.xml.transform.Result;

public class GameListener implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void onPlayerDeath(PlayerDeathEvent event) {
        // todo move this to team manager on a method called "checkIfTeamWins"
        // todo add a check when attacker is not online but its alive create a timer before defender team wins
        // todo if defender team is not online the game automatically pause after 5 minutes
        TeamManager teamManager = SkyDefenderReboot.getGameManager().getTeamManager();
        TeamPlayer teamPlayer = teamManager.getTeamPlayer(event.getEntity());
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

    @EventHandler(ignoreCancelled = true)
    public void onPlayerQuit(PlayerQuitEvent event) {
        // todo handle in player leave function
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerKick(PlayerKickEvent event) {
        // todo handle in player leave function
    }

    @EventHandler(ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent event) {
        // todo add a method to check if the player can break the block by checking the game status

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

        // move this to a method in banner location class
        Player player = event.getPlayer();
        if (player.hasMetadata("setup_banner")) {
            player.removeMetadata("setup_banner", SkyDefenderReboot.getInstance());
            if (SkyDefenderReboot.getGameManager().getGameStatus() != GameStatus.LOBBY) {
                player.sendMessage("§cYou can't do this now (game is started)");
                return;
            }
            // if Material contains BANNER then set the banner
            if (event.getClickedBlock().getType().toString().contains("BANNER")) {
                GameManager gameManager = SkyDefenderReboot.getGameManager();
                gameManager.setBannerLocation(new BannerLocation(event.getClickedBlock().getLocation()));
                SkyDefenderReboot.getInstance().saveGameManager();
                player.sendMessage("§aBanner set!");
            } else {
                player.sendMessage("§cYou must click on a banner!");
            }
        }
    }
}

