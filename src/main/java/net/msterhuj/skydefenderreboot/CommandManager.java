package net.msterhuj.skydefenderreboot;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;

/**
 * The {@code CommandManager} class is responsible for managing the registration of command executors
 * for the SkyDefenderReboot plugin.
 * <p>
 * This class provides a way to associate command names with their corresponding command executors.
 */
public class CommandManager {

    /**
     * Constructs a new {@code CommandManager} and sets up command executors for the SkyDefenderReboot plugin.
     *
     * @param plugin The instance of the SkyDefenderReboot plugin.
     */
    public CommandManager(SkyDefenderReboot plugin) {
        registerCommandExecutor(plugin, "skydefenderreboot", new Commands());
        registerCommandExecutor(plugin, "test", new TestCommands());
    }

    /**
     * Registers a command executor for the specified command.
     *
     * @param plugin   The instance of the plugin.
     * @param command  The name of the command.
     * @param executor The command executor associated with the command.
     */
    private void registerCommandExecutor(SkyDefenderReboot plugin, String command, CommandExecutor executor) {
        PluginCommand pluginCommand = plugin.getCommand(command);
        if (pluginCommand != null) {
            pluginCommand.setExecutor(executor);
        } else {
            plugin.getLogger().warning("Failed to register command executor for '" + command + "'. Command not found.");
        }
    }
}

