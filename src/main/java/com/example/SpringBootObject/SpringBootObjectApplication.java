package com.example.SpringBootObject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;


@EnableScheduling
@SpringBootApplication
@EnableCaching
@EnableAspectJAutoProxy
public class SpringBootObjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootObjectApplication.class, args);
	}

}
