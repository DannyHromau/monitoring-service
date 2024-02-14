package com.dannyhromau.monitoring.meter.core.config;

import com.dannyhromau.monitoring.meter.aspect.ExecutionTimeLoggingAspect;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class JdbcConfig {
    public static final String DB_URL = "db.url";
    public static final String DB_USERNAME = "db.username";
    public static final String DB_PASSWORD = "db.password";
    public static final String DB_DRIVER = "db.driverClassName";
    public static final String DB_CONSOLE_ENABLED = "db.console.enabled";
    private static Logger logger = LogManager.getLogger(ExecutionTimeLoggingAspect.class);
    private Properties properties = new Properties();

    public synchronized String getProperty(String name, String path) {
        if (properties.isEmpty()) {
            try (InputStream is = JdbcConfig.class.getClassLoader().getResourceAsStream(path)) {
                properties.load(is);
            } catch (IOException ex) {
                logger.log(Level.ERROR, ex.getMessage());
            }
        }
        return properties.getProperty(name);
    }
}
