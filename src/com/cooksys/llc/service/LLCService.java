package com.cooksys.llc.service;

import java.util.List;

public interface LLCService {

	@SuppressWarnings("rawtypes")
	public List getZipCodesByCountyName(String countyName);
}
