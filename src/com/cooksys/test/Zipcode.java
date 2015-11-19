package com.cooksys.test;

public class Zipcode {

	private Long id;
	private String countyName;
	private String countyCode;
	private String zipcode;
	
	public Zipcode(String countyName, String countyCode, String zipcode) {
		this.countyName = countyName;
		this.countyCode = countyCode;
		this.zipcode = zipcode;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
}
