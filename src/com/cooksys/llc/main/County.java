package com.cooksys.llc.main;

public class County {

	private String countyName;
	private String countyCode;
	private String zipCode;
	
	public County (String countyName, String countyCode, String zipCode) {
		this.countyName = countyName;
		this.countyCode = countyCode;
		this.zipCode = zipCode;
	}
	
	public String getCountyName() {
		return countyName;
	}
	public void setCountyName(String countyName) {
		this.countyName = countyName;
	}
	public String getCountyCode() {
		return countyCode;
	}
	public void setCountyCode(String countyCode) {
		this.countyCode = countyCode;
	}
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	
}
