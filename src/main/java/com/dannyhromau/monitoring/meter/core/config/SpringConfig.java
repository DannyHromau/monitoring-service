package com.dannyhromau.monitoring.meter.core.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
@ComponentScan("com.dannyhromau.monitoring.meter")
public class SpringConfig {
}
