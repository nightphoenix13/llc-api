package com.cooksys.llc.service;

import java.util.ArrayList;
import java.util.List;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.ScanResult;

public class LLCServiceImpl implements LLCService {

	@SuppressWarnings("rawtypes")
	@Override
	public List getZipCodesByCountyName(String countyName) {
		List list = new ArrayList();
		Jedis jedis = new Jedis("localhost", 6379);
		try {
			ScanResult scan = jedis.sscan("county:" + countyName, 0);
			list = scan.getResult();
		} catch (Exception e) {
			System.out.println("Error getting list by county name.");
		} finally {
			jedis.close();
		}
		if (list.size() > 0)
			return list;
		return null;
	}

}
