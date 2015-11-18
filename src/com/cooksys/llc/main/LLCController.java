package com.cooksys.llc.main;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.llc.service.ZipCodeService;
import com.cooksys.llc.service.ZipCodeServiceImpl;

@RestController
public class LLCController {

	private ZipCodeService service = new ZipCodeServiceImpl();
	
	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();
	
	@RequestMapping(value="/zips/by/stateCounty/TX/Travis", method=RequestMethod.POST)
	public String zipsByStateCounty(@RequestBody String string){
		return "test";
	}
	
	@RequestMapping("/test")
	public Test test(@RequestParam(value="name", defaultValue="World") String name) {
		return new Test(counter.incrementAndGet(), String.format(template, name));
	}
	
	@RequestMapping("/county")
	public Test findZipCode(@RequestParam(value="county", defaultValue="Travis") String county) {
		County countyImpl = service.findByCounty(county);
		if (countyImpl == null)
			return new Test(counter.incrementAndGet(), "EMPTY FUCKING STRING");
		return new Test(counter.incrementAndGet(), "not empty >_<");
		//service.findZipCodeOnly(county);
//		if (county.isEmpty())
//			return new Test(counter.incrementAndGet(), "EMPTY FUCKING STRING");
//		return new Test(counter.incrementAndGet(), "not empty >_<");		
	}
}