package com.dannyhromau.monitoring.meter.core.config;

import java.io.InputStream;
import java.util.Properties;

//TODO: add logic to catch block
public class LiquibaseConfig {
    public static final String SCHEMA_SYSTEM = "liquibase.schema.system";
    public static final String SCHEMA_AUDIT = "liquibase.schema.audit";
    public static final String SCHEMA_MAIN = "liquibase.schema.application";
    public static final String MASTER_PATH = "liquibase.changelog.master";

    private Properties properties = new Properties();

    public synchronized String getProperty(String name, String path) {
        if (properties.isEmpty()) {
            try (InputStream is = JdbcConfig.class.getClassLoader().getResourceAsStream(path)) {
                properties.load(is);
            } catch (Exception ex) {
                ex.printStackTrace();
                throw new RuntimeException();
            }
        }
        return properties.getProperty(name);
    }
}
