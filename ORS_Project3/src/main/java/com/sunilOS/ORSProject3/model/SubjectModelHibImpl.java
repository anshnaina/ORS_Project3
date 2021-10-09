package com.sunilOS.ORSProject3.model;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.sunilOS.ORSProject3.dto.CourseDTO;
import com.sunilOS.ORSProject3.dto.SubjectDTO;
import com.sunilOS.ORSProject3.exception.ApplicationException;
import com.sunilOS.ORSProject3.exception.DuplicateRecordException;
import com.sunilOS.ORSProject3.util.HibDataSource;

public class SubjectModelHibImpl implements SubjectModelInt
{

	public long add(SubjectDTO dto) throws ApplicationException, DuplicateRecordException 
	{
		Session session = HibDataSource.getSession();
		Transaction tx = null;

		CourseModelInt cModel = ModelFactory.getInstance().getCourseModel();
		CourseDTO cDto = cModel.findByPk(dto.getCourseId());
		dto.setCourseName(cDto.getCourseName());
		
		SubjectDTO duplicataSub = findBySubjectName(dto.getSubjectName());
		
	    // Check if create Subject already exist
	    if (duplicataSub!= null && duplicataSub.getSubjectName()!=null) 
	    {
	        throw new DuplicateRecordException("Subject already exists");
	    }
	    
	    try 
		{
			tx = session.beginTransaction();
			session.save(dto);
			tx.commit();
		} 
	    catch (HibernateException e) 
	    {
			if (tx != null)
			{
				tx.rollback();
			}
			throw new ApplicationException("Exception: Exception in SubjectModel add method");
		} 
	    finally 
	    {
			session.close();
		}
		return dto.getId();
	}

	public void update(SubjectDTO dto) throws ApplicationException, DuplicateRecordException {

		Session session = HibDataSource.getSession();
		Transaction tx = null;

		CourseModelInt cModel = ModelFactory.getInstance().getCourseModel();
		CourseDTO cDto = cModel.findByPk(dto.getCourseId());
		dto.setCourseName(cDto.getCourseName());
		
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
			throw new ApplicationException("Exception: Exception in SubjectModel update method");
		} 
		finally 
		{
			session.close();
		}
	}

	public void delete(SubjectDTO dto) throws ApplicationException 
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
			if (tx != null) 
			{
				tx.rollback();
			}
			throw new ApplicationException("Exception: Exception in SubjectModel delete method");
		} 
		finally
		{
			session.close();
		}
	}

	public SubjectDTO findByPk(long pk) throws ApplicationException 
	{
		Session session = HibDataSource.getSession();
		SubjectDTO dto = null;

		try 
		{
			dto = (SubjectDTO) session.get(SubjectDTO.class, pk);
		} 
		catch (HibernateException e) 
		{
			e.printStackTrace();
			throw new ApplicationException("Exception: Exception in SubjectModel findByPK method");
		} 
		finally 
		{
			session.close();
		}
		return dto;
	}

	public SubjectDTO findBySubjectName(String subjectName) throws ApplicationException 
	{
		Session session = HibDataSource.getSession();
		SubjectDTO dto = null;
		
		try 
		{
			Criteria criteria = session.createCriteria(SubjectDTO.class);
			criteria.add(Restrictions.like("subjectName", subjectName));

			List list = criteria.list();
			if (list.size() > 0) 
			{
				dto = (SubjectDTO) list.get(0);
			}
		} 
		catch (HibernateException e) 
		{
			e.printStackTrace();
			throw new ApplicationException("Exception: Exception in SubjectModel findBySubjectName method");
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
		
		try {
			Criteria criteria = session.createCriteria(SubjectDTO.class);
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
			throw new ApplicationException("Exception: Exception in SubjectModel list method");
		}
		finally
		{
			session.close();
		}
		return list;
	}

	public List search(SubjectDTO dto) throws ApplicationException 
	{
		return search(dto, 0, 0);
	}

	public List search(SubjectDTO dto, int pageNo, int pageSize) throws ApplicationException {

		Session session = null;
		List list = null;
		try {
			session = HibDataSource.getSession();
			Criteria criteria = session.createCriteria(SubjectDTO.class);

			if (dto != null) {
				if (dto.getId() > 0) {
					criteria.add(Restrictions.eq("id", dto.getId()));

				}
				if (dto.getCourseId() > 0) {
					criteria.add(Restrictions.eq("courseId", dto.getCourseId()));
				}
				if (dto.getCourseName() != null && dto.getCourseName().length() > 0) {
					criteria.add(Restrictions.like("courseName", dto.getCourseName() + "%"));
				}
				if (dto.getSubjectName() != null && dto.getSubjectName().length() > 0) {
					criteria.add(Restrictions.like("subjectName", dto.getSubjectName() + "%"));
				}
			}
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

			throw new ApplicationException("Exception: Exception in SubjectModel search method");
		} 
		finally 
		{
			session.close();
		}
		return list;
	}
	
}
