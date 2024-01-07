package net.msterhuj.skydefenderreboot.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Properties;

public class ServerPropertiesParser {

    public static String getString(String key) {
        Properties properties = new Properties();
        try {
            FileInputStream fileInputStream = new FileInputStream("server.properties");
            properties.load(fileInputStream);
            String string = properties.getProperty(key);
            return string;
        } catch (IOException e) {
            System.out.println("Error while reading server.properties");
        }
        return "";
    }

    public static void setString(String key, String newValue ) {
        Properties properties = new Properties();
        try {
            File file = new File("server.properties");
            FileInputStream fileInputStream = new FileInputStream(file);
            properties.load(fileInputStream);
            properties.setProperty(key,newValue);
            properties.store(Files.newOutputStream(file.toPath()), null);
        } catch (IOException e) {
            System.out.println("Error while manipulating server.properties");
        }
    }
}
