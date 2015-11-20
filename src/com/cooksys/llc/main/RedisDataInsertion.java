package com.cooksys.llc.main;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;

@Service("redisDataInsertion")
public class RedisDataInsertion {

	private static final String DIRECTORY = "src/main/resources/";
	private static final String COUNTY_FILE = "national_county.txt";
	private static final String ZIP_FILE = "COUNTY_ZIP_062015.csv";
	@Autowired
	private RedisTemplate<String, String> template;
	private static final Logger logger = LoggerFactory.getLogger(RedisDataInsertion.class);
	
	public void redisDataInsertion() {
		Jedis jedis = new Jedis("localhost", 6379);
		jedis.flushAll();
		int count = 0;
		Map<String, String> countyMap = getCounties();
		Map<String, String> zipMap = getZips();
		for (String countyKey : countyMap.keySet()) {
			String zips = "";
			for (String zipKey : zipMap.keySet()) {
				if (countyMap.get(countyKey).equals(zipMap.get(zipKey))) {
					if (zips.isEmpty()) {
						zips = zips + zipKey;
					} else {
						zips = zips + ", " + zipKey;
					}
				}
			}
			template.opsForHash().put(countyKey, "zipCodes", zips);
			count++;
		}
		logger.info(count + " counties loaded.");
	}

	private static Map<String, String> getZips() {
		BufferedReader reader = null;
		String line = "";
		String splitBy = ",";
		Map<String, String> zipMap = new HashMap<String, String>();
		try {
			reader = new BufferedReader(new FileReader(DIRECTORY + ZIP_FILE));
			while ((line = reader.readLine()) != null) {
				String[] zip = line.split(splitBy);
				zipMap.put(zip[1], zip[0]);
			}
		} catch (FileNotFoundException e) {
			logger.error("File not found: " + e);
		} catch (IOException e) {
			logger.error("Failed reading from file: " + e);
		} finally {
			try {
				if (reader != null) {
					reader.close();
				}
			} catch (IOException e) {
				logger.error("Failed closing BufferedReader: " + e);
			}
		}
		if (zipMap.isEmpty())
			return null;
		return zipMap;
	}
	
	private static Map<String, String> getCounties() {
		BufferedReader reader = null;
		String key = "";
		String line = "";
		String splitBy = ",";
		Map<String, String> countyMap = new HashMap<String, String>();
		try {
			reader = new BufferedReader(new FileReader(DIRECTORY + COUNTY_FILE));
			while ((line = reader.readLine())!= null) {
				String[] county = line.split(splitBy);
				key = county[0] + "_" + county[3];
				key = key.substring(0, key.length() - 6);
				countyMap.put(key, county[1] + county[2]);
			} 
		} catch (FileNotFoundException e) {
			logger.error("File not found: " + e);
		} catch (IOException e) {
			logger.error("Failed reading from file: " + e);
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					logger.error("Failed closing BufferedReader: " + e);
				}
			}
		}
		if (countyMap.isEmpty())
			return null;
		return countyMap;
	}
}