package com.dannyhromau.monitoring.meter.aspect;

import com.dannyhromau.monitoring.meter.model.audit.Audit;
import com.dannyhromau.monitoring.meter.service.AuditService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;
import java.time.LocalDateTime;

@Aspect
@Component
public class AuditAspect {
    private static final Logger logger = LogManager.getLogger(AuditAspect.class);
    private AuditService auditService;
    public AuditAspect() {
    }

    @Around("@annotation(com.dannyhromau.monitoring.meter.annotation.Auditable)")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        System.out.println();
        StringBuilder auditingArgsBuilder = new StringBuilder();
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            auditingArgsBuilder.append(arg.toString()).append(" ");
        }
        String action = "Method called: " + methodName;
//        Audit audit = Audit.builder()
//                .auditingArgs(auditingArgsBuilder.toString())
//                .action(action)
//                .timestamp(LocalDateTime.now())
//                .build();
        try {
//            auditService.add(audit);
        } catch (Exception e) {
            logger.error("Error adding audit record: " + e.getMessage());
        }
        return joinPoint.proceed();
    }
}

