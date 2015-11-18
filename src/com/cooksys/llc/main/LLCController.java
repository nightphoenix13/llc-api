package com.cooksys.llc.main;

import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LLCController {

	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();
	private final Logger logger = LoggerFactory.getLogger(LLCController.class);
	
	@RequestMapping(value="/zips/by/stateCounty/{state}/{county}", method=RequestMethod.POST)
	public String zipsByStateCounty(@RequestBody String string){
		
		logger.info("llc-api - Rest call to /zips/by/stateCounty/{state}/{county} received");
		
		return "test";
	}
	
	@RequestMapping("/test")
	public Test test(@RequestParam(value="name", defaultValue="World") String name) {
		
		logger.info("llc-api - Rest call to /test received");
		
		return new Test(counter.incrementAndGet(), String.format(template, name));
	}
}