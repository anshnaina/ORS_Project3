package com.sunilOS.ORSProject3.dto;


/**
 * College JavaDTO encapsulates College attributes
 * @author Anshul
 *
 */
public class CollegeDTO extends BaseDTO {
	
	/**
	 * Name of college
	 */
	private String collegeName;
	/**
	 * address of college
	 */
	private String address;
	/**
	 * city of college
	 */
	private String city;
	/**
	 * state of college
	 */
	private String state;
	/**
	 * state of college
	 */
	private String phoneNo;
	

	
	/**
	 * accessor
	 * @return getter and setter methods
	 */
	
	
	public String getCollegeName() {
		return collegeName;
	}

	public void setCollegeName(String collegeName) {
		this.collegeName = collegeName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getKey() {
		
		return id+"";
	}

	public String getValue() {
		
		return collegeName+"";
	}

}
