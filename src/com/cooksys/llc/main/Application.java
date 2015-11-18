package com.cooksys.llc.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class Application {

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
		Object[] sources = { Application.class, AppConfig.class };
		ApplicationContext ctx = SpringApplication.run(sources, args);
	}
}