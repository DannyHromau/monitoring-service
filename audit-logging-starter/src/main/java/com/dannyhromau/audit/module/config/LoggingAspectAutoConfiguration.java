package com.dannyhromau.audit.module.config;

import com.dannyhromau.audit.module.aspect.ExecutionTimeLoggingAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
public class LoggingAspectAutoConfiguration {

    @Bean
    public ExecutionTimeLoggingAspect executionTimeLoggingAspect() {
        return new ExecutionTimeLoggingAspect();
    }
}
