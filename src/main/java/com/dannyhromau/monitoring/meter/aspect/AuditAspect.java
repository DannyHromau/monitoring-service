package com.dannyhromau.monitoring.meter.aspect;

import com.dannyhromau.monitoring.meter.model.audit.UserAudit;
import com.dannyhromau.monitoring.meter.service.AuditService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;

import java.sql.SQLException;
import java.time.LocalDateTime;

@Aspect
@RequiredArgsConstructor
public class AuditAspect {
    private static final Logger logger = LogManager.getLogger(AuditAspect.class);
    private final AuditService<UserAudit> auditService;

    @AfterReturning("@annotation(com.dannyhromau.monitoring.meter.annotation.AspectAuditLogging)")
    public void logMethodExecution(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        StringBuilder auditingArgsBuilder = new StringBuilder();
        Object[] args = joinPoint.getArgs();
        for (Object o : args){
            auditingArgsBuilder.append(o.toString()).append(" ");
        }
        String action = "called: " + methodName;
        UserAudit userAudit = UserAudit.builder()
                .auditingArgs(auditingArgsBuilder.toString())
                .action(action)
                .timestamp(LocalDateTime.now())
                .build();
        try {
            auditService.add(userAudit);
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
    }
}
