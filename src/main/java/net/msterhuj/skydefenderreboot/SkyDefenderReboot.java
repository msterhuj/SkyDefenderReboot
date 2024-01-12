package net.msterhuj.skydefenderreboot;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import lombok.Getter;
import net.msterhuj.skydefenderreboot.core.GameManager;
import net.msterhuj.skydefenderreboot.core.GameTask;
import net.msterhuj.skydefenderreboot.utils.GameConfig;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;

public final class SkyDefenderReboot extends JavaPlugin {

    @Getter
    private static SkyDefenderReboot instance;

    @Getter
    private static GameManager gameManager;

    @Getter
    private static GameConfig gameConfig;

    @Getter
    private static CommandManager commandManager;

    @Getter
    private static ListenerManager listenerManager;

    @Override
    public void onEnable() {
        instance = this;
        this.saveDefaultConfig();
        gameConfig = new GameConfig();
        this.loadGameManager();
        commandManager = new CommandManager(this);
        listenerManager = new ListenerManager(this);

        GameTask gameTask = new GameTask(this, gameManager);
        gameTask.runTaskTimer(this, 0, 20L);
    }

    @Override
    public void onDisable() {
        this.saveGameManager();
    }

    public void initNew() {
        gameManager = new GameManager();
        this.saveGameManager();
    }

    public void loadGameManager() {
        instance.getLogger().info("Loading data...");
        try {
            Gson gson = new Gson();
            JsonReader reader = new JsonReader(new FileReader(instance.getDataFolder().getPath() + "/data.json"));
            gameManager = gson.fromJson(reader, GameManager.class);
            instance.getLogger().info("Data loaded!");
        } catch (FileNotFoundException e) {
            instance.getLogger().info("Data not found! Creating new data file...");
            this.initNew();
        }
    }
    public void saveGameManager() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        instance.getLogger().info("Saving data...");
        try {
            String data = gson.toJson(SkyDefenderReboot.gameManager);
            String path = SkyDefenderReboot.getInstance().getDataFolder().getPath();
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File(path, "data.json")));
            writer.write(data);
            writer.close();
            instance.getLogger().info("Data saved!");
        } catch (IOException | StackOverflowError e) {
            instance.getLogger().warning("Data not saved!");
            instance.getLogger().warning("Error: " + e.getMessage());
        }
    }
}
