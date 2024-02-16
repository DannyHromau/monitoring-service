package com.dannyhromau.monitoring.meter.config;

import com.dannyhromau.monitoring.meter.core.config.AuditJdbcConfig;
import com.dannyhromau.monitoring.meter.core.config.JdbcConfig;
import com.dannyhromau.monitoring.meter.security.provider.custom.CustomSecurityConfig;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;


public class MonitoringWebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{CustomSecurityConfig.class,
                JdbcConfig.class,
                AuditJdbcConfig.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{AppConfig.class};
    }

    @NonNull
    @Override
    protected String[] getServletMappings() {
        return new String[]{"/*"};
    }

}

