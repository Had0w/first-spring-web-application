package com.klyuev.demoboot.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ProductRepositoryAspect {
    @Before("execution(public * com.klyuev.demoboot.repositories.ProductRepository.*(..))")
    public void beforeUsingProductRepository() {
        System.out.println("ProductRepository is using...");
    }

    @AfterThrowing(pointcut = "execution(public * com.klyuev.demoboot.repositories.ProductRepository.*(..))",
    throwing = "exc")
    public void afterThrowing(JoinPoint joinPoint, Throwable exc) {
        System.out.println(exc);
    }
    @Around(value = "execution(public * com.klyuev.demoboot.repositories.ProductRepository.*(..))")
    public Object getAllProductsTimer(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        System.out.println("starting proceeding");
        long start = System.currentTimeMillis();
        Object obj = proceedingJoinPoint.proceed();
        long end = start - System.currentTimeMillis();
        System.out.println("operation - " + end + "ms");
        System.out.println("ending proceeding");
        return obj;
    }
}
