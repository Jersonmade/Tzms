package com.example.Tzms.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
public class MethodTimerAspect {
    private static final Logger logger = LoggerFactory.getLogger(MethodTimerAspect.class);

    @Around("@annotation(MethodTimer)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {

        final StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        try {
            return joinPoint.proceed();
        } finally {
            stopWatch.stop();
            logger.info("Method {} executed in {} ms", joinPoint.getSignature(), stopWatch.getTotalTimeMillis());
        }
    }
}
