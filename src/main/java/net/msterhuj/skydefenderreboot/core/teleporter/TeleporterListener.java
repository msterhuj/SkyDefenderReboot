package net.msterhuj.skydefenderreboot.core.teleporter;

import net.msterhuj.skydefenderreboot.SkyDefenderReboot;
import net.msterhuj.skydefenderreboot.core.teams.TeamManager;
import net.msterhuj.skydefenderreboot.core.teams.TeamType;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class TeleporterListener implements Listener {
    @EventHandler(ignoreCancelled = true)
    public void onPlayerInteract(PlayerInteractEvent event) {
        // todo add a check to prevent teleportation loop if its output (temporary fix)
        if (event.getAction().equals(Action.PHYSICAL)) {
            if (event.getClickedBlock().getType() == Material.LIGHT_WEIGHTED_PRESSURE_PLATE) {
                TeamManager teamManager = SkyDefenderReboot.getGameManager().getTeamManager();
                if (teamManager.getTeamPlayer(event.getPlayer()).getTeamType() != TeamType.DEFENDER) return;
                SkyDefenderReboot.getGameManager().getTeleporterManager().teleportPlayer(event.getPlayer(), event.getClickedBlock().getLocation());
            }
        }
    }
    // todo add protection to prevent teleport destruction
}
