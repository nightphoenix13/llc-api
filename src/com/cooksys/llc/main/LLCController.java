package com.cooksys.llc.main;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.boot.SpringApplication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LLCController {

	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();
	
	@RequestMapping(value="/zips/by/stateCounty/TX/Travis", method=RequestMethod.POST)
	public @ResponseBody String zipsByStateCounty(@RequestBody String string){
		return "test";
	}
	
	@RequestMapping("/")
	public Test test(@RequestParam(value="name", defaultValue="World") String name) {
		return new Test(counter.incrementAndGet(), String.format(template, name));
	}
}