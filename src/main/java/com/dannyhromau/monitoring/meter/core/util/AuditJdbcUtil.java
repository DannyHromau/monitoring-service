package com.dannyhromau.monitoring.meter.core.util;

import com.dannyhromau.monitoring.meter.core.config.AppConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Component
@RequiredArgsConstructor
public class AuditJdbcUtil {
    private final AppConfig appConfig;

    public Connection getConnection() throws SQLException {
        Connection con = DriverManager.getConnection(
                appConfig.getAuditSourceUrl(),
                appConfig.getAuditSourceUsername(),
                appConfig.getAuditSourcePassword());
        return con;
    }
}
