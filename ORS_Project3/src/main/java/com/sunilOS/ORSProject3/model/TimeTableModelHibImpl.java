package com.sunilOS.ORSProject3.model;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Restrictions;

import com.sunilOS.ORSProject3.dto.CourseDTO;
import com.sunilOS.ORSProject3.dto.MarksheetDTO;
import com.sunilOS.ORSProject3.dto.SubjectDTO;
import com.sunilOS.ORSProject3.dto.TimeTableDTO;
import com.sunilOS.ORSProject3.exception.ApplicationException;
import com.sunilOS.ORSProject3.util.HibDataSource;


public class TimeTableModelHibImpl implements TimeTableModelInt
{

	public long add(TimeTableDTO dto) throws ApplicationException 
	{
		CourseModelInt Cmodel = ModelFactory.getInstance().getCourseModel();
		CourseDTO CDto = null; CDto = Cmodel.findByPk(dto.getCourseId());
		dto.setCourseName(CDto.getCourseName());
		  
		SubjectModelInt Smodel = ModelFactory.getInstance().getSubjectModel();
		SubjectDTO SDto = Smodel.findByPk(dto.getSubjectId());
		dto.setSubjectName(SDto.getSubjectName());
		
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
			throw new ApplicationException("Exception: Exception in TimeTableModel add method");
		}
		return dto.getId();
	}

	public void update(TimeTableDTO dto) throws ApplicationException 
	{
		CourseModelInt Cmodel = ModelFactory.getInstance().getCourseModel();
		CourseDTO CDto = null; CDto = Cmodel.findByPk(dto.getCourseId());
		dto.setCourseName(CDto.getCourseName());
		  
		SubjectModelInt Smodel = ModelFactory.getInstance().getSubjectModel();
		SubjectDTO SDto = Smodel.findByPk(dto.getSubjectId());
		dto.setSubjectName(SDto.getSubjectName());
		
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
			throw new ApplicationException("Exception: Exception in TimeTableModel update method");
		}
		finally
		{
			session.close();
		}
	}

	public void delete(TimeTableDTO dto) throws ApplicationException 
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
			throw new ApplicationException("Exception: Exception in TimeTableModel delete method");
		}
		finally
		{
			session.close();
		}
	}

	public TimeTableDTO findByPk(long pk) throws ApplicationException 
	{
		Session session = HibDataSource.getSession();
		TimeTableDTO dto = null;
		
		try
		{
			Criteria criteria = session.createCriteria(TimeTableDTO.class);
			criteria.add(Restrictions.eq("id", pk));
			List list = criteria.list();
			if (list.size() > 0)
			{
				dto = (TimeTableDTO) list.get(0);
			}
		}
		catch(HibernateException e)
		{
			e.printStackTrace();
			throw new ApplicationException("Exception: Exception in TimeTableModel findByPK method");
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
			Criteria criteria = session.createCriteria(TimeTableDTO.class);
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
			throw new ApplicationException("Exception: Exception in TimeTableModel list method");
		}
		finally
		{
			session.close();
		}
		return list;
	}

	public List search(TimeTableDTO dto) throws ApplicationException 
	{
		return search(dto, 0, 0);
	}

	public List search(TimeTableDTO dto, int pageNo, int pageSize) throws ApplicationException 
	{
		Session session = HibDataSource.getSession();
		List list = null;
		
		try
		{
			Criteria criteria = session.createCriteria(TimeTableDTO.class);
			System.out.println("insssssssside ttm");
			if (dto != null)
			{
				if (dto.getId() > 0)
				{
					criteria.add(Restrictions.eq("id", dto.getId()));
				}
				if (dto.getCourseName() != null && dto.getCourseName().length() > 0)
				{
					criteria.add(Restrictions.like("courseName", dto.getCourseName() +  "%"));
				}
				if (dto.getSubjectName() != null && dto.getSubjectName().length() > 0) {
					criteria.add(Restrictions.like("subjectName", dto.getSubjectName() + "%"));
				}
				if (dto.getSemester() != null && dto.getSemester().length() > 0) {
					criteria.add(Restrictions.like("semester", dto.getSemester() + "%"));
				}
				if (dto.getExamDate() != null && dto.getExamDate().getDate() > 0) {
					criteria.add(Restrictions.eq("examDate", dto.getExamDate()));
				}
				if (dto.getSubjectId() > 0) {
					criteria.add(Restrictions.eq("subjectId", dto.getSubjectId()));
				}
				if (dto.getCourseId() > 0) {
					criteria.add(Restrictions.eq("courseId", dto.getCourseId()));
				}
			}
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
			throw new ApplicationException("Exception: Exception in TimeTableModel search method");
		}
		finally
		{
			session.close();
		}
		return list;
	}

	public TimeTableDTO checkByCourseName(long courseId, Date examDate) throws ApplicationException 
	{
		Session session = HibDataSource.getSession();
		TimeTableDTO dto = null;
		/*
		 * long l = examDate.getTime(); java.sql.Date date = new java.sql.Date(l);
		 */

		try
		{
			Criteria criteria =  session.createCriteria(TimeTableDTO.class);
			
			criteria.add(Restrictions.and(Restrictions.eq("courseId", courseId),Restrictions.eq("examDate", examDate)));
			List list = criteria.list();
			if (list.size() > 0)
			{
				dto = (TimeTableDTO) list.get(0);
			}
		}
		catch(HibernateException e)
		{
			e.printStackTrace();
			throw new ApplicationException("Exception: Exception in TimeTableModel checkByCourseName method");
		}
		finally
		{
			session.close();
		}
		return dto;
	}

	public TimeTableDTO checkBySubjectName(long courseId, long subjectId, Date examDate) throws ApplicationException 
	{
		Session session = HibDataSource.getSession();
		TimeTableDTO dto = null;
		List list = null;
		
		long l = examDate.getTime();
		java.sql.Date date = new java.sql.Date(l);
		
		try
		{
			Criteria criteria = session.createCriteria(TimeTableDTO.class);
			Disjunction dis = Restrictions.disjunction();
			dis.add(Restrictions.eq("courseId", courseId));
			dis.add(Restrictions.eq("subjectId", subjectId));
			dis.add(Restrictions.eq("examDate", date));
			criteria.add(dis);
			list = criteria.list();
					
			if (list.size() > 0)
			{
				dto = (TimeTableDTO) list.get(0);
			}
		}
		catch(HibernateException e)
		{
			e.printStackTrace();
			throw new ApplicationException("Exception: Exception in TimeTableModel checkBySubjectName method");
		}
		finally
		{
			session.close();
		}
		return dto;
	}

	public TimeTableDTO checkBySemester(long courseId, long subjectId, String semester, Date examDate)
			throws ApplicationException 
	{
		Session session = HibDataSource.getSession();
		TimeTableDTO dto = null;
		long l = examDate.getTime();
		java.sql.Date date = new java.sql.Date(l);
		
		try
		{
			Criteria criteria = session.createCriteria(TimeTableDTO.class);
			Disjunction dis = Restrictions.disjunction();
			dis.add(Restrictions.eq("courseId", courseId));
			dis.add(Restrictions.eq("subjectId", subjectId));
			dis.add(Restrictions.like("semester", semester));
			dis.add(Restrictions.eq("examDate", date));
			criteria.add(dis);
			List list = criteria.list();
			if (list.size() > 0) 
			{
				dto = (TimeTableDTO) list.get(0);
			}
		}
		catch(HibernateException e)
		{
			e.printStackTrace();
			throw new ApplicationException("Exception: Exception in TimeTableModel checkBySemester method");
		}
		finally
		{
			session.close();
		}
		return dto;
	}
	public static void main(String[] args)throws ApplicationException, Exception
	{
		TimeTableDTO dto  = new TimeTableDTO();
		
		TimeTableModelHibImpl model = new TimeTableModelHibImpl();
		
		List list = model.list();
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
