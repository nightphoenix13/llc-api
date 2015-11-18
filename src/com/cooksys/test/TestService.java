package com.cooksys.test;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service("testService")
public class TestService {

	@Autowired
	private RedisTemplate<String, Object> template;

	public Object getValue(final String key) {
		return template.opsForValue().get(key);
	}

	public void setValue(final String key, final String value) {
		template.opsForValue().set(key, value);
	}

	public void setUser(final User user) {
		final String key = String.format("user:%s", user.getId());
		final Map<String, Object> properties = new HashMap<String, Object>();

		properties.put("id", user.getId());
		properties.put("name", user.getName());
		properties.put("email", user.getEmail());

		template.multi();
		template.opsForHash().putAll(key, properties);
		template.exec();
	}

	public User getUser(final String id) {
		final String key = String.format("user:%s", id);

		final String name = (String) template.opsForHash().get(key, "name");
		final String email = (String) template.opsForHash().get(key, "email");

		return new User(id, name, email);
	}

}
