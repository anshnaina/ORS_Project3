package com.sunilOS.ORSProject3.dto;

import java.util.Date;


/**
 *  faculty JavaBean encapsulates faculty attributes
 * @author Anshul
 *
 */
public class FacultyDTO extends BaseDTO{
	
	
	private static final long serialVersionUID = 1L;


	/**
	 * firstName of faculty
	 */
    private String firstName;
    
    
    /**
	 * lastName of faculty
	 */
    private String lastName;
    
    /**
	 * gender of faculty
	 */
    private String gender;
    
    /**
	 * qualification of faculty
	 */
    private String qualification;
    
    /**
	 * mobileNo of faculty
	 */
    private String mobileNo;
    
    /**
	 * login of faculty
	 */
    private String email;
    
    /**
	 * dob of faculty
	 */
    private Date dob;
    
    /**
	 * collegeId of faculty
	 */
    private long collegeId;
    
    
    /**
	 * courseId of faculty
	 */
    private long courseId;
    
    /**
	 * subjectId of faculty
	 */
    private long subjectId;
    
    /**
	 * collegeName of faculty
	 */
    private String collegeName;
    
    /**
	 * courseName of faculty
	 */
    private String courseName;
    
    /**
	 * subjectName of faculty
	 */
    private String subjectName;
    
    
    /*
     * accessor
     */
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

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getQualification() {
		return qualification;
	}

	public void setQualification(String qualification) {
		this.qualification = qualification;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public long getCollegeId() {
		return collegeId;
	}

	public void setCollegeId(long collegeId) {
		this.collegeId = collegeId;
	}

	public long getCourseId() {
		return courseId;
	}

	public void setCourseId(long courseId) {
		this.courseId = courseId;
	}

	public long getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(long subjectId) {
		this.subjectId = subjectId;
	}

	public String getCollegeName() {
		return collegeName;
	}

	public void setCollegeName(String collegeName) {
		this.collegeName = collegeName;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public String getKey() {
		// TODO Auto-generated method stub
		return id+"";
	}

	public String getValue() {
		// TODO Auto-generated method stub
		return firstName+"";
	}

}
