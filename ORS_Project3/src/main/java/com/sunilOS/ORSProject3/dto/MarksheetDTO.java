	package com.sunilOS.ORSProject3.dto;

/**
 * marksheet JavaBean encapsulates marksheet attributes
 * @author Anshul
 *
 */
public class MarksheetDTO extends BaseDTO{
	
	
	 /**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
		 * rollNo
		 */
   private String rollNo;
   
   /**
  	 * studentId
  	 */
   private long studentId;
   
   /**
  	 * name
  	 */
   private String name;
   
   /**
  	 * physics
  	 */
   private Integer physics;
   
   /**
  	 * chemistry
  	 */
   private Integer chemistry;
   
   /**
  	 * maths
  	 */
   private Integer maths;
   
   
/*
 * accessor
 */
	public String getRollNo() {
	return rollNo;
}

public void setRollNo(String rollNo) {
	this.rollNo = rollNo;
}



public long getStudentId() {
	return studentId;
}

public void setStudentId(long studentId) {
	this.studentId = studentId;
}

public String getName() {
	return name;
}

public void setName(String name) {
	this.name = name;
}

public Integer getPhysics() {
	return physics;
}

public void setPhysics(Integer physics) {
	this.physics = physics;
}

public Integer getChemistry() {
	return chemistry;
}

public void setChemistry(Integer chemistry) {
	this.chemistry = chemistry;
}

public Integer getMaths() {
	return maths;
}

public void setMaths(Integer maths) {
	this.maths = maths;
}

	public String getKey() {
		return id+"";
	}

	public String getValue() {
		return rollNo+"";
	}

}
