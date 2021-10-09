package com.sunilOS.ORSProject3.dto;


/**
 * student JavaDTO encapsulates student attributes
 * @author Anshul
 *
 */

public class SubjectDTO extends BaseDTO {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/*
	 * Subject courseId
	 */
	private long courseId;
	/*
	 * Subject subjectName
	 */
	private String subjectName;
	/*
	 * Subject courseName
	 */
	private String courseName;
	/*
	 * Subject description
	 */
	private String description;

	

	public long getCourseId() {
		return courseId;
	}

	public void setCourseId(long courseId) {
		this.courseId = courseId;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getKey() {
		return id+"";
	}

	public String getValue() {
		// TODO Auto-generated method stub
		return subjectName+"";
	}

}
