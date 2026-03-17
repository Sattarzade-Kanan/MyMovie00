package com.example.demo.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
    @Before("execution(* com.example.demo.service.*.*(...)") //Point cut
    public void logBeForeMethod(JoinPoint joinPoint){
        System.out.println("AOP LOG -> method called:" + joinPoint.getSignature().getName());
    }
}
