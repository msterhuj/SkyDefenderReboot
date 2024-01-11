package net.msterhuj.skydefenderreboot.core.locations;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import java.util.UUID;

public class BannerLocation {
    private UUID worldUUID;
    private final int x, y, z;

    public BannerLocation(Location location) {
        this.worldUUID = location.getWorld().getUID();
        this.x = location.getBlockX();
        this.y = location.getBlockY();
        this.z = location.getBlockZ();
    }

    // todo add function to check if the banner is present in the world at the location

    public Location getLocation() {
        return new Location(Bukkit.getWorld(this.worldUUID), this.x, this.y, this.z);
    }
}
