package ru.sfedu.opensv.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.opensv.Constants;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigurationUtil {
    private static final String DEFAULT_CONFIG_PATH = "./src/main/resources/environment.properties";
    private static final Properties configuration = new Properties();
    private static final Logger log = LogManager.getLogger(ConfigurationUtil.class);

    public ConfigurationUtil() {
    }

    private static Properties getConfiguration() throws IOException {
        if (configuration.isEmpty()) loadConfiguration();
        return configuration;
    }

    private static void loadConfiguration() throws IOException {
        File file = new File(DEFAULT_CONFIG_PATH);
        String userPath = System.getProperty(Constants.ENVIRONMENT_PATH);

        if (userPath != null) {
            File userFile = new File(userPath);
            if (userFile.exists()) file = userFile;
            else log.error("Your environment configuration file not found. Default loaded.");
        }

        try (InputStream in = new FileInputStream(file)) {
            configuration.load(in);
        } catch (IOException e) {
            throw new IOException(e);
        }
    }

    public static String getConfigurationEntry(String key) throws IOException {
        return getConfiguration().getProperty(key);
    }
}
