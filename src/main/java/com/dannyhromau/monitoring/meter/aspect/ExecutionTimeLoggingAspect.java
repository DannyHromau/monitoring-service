package com.dannyhromau.monitoring.meter.aspect;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class ExecutionTimeLoggingAspect  {

    private static final Logger logger = LogManager.getLogger(ExecutionTimeLoggingAspect.class);

    @Around("@within(com.yourpackage.ExecutionTimeLogging)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getName();
        String logMessage = String
                .format("Method %s in class %s executed in %d ms", methodName, className, executionTime);
        logger.log(Level.INFO, logMessage);
        return result;
    }
}
