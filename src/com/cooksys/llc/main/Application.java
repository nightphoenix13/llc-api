package com.cooksys.llc.main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
	
	private static final Logger logger = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) {
		
		logger.info("llc-api - About to launch application");
		
		SpringApplication.run(Application.class, args);
		
		logger.info("llp-api - Application launched");
	}
}