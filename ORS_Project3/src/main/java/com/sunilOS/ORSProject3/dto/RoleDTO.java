package com.sunilOS.ORSProject3.dto;


/**
 * role JavaDTO encapsulates role attributes
 * @author Anshul
 *
 */
public class RoleDTO extends BaseDTO {
	
	
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * predefined role
	 */
	public static final int ADMIN = 1;
	public static final int STAFF = 2;
	public static final int STUDENT = 3;
	public static final int GUEST = 4;
	
	/**
	 * role name
	 */
	private String roleName;
	/**
	 * role description
	 */
	private String description;

	
	
	/*
	 * accessor
	 */
	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
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

		return roleName+"";
	}
}
