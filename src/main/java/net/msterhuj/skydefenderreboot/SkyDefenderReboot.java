package net.msterhuj.skydefenderreboot;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import lombok.Getter;
import net.msterhuj.skydefenderreboot.core.SkyDefenderRebootData;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;

public final class SkyDefenderReboot extends JavaPlugin {

    @Getter
    private static SkyDefenderReboot instance;

    @Getter
    private static SkyDefenderRebootData data;

    @Getter
    private static CommandManager commandManager;

    @Getter
    private static ListenerManager listenerManager;

    @Override
    public void onEnable() {
        instance = this;
        this.saveDefaultConfig();
        this.loadData();
        commandManager = new CommandManager(this);
        listenerManager = new ListenerManager(this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void initNew() {
        data = new SkyDefenderRebootData();
        this.saveData();
    }

    public void loadData() {
        instance.getLogger().info("Loading data...");
        if(new File(instance.getDataFolder() + "/data.json").exists()) {
            try {
                Gson gson = new Gson();
                JsonReader reader = new JsonReader(new FileReader(instance.getDataFolder().getPath() + "/data.json"));
                data = gson.fromJson(reader, SkyDefenderRebootData.class);
                instance.getLogger().info("Data loaded!");
            } catch (FileNotFoundException e) {
                instance.getLogger().info("Data not found! Creating new data file...");
                this.initNew();
            }
        } else {
            instance.getLogger().info("Data not found! Creating new data file...");
            this.initNew();
        }
    }
    public void saveData() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        instance.getLogger().info("Saving data...");
        try {
            String data = gson.toJson(SkyDefenderReboot.data);
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
