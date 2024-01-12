package net.msterhuj.skydefenderreboot.core.locations;

import net.msterhuj.skydefenderreboot.SkyDefenderReboot;
import net.msterhuj.skydefenderreboot.core.GameStatus;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.UUID;

public class BannerLocation {
    private UUID worldUUID;
    private int x, y, z;

    public void setupLocation(Location location) {
        this.worldUUID = location.getWorld().getUID();
        this.x = location.getBlockX();
        this.y = location.getBlockY();
        this.z = location.getBlockZ();
    }

    public void handleSetup(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (player.hasMetadata("setup_banner")) {
            player.removeMetadata("setup_banner", SkyDefenderReboot.getInstance());
            if (SkyDefenderReboot.getGameManager().getGameStatus() != GameStatus.LOBBY) {
                player.sendMessage("§cYou can't do this now (game is started)");
                return;
            }
            // if Material contains BANNER then set the banner
            if (isBanner(event.getClickedBlock().getType())) {
                setupLocation(event.getClickedBlock().getLocation());
                SkyDefenderReboot.getInstance().saveGameManager();
                player.sendMessage("§aBanner set!");
            } else {
                player.sendMessage("§cYou must click on a banner!");
            }
        }
    }

    public boolean isPresent() {
        Location location = getLocation();
        if (location == null) return false;
        return isBanner(location.getBlock().getType());
    }

    public boolean isBanner(Material material) {
        return material.toString().contains("BANNER");
    }

    public Location getLocation() {
        return new Location(Bukkit.getWorld(this.worldUUID), this.x, this.y, this.z);
    }
}
