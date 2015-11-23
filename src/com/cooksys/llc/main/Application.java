package com.cooksys.llc.main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

/**
 * Application class is the Spring Boot starter class. Execution of the main method starts
 * the embedded Tomcat server, starts the application, and loads data into redis.
 * @author Dustin Baugh, Zack Rosales
 * 
 */
@SpringBootApplication
public class Application {

	// SLF4J logger
	private static final Logger logger = LoggerFactory.getLogger(Application.class);
	
	/**
	 * Starting point of the application. Starts Tomcat server and loads data into redis. 
	 * @param args
	 */
	public static void main(String[] args) {
		
//		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
//		ctx.register(AppConfig.class);
//		ctx.refresh();
//		StringRedisTemplate stringRedisTemplate = ctx.getBean(StringRedisTemplate.class);
//		// Using set to set value
//		stringRedisTemplate.opsForValue().set("R", "Ram");
//		stringRedisTemplate.opsForValue().set("S", "Shyam");
//		// Fetch values from set
//		System.out.println(stringRedisTemplate.opsForValue().get("R"));
//		System.out.println(stringRedisTemplate.opsForValue().get("S"));
//		// Using Hash Operation
//		String mohan = "Mohan";
//		stringRedisTemplate.opsForHash().put("M", String.valueOf(mohan.hashCode()), mohan);
//		System.out.println(stringRedisTemplate.opsForHash().get("M", String.valueOf(mohan.hashCode())));

		logger.info("llc-api - About to launch application");
		
//		Object[] sources = { Application.class, AppConfig.class };
		ApplicationContext ctx = SpringApplication.run(Application.class, args);
		RedisService service = new RedisService();
		service.redisDataInsertion();
		
		logger.info("llp-api - Application launched");
	}
}