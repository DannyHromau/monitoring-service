package com.dannyhromau.monitoring.meter.core.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.List;

@Getter
@Setter
@Configuration
@PropertySource("classpath:application.yml")
public class AppConfig {
    @Value("${spring.liquibase.enabled}")
    private String liquibaseEnabled;

    @Value("${spring.liquibase.drop-first}")
    private String liquibaseDropFirst;

    @Value("${spring.liquibase.change-log}")
    private String liquibaseChangeLog;

    @Value("${spring.liquibase.default-schema}")
    private String liquibaseDefaultSchema;

    @Value("${spring.liquibase.liquibase-schema}")
    private String liquibaseSchema;

    @Value("${spring.liquibase.schemas}")
    private String liquibaseSchemas;

    @Value("${spring.datasource.url}")
    private String dataSourceUrl;

    @Value("${spring.datasource.username}")
    private String dataSourceUsername;

    @Value("${spring.datasource.password}")
    private String dataSourcePassword;

    @Value("${spring.datasource.driver-class-name}")
    private String dataSourceDriverClassName;

    @Value("${spring.auditsource.url}")
    private String auditSourceUrl;

    @Value("${spring.auditsource.username}")
    private String auditSourceUsername;

    @Value("${spring.auditsource.password}")
    private String auditSourcePassword;

    @Value("${spring.auditsource.driver-class-name}")
    private String auditSourceDriverClassName;

    @Value("${spring.liquibasesource.url}")
    private String liquibaseSourceUrl;

    @Value("${spring.liquibasesource.username}")
    private String liquibaseSourceUsername;

    @Value("${spring.liquibasesource.password}")
    private String liquibaseSourcePassword;

    @Value("${spring.liquibasesource.driver-class-name}")
    private String liquibaseSourceDriverClassName;

    @Value("${validator.credential.emailPattern}")
    private String emailPattern;

    @Value("${validator.credential.passwordPattern}")
    private String passwordPattern;

    @Value("#{'${permission.unauthorized.urls}'.split(',')}")
    private List<String> urls;
}
