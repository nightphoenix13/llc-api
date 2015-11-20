package com.cooksys.llc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.cooksys.llc.main.County;
import com.cooksys.llc.main.ZipCode;

@Service("ZipCodeService")
public class ZipCodeServiceImpl implements ZipCodeService {

//	@Autowired
	private RedisTemplate<String, ZipCode> redisTemplate;
	
	
	private static String ZIP_CODE_KEY = "county";
	private static String COUNTY_KEY = "id";
	
	@Override
	public ZipCode findZipCodeOnly(String countyName) {
		return (ZipCode) this.redisTemplate.opsForHash().get(ZIP_CODE_KEY, ZIP_CODE_KEY + ":" + countyName);
	}

	@Override
	public County findByCounty(String county) {
		//County county = (County) this.redisTemplate.opsForHash().get(COUNTY_KEY, "1");
		County newCounty = new County("Travis", "78759","???");
		if (county != null)
			return newCounty;
		else {
			System.out.println("it's null dammit!");
			return null;
		}
			
	}
	

	public RedisTemplate<String, ZipCode> getRedisTemplate() {
		return redisTemplate;
	}

	public void setRedisTemplate(RedisTemplate<String, ZipCode> redisTemplateZipCode) {
		this.redisTemplate = redisTemplateZipCode;
	}

}
