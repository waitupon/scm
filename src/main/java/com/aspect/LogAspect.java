package com.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.Arrays;


/**
 * Created by Administrator on 2017/10/9 0009.
 */
@Aspect
@Component
public class LogAspect {

    @Before("execution(* com.dao..*.*(..))")
    public void log(){
        System.out.println("log start");
    }

    @After("execution(* com.dao..*.*(..))")
    public void showInfo(JoinPoint point){
        System.out.println("log end");
        System.out.println("class=" + point.getTarget().getClass() + "  args=" + Arrays.asList(point.getArgs()) + " method=" + point.getSignature().getName());
    }
}
