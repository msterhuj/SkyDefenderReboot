package net.msterhuj.skydefenderreboot.core.teleporter;

import lombok.Data;

import lombok.NoArgsConstructor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

@Data
@NoArgsConstructor
public class Teleporter {

    private String name;
    private TeleporterLocation input;
    private TeleporterLocation output;


    public TeleporterLocation getTeleporterLocation(TeleporterType teleporterType) {
        if (TeleporterType.INPUT == teleporterType) return this.input;
        return this.output;
    }

    public void setTeleporterLocation(TeleporterLocation teleporterLocation, TeleporterType teleporterType) {
        if (TeleporterType.INPUT == teleporterType) {
            this.input = teleporterLocation;
            return;
        }
        this.output = teleporterLocation;
    }

    public void clearTeleporterLocation(TeleporterType teleporterType) {
        if (TeleporterType.INPUT == teleporterType) {
            this.input = null;
            return;
        }
        this.output = null;
    }

    public void teleportPlayer(Location eventLocation, Player player, boolean keepPlayerDirection) {
        if (this.input != null && this.output != null) {
            Location destinationLocation = this.getOppositeTeleporterLocation(eventLocation).getLocation();
            destinationLocation.setX(destinationLocation.getBlockX() + 0.5);
            destinationLocation.setZ(destinationLocation.getBlockZ() + 0.5);

            if (keepPlayerDirection) destinationLocation.setDirection(player.getLocation().getDirection());

            player.teleport(destinationLocation);
        } else {
            player.sendMessage("No output teleporter set !");
        }
    }

    private boolean isLocationMatch(Location queryLocation, Location matchLocation) {
        return queryLocation.getBlockX() == matchLocation.getBlockX()
                && queryLocation.getBlockY() == matchLocation.getBlockY()
                && queryLocation.getBlockZ() == matchLocation.getBlockZ();
    }

    public boolean isLocationOneOfTeleporter(Location location) {
        return (this.input != null && isLocationMatch(this.input.getLocation(), location))
                || (this.output != null && isLocationMatch(this.output.getLocation(), location));
    }

    public TeleporterLocation getTeleporterLocation(Location location) {
        if (this.input != null && isLocationMatch(this.input.getLocation(), location)) return this.input;
        if (this.output != null && isLocationMatch(this.output.getLocation(), location)) return this.output;
        return null;
    }

    public TeleporterLocation getOppositeTeleporterLocation(Location location) {
        if (this.input != null && isLocationMatch(this.input.getLocation(), location)) return this.output;
        if (this.output != null && isLocationMatch(this.output.getLocation(), location)) return this.input;
        return null;
    }

    public void resetTeleporter() {
        if (this.input != null) this.input.resetBlock();
        if (this.output != null) this.output.resetBlock();
    }
}