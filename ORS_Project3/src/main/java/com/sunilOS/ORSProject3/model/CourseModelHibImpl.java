package com.sunilOS.ORSProject3.model;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.sunilOS.ORSProject3.dto.CourseDTO;
import com.sunilOS.ORSProject3.exception.ApplicationException;
import com.sunilOS.ORSProject3.util.HibDataSource;

public class CourseModelHibImpl implements CourseModelInt
{

	public long add(CourseDTO dto) throws ApplicationException 
	{
		Session session = HibDataSource.getSession();
		Transaction tx = null;
		
		try 
		{
			tx = session.beginTransaction();
			session.save(dto);
			tx.commit();
		}
		catch (HibernateException e)
		{
			e.printStackTrace();
			if (tx != null) 
			{
				tx.rollback();
			}
			throw new ApplicationException("Exception: Exception in CourseModel add");
		}
		finally
		{
			session.close();
		}
		return dto.getId();
	}

	public void update(CourseDTO dto) throws ApplicationException 
	{
		Session session = HibDataSource.getSession();
		Transaction tx = null;
		
		try
		{
			tx = session.beginTransaction();
			session.update(dto);
			tx.commit();
		}
		catch (HibernateException e)
		{
			e.printStackTrace();
			if (tx != null) 
			{
				tx.rollback();
			}
			throw new ApplicationException("Exception: Exception in CourseModel update");
		}
		finally
		{
			session.close();
		}
	}

	public void delete(CourseDTO dto) throws ApplicationException 
	{
		Session session = HibDataSource.getSession();
		Transaction tx = null;
		
		try
		{
			tx = session.beginTransaction();
			session.delete(dto);
			tx.commit();
		}
		catch (HibernateException e)
		{
			e.printStackTrace();
			throw new ApplicationException("Exception: Exception in CourseModel delete");
		}
		finally
		{
			session.close();
		}
	}

	public CourseDTO findByCourseName(String courseName) throws ApplicationException 
	{
		Session session = HibDataSource.getSession();
		CourseDTO dto = null;
		
		try
		{
			Criteria criteria = session.createCriteria(CourseDTO.class);
			criteria.add(Restrictions.eq("courseName", courseName));
			
			List list = criteria.list();
			if (list.size() > 0)
			{
				dto = (CourseDTO) list.get(0);
			}
		}
		catch (HibernateException e)
		{
			e.printStackTrace();
			throw new ApplicationException("Exception: Exception in CourseModel findByCourseName");
		}
		finally
		{
			session.close();
		}
		return dto;
	}

	public CourseDTO findByPk(long pk) throws ApplicationException 
	{
		Session session = HibDataSource.getSession();
		CourseDTO dto = null;
		
		try
		{
			dto = session.get(CourseDTO.class, pk);
		}
		catch (HibernateException e)
		{
			e.printStackTrace();
			throw new ApplicationException("Exception: Exception in CourseModel delete");
		}
		finally
		{
			session.close();
		}
		return dto;
	}

	public List list() throws ApplicationException 
	{
		return list(0, 0);
	}

	public List list(int pageNo, int pageSize) throws ApplicationException 
	{
		Session session = HibDataSource.getSession();
		List list = null;
		
		try
		{
			Criteria criteria = session.createCriteria(CourseDTO.class);
			if (pageSize > 0)
			{
				pageNo = (pageNo-1)*pageSize;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}
			list = criteria.list();
		}
		catch (HibernateException e)
		{
			e.printStackTrace();
			throw new ApplicationException("Exception: Exception in CourseModel list");
		}
		finally
		{
			session.close();
		}
		return list;
	}

	public List search(CourseDTO dto) throws ApplicationException 
	{
		return search(dto, 0, 0);
	}

	public List search(CourseDTO dto, int pageNo, int pageSize) throws ApplicationException 
	{
		Session session = HibDataSource.getSession();
		List list = null;
		
		try
		{
			Criteria criteria = session.createCriteria(CourseDTO.class);
			
			if(dto != null)
			{
				if(dto.getId() > 0)
				{
					criteria.add(Restrictions.eq("id", dto.getId()));
				}
				if (dto.getCourseName() != null && dto.getCourseName().length() > 0) {
					criteria.add(Restrictions.like("courseName", dto.getCourseName() + "%"));
				}
				if (dto.getCourseDuration() != null && dto.getCourseDuration().length() > 0) {
					criteria.add(Restrictions.like("courseDuration", dto.getCourseDuration() + "%"));
				}
			}
			if (pageSize > 0) {
				pageNo = (pageNo - 1) * pageSize;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}
			list = criteria.list();
		}
		catch (HibernateException e)
		{
			e.printStackTrace();
			throw new ApplicationException("Exception: Exception in CourseModel search");
		}
		finally
		{
			session.close();
		}
		return list;
	}
	
}
