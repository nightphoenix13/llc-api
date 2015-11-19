package com.cooksys.insertion;

/**
 * CountyInfo class is used to store the county information taken from data source
 * @author Zack Rosales
 *
 */
public class CountyInfo {

	private String state;
	private String countyNumber;
	private String countyName;
	
	// default constructor
	public CountyInfo() {
		this(null, null, null);
	}
	
	// three-argument constructor
	public CountyInfo(String state, String countyNumber, String countyName) {
		this.state = state;
		this.countyNumber = countyNumber;
		this.countyName = countyName;
	}

	// getters and setters
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountyNumber() {
		return countyNumber;
	}

	public void setCountyNumber(String countyNumber) {
		this.countyNumber = countyNumber;
	}

	public String getCountyName() {
		return countyName;
	}

	public void setCountyName(String countyName) {
		this.countyName = countyName;
	}
}
