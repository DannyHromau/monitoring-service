package com.dannyhromau.audit.module.aspect;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;


@Aspect
@Component
@Log4j2
public class ExecutionTimeLoggingAspect {

    private long startTime;

    @Before("execution(* com.dannyhromau..*.*(..))")
    public void logBefore(JoinPoint joinPoint) {
        startTime = System.currentTimeMillis();
    }

    @After("execution(* com.dannyhromau..*.*(..))")
    public void logAfter(JoinPoint joinPoint) {
        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;
        String methodName = joinPoint.getSignature().getName();
        String logMessage = String.format("Method %s executed in %d ms", methodName, executionTime);
        log.warn(logMessage);
    }
}

