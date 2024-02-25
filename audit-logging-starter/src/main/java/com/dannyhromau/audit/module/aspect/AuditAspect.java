package com.dannyhromau.audit.module.aspect;

import com.dannyhromau.audit.module.model.Audit;
import com.dannyhromau.audit.module.service.AuditService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Aspect
@RequiredArgsConstructor
@Log4j2
public class AuditAspect {
    private final AuditService auditService;

    @Around("@annotation(com.dannyhromau.audit.module.annotation.Auditable)")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        StringBuilder auditingArgsBuilder = new StringBuilder();
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            auditingArgsBuilder.append(arg.toString()).append(" ");
        }
        String action = "Method called: " + methodName;
        Audit audit = new Audit(LocalDateTime.now(), auditingArgsBuilder.toString(), action);
        try {
            auditService.add(audit);
        } catch (Exception e) {
            log.error("Error adding audit record: " + e.getMessage());
        }
        return joinPoint.proceed();
    }
}
