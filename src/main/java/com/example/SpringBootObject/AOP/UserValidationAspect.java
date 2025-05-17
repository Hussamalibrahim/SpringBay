package com.example.SpringBootObject.AOP;

import com.example.SpringBootObject.Model.Users;
import com.example.SpringBootObject.Service.UserService;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.RedirectView;

@Aspect
@Component
@SuppressWarnings("unused")
public class UserValidationAspect {

    @Autowired
    private UserService userService;

    @Pointcut("execution(* com.example.SpringBootObject.Controller.*.*(..)) && args(userDetails, ..)")
    public void methodsRequiringUserValidation(UserDetails userDetails) {}

    @Around("methodsRequiringUserValidation(userDetails)")
    public Object validateUser(ProceedingJoinPoint joinPoint, UserDetails userDetails) throws Throwable {
        Users user = userService.findByEmail(userDetails.getUsername());
        if (user == null) {
            return new RedirectView("/loginPage");
        }
        return joinPoint.proceed();
    }
}