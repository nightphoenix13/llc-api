package com.cooksys.insertion;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;

public class TravisCountyInsertion {
	
	private final static String FILE = "src/main/resources/COUNTY_ZIP_062015.csv";
	private static Logger logger = LoggerFactory.getLogger(TravisCountyInsertion.class);
	
	public static void insertDataToRedis() {
		Jedis jedis = new Jedis("localhost", 6379);
		jedis.flushAll();
		BufferedReader reader = null;
		String line = "";
		String splitBy = ",";
		int count = 0;
		String[] zips = new String[91];
		try {
			reader = new BufferedReader(new FileReader(FILE));
			while ((line = reader.readLine()) != null) {
				String[] county = line.split(splitBy);
				if (county[0].equals("48453")) {
					zips[count] = county[1];
					count++;
				}
			}
			jedis.sadd("TX_Travis", zips[0], zips[1], zips[2], zips[3], zips[4], zips[5], zips[6], zips[7],
					zips[8], zips[9], zips[10], zips[11], zips[12], zips[13], zips[14], zips[15], zips[16], 
					zips[17], zips[18], zips[19], zips[20], zips[21], zips[22], zips[23], zips[24], zips[25], 
					zips[26], zips[27], zips[28], zips[29], zips[30], zips[31], zips[32], zips[33], zips[34], 
					zips[35], zips[36], zips[37], zips[38], zips[39], zips[40], zips[41], zips[42], zips[43], 
					zips[44], zips[45], zips[46], zips[47], zips[48], zips[49], zips[50], zips[51], zips[52], 
					zips[53], zips[54], zips[55], zips[56], zips[57], zips[58], zips[59], zips[60], zips[61], 
					zips[62], zips[63], zips[64], zips[65], zips[66], zips[67], zips[68], zips[69], zips[70], 
					zips[71], zips[72], zips[73], zips[74], zips[75], zips[76], zips[77], zips[78], zips[79], 
					zips[80], zips[81], zips[82], zips[83], zips[84], zips[85], zips[86], zips[87], zips[88], 
					zips[89], zips[90]);
		} catch(FileNotFoundException e) {
			logger.error("File not found: " + e);
		} catch(IOException e) {
			logger.error("Failed reading from file: " + e);
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					logger.error("Failed closing file: " + e);
				}
			}
		}
	}
}
