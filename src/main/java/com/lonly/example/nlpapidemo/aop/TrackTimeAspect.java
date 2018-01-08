package com.lonly.example.nlpapidemo.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Slf4j(topic = "TrackTime")
@Component
@Aspect
public class TrackTimeAspect {

    /**
     * 构造函数被调用
     */
    @Pointcut("execution(* com.lonly.example.nlpapidemo.utils..*(..)))")
    public void callConstructor() {
    }

    @Around("callConstructor()")
    public Object ProfierTime(ProceedingJoinPoint pjp) throws Throwable {
        long start = System.currentTimeMillis();
        Object output = pjp.proceed();
        long elapsedTime = System.currentTimeMillis() - start;
        log.info("{} execution time: {} milliseconds.", pjp.getSignature(), elapsedTime);
        return output;
    }
}
