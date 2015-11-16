package com.cooksys.llc.main;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RestController
public class LLCController {

	@RequestMapping(value="/zips/by/stateCounty/TX/Travis", method=RequestMethod.POST)
	public @ResponseBody String zipsByStateCounty(@RequestBody String string){
		return "test";
	}
	
}
