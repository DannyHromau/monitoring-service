package com.dannyhromau.monitoring.system.meter.core.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "validator.credential")
public class ValidatorConfig {
    private String loginPattern;
    private String passwordPattern;
}
