package com.hostel.configuration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * 
 *
 */
@EnableAutoConfiguration
@ComponentScan(basePackages = { "com.hostel.configuration", 
		"com.hostel.controller", 
		"com.hostel.service",
		"com.hostel.entity"})
public class Application {
	public static void main(String[] args) throws Exception {
		SpringApplication.run(Application.class, args);
	}
	
	
/*
 * http://kielczewski.eu/2014/04/spring-boot-mvc-application/
 */
}