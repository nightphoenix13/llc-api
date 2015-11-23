package com.cooksys.llc.main;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;

@Service("redisService")
public class RedisService {

	private static final String FILE = "src/main/resources/combined_list.txt";
	private static final Logger logger = LoggerFactory.getLogger(RedisService.class);
	private static Integer index = 0; 
	
	public List<String> getZipsByStateCounty(String stateCounty) {
		Jedis jedis = new Jedis("localhost", 6379);
		List<String> output = jedis.hmget(stateCounty, "zipCodes");
		jedis.close();
		return output;
	}
	
	public void redisDataInsertion() {
		Jedis jedis = new Jedis("localhost", 6379);
		jedis.flushAll();
		Map<String, String> countyZipMap = getMapFromFile();
		for (String key : countyZipMap.keySet()) {
			Map<String, String> zipMap = new HashMap<String, String>();
			zipMap.put("zipCodes", countyZipMap.get(key));
			jedis.hmset(key, zipMap);
		}
		jedis.close();
	}

	private Map<String, String> getMapFromFile() {
		Map<String, String> countyZipMap = new HashMap<String, String>();
		Integer count = new Integer(0);
		BufferedReader reader = null;
		String line = "";
		try {
			reader = new BufferedReader(new FileReader(FILE));
			while ((line = reader.readLine()) != null) {
				String key = getStateCountyKey(line);
				String zips = line.substring(index + 2);
				countyZipMap.put(key, zips);
				count++;
			}
		} catch (FileNotFoundException e) {
			logger.error("File Not Found: " + e);
		} catch (IOException e) {
			logger.error("Failed Reading File: " + e);
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				logger.error("Failed Closing File: " + e);
			}
		}
		logger.info("State-County map completed. " + count + " records added.");
		return countyZipMap;
	}

	private String getStateCountyKey(String line) {
		Boolean foundSpace = false;
		String stateCounty = "";
		for (int x = 0; x < line.length() - 1 && !foundSpace; x++) {
			Character ch = line.charAt(x);
			if (!Character.isWhitespace(ch)) {
				stateCounty = stateCounty + ch;
			} else {
				index = x;
				foundSpace = true;
			}
		}
		return stateCounty;
	}
}