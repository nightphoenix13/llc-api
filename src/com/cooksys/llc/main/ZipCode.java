package com.cooksys.llc.main;

public class ZipCode {

	private String county;
	private String zipcode;
	
	public ZipCode(String county, String zipcode) {
		this.county = county;
		this.zipcode = zipcode;
	}
	
	public String getCounty() {
		return county;
	}
	public void setCounty(String county) {
		this.county = county;
	}
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
}
