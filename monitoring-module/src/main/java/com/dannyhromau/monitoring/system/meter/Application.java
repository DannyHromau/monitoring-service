package com.dannyhromau.monitoring.system.meter;

import com.dannyhromau.monitoring.system.meter.security.oauth2.provider.custom.PermissionConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

//TODO: move core tools to library-core module
@SpringBootApplication
@EnableConfigurationProperties({PermissionConfig.class})
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
