package net.msterhuj.skydefenderreboot.core.teleporter;

import lombok.Data;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import java.util.HashMap;
import java.util.Map;

@Data
public class TeleporterManager {


    private HashMap<String, Teleporter> teleporters;

    public TeleporterManager() {
        this.teleporters = new HashMap<>();
    }

    public Teleporter getTeleporter(String teleporterName) {
        if (this.teleporters.containsKey(teleporterName))
            return this.teleporters.get(teleporterName);
        return new Teleporter();
    }

    public boolean isTeleporterLocationExists(Teleporter teleporter, TeleporterType teleporterType) {
        return teleporter.getTeleporterLocation(teleporterType) != null;
    }

    public void addTeleporter(Player player, String teleporterName, TeleporterType teleporterType) {
        Location location = player.getLocation();
        Teleporter teleporter = this.getTeleporter(teleporterName);

        // remove location if exist
        if (this.isTeleporterLocationExists(teleporter, teleporterType)) {
            teleporter.getTeleporterLocation(teleporterType).resetBlock();
            teleporter.clearTeleporterLocation(teleporterType);
        }

        // register new location
        TeleporterLocation newTeleporterLocation = new TeleporterLocation(player, teleporterType);
        newTeleporterLocation.setupBock(player);
        teleporter.setTeleporterLocation(newTeleporterLocation, teleporterType);

        this.teleporters.put(teleporterName, teleporter);
    }

    public void resetTeleporter(String teleporterName) {
        this.getTeleporter(teleporterName).resetTeleporter();
        this.teleporters.remove(teleporterName);
    }

    public void teleportPlayer(Player player, Location eventLocation) {


        for (Teleporter teleporter : this.teleporters.values()) {
            if (teleporter.isLocationOneOfTeleporter(eventLocation)) {
                teleporter.teleportPlayer(eventLocation, player, true);
                return;
            }
        }
    }
}
