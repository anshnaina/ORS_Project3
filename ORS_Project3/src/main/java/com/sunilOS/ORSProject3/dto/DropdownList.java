package com.sunilOS.ORSProject3.dto;

/**
 * DropdownList interface is implemented by Beans those are used to create drop
 * down list on HTML pages
 * 
 * @author Anshul
 *
 */
public interface DropdownList {

	/**
	 * return key of list element
	 * 
	 * @return key
	 */
	public String getKey();

	/**
	 * display list of key element
	 * 
	 * @return value
	 */
	public String getValue();
}
