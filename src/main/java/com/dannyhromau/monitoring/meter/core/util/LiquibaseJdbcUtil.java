package com.dannyhromau.monitoring.meter.core.util;

import com.dannyhromau.monitoring.meter.core.config.AppConfig;
import liquibase.integration.spring.SpringLiquibase;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;


@Component
@RequiredArgsConstructor
public class LiquibaseJdbcUtil {

    private final AppConfig appConfig;

    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(appConfig.getDataSourceUrl());
        dataSource.setUsername(appConfig.getDataSourceUsername());
        dataSource.setPassword(appConfig.getDataSourcePassword());
        dataSource.setDriverClassName(appConfig.getDataSourceDriverClassName());
        return dataSource;
    }

    @Bean
    public SpringLiquibase liquibase() {
        SpringLiquibase liquibase = new SpringLiquibase();
        if (Boolean.parseBoolean(appConfig.getLiquibaseEnabled())) {
            liquibase.setDataSource(dataSource());
            liquibase.setChangeLog(appConfig.getLiquibaseChangeLog());
            liquibase.setLiquibaseSchema(appConfig.getLiquibaseSchema());
            liquibase.setDropFirst(Boolean.parseBoolean(appConfig.getLiquibaseDropFirst()));
        } else {
            liquibase = null;
        }
        return liquibase;
    }
}
