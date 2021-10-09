package com.sunilOS.ORSProject3.dto;

import java.util.Date;



/**
 * student JavaDTO encapsulates student attributes
 * @author Anshul
 *
 */
public class StudentDTO extends BaseDTO {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	/*
	 * Student firstName 
	 */
	private String firstName;
	/*
	 * Student lastName 
	 */
	private String lastName;
	/*
	 * Student email 
	 */
	private String email;
	/*
	 * Student mobileNo 
	 */
	private String mobileNo;
	/*
	 * Student collegeId 
	 */
	private long collegeId;
	/*
	 * Student dob 
	 */
	private Date dob;
	/*
	 * Student collegeName 
	 */
	private String collegeName;
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public long getCollegeId() {
		return collegeId;
	}

	public void setCollegeId(long collegeId) {
		this.collegeId = collegeId;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getCollegeName() {
		return collegeName;
	}

	public void setCollegeName(String collegeName) {
		this.collegeName = collegeName;
	}

	public String getKey() {
		// TODO Auto-generated method stub
		return id+"";
	}

	public String getValue() {
		// TODO Auto-generated method stub
		return firstName+" "+lastName;
	}

}
