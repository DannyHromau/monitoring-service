package com.dannyhromau.audit.module.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;

@Configuration
@EnableAspectJAutoProxy
@ComponentScan("com.dannyhromau.audit.module")
@EnableJdbcRepositories("com")
public class AuditableAutoConfiguration {
}
