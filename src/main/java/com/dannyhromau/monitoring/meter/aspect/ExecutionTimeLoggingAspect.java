package com.dannyhromau.monitoring.meter.aspect;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ExecutionTimeLoggingAspect {

    private static Logger logger = LogManager.getLogger(ExecutionTimeLoggingAspect.class);
    private long startTime;

    @Before("@annotation(com.dannyhromau.monitoring.meter.annotation.AspectLogging)")
    public void logBefore(JoinPoint joinPoint) {
        startTime = System.currentTimeMillis();
    }

    @After("@annotation(com.dannyhromau.monitoring.meter.annotation.AspectLogging)")
    public void logAfter(JoinPoint joinPoint) {
        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;
        String methodName = joinPoint.getSignature().getName();
        String logMessage = String.format("Method %s executed in %d ms", methodName, executionTime);
        logger.log(Level.INFO, logMessage);
    }
}
