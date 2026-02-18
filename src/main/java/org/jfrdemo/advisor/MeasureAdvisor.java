package org.jfrdemo.advisor;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.jfrdemo.statistics.StatisticsKeeper;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class MeasureAdvisor {

    @Around(value = "@annotation(org.jfrdemo.annot.Measure)")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        long timeStart = System.nanoTime();
        Object proceed = joinPoint.proceed();
        long timeEnd = System.nanoTime();
        Signature signature = joinPoint.getStaticPart().getSignature();
        String declaringTypeName = signature.getDeclaringTypeName();
        String methodName = signature.getName();
        long time = timeEnd - timeStart;
        log.info("Execution of {}.{} takes {}", declaringTypeName, methodName, time);
        StatisticsKeeper.setMaxVal(declaringTypeName+methodName, time);
        return proceed;
    }

}
