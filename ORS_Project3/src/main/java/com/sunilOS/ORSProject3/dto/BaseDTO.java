package com.sunilOS.ORSProject3.dto;

import java.io.Serializable;
import java.sql.Timestamp;



/**
 * Parent class of all DTO in application. It contains generic attributes.
 * @author Anshul
 *
 */
public abstract class BaseDTO implements Serializable, Comparable<BaseDTO>, DropdownList {
	
	/**
	 * Non Business primary key
	 */
	protected Long id;
	
	/**
	 * Contains USER ID who created this database record
	 */
	protected String createdBy;
	/**
	 * Contains Created Timestamp of database record
	 */
	protected String modifiedBy;
	/**
     * Contains Created Timestamp of database record
     */
	protected Timestamp createdDatetime;
	/**
     * Contains Modified Timestamp of database record
     */
	protected Timestamp modifiedDatetime;

	
	

	 /**
    * accessor
    */
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Timestamp getCreatedDatetime() {
		return createdDatetime;
	}

	public void setCreatedDatetime(Timestamp createdDatetime) {
		this.createdDatetime = createdDatetime;
	}

	public Timestamp getModifiedDatetime() {
		return modifiedDatetime;
	}

	public void setModifiedDatetime(Timestamp modifiedDatetime) {
		this.modifiedDatetime = modifiedDatetime;
	}

	public int compareTo(BaseDTO next) {
		return getValue().compareTo(next.getValue());
	}
}
