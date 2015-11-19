package com.cooksys.test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.ScanOptions.ScanOptionsBuilder;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;

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
		template.opsForHash().putAll(key, properties);
		// final String key = String.format("user:%s", user.getId());
		// final Map<String, Object> properties = new HashMap<String, Object>();
		//
		// properties.put("id", user.getId());
		// properties.put("name", user.getName());
		// properties.put("email", user.getEmail());
		//
		// // template.multi();
		// template.opsForHash().putAll(key, properties);
	}

	public User getUser(final String id) {
		final String key = String.format("user:%s", id);

		final String name = (String) template.opsForHash().get(key, "name");
		final String email = (String) template.opsForHash().get(key, "email");

		return new User(id, name, email);
	}
	
	public void getZipcodeList() {
	}
	
	public void massInsertion() {
		hashRedisInsertion();
	}
	
	public void hashRedisInsertion() {
		String file = "/home/dustin/build/data/COUNTY_ZIP_062015.csv";
		BufferedReader br = null;
		String line = "";
		String splitBy = ",";
		Map<String, String[]> countyMap = getCounties();
		
		try {
			br = new BufferedReader(new FileReader(file));
			int count = 1;

			while ((line = br.readLine()) != null) {
				String[] county = line.split(splitBy);
//				Map<String, String> countyHashMap = new HashMap<String, String>();
				String key = String.format("countiesInfo:%s", countyMap.get(0)[0] + "_" + countyMap.get(county[0])[4].replace(" County", ""));
				Map<String, Object> properties = new HashMap<String, Object>();

				properties.put("id", count);
				properties.put("countyCode", county[0]);
				properties.put("countyName", countyMap.get(county[0])[4].replace(" County", ""));
				properties.put("state", countyMap.get(county[0])[0]);
				properties.put("zipcode", county[1]);
				template.opsForHash().putAll(key, properties);
				
				System.out.println("current record id: " + count);
				// increment counter
				count++;
			}
		} catch (Exception e) {
			System.out.println("error inserting hash mapped counties");
		}
	}
	
	public static void differentRedisInsertion() {
		// file location
		String file = "/home/dustin/build/data/COUNTY_ZIP_062015.csv";

		// reader
		BufferedReader br = null;
		// reader's current line
		String line = "";
		// parser
		String splitBy = ",";

		// jedis client
		Jedis jedis = new Jedis("localhost", 6379);

		// map of county codes and names
		Map<String, String[]> countyMap = getCounties();

		try {

			br = new BufferedReader(new FileReader(file));

			// counter for id

			while ((line = br.readLine()) != null) {

				// split county and zip codes from each other
				String[] county = line.split(splitBy);

				// map of county codes, names and zip codes
				Map<String, String> countyWithZip = new HashMap<String, String>();

				// insert into redis
				String str = countyMap.get(county[0])[4].replace(" County", "");
				System.out.println(str);
				jedis.sadd("county:" + str, county[1]);

			}
			System.out.println("Finish!");
		} catch (FileNotFoundException e) {
			System.out.println("FILE NOT FOUND");
		} catch (IOException e) {
			System.out.println("IOEXCEPTION");
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					System.out.println("2nd ioexception");
				}
			}
		}
	}

	public void uploadRecords() {
		// file location
		String file = "/home/dustin/build/data/COUNTY_ZIP_062015.csv";

		// reader
		BufferedReader br = null;
		// reader's current line
		String line = "";
		// parser
		String splitBy = ",";

		// jedis client
		Jedis jedis = new Jedis("localhost", 6379);

		// map of county codes and names
		Map<String, String[]> countyMap = getCounties();

		try {

			br = new BufferedReader(new FileReader(file));

			// counter for id
			int count = 1;

			while ((line = br.readLine()) != null) {

				// split county and zip codes from each other
				String[] county = line.split(splitBy);

				// map of county codes, names and zip codes
				Map<String, String> countyWithZip = new HashMap<String, String>();

				// 3 fields being added to map
				countyWithZip.put("countyCode", county[0]);
				countyWithZip.put("countyName", countyMap.get(county[0])[4]);
				countyWithZip.put("zipCode", county[1]);

				// insert into redis
				template.opsForHash().putAll("countyInfo:" + count, countyWithZip);

				System.out.println("current record id: " + count);
				// increment counter
				count++;
			}
			System.out.println("Finish!");
		} catch (FileNotFoundException e) {
			System.out.println("FILE NOT FOUND");
		} catch (IOException e) {
			System.out.println("IOEXCEPTION");
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					System.out.println("2nd ioexception");
				}
			}
		}
	}

	public static Map<String, String[]> getCounties() {
		// file location
		String file = "/home/dustin/build/data/national_county.txt";

		// reader
		BufferedReader br = null;
		// reader's current line
		String line = "";
		// parser
		String splitBy = ",";

		// map of county codes and names
		Map<String, String[]> countyMap = new HashMap<String, String[]>();

		try {

			br = new BufferedReader(new FileReader(file));

			while ((line = br.readLine()) != null) {
				// parse line
				String[] county = line.split(splitBy);
				// add county name and code to hash map
				countyMap.put(county[1] + county[2], county);
			}
		} catch (FileNotFoundException e) {
			System.out.println("file not found - counties");
		} catch (IOException e) {
			System.out.println("ioexception - counties");
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					System.out.println("failed closing - counties");
				}
			}
		}
		if (countyMap.isEmpty())
			return null;
		return countyMap;
	}

}
