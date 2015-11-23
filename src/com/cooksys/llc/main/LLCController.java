package com.cooksys.llc.main;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
	public List<Integer> zipsByStateCounty(@PathVariable("state") String state,
			@PathVariable("county") String county){
		
		logger.info("Rest call to /zips/by/stateCounty/" + state + "/" + county + " received");
		
		List<Integer> output = service.getZipsByStateCounty(state , county);
		
		logger.info(output.toString());
		
		return output;
	}
	
	@RequestMapping(value="/zips/by/state/{state}", method=RequestMethod.GET)
	public Map<String, List<Integer>> zipsByState(@PathVariable("state") String state) {
		logger.info("Rest call to /zips/by/state/" + state + " received");
		
		Map<String, List<Integer>> output = service.getZipsByState(state);
		
		logger.info(output.toString());
		
		return output;
	}
}