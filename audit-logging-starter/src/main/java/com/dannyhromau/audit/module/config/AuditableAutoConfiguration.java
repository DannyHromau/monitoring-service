package com.dannyhromau.audit.module.config;

import com.dannyhromau.audit.module.annotation.Auditable;
import com.dannyhromau.audit.module.aspect.AuditAspect;
import com.dannyhromau.audit.module.service.AuditService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@ConditionalOnClass(Auditable.class)
@EnableAspectJAutoProxy
public class AuditableAutoConfiguration {

    @Bean
    @ConditionalOnBean(AuditService.class)
    public AuditAspect auditAspect(AuditService auditService) {
        return new AuditAspect(auditService);
    }
}
