package com.sunilOS.ORSProject3.model;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.sunilOS.ORSProject3.dto.RoleDTO;
import com.sunilOS.ORSProject3.dto.UserDTO;
import com.sunilOS.ORSProject3.exception.ApplicationException;
import com.sunilOS.ORSProject3.exception.DuplicateRecordException;
import com.sunilOS.ORSProject3.util.HibDataSource;

public class RoleModelHibImpl implements RoleModelInt {

	
	
	public long add (RoleDTO dto) throws ApplicationException, DuplicateRecordException
	{
		Session session = HibDataSource.getSession();
		Transaction tx = null;
		
		RoleDTO roleDTO;
		try
		{
		if(dto != null)
		{
			roleDTO = findByRoleName(dto.getRoleName());
			if(roleDTO!=null && dto.getRoleName() != roleDTO.getRoleName())
			{
				throw new DuplicateRecordException("Role already exists!");
			}
			else
			{
		
			tx = session.beginTransaction();
			session.save(dto);
			tx.commit();
		}}}
		catch(HibernateException e)
		{
			e.printStackTrace();
			if (tx != null)
			{
				tx.rollback();
			}
			throw new ApplicationException("Unable to add record");
		}
		finally
		{
			session.close();
		}
		return dto.getId();
	}

	public void update(RoleDTO dto) throws ApplicationException, DuplicateRecordException
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
			if(tx!=null)
			{
				tx.rollback();
			}
			throw new ApplicationException("Unable to update record");
		}
		finally
		{
			session.close();
		}
	}

	public void delete(RoleDTO dto) throws ApplicationException 
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
			if(tx!=null)
			{
				tx.rollback();
			}
			throw new ApplicationException("unable to delete record");
		}
		finally
		{
			session.close();
		}
	}

	public RoleDTO findByPK(long pk) throws ApplicationException 
	{
		Session session = HibDataSource.getSession();
		RoleDTO dto = null;
		
		try
		{
			dto = (RoleDTO) session.get(RoleDTO.class, pk);
		}
		catch(HibernateException e)
		{
			e.printStackTrace();
			throw new ApplicationException("Unable to fetch record");
		}
		finally
		{
			session.close();
		}
		return dto;
	}

	public RoleDTO findByRoleName(String roleName) throws ApplicationException 
	{
		Session session = HibDataSource.getSession();
		RoleDTO dto = null;
		
		try
		{
			Criteria criteria = session.createCriteria(RoleDTO.class);
			criteria.add(Restrictions.eq("roleName",  roleName));
			List<RoleDTO> list = criteria.list();
			if (list.size() == 1)
			{
				dto = (RoleDTO) list.get(0);
			}
		}
		catch (HibernateException e)
		{
			e.printStackTrace();
			throw new ApplicationException("Unable to fetch data");
		}
		finally
		{
			session.close();
		}
		return dto;
	}

	public List list() throws ApplicationException
	{
		return list(0,0);
	}

	public List list(int pageNo, int pageSize) throws ApplicationException 
	{
		Session session = HibDataSource.getSession();
		List list = null;
		
		try
		{
			Criteria criteria = session.createCriteria(RoleDTO.class);
			
			if(pageSize > 0)
			{
				pageNo = ((pageNo-1)*pageSize)+1;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}
			list = criteria.list();
		}
			catch(HibernateException e)
			{
				e.printStackTrace();
				throw new ApplicationException("Exception : Exception in  role list");
			}
			finally
			{
				session.close();
			}
		
		return list;
	}

	public List search(RoleDTO dto) throws ApplicationException
	{
		return search(dto, 0, 0);
	}

	public List search(RoleDTO dto, int pageNo, int pageSize) throws ApplicationException 
	{
		Session session = HibDataSource.getSession();
		List list = null;
		
		try
		{
			Criteria criteria = session.createCriteria(RoleDTO.class);
			
			if(dto!=null)
			{
				if(dto.getId()>0)
				{
					criteria.add(Restrictions.eq("id", dto.getId()));
				}
				if(dto.getRoleName()!=null && dto.getRoleName().length()>0)
				{
					criteria.add(Restrictions.eq("roleName", dto.getRoleName()));
				}
				if(dto.getDescription()!=null && dto.getDescription().length()>0)
				{
					criteria.add(Restrictions.eq("description", dto.getDescription()));
				}
			}
			if(pageSize>0)
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
			throw new ApplicationException("Exception : Exception in  role list");
		}
		finally 
		{
			session.close();
		}
		return list;
	}
	
	public static void main(String[] args)throws ApplicationException
	{
		RoleDTO dto  = new RoleDTO();
		
		RoleModelHibImpl model = new RoleModelHibImpl();
		
//		dto = model.findByPK(1);
//	System.out.println(dto.getId()  + " " + dto.getRoleName() + " " + dto.getDescription());

		//dto.setId((long) (6));
//		dto.setRoleName("Peon");
//	dto.setDescription("Peon Role");
//		model.add(dto);
	
//		dto.setId((long) 1);
//		model.delete(dto);
		
		//System.out.println(model.list( 0, 0));
//		
//		List list = model.list();
//		System.out.println(list);
		dto = model.findByRoleName("Teacher");
		System.out.println(dto.getRoleName() + " " + dto.getDescription());

	}

}
