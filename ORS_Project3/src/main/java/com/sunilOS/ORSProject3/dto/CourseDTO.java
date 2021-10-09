package com.sunilOS.ORSProject3.dto;


/**
 * Course JavaDTO encapsulates College attributes
 * @author Anshul
 *
 */
public class CourseDTO extends BaseDTO {
   
	
	/**
	 * CourseName of college
	 */
	private String courseName;
	
	/**
	 * description of college
	 */
	private String courseDescription;
	
	/**
	 * duration of college
	 */
	private String courseDuration;
	
	
	
	/*
	 * accessor
	 */
	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getCourseDescription() {
		return courseDescription;
	}

	public void setCourseDescription(String courseDescription) {
		this.courseDescription = courseDescription;
	}

	public String getCourseDuration() {
		return courseDuration;
	}

	public void setCourseDuration(String courseDuration) {
		this.courseDuration = courseDuration;
	}

	public String getKey() {
		return id+"";
	}

	public String getValue() {
		return courseName+"";
	}

}
