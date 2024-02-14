package com.dannyhromau.monitoring.meter.core.config;

import com.dannyhromau.monitoring.meter.aspect.ExecutionTimeLoggingAspect;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class LiquibaseConfig {
    public static final String SCHEMA_SYSTEM = "liquibase.schema.system";
    public static final String SCHEMA_AUDIT = "liquibase.schema.audit";
    public static final String SCHEMA_MAIN = "liquibase.schema.application";
    public static final String MASTER_PATH = "liquibase.changelog.master";
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
