package com.cooksys.insertion;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;

@Service("redisDataInsertion")
public class RedisDataInsertion {

	private static final String DIRECTORY = "src/main/resources/";
	private static final String COUNTY_FILE = "national_county.txt";
	private static final String ZIP_FILE = "COUNTY_ZIP_062015.csv";
	@Autowired
	private static RedisTemplate<String, String> template;
	private static final Logger logger = LoggerFactory.getLogger(RedisDataInsertion.class);
	
	public static void redisDataInsertion() {
		Jedis jedis = new Jedis("localhost", 6379);
		jedis.flushAll();
		int count = 0;
		Map<String, String> countyMap = getCounties();
		Map<String, String> zipMap = getZips();
		logger.info("countyMap size: " + countyMap.size() + " zipMap size: " + zipMap.size());
		insertToRedis(countyMap, zipMap);
		for (String countyKey : countyMap.keySet()) {
			logger.info("in for loop for countyMap: " + countyKey);
			String zips = "";
			for (String zipKey : zipMap.keySet()) {
				logger.info(countyMap.get(countyKey) + " " + zipMap.get(zipKey));
				if (countyMap.get(countyKey).equals(zipMap.get(zipKey))) {
					logger.info("made it here");
					if (zips.isEmpty()) {
						zips = zips + zipKey;
					} else {
						zips = zips + ", " + zipKey;
					}
				}
			}
			logger.info("Entering into redis - Key: " + countyKey + " Value: " + zips);
			template.opsForHash().put(countyKey, "zipCodes", zips);
			count++;
		}
		logger.info(count + " counties loaded.");
	}
	
	private static void insertToRedis(Map<String, String> countyMap, Map<String, String> zipMap) {
		// TODO Auto-generated method stub
		
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
				logger.info("Adding to zipMap - Key: " + zip[1] + " Value: " + zip[0]);
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
				logger.info("Adding to countyMap - Key: " + key + " Value: " + county[1] + county[2]);
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
