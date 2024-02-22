package com.dannyhromau.monitoring.meter.aspect;

import com.dannyhromau.monitoring.meter.model.audit.Audit;
import com.dannyhromau.monitoring.meter.service.AuditService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

//TODO: refactor logAround logic
@Component
@Aspect
@RequiredArgsConstructor
public class AuditAspect {
    private static final Logger logger = LogManager.getLogger(AuditAspect.class);
    private final AuditService auditService;

    @Around("@annotation(com.dannyhromau.monitoring.meter.annotation.Auditable)")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        StringBuilder auditingArgsBuilder = new StringBuilder();
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            auditingArgsBuilder.append(arg.toString()).append(" ");
        }
        String action = "Method called: " + methodName;
        Audit audit = Audit.builder()
                .auditingArgs(auditingArgsBuilder.toString())
                .audit_action(action)
                .audit_time(LocalDateTime.now())
                .build();
        try {
            auditService.add(audit);
        } catch (Exception e) {
            logger.error("Error adding audit record: " + e.getMessage());
        }
        return joinPoint.proceed();
    }
}

