package com.cooksys.insertion;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;

/**
 * 
 * @author Dustin Baugh, Zack Rosales
 *
 */
public class RedisInsertion {

	private static final String DIRECTORY = "src/main/resources/";
	private static final String ZIP_CODES = "national_county.txt";
	private static final String ZIP_CODE_INFO = "COUNTY_ZIP_062015.csv";
	private static final Logger logger = LoggerFactory.getLogger(RedisInsertion.class);
	
	public static void loadRedisData(){
		logger.info("Starting zip code insertion to redis");
		insertZipCodesToRedis();
		logger.info("Zip code insertion to redis completed");
	}
	
	private static void insertZipCodesToRedis(){
		BufferedReader reader = null;
		String line = "";
		String splitBy = ",";
		Boolean firstLine = true;
		Jedis jedis = new Jedis("localhost", 6379);
		jedis.flushAll();
		Map<String, String> countyMap = getCounties();
		try {
			reader = new BufferedReader(new FileReader(DIRECTORY + ZIP_CODE_INFO));
			while ((line = reader.readLine()) != null) {
				if (firstLine) {
					firstLine = false;
				} else {
					String[] county = line.split(splitBy);
					String str = countyMap.get(county[0]).replace(" County", "");
					jedis.sadd("county:" + str, county[1]);
				}
			} 
		} catch (FileNotFoundException e){
			logger.error("File not found: " + e);
		} catch (IOException e) {
			logger.error("BufferedReader failed to read file: " + e);
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				logger.error("Failed to close BufferedReader: " + e);
			}
		}
	}
	
	private static Map<String, String> getCounties(){
		BufferedReader reader = null;
		String line = "";
		String splitBy = ",";
		Map<String, String> countyMap = new HashMap();
		try {
			reader = new BufferedReader(new FileReader(DIRECTORY + ZIP_CODES));
			while ((line = reader.readLine()) != null) {
				String[] county = line.split(splitBy);
				countyMap.put(county[1] + county[2], county[3]);
			}
		} catch (FileNotFoundException e) {
			logger.error("File Not Found: " + e);
		} catch (IOException e) {
			logger.error("BufferedReader failed to read file: " + e); 
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				logger.error("Failed closing BufferedReader: " + e);
			}
		}
		if (countyMap.isEmpty())
			return null;
		return countyMap;
	}
}
