package com.sunilOS.ORSProject3.model;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.sunilOS.ORSProject3.dto.CollegeDTO;
import com.sunilOS.ORSProject3.dto.CourseDTO;
import com.sunilOS.ORSProject3.dto.FacultyDTO;
import com.sunilOS.ORSProject3.dto.SubjectDTO;
import com.sunilOS.ORSProject3.exception.ApplicationException;
import com.sunilOS.ORSProject3.util.HibDataSource;

public class FacultyModelHibImpl implements FacultyModelInt
{

	public long add(FacultyDTO dto) throws ApplicationException
	{
		Session session=HibDataSource.getSession();
		Transaction tx=null;
		
		CourseModelInt courseModel = ModelFactory.getInstance().getCourseModel();
		CourseDTO courseDTO = courseModel.findByPk(dto.getCourseId());
		dto.setCourseName(courseDTO.getCourseName());
		
		CollegeModelInt collegeModel = ModelFactory.getInstance().getCollegeModel();
		CollegeDTO collegeDTO = collegeModel.findByPk(dto.getCollegeId());
		dto.setCollegeName(collegeDTO.getCollegeName());
		
		SubjectModelInt subjectModel = ModelFactory.getInstance().getSubjectModel();
		SubjectDTO Sbean = subjectModel.findByPk(dto.getSubjectId());
		dto.setSubjectName(Sbean.getSubjectName());
		
		try 
		{
			tx=session.beginTransaction();
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
			throw new ApplicationException("Exception: Exception in FacultyModel add method");
		}
		finally 
		{
			session.close();
		}
			return dto.getId();
	}

	public void update(FacultyDTO dto) throws ApplicationException 
	{
		Session session=HibDataSource.getSession();
		Transaction tx=null;
		
		CourseModelInt courseModel = ModelFactory.getInstance().getCourseModel();
		CourseDTO courseDTO = courseModel.findByPk(dto.getCourseId());
		dto.setCourseName(courseDTO.getCourseName());
		
		CollegeModelInt collegeModel = ModelFactory.getInstance().getCollegeModel();
		CollegeDTO collegeDTO = collegeModel.findByPk(dto.getCollegeId());
		dto.setCollegeName(collegeDTO.getCollegeName());
		
		SubjectModelInt subjectModel = ModelFactory.getInstance().getSubjectModel();
		SubjectDTO Sbean = subjectModel.findByPk(dto.getSubjectId());
		dto.setSubjectName(Sbean.getSubjectName());
		
		try 
		{
		tx=session.beginTransaction();
		session.update(dto);
		tx.commit();
		}
		catch(HibernateException e) 
		{
			if(tx!=null) 
			{
				tx.rollback();
			}
			throw new ApplicationException("Exception: Exception in FacultyModel update method");
		}
		finally 
		{
			session.close();
		}
	}

	public void delete(FacultyDTO dto) throws ApplicationException 
	{
		Session session=HibDataSource.getSession();
		Transaction tx=null;
		
		try 
		{
		tx=session.beginTransaction();
		session.delete(dto);
		tx.commit();
		}
		catch(HibernateException e)
		{
			if(tx!=null) 
			{
				tx.rollback();
			}
			throw new ApplicationException("Exception: Exception in FacultyModel delete method");
		}
		finally 
		{
			session.close();
		}
	}

	public FacultyDTO findByPk(long pk) throws ApplicationException 
	{
		Session session=HibDataSource.getSession();
		FacultyDTO dto=null;

		try 
		{
		dto=(FacultyDTO) session.get(FacultyDTO.class, pk);
		}
		catch(HibernateException e) 
		{
			e.printStackTrace();
			throw new ApplicationException("Exception: Exception in FacultyModel findByPK method");
		}
		finally 
		{
			session.close();
		}
		return dto;
	}

	public FacultyDTO findByEmail(String email) throws ApplicationException 
	{
		Session session=HibDataSource.getSession();
		FacultyDTO dto=null;
		
		try 
		{
			Criteria criteria=	session.createCriteria(FacultyDTO.class);
			criteria.add(Restrictions.eq("email", email));
			List list = criteria.list();
			
			if (list.size() > 0) 
			{
				dto = (FacultyDTO) list.get(0);
			}
		}
		catch(HibernateException e) 
		{
			e.printStackTrace();
			throw new ApplicationException("Exception: Exception in FacultyModel findByEmail method");
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
		Session session = HibDataSource.getSession();;
		List list = null;
		
		try 
		{
			Criteria criteria = session.createCriteria(FacultyDTO.class);
			if (pageSize > 0) 
			{
				pageNo = ((pageNo - 1) * pageSize);
				criteria.setFirstResult(pageNo);
				criteria.setMaxResults(pageSize);
			}
			list = criteria.list();
		} 
		catch(HibernateException e) 
		{
			e.printStackTrace();
			throw new ApplicationException("Exception: Exception in FacultyModel list method");
		}
		finally 
		{
			session.close();
		}
		return list;
	}

	public List search(FacultyDTO dto) throws ApplicationException 
	{
		return search(dto, 0, 0);
	}

	public List search(FacultyDTO dto, int pageNo, int pageSize) throws ApplicationException 
	{
		Session session = HibDataSource.getSession();
        List list = null;
       
        try 
        {
            Criteria criteria = session.createCriteria(FacultyDTO.class);
         
         if(dto!=null)
         {
        	 if (dto.getId() > 0) {
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
            if (dto.getCollegeId() > 0) {
                criteria.add(Restrictions.eq("collegeId", dto.getCollegeId()));
            }
            if (dto.getCourseId() > 0) {
                criteria.add(Restrictions.eq("courseId", dto.getCourseId()));
            }
            if (dto.getSubjectId() > 0) {
                criteria.add(Restrictions.eq("subjectId", dto.getSubjectId()));
            }}

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
			throw new ApplicationException("Exception: Exception in FacultyModel search method");
		}
		finally 
		{
			session.close();
		}
		return list;
	}
	
}
