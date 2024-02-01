package com.dannyhromau.monitoring.meter.db.migration;

import com.dannyhromau.monitoring.meter.core.util.JdbcUtil;
import liquibase.Liquibase;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;
import lombok.RequiredArgsConstructor;

import java.sql.Connection;

@RequiredArgsConstructor
public class LiquibaseRunner {
    private final JdbcUtil jdbcUtil;
    public void run(String changeLogPath) {
        try (Connection connection = jdbcUtil
                .getConnection()) {
            Liquibase liquibase = new Liquibase(changeLogPath,
                    new ClassLoaderResourceAccessor(),
                    DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection)));
            liquibase.update();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}