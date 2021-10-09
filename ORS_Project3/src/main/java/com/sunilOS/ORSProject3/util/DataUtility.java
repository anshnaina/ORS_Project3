package com.sunilOS.ORSProject3.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * data Utility class to format data
 * @author Anshul
 *
 */
public class DataUtility {

	/**
	 * Application time data format
	 */
	public static final String APP_DATE_FORMAT = "yyyy-MM-dd";
	
	public static final String APP_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

	/**
	 * Applicaton time data format
	 */
	public static final SimpleDateFormat formatter = new SimpleDateFormat(APP_DATE_FORMAT);
	
	private static final SimpleDateFormat timeFormatter = new SimpleDateFormat(APP_TIME_FORMAT);
	
	
	/**
	 * getString(String s) Trims and trailing and leading spaces of a String
	 * 
	 * @param val
	 * @return val
	 */
	public static String getString(String val){
		if(DataValidator.isNotNull(val)){
			return val.trim();
		}else{
			return val;
		}
	}
	
	/**
	 * Converts and Object to String
	 * 
	 * @param val
	 *            :value
	 * @return String
	 */
	public static String getStringData(Object val){
		if (val!=null){
			return val.toString();
		}else{
			return "";
		}
	}
	
	/**
	 * 
	 * Converts String InTo Integer
	 * 
	 * @param val
	 *            :value
	 * @return int
	 */
	public static int getInt(String val){
		if (DataValidator.isLong(val)){
			return Integer.parseInt(val);
		}else{
			return 0;
		}
	}
	
	/**
	 * 
	 * Converts String InTo Long
	 * 
	 * @param val
	 *            :value
	 * @return Long
	 */
	 public static long getLong(String val) {
    if (DataValidator.isLong(val)) {
	            return Long.parseLong(val);
	        } else {
	            return (long) 0;
	        }
	    }
	 
	 /**
		 * Convert String in to Date
		 * 
		 * @param val
		 *            :value
		 * @return Date
		 */
	  public static Date getStringToDate(String val) {
	        Date date = null;
	        try {
	            date = formatter.parse(val);
	        } catch (Exception e) {

	        }
	        return date;
	    }
	  
	
	  
	  /**
		 * convert string to date
		 * @param date
		 * @return
		 */
	  public static String getDateToString(Date date) {
	        try {
	            return formatter.format(date);
	        } catch (Exception e) {
	        }
	        return "";
	    }
	  
	  /**
		 * convert date and time
		 * @param date * 	
		 *  * @param day
		 * @return
		 */
	  public static Date getDate(Date date, int day){
		  return null;
	  }
	  
	  /**
		 * convert timestamp to string
		 * @param val
		 * @return timestamp
		 */
	  public static Timestamp getTimestamp(String val) {

	        Timestamp ts = null;
	        try {
	            ts = new Timestamp((timeFormatter.parse(val)).getTime());
	        } catch (Exception e) {
	            return null;
	        }
	        return ts;
	    }
	  
	  /** 
		 * convert timestamp in to long
		 * @param l
		 * @return timestamp
		 */
	  public static Timestamp getTimestamp(long l) {

	        Timestamp ts = null;
	        try {
	            ts = new Timestamp(l);
	        } catch (Exception e) {
	            return null;
	        }
	        return ts;
	    }
	  
	  /**
		 * convert timestamp in to string
		 * @return Timestamp
		 */
	  public static Timestamp getCurrentTimestamp() {
	        Timestamp ts = null;
	        try {
	            ts = new Timestamp(new Date().getTime());
	        } catch (Exception e) {
	        }
	        return ts;

	    }
	  
	  /**
		 * convert timestamp timestamp to long
		 * @param Timestamp
		 * @return long
		 */
	  public static long getTimestamp(Timestamp t) {
	        try {
	            return t.getTime();
	        } catch (Exception e) {
	            return 0;
	        }
	    }

	    public static void main(String[] args) {
//	        System.out.println("DU(main)"+getInt("124"));
	    }
	    
	    
}
