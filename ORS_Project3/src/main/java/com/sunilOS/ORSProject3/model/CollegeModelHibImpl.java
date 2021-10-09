package com.sunilOS.ORSProject3.model;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.sunilOS.ORSProject3.dto.CollegeDTO;
import com.sunilOS.ORSProject3.dto.RoleDTO;
import com.sunilOS.ORSProject3.exception.ApplicationException;
import com.sunilOS.ORSProject3.exception.DuplicateRecordException;
import com.sunilOS.ORSProject3.util.HibDataSource;

public class CollegeModelHibImpl implements CollegeModelInt
{
	public long add(CollegeDTO dto) throws ApplicationException, DuplicateRecordException 
	{
		Session session = HibDataSource.getSession();
		Transaction tx = null;
		
		try 
		{
			tx = session.beginTransaction();
			session.save(dto);
			tx.commit();
		}
		catch(HibernateException e)
		{
			e.printStackTrace();
			if (tx != null)
			{
				tx.rollback();
			}
			throw new ApplicationException("Exception: Exception in College add method");
		}
		finally
		{
			session.close();
		}
		return dto.getId();
	}

	public void delete(CollegeDTO dto) throws ApplicationException 
	{
		Session session = HibDataSource.getSession();
		Transaction tx = null;
		
		try
		{
			tx = session.beginTransaction();
			session.delete(dto);
			tx.commit();
		}
		catch(HibernateException e)
		{
			e.printStackTrace();
			if(tx != null)
			{
				tx.rollback();
			}
			throw new ApplicationException("Exception: Exception in College delete");
		}
		finally
		{
			session.close();
		}
	}

	public void update(CollegeDTO dto) throws ApplicationException 
	{
		Session session = HibDataSource.getSession();
		Transaction tx = null;
		
		try
		{
			tx = session.beginTransaction();
			session.update(dto);
			tx.commit();
		}
		catch(HibernateException e)
		{
			e.printStackTrace();
			if(tx != null)
			{
				tx.rollback();
			}
			throw new ApplicationException("Exception: Exception in College update");
		}
		finally
		{
			session.close();
		}
	}

	public CollegeDTO findByPk(long pk) throws ApplicationException 
	{
		Session session = HibDataSource.getSession();
		CollegeDTO dto = null;
		
		try
		{
			dto = session.get(CollegeDTO.class, pk);	
		}
		catch(HibernateException e)
		{
			e.printStackTrace();
			throw new ApplicationException("Exception: Exception in College update");
		}
		finally
		{
			session.close();
		}
		return dto;
	}

	public CollegeDTO findByCollegeName(String collegeName) throws ApplicationException
	{
		Session session =  HibDataSource.getSession();
		CollegeDTO dto = null;
		
		try
		{
			Criteria criteria = session.createCriteria(CollegeDTO.class);
			criteria.add(Restrictions.eq("collegeName", collegeName));
			
			List list = criteria.list();
			if(list.size() > 0)
			{
				dto = (CollegeDTO) list.get(0);
			}
			
		}
		catch(HibernateException e)
		{
			e.printStackTrace();
			throw new ApplicationException("Exception: Exception in CollegeModel findByCollegeName");
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
			Criteria criteria = session.createCriteria(CollegeDTO.class);
			if(pageSize > 0)
			{
				pageNo = (pageNo-1)*pageSize;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}
			list = criteria.list();
		}
		catch(HibernateException e)
		{
			e.printStackTrace();
			throw new ApplicationException("Exception: Exception in CollegeModel list");
		}
		finally
		{
			session.close();
		}
		return list;
	}

	public List search(CollegeDTO dto) throws ApplicationException 
	{
		return search(dto, 0, 0);
	}

	public List search(CollegeDTO dto, int pageNo, int pageSize) throws ApplicationException 
	{
		Session session = HibDataSource.getSession();
		List list = null;
		
		try
		{
			Criteria criteria = session.createCriteria(CollegeDTO.class);
			
			if(dto != null)
			{
				if(dto.getId() > 0)
				{
					criteria.add(Restrictions.eq("id", dto.getId()));
				}
				if(dto.getCollegeName() != null && dto.getCollegeName().length() > 0)
				{
					criteria.add(Restrictions.like("collegeName", dto.getCollegeName() + "%"));
				}
				if (dto.getAddress() != null && dto.getAddress().length() > 0) {
					criteria.add(Restrictions.like("address", dto.getAddress() + "%"));
				}
				if (dto.getCity() != null && dto.getCity().length() > 0) {
					criteria.add(Restrictions.like("city", dto.getCity() + "%"));
				}
				if (dto.getPhoneNo() != null && dto.getPhoneNo().length() > 0) {
					criteria.add(Restrictions.like("phoneNo", dto.getPhoneNo() + "%"));
				}
			}
			if (pageSize > 0)
			{
				pageNo = (pageNo-1)*pageSize;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}
			list = criteria.list();
		}
		catch(HibernateException e)
		{
			e.printStackTrace();
			throw new ApplicationException("Exception: Exception in CollegeModel search");
		}
		finally
		{
			session.close();
		}
		return list;
	}
	public static void main(String[] args)throws ApplicationException
	{
		CollegeDTO dto  = new CollegeDTO();
		
		CollegeModelHibImpl model = new CollegeModelHibImpl();
		
//		dto = model.findByPK(1);
//	System.out.println(dto.getId()  + " " + dto.getRoleName() + " " + dto.getDescription());

		//dto.setId((long) (6));
//		dto.setRoleName("Peon");
//	dto.setDescription("Peon Role");
//		model.add(dto);
	
//		dto.setId((long) 1);
//		model.delete(dto);
		
		//System.out.println(model.list( 0, 0));
		
		List list = model.list();
	}
}
