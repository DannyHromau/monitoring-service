package com.dannyhromau.monitoring.meter.core.util;

import com.dannyhromau.monitoring.meter.core.config.JdbcConfig;
import lombok.RequiredArgsConstructor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@RequiredArgsConstructor
public class JdbcUtil {
    private final String propertyFilePath;
    public Connection getConnection() throws SQLException {
        JdbcConfig config = new JdbcConfig();
        Connection con = DriverManager.getConnection(
                config.getProperty(JdbcConfig.DB_URL, propertyFilePath),
                config.getProperty(JdbcConfig.DB_USERNAME, propertyFilePath),
                config.getProperty(JdbcConfig.DB_PASSWORD, propertyFilePath));
        return con;
    }
}
