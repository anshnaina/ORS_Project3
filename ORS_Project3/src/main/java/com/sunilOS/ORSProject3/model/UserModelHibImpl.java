package com.sunilOS.ORSProject3.model;

import java.util.HashMap;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;

import com.sunilOS.ORSProject3.dto.UserDTO;
import com.sunilOS.ORSProject3.exception.ApplicationException;
import com.sunilOS.ORSProject3.exception.DuplicateRecordException;
import com.sunilOS.ORSProject3.exception.RecordNotFoundException;
import com.sunilOS.ORSProject3.util.EmailBuilder;
import com.sunilOS.ORSProject3.util.EmailMessage;
import com.sunilOS.ORSProject3.util.EmailUtility;
import com.sunilOS.ORSProject3.util.HibDataSource;

public class UserModelHibImpl implements UserModelInt
{

	public long add(UserDTO dto) throws ApplicationException, DuplicateRecordException
	{
		Session session = HibDataSource.getSession();
		Transaction tx = null;
		
		UserDTO userDTO;
		try
		{
		if(dto != null)
		{
			userDTO = findByEmail(dto.getEmail());
			if(userDTO!=null && dto.getEmail() != userDTO.getEmail())
			{
				throw new DuplicateRecordException("Given email is already registered with us!");
			}
			else
			{
			tx = session.beginTransaction();
			session.save(dto);
			tx.commit();
			}
		}
		}
		catch(HibernateException e)
		{
			e.printStackTrace();
			if(tx!=null)
			{
				tx.rollback();
			}
			throw new ApplicationException("Unable  to add data");
		}
		finally 
		{
			session.close();
		}
		return dto.getId();
	}

	public void delete(UserDTO dto) throws ApplicationException 
	{
		Session session  = HibDataSource.getSession();
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
			throw new ApplicationException("Unable to delete data");
		}
		finally 
		{
			session.close();
		}
	}

	public void update(UserDTO dto) throws ApplicationException, DuplicateRecordException 
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
			throw new ApplicationException("Unable to update data");
		}
		finally 
		{
			session.close();
		}
	}

	public UserDTO findByPk(long pk) throws ApplicationException 
	{
		Session session = HibDataSource.getSession();
		UserDTO dto = null;
		
		try
		{
			dto = session.get(UserDTO.class, pk);
		}
		catch(HibernateException e)
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

	public UserDTO findByEmail(String email) throws ApplicationException 
	{
		Session session = HibDataSource.getSession();
		UserDTO dto = null;
		
		try
		{
			Criteria criteria = session.createCriteria(UserDTO.class);
			criteria.add(Restrictions.eq("email",  email));
			List<UserDTO> list = criteria.list();
			if (list.size() == 1)
			{
				dto = (UserDTO) list.get(0);
			}
		}
		catch(HibernateException e)
		{
			e.printStackTrace();
			throw new ApplicationException("Unable  to fetch data");	
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
		{Criteria criteria = session.createCriteria(UserDTO.class);
		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			criteria.setFirstResult(pageNo);
			criteria.setMaxResults(pageSize);
		}
		list = criteria.list();

		}
		catch (HibernateException e) 
		{
			throw new ApplicationException("Exception : Exception in  Users list");
		}
		finally 
		{
			session.close();
		}
		return list;
	}

	public List search(UserDTO dto) throws ApplicationException 
	{
		return search(dto, 0, 0);
	}

	public List search(UserDTO dto, int pageNo, int pageSize) throws ApplicationException
	{
		Session session = HibDataSource.getSession();
		List list = null;
		try
		{
		Criteria criteria = session.createCriteria(UserDTO.class);
		
		if(dto != null)
		{
			if(dto.getId() != null)
			{
				criteria.add(Restrictions.like("id", dto.getId()));
			}
			if(dto.getFirstName() != null && dto.getFirstName().length()>0)
			{
				criteria.add(Restrictions.like("firstName", dto.getFirstName()+"%"));
			}
			if (dto.getLastName() != null && dto.getLastName().length() > 0) {
				criteria.add(Restrictions.like("lastName", dto.getLastName() + "%"));
			}
			if (dto.getEmail() != null && dto.getEmail().length() > 0) {
				criteria.add(Restrictions.like("email", dto.getEmail() + "%"));
			}
			if (dto.getPassword() != null && dto.getPassword().length() > 0) {
				criteria.add(Restrictions.like("password", dto.getPassword() + "%"));
			}
			if (dto.getGender() != null && dto.getGender().length() > 0) {
				criteria.add(Restrictions.like("gender", dto.getGender() + "%"));
			}
			if (dto.getDob() != null && dto.getDob().getDate() > 0) {
				criteria.add(Restrictions.eq("dob", dto.getDob()));
			}
			if (dto.getRoleId() > 0) {
				criteria.add(Restrictions.eq("roleId", dto.getRoleId()));
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
			throw new ApplicationException("Exception : Exception in  User Search");
		}
		finally
		{
			session.close();
		}
		return list;
	}

	public boolean changePassword(long pk, String newPassword, String oldPassword)
			throws ApplicationException, RecordNotFoundException, DuplicateRecordException
	{
		Session session = HibDataSource.getSession();
		boolean flag = false;
		UserDTO dto = null;
		dto = findByPk(pk);
		
		if(dto != null && dto.getPassword().equals(oldPassword))
		{
			dto.setPassword(newPassword);
			try
			{
				update(dto);
			}
			catch(DuplicateRecordException e)
			{
				e.printStackTrace();
				throw new DuplicateRecordException("Exception: Exception in User ChangePassword");
			}
			flag = true;
		}
		else
		{
			throw new RecordNotFoundException("Email doest not exist");
		}
	
		HashMap<String, String> map = new HashMap<String, String>();
		
		map.put("email", dto.getEmail());
		map.put("password", dto.getPassword());
		map.put("firstName", dto.getFirstName());
		map.put("lastName", dto.getLastName());
		
		String message = EmailBuilder.getChangePasswordMessage(map);
		EmailMessage msg = new EmailMessage();
		
		msg.setTo(dto.getEmail());
		msg.setSubject("Password has been changed successfully");
		msg.setMessage(message);
		msg.setMessageType(EmailMessage.HTML_MSG);
				
		EmailUtility.sendMail(msg);
		
		return false;
	}

	public UserDTO authenticate(String email, String password) throws ApplicationException 
	{
		Session session = HibDataSource.getSession();
		UserDTO dto = null;
		
		Query q = session.createQuery("from UserDTO where email=?0 and password=?1");
		q.setString(0, email);
		q.setString(1, password);
		List list = q.list();
		
		if(list.size()>0)
		{
			dto = (UserDTO) list.get(0);
		}
		else
		{
			dto = null;
		}
		return dto;
	}

	public boolean forgotPassword(String email) throws ApplicationException, RecordNotFoundException 
	{
		Session session = HibDataSource.getSession();
		UserDTO dto = null;
		boolean flag = false;
		
		dto = findByEmail(email);
		if(dto == null)
		{
			throw new RecordNotFoundException("Given email id is not registered with us!");
		}
		
		HashMap<String, String> map = new HashMap<String, String>();
		
		map.put("email", dto.getEmail());
		map.put("password", dto.getPassword());
		map.put("firstName", dto.getFirstName());
		map.put("lastName", dto.getLastName());
		
		String message = EmailBuilder.getForgetPasswordMessage(map);
		EmailMessage msg = new EmailMessage();
		
		msg.setTo(dto.getEmail());
		msg.setSubject("Password recovery");
		msg.setMessage(message);
		msg.setMessageType(EmailMessage.HTML_MSG);;
		
		EmailUtility.sendMail(msg);
		
		return false;
		
	}

	public long registerUser(UserDTO dto) throws ApplicationException, DuplicateRecordException 
	{
		Session session = HibDataSource.getSession();
		long pk = 0;
		UserDTO userDTO;
		try
		{
		if(dto != null)
		{
			userDTO = findByEmail(dto.getEmail());
			if(userDTO!=null && dto.getEmail() != userDTO.getEmail())
			{
				throw new DuplicateRecordException("Given email is already registered with us!");
			}
			else
			{	
			pk = add(dto);
			
			HashMap<String, String> map = new HashMap<String, String>();
			
			map.put("email", dto.getEmail());
			map.put("password", dto.getPassword());
			
			String message = EmailBuilder.getUserRegistrationMessage(map);
			
			EmailMessage msg = new EmailMessage();
			
			msg.setTo(dto.getEmail());
			msg.setSubject("Registration is successful for ORS Project SUNRAYS Technologies");
			msg.setMessage(message);
			msg.setMessageType(EmailMessage.HTML_MSG);
			
			EmailUtility.sendMail(msg);
			}}}
			
		catch(ApplicationException e)
		{
			e.printStackTrace();
			throw new ApplicationException("Unable to add user");
		}
		
		return pk;
	}
	
	public static void main(String[] args) throws ApplicationException 
	{
		UserDTO userDto = new UserDTO();
		UserModelHibImpl userModel = new UserModelHibImpl();
		
		userDto = userModel.authenticate("er.anshulnaina@gmail.com", "naina");
	}
	
}
