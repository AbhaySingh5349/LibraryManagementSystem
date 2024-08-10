package com.example.LibraryManagementSystem;

import com.example.LibraryManagementSystem.model.Book;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

// Point Cut Expression: execution modifier, return type, package, class, method

@Aspect
@Component
@Slf4j
public class CustomAspect {
    @Before("execution(* com.example.LibraryManagementSystem.controller.BookController.getBooks(..))")
    public void emitBeforeLogs(JoinPoint joinPoint){
        log.info("emit logs BEFORE: " + joinPoint.getSignature());
    }

    @Around("execution(* com.example.LibraryManagementSystem.service.BookService.getBooks(..))")
    public Object emitAroundLogs(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        log.info("emit logs AROUND start: " + proceedingJoinPoint.getSignature());

        Object response = proceedingJoinPoint.proceed();// method execution completes here

        log.info("emit logs AROUND end: " + proceedingJoinPoint.getSignature());

        return response;
    }

    @After("execution(* com.example.LibraryManagementSystem.controller.BookController.getBooks(..))")
    public void emitAfterLogs(JoinPoint joinPoint){
        log.info("emit logs AFTER: " + joinPoint.getSignature());
    }

    @Around("@annotation(com.example.LibraryManagementSystem.annotations.LogAnnotation)")
    public Object emitAroundLogsUsingAnnotation(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        log.info("emit Annotation logs AROUND start: " + proceedingJoinPoint.getSignature());

        Object response = proceedingJoinPoint.proceed();

        log.info("emit Annotation logs AROUND end: " + proceedingJoinPoint.getSignature());

        return response;
    }
}
