package com.example.SpringBootObject.AOP;

import com.example.SpringBootObject.Model.Product;
import com.example.SpringBootObject.Service.ProductService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.RedirectView;

@Aspect
@Component
@SuppressWarnings("unused")

public class ProductValidationAspect {
    @Autowired
    private ProductService productService;

    @Pointcut("execution(* com.example.SpringBootObject.Controller.*.*(..)) && args(productId, ..)")
    public void methodsRequiringUserValidation(int productId) {}

    @Around("methodsRequiringUserValidation(userDetails)")
    public Object validateProduct(ProceedingJoinPoint joinPoint, int productId) throws Throwable {
        Product product = productService.getProductById(productId);
        if (product == null) {
            return new RedirectView("redirect:/productPage");
        }
        return joinPoint.proceed();
    }

}
