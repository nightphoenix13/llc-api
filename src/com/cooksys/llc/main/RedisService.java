package com.cooksys.llc.main;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
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
	
	public List<Integer> getZipsByStateCounty(String state, String county) {
		Jedis jedis = new Jedis("localhost", 6379);
		List<String> output = jedis.hmget(state, county);
		jedis.close();
		List<Integer> zips = new ArrayList<Integer>();
		for (int x = 0; x < output.get(0).length() - 1; x = x + 7) {
			zips.add(Integer.parseInt(output.get(0).substring(x, x + 5)));
		}
		return zips;
	}
	
	public Map<String, List<Integer>> getZipsByState(String state) {
		Jedis jedis = new Jedis("localhost", 6379);
		Map<String, String> output = jedis.hgetAll(state);
		jedis.close();
		Map<String, List<Integer>> zipMap = new HashMap<String, List<Integer>>();
		for (String key : output.keySet()) {
			List<Integer> zips = new ArrayList<Integer>();
			for (int x = 0; x < output.get(key).length() - 1; x = x + 7) {
				zips.add(Integer.parseInt(output.get(key).substring(x, x + 5)));
			}
			zipMap.put(key, zips);
		}
		return zipMap;
	}

	public void getMapFromFile() {
		Jedis jedis = new Jedis("localhost", 6379);
		jedis.flushAll();
		Map<String, String> countyZipMap = new HashMap<String, String>();
		Integer count = new Integer(0);
		BufferedReader reader = null;
		String line = "";
		try {
			reader = new BufferedReader(new FileReader(FILE));
			while ((line = reader.readLine()) != null) {
				String state = line.substring(0, 2);
				String county = getCounty(line);
				String zips = line.substring(index + 2);
				logger.info("State: " + state + " County: " + county + " Zips: " + zips);
				countyZipMap.put(county, zips);
				jedis.hmset(state, countyZipMap);
				countyZipMap.clear();
				}
			count++;
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
		jedis.close();
		logger.info("State-County map completed. " + count + " records added.");
	}

	private String getCounty(String line) {
		Boolean foundSpace = false;
		String county = "";
		for (int x = 3; x < line.length() - 1 && !foundSpace; x++) {
			Character ch = line.charAt(x);
			if (Character.isWhitespace(ch)) {
				index = x;
				foundSpace = true;
			} else {
				county = county + ch;
			}
		}
		return county;
	}
}