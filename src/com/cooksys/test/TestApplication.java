package com.cooksys.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class TestApplication {

	public static void main(String[] args) {
		Object[] sources = {TestApplication.class , TestAppConfig.class };
		ApplicationContext ctx = SpringApplication.run(sources, args);
//		TestService service = new TestService();
//		service.setUser(new User("1", "dustin", "dustin@dustin.com"));
	}
}
