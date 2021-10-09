package com.sunilOS.ORSProject3.model;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.sunilOS.ORSProject3.dto.FacultyDTO;
import com.sunilOS.ORSProject3.dto.MarksheetDTO;
import com.sunilOS.ORSProject3.dto.RoleDTO;
import com.sunilOS.ORSProject3.dto.StudentDTO;
import com.sunilOS.ORSProject3.exception.ApplicationException;
import com.sunilOS.ORSProject3.exception.DuplicateRecordException;
import com.sunilOS.ORSProject3.util.HibDataSource;

public class MarksheetModelHibImpl implements MarksheetModelInt
{

	public long add(MarksheetDTO dto) throws ApplicationException, DuplicateRecordException 
	{
		Session session = HibDataSource.getSession();
		Transaction tx = null;

		StudentModelInt studentModel = ModelFactory.getInstance().getStudentModel();
		StudentDTO studentDTO = studentModel.findByPk(dto.getStudentId());
		dto.setName(studentDTO.getFirstName() + " " + studentDTO.getLastName());

		MarksheetDTO existdto = findByRollNo(dto.getRollNo());
		if (existdto != null) 
		{
			throw new DuplicateRecordException("Record already exists");
		}
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
			throw new ApplicationException("Exception: Exception in MarksheetModel add method");
		} 
		finally 
		{
			session.close();
		}
		return dto.getId();
	}

	public void update(MarksheetDTO dto) throws ApplicationException, DuplicateRecordException 
	{
		Session session = HibDataSource.getSession();
		Transaction tx = null;

		StudentModelInt studentModel = ModelFactory.getInstance().getStudentModel();
		StudentDTO studentDTO = studentModel.findByPk(dto.getStudentId());
		dto.setName(studentDTO.getFirstName() + " " + studentDTO.getLastName());
		
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
			throw new ApplicationException("Exception: Exception in MarksheetModel update method");
		} 
		finally 
		{
			session.close();
		}
	}

	public void delete(MarksheetDTO dto) throws ApplicationException 
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
			throw new ApplicationException("Exception: Exception in MarksheetModel delete method");
		}
		finally {
			session.close();
		}
	}

	public MarksheetDTO findByRollNo(String rollNo) throws ApplicationException 
	{
		Session session = HibDataSource.getSession();
		Transaction tx = null;
		MarksheetDTO dto = null;

		try 
		{
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(MarksheetDTO.class);
			criteria.add(Restrictions.eq("rollNo", rollNo));
			List list = criteria.list();
			
			if (list.size() > 0) 
			{
				dto = (MarksheetDTO) list.get(0);
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			throw new ApplicationException("Exception: Exception in MarksheetModel findByRollNo method");
		} 
		finally 
		{
			session.close();
		}	
		return dto;
	}

	public MarksheetDTO findByPk(long pk) throws ApplicationException 
	{
		Session session = HibDataSource.getSession();
		MarksheetDTO dto = null;
		
		try 
		{
			dto=(MarksheetDTO) session.get(MarksheetDTO.class, pk);
		}
		catch (HibernateException e)
		{
			e.printStackTrace();
			throw new ApplicationException("Exception: Exception in MarksheetModel findByPK method");
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
		
		try {
			Criteria criteria = session.createCriteria(MarksheetDTO.class);
			if (pageSize > 0) 
			{
				pageNo = (pageNo - 1) * pageSize;
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}
			list = criteria.list();
		} 
		catch (HibernateException e) 
		{
			e.printStackTrace();
			throw new ApplicationException("Exception: Exception in MarksheetModel list method");
		} 
		finally
		{
			session.close();
		}
		return list;
	}

	public List search(MarksheetDTO dto) throws ApplicationException 
	{
		return search(dto, 0, 0);
	}

	public List search(MarksheetDTO dto, int pageNo, int pageSize) throws ApplicationException 
	{
		Session session = HibDataSource.getSession();
		List list = null;
		
		try 
		{
			Criteria criteria = session.createCriteria(MarksheetDTO.class);
			if(dto != null)
			{
				if (dto.getId() > 0) {
					criteria.add(Restrictions.eq("id", dto.getId()));
				}
				if (dto.getRollNo() != null && dto.getRollNo().length() > 0) {
					criteria.add(Restrictions.like("rollNo", dto.getRollNo() + "%"));
				}
				if (dto.getName() != null && dto.getName().length() > 0) {
					criteria.add(Restrictions.like("name", dto.getName() + "%"));
				}
				if (dto.getPhysics() != null && dto.getPhysics() > 0) {
					criteria.add(Restrictions.eq("physics", dto.getPhysics()));
				}
				if (dto.getChemistry() != null && dto.getChemistry() > 0) {
					criteria.add(Restrictions.eq("chemistry", dto.getChemistry()));
				}
				if (dto.getMaths() != null && dto.getMaths() > 0) {
					criteria.add(Restrictions.eq("maths", dto.getMaths()));
				}
				if (pageSize > 0) 
				{
					pageNo = ((pageNo - 1) * pageSize);
					criteria.setFirstResult(pageNo);
					criteria.setMaxResults(pageSize);
				}
			list = criteria.list();
			} 
		}
		catch (HibernateException e) 
		{
			e.printStackTrace();
			throw new ApplicationException("Exception: Exception in MarksheetModel search method");
		} 
		finally
		{
			session.close();
		}
		return list;
	}

	public List getMeritList(int pageNo, int pageSize) throws ApplicationException 
	{
		Session session = HibDataSource.getSession();
		List list = null;
		
		try
		{
			StringBuffer hql = new StringBuffer("FROM MarksheetDTO ORDER BY (physics+chemistry+maths) DESC");
			
			Query query = session.createQuery(hql.toString()).setMaxResults(pageSize);            
			list = query.list();
		}
		catch (HibernateException e) 
		{
			e.printStackTrace();
			throw new ApplicationException("Exception: Exception in MarksheetModel getMeritList method");
		} 
		finally
		{
			session.close();
		}
		return list;
	}
	public static void main(String[] args)throws ApplicationException, Exception
	{
		MarksheetDTO dto  = new MarksheetDTO();
		
		MarksheetModelHibImpl model = new MarksheetModelHibImpl();
		
		List list = model.getMeritList(0, 10);
		System.out.println(list.size());
		
		//dto.setId((long) (6));
//	dto.setRollNo("0911cs091019");
//	dto.setStudentId(12233);
//	dto.setName("abc");
//	dto.setPhysics(32);
//	dto.setChemistry(32);
//	dto.setMaths(32);
//	model.add(dto);
		
		
	}
}
