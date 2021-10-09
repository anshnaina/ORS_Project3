package com.sunilOS.ORSProject3.model;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.sunilOS.ORSProject3.dto.CollegeDTO;
import com.sunilOS.ORSProject3.dto.RoleDTO;
import com.sunilOS.ORSProject3.dto.StudentDTO;
import com.sunilOS.ORSProject3.exception.ApplicationException;
import com.sunilOS.ORSProject3.exception.DuplicateRecordException;
import com.sunilOS.ORSProject3.util.HibDataSource;

public class StudentModelHibImpl implements StudentModelInt
{

	public long add(StudentDTO dto) throws ApplicationException, DuplicateRecordException 
	{
		Session session = HibDataSource.getSession();
		Transaction tx = null;
		long pk = 0;
		
		  CollegeModelInt collegeModel = ModelFactory.getInstance().getCollegeModel();
		  CollegeDTO collegeDto = collegeModel.findByPk(dto.getCollegeId());
		  dto.setCollegeName(collegeDto.getCollegeName());

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
			throw new ApplicationException("Exception: Exception in StudentModel add method");
		} finally {
			session.close();
		}
		return dto.getId();
	}

	public void delete(StudentDTO dto) throws ApplicationException 
	{
			Session session = HibDataSource.getSession();
			Transaction tx = null;
				
		try {
				tx = session.beginTransaction();
				session.delete(dto);
				tx.commit();

			} 
		catch (HibernateException e) 
		{
			e.printStackTrace();
			throw new ApplicationException("Exception: Exception in StudentModel delete method");
		} 
		finally
		{
			session.close();
		}
	}

	public void update(StudentDTO dto) throws ApplicationException, DuplicateRecordException 
	{

		Session session = HibDataSource.getSession();
		Transaction tx = null;
				
		CollegeModelInt collegeModel = ModelFactory.getInstance().getCollegeModel();
		CollegeDTO collegeDto = collegeModel.findByPk(dto.getCollegeId());
		dto.setCollegeName(collegeDto.getCollegeName());
			
		try 
			{
				tx = session.beginTransaction();
				session.update(dto);
				tx.commit();
			} 
			catch (HibernateException e)
			{
				if (tx != null) 
				{
					tx.rollback();
				}
				throw new ApplicationException("Exception: Exception in StudentModel update method");
			} 
			finally 
			{
				session.close();
			}
	}

	public StudentDTO findByEmail(String email) throws ApplicationException 
	{
		Session session=HibDataSource.getSession();
		StudentDTO dto=null;
		
		try 
		{
			Criteria criteria=session.createCriteria(StudentDTO.class);
			criteria.add(Restrictions.eq("email", email));
			List list=criteria.list();
			
			if(list.size() > 0)
			{
				dto=(StudentDTO) list.get(0);
			}
		} 
		catch (HibernateException e) 
		{
			e.printStackTrace();
			throw new ApplicationException("Exception: Exception in StudentModel findByEmail method");
        } 
		finally 
		{
            session.close();
        }
		return dto;
	}

	public StudentDTO findByPk(long pk) throws ApplicationException 
	{
		Session session = HibDataSource.getSession();
		StudentDTO dto = null;
		
				try {
					Criteria criteria = session.createCriteria(StudentDTO.class);
					criteria.add(Restrictions.eq("id", pk));
					List list = criteria.list();
					if (list.size() == 1) {
						dto = (StudentDTO) list.get(0);
					}
				} 
				catch (HibernateException e) 
				{
					e.printStackTrace();
					throw new ApplicationException("Exception: Exception in StudentModel findByPK method)");
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
			Criteria criteria = session.createCriteria(StudentDTO.class);

			// if page size is greater than zero then apply pagination
			if (pageSize > 0) 
			{
				pageNo = ((pageNo - 1) * pageSize);
			    criteria.setFirstResult(pageNo);
			    criteria.setMaxResults(pageSize);
			}
			list = criteria.list();
			} 
		catch (HibernateException e) 
		{
			e.printStackTrace();
			throw new ApplicationException("Exception: Exception in StudentModel list method)");
		}
		finally 
		{
            session.close();
        }
		return list;
	}

	public List search(StudentDTO dto) throws ApplicationException 
	{
		return search(dto,0,0);
	}

	public List search(StudentDTO dto, int pageNo, int pageSize) throws ApplicationException 
	{
		Session session = HibDataSource.getSession();
		List list = null;
		        try 
		        {
		        	Criteria criteria = session.createCriteria(StudentDTO.class);
		            if (dto != null) 
		            {
		            	if(dto.getId() > 0)
						{
							criteria.add(Restrictions.eq("id", dto.getId()));
						}
		            if (dto.getFirstName() != null && dto.getFirstName().length() > 0) {
		                criteria.add(Restrictions.like("firstName", dto.getFirstName() + "%"));
		            }
		            if (dto.getEmail() != null && dto.getEmail().length() > 0) {
		                criteria.add(Restrictions.like("email", dto.getEmail() + "%"));
		            }
		            if (dto.getLastName() != null && dto.getLastName().length() > 0) {
		                criteria.add(Restrictions.like("lastName", dto.getLastName() + "%"));
		            }
		            if (dto.getDob() != null && dto.getDob().getDate() > 0) {
		                criteria.add(Restrictions.eq("dob", dto.getDob()));
		            }
		            if (dto.getCollegeId() >0 ) {
		                criteria.add(Restrictions.eq("collegeId", dto.getCollegeId()));
		            }
		            
		            if (dto.getMobileNo() != null && dto.getMobileNo().length() > 0) {
		                criteria.add(Restrictions.like("mobileNo", dto.getMobileNo()  + "%"));
		            }
		           }
		            // if page size is greater than zero the apply pagination
		            if (pageSize > 0) 
		            {
		            	pageNo = ((pageNo - 1) * pageSize);
		                criteria.setFirstResult(pageNo);
		                criteria.setMaxResults(pageSize);
		            }
		            list = criteria.list();
		        }
		        catch (HibernateException e) 
		        {
		        	e.printStackTrace();
					throw new ApplicationException("Exception: Exception in StudentModel search method");
				}
				finally 
				{
		            session.close();
		        }
		       return list;
			}
	public static void main(String[] args)throws ApplicationException
	{
		StudentDTO dto  = new StudentDTO();
		
		StudentModelHibImpl model = new StudentModelHibImpl();
		
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
		System.out.println(list.size());
	}
}
