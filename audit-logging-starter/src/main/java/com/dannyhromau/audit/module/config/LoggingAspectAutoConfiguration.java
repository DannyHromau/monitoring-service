package com.dannyhromau.audit.module.config;

import com.dannyhromau.audit.module.aspect.ExecutionTimeLoggingAspect;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;

@Configuration
@EnableAspectJAutoProxy
@Import(ExecutionTimeLoggingAspect.class)
public class LoggingAspectAutoConfiguration {
}
