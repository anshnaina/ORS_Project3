package com.sunilOS.ORSProject3.dto;

import java.util.Date;



/**
 * Timetable JavaDTO encapsulates student attributes
 * @author Anshul
 *
 */
public class TimeTableDTO extends BaseDTO
{
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	
	
	/*
	 * TimeTable Subject Id
	 */
	private long subjectId;
	/*
	 * TimeTable courseId 
	 */
	private long courseId;
	/*
	 * TimeTable Subject Name
	 */
	private String subjectName;
	/*
	 * TimeTable course Name 
	 */
	private String courseName;
	/*
	 * TimeTable description
	 */
	private String description;
	/*
	 * TimeTable examDate 
	 */
	private Date examDate;
	/*
	 * TimeTable semester 
	 */
	private String semester;
	/*
	 * TimeTable examTime
	 */
	private String examTime;
	
	
	/*
	 * accessor
	 */
	public long getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(long subjectId) {
		this.subjectId = subjectId;
	}

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

	public Date getExamDate() {
		return examDate;
	}

	public void setExamDate(Date examDate) {
		this.examDate = examDate;
	}

	public String getSemester() {
		return semester;
	}

	public void setSemester(String semester) {
		this.semester = semester;
	}

	public String getExamTime() {
		return examTime;
	}

	public void setExamTime(String examTime) {
		this.examTime = examTime;
	}

	

	public String getKey() {
		return id+"";
	}

	public String getValue() {
		return subjectName+"";
	}

}
