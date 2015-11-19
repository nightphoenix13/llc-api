package com.cooksys.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class TestApplication {

	public static void main(String[] args) {
//		final ApplicationContext context = new AnnotationConfigApplicationContext(TestAppConfig.class);
//		final IRedisService service = context.getBean( IRedisService.class );
//		Object[] sources = {TestApplication.class , TestAppConfig.class };
		ApplicationContext context = SpringApplication.run(TestApplication.class, args);
//		SpringApplication.run(TestAppConfig.class, args);
//		ApplicationContext ctx = SpringApplication.run(sources, args);
		TestService service = context.getBean(TestService.class);
//		service.setUser(new User("5", "dustin", "dustin@dustin.com"));
//		User user = service.getUser("1");
//		System.out.println(user.getEmail());
		service.massInsertion();
	}
}
