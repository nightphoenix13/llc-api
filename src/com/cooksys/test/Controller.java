package com.cooksys.test;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

	private TestService service = new TestService();
	
	@RequestMapping("/demo")
	public String demo() {
		service.setUser(new User("1","dustin", "dbaugh@cooksys.com"));
		User user = service.getUser("1");
		return String.format("id:%s\nname:$s\nemail$s", user.getId(), user.getName(), user.getEmail());
	}
}
