package com.dannyhromau.monitoring.meter.security.common;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.ArrayList;

@Getter
@Setter
@Configuration
@PropertySource("classpath:application.yml")
public class SecurityUrlConfig {
    private ArrayList<String> urls;
}
