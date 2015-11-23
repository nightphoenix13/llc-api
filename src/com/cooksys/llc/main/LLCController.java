package com.cooksys.llc.main;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LLCController {

	private final Logger logger = LoggerFactory.getLogger(LLCController.class);
	@Autowired
	private RedisService service = new RedisService();
	
	@RequestMapping(value="/zips/by/stateCounty/{state}/{county}", method=RequestMethod.GET)
	public List<String> zipsByStateCounty(@PathVariable("state") String state,
			@PathVariable("county") String county){
		
		logger.info("llc-api - Rest call to /zips/by/stateCounty/{state}/{county} received");
		
		List<String> output = service.getZipsByStateCounty(state + "_" + county);
		
		logger.info(output.toString());
		
		return output;
	}
}