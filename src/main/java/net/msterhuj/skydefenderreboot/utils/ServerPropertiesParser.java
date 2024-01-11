package net.msterhuj.skydefenderreboot.utils;

import java.io.*;
import java.nio.file.Files;
import java.util.Properties;

/**
 * The {@code ServerPropertiesParser} class provides utility methods for reading and modifying
 * properties in the server.properties file.
 */
public class ServerPropertiesParser {

    private static String serverPropertiesPath = "server.properties";

    /**
     * Retrieves a string value from the server.properties file based on the provided key.
     *
     * @param key The key associated with the desired property.
     * @return The value of the property as a string, or an empty string if not found or an error occurs.
     */
    public static String getString(String key) {
        Properties properties = loadProperties();
        return properties.getProperty(key, "");
    }

    /**
     * Sets a string value for the specified key in the server.properties file.
     *
     * @param key      The key associated with the property to be modified.
     * @param newValue The new value to set for the property.
     */
    public static void setString(String key, String newValue) {
        Properties properties = loadProperties();
        properties.setProperty(key, newValue);

        saveProperties(properties);
    }

    /**
     * Loads properties from the server.properties file.
     *
     * @return A {@code Properties} object containing the loaded properties.
     */
    private static Properties loadProperties() {
        Properties properties = new Properties();
        try (FileInputStream fileInputStream = new FileInputStream(ServerPropertiesParser.serverPropertiesPath)) {
            properties.load(fileInputStream);
        } catch (IOException e) {
            System.out.println("Error while reading " + ServerPropertiesParser.serverPropertiesPath + " file.");
        }
        return properties;
    }

    /**
     * Saves modified properties to the server.properties file.
     *
     * @param properties The {@code Properties} object containing the modified properties.
     */
    private static void saveProperties(Properties properties) {
        try {
            File file = new File(ServerPropertiesParser.serverPropertiesPath);
            properties.store(Files.newOutputStream(file.toPath()), null);
        } catch (IOException e) {
            System.out.println("Error while manipulating server.properties" + ServerPropertiesParser.serverPropertiesPath + " file.");
        }
    }
}