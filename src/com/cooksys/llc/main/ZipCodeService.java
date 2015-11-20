package com.cooksys.llc.main;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Service;

@Service("zipCodeService")
public class ZipCodeService {

//	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	
	
	private static String ZIP_CODE_KEY = "county";
	private static String COUNTY_KEY = "id";
	
	public ArrayList<ZipCode> findZipCodeOnly(String countyName) {
		ArrayList<ZipCode> list = (ArrayList<ZipCode>) redisTemplate.opsForZSet().scan(ZIP_CODE_KEY + ":" + countyName, ScanOptions.NONE);
		ZipCode zipcode =  (ZipCode) this.redisTemplate.opsForHash().get(ZIP_CODE_KEY, ZIP_CODE_KEY + ":" + countyName);
		return list;
	}

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
	

	public RedisTemplate<String, Object> getRedisTemplate() {
		return redisTemplate;
	}

	public void setRedisTemplate(RedisTemplate<String, Object> redisTemplateZipCode) {
		this.redisTemplate = redisTemplateZipCode;
	}

}
