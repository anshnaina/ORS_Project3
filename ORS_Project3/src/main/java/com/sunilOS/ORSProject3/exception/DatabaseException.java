package com.sunilOS.ORSProject3.exception;

/**
 * DatabaseException is propogated by DAO classes when an unhandled Database
 * exception occurred
 * @author Anshul
 *
 */
public class DatabaseException extends Exception{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param msg
	 * error message
	 */
	public DatabaseException(String msg){
		super(msg);
	}

}
