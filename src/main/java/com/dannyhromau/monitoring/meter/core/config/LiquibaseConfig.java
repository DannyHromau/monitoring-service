package com.dannyhromau.monitoring.meter.core.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.List;

@Getter
@Setter
@Configuration
@PropertySource("classpath:application.yml")
public class LiquibaseConfig {
    private boolean enabled;
    private boolean dropFirst;
    private String changeLog;
    private String defaultSchema;
    private String liquibaseSchema;
    private List<String> schemas;
}
