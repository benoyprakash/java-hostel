package com.hostel.configuration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * 
 *
 */
@EnableWebMvc
@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan(basePackages = { "com.hostel.configuration", 
		"com.hostel.controller", 
		"com.hostel.service",
		"com.hostel.entity"})
//com.hostel.entity
@EnableJpaRepositories("com.hostel.repository")
public class Application extends WebMvcAutoConfiguration{
	public static void main(String[] args) throws Exception {
		SpringApplication.run(Application.class, args);
		
	}
	
	
/*
 * http://kielczewski.eu/2014/04/spring-boot-mvc-application/
 */
	
	
	
}