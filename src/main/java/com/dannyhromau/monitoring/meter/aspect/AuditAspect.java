package com.dannyhromau.monitoring.meter.aspect;

import com.dannyhromau.monitoring.meter.core.config.LiquibaseConfig;
import com.dannyhromau.monitoring.meter.core.util.JdbcUtil;
import com.dannyhromau.monitoring.meter.model.audit.Audit;
import com.dannyhromau.monitoring.meter.repository.impl.jdbc.JdbcUserAuditRepository;
import com.dannyhromau.monitoring.meter.service.AuditService;
import com.dannyhromau.monitoring.meter.service.impl.AuditServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import java.sql.SQLException;
import java.time.LocalDateTime;

@Aspect
public class AuditAspect {
    private AuditService<Audit> auditService;
    private JdbcUserAuditRepository userAuditRepository;
    private JdbcUtil jdbcUtil;

    public AuditAspect() {
        LiquibaseConfig liquibaseConfig = new LiquibaseConfig();
        jdbcUtil = new JdbcUtil(liquibaseConfig
                .getProperty(LiquibaseConfig.SCHEMA_AUDIT, "db/liquibase.properties"));
        userAuditRepository = new JdbcUserAuditRepository(jdbcUtil);
        auditService = new AuditServiceImpl(userAuditRepository);
    }

    private static Logger logger = LogManager.getLogger(AuditAspect.class);

    @Around("@annotation(com.dannyhromau.monitoring.meter.annotation.Auditable)")
    public Object logAfter(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        StringBuilder auditingArgsBuilder = new StringBuilder();
        Object[] args = joinPoint.getArgs();
        for (Object o : args) {
            auditingArgsBuilder.append(o.toString()).append(" ");
        }
        String action = "called: " + methodName;
        Audit audit = Audit.builder()
                .auditingArgs(joinPoint.proceed().toString())
                .action(action)
                .timestamp(LocalDateTime.now())
                .build();
        try {
            auditService.add(audit);
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return joinPoint.proceed();
    }
}