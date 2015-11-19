package com.cooksys.insertion;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;

/**
 * RedisInsertion class loads the zip code data needed for the application.
 * @author Dustin Baugh, Zack Rosales
 *
 */
public class RedisInsertion {

	// String constants for directory and filenames of data sources
	private static final String DIRECTORY = "src/main/resources/";
	private static final String ZIP_CODES = "national_county.txt";
	private static final String ZIP_CODE_INFO = "COUNTY_ZIP_062015.csv";
	
	// SLF4J logger
	private static final Logger logger = LoggerFactory.getLogger(RedisInsertion.class);
	
	/**
	 * loadRedisData method compiles the data from data sources and adds the data to redis
	 */
	public static void loadRedisData(){
		logger.info("Starting zip code insertion to redis");
		
		// reader to read from file
		BufferedReader reader = null;
		
		// stores current line from reader
		String line = "";
		
		// delimiter to split line with
		String splitBy = ",";
		
		// boolean to determine if it is reading the first line (which contains column names, instead
		// of actual data needed by the application).
		Boolean firstLine = true;
		
		// jedis client
		Jedis jedis = new Jedis("localhost", 6379);
		
		// flushes all jedis databases so it loads into an empty database each time application starts
		jedis.flushAll();
		
		// map of county codes and names
		CountyInfo countyInfo = getCounties();
		
		try {
			
			// instantiating reader
			reader = new BufferedReader(new FileReader(DIRECTORY + ZIP_CODE_INFO));
			
			// loop through lines until end of file
			while ((line = reader.readLine()) != null) {
				if (firstLine) {
					
					// skips first line and changes boolean value
					firstLine = false;
					
				} else {
					
					// splits line into String array
					String[] county = line.split(splitBy);
					
					// gets county name from countyMap
					String str = countyInfo.getCountyName();
					
					// adds county to redis.
					jedis.sadd("county:" + str, county[1]);
				}
			} 
		} catch (FileNotFoundException e){
			logger.error("File not found: " + e);
		} catch (IOException e) {
			logger.error("BufferedReader failed to read file: " + e);
		} finally {
			try {
				
				// close the BufferedReader
				reader.close();
			} catch (IOException e) {
				logger.error("Failed to close BufferedReader: " + e);
			}
		}
		logger.info("Zip code insertion to redis completed");
	}
	
	/**
	 * getCounties method compiles a Map of county numbers and names from data source
	 * @return Map of county numbers and names, respectively.
	 */
	private static CountyInfo getCounties(){
		
		// reader to read file
		BufferedReader reader = null;
		
		// stores current line of reader
		String line = "";
		
		// delimiter to split line by
		String splitBy = ",";
		
		// CountyInfo object to hold county data
		CountyInfo countyInfo = new CountyInfo();
		
		try {
			
			// instantiating reader
			reader = new BufferedReader(new FileReader(DIRECTORY + ZIP_CODES));
			
			// loop through each line until end of file
			while ((line = reader.readLine()) != null) {
				
				// splits line into String array 
				String[] county = line.split(splitBy);
				
				// stores county info in CountyInfo class;
				countyInfo.setState(county[0]);
				countyInfo.setCountyNumber(county[1] + county[2]);
				countyInfo.setCountyName(county[3]);
			}
		} catch (FileNotFoundException e) {
			logger.error("File Not Found: " + e);
		} catch (IOException e) {
			logger.error("BufferedReader failed to read file: " + e); 
		} finally {
			try {
				
				// closing reader
				reader.close();
			} catch (IOException e) {
				logger.error("Failed closing BufferedReader: " + e);
			}
		}
		
		// if map is empty, return null instead of empty map
		if (countyInfo.getCountyName() == null || countyInfo.getCountyName().isEmpty())
			return null;
		
		// returns county map
		return countyInfo;
	}
}