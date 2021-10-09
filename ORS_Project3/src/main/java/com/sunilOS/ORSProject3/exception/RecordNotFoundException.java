package com.sunilOS.ORSProject3.exception;

/**
 *  RecordNotFoundException thrown when a record not found occurred
 * @author Anshul
 *
 */
public class RecordNotFoundException extends Exception{

	
	private static final long serialVersionUID = 1L;

	/**
	 * @param msg
	 *      : Error message
	 */
	public RecordNotFoundException(String msg){
		
		super(msg);
	}
}
