package com.dannyhromau.monitoring.meter.core.util;

import com.dannyhromau.monitoring.meter.core.config.JdbcConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Component
@RequiredArgsConstructor
public class JdbcUtil {
    private final JdbcConfig jdbcConfig;
    public Connection getConnection() throws SQLException {
        Connection con = DriverManager.getConnection(
                jdbcConfig.getUrl(),
                jdbcConfig.getUsername(),
                jdbcConfig.getPassword());
        return con;
    }
}
