package com.rbts.hrms.multitenant.logging;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.Date;


@Aspect
@Configuration
@Component
public class LoggingAspects {

    private Logger log = LoggerFactory.getLogger(LoggingAspects.class);

    @Before(value = "execution(* com.rbts.hrms.multitenant.Controller..*(..))")
    public void beforeAdvice(JoinPoint joinPoint) {
        log.info("Request to" + joinPoint.getSignature() + "started at" + new Date());
    }



    @After(value = "execution(* com.rbts.hrms.multitenant.Service..*(..))")
    public void afterAdvice(JoinPoint joinPoint) {
        log.info("Request to" + joinPoint.getSignature() + "ended at" + new Date());
    }


//    @AfterReturning(value = "execution(* com.example.BaseMicroservice1.Service.EmployeeService.save*(..))")
//    public void afterReturningAdviceSaveService(JoinPoint joinPoint){
//        System.out.println("Bussiness logic to save an emplyoee can succesfully and employee is save with id " + new Date());
//    }

//    @AfterThrowing(value = "execution(* com.example.BaseMicroservice1.Service.EmployeeService.save*(..))")
//    public void afterTrowingAdviceSaveService(JoinPoint joinPoint) {
//        System.out.println("Bussiness logic to save an employee throw new exception  " + new Date());
//    }


    //    @Around(value = "execution(* com.example.BaseMicroservice1.Controller.EmployeeController.*(..))")
//    public void timeTracker(JoinPoint joinPoint) {
//
//        long statTime=System.currentTimeMillis();
//
//        long timeTaken=System.currentTimeMillis()-statTime;
//       System.out.println("Request to" +timeTaken);
//    }

}
