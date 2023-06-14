package com.example.springboot.log;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingAspect.class);

    @Before("execution(* com.example.springboot.controller.BookController.*(..))")
    public void logBefore(JoinPoint joinPoint) {
        LOGGER.info("Executing method: {}", joinPoint.getSignature().toShortString());
    }

    @AfterReturning("execution(* com.example.springboot.controller.BookController.*(..))")
    public void logAfterReturning(JoinPoint joinPoint) {
        LOGGER.info("Finished executing method: {}", joinPoint.getSignature().toShortString());
    }

    @AfterThrowing(pointcut = "execution(* com.example.springboot.controller.BookController.*(..))", throwing = "ex")
    public void logAfterThrowing(JoinPoint joinPoint, Exception ex) {
        LOGGER.error("Exception thrown from method: {}", joinPoint.getSignature().toShortString());
        LOGGER.error("Exception message: {}", ex.getMessage());
    }
}