package com.cooksys.llc.service;

import com.cooksys.llc.main.County;
import com.cooksys.llc.main.ZipCode;

public interface ZipCodeService {

	public ZipCode findZipCodeOnly(String countyName);
	
	public County findByCounty(String countyName);
	
}
