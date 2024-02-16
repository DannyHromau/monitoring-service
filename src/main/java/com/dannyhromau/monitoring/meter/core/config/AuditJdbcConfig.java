package com.dannyhromau.monitoring.meter.core.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;

@Getter
@Setter
@Configuration
@PropertySource("classpath:application.yml")
public class AuditJdbcConfig {
    @Value("${spring.auditsource.url}")
    private String url;
    @Value("${spring.auditsource.username}")
    private String username;
    @Value("${spring.auditsource.password}")
    private String password;
    @Value("${spring.auditsource.driver-class-name}")
    private String driverClassName;


}
