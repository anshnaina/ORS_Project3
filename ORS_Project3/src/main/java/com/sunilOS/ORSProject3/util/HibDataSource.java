package com.sunilOS.ORSProject3.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;



/**
 * HibDataSource is  a Data Connection Pool
 * @author Anshul
 *
 */

public class HibDataSource {

	private static SessionFactory sessionFactory = null;
	
	
	/**
	 * Gets the sessionFactory from configuration
	 * @return sessionFactory
	 *
	 *
	 */
	public static SessionFactory getSessionFactory()
	{
		if (sessionFactory == null)
		{
			sessionFactory = new Configuration().configure().buildSessionFactory();
		}
		return sessionFactory;
	}
	
	/**
	 * Gets the session from SessionFactory
	 * @return session
	 *
	 */
	
	public static Session getSession()
	{
		Session session = getSessionFactory().openSession();
		return session;
	}
	
	/**
	   * Closes connection
	   * @param session
	   * 
	   */
	public static void closeConnection(Session session)
	{
		if (session == null)
		{
			session.close();
		}
	}
	
}

