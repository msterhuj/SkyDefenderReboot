package net.msterhuj.skydefenderreboot;

import net.msterhuj.skydefenderreboot.core.GameListener;
import net.msterhuj.skydefenderreboot.core.teams.TeamListener;
import net.msterhuj.skydefenderreboot.core.teleporter.TeleporterListener;
import net.msterhuj.skydefenderreboot.core.world.WorldListener;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

/**
 * The {@code ListenerManager} class is responsible for managing the registration of event listeners
 * for the SkyDefenderReboot plugin.
 * <p>
 * This class provides a flexible way to register and unregister multiple listeners with ease.
 */
public class ListenerManager {

    /**
     * Constructs a new {@code ListenerManager} and registers default event listeners for the SkyDefenderReboot plugin.
     *
     * @param plugin The instance of the SkyDefenderReboot plugin.
     */
    public ListenerManager(SkyDefenderReboot plugin) {
        registerListeners(plugin,
                new TeleporterListener(),
                new TeamListener(),
                new GameListener(),
                new WorldListener());
    }

    /**
     * Registers multiple event listeners with the Bukkit PluginManager.
     *
     * @param plugin    The instance of the plugin.
     * @param listeners The event listeners to be registered.
     */
    private void registerListeners(Plugin plugin, Listener... listeners) {
        PluginManager pluginManager = plugin.getServer().getPluginManager();
        for (Listener listener : listeners) {
            pluginManager.registerEvents(listener, plugin);
        }
    }

    /**
     * Unregisters multiple event listeners.
     * <p>
     * Note: This method removes all registered handlers for the provided listeners.
     *
     * @param plugin    The instance of the plugin.
     * @param listeners The event listeners to be unregistered.
     */
    private void unregisterListeners(Plugin plugin, Listener... listeners) {
        PluginManager pluginManager = plugin.getServer().getPluginManager();
        for (Listener listener : listeners) {
            HandlerList.unregisterAll(listener);
        }
    }
}
