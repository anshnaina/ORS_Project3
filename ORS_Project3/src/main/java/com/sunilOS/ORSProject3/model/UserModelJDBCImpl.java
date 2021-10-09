package com.sunilOS.ORSProject3.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.sunilOS.ORSProject3.dto.UserDTO;
import com.sunilOS.ORSProject3.exception.ApplicationException;
import com.sunilOS.ORSProject3.exception.DatabaseException;
import com.sunilOS.ORSProject3.exception.DuplicateRecordException;
import com.sunilOS.ORSProject3.exception.RecordNotFoundException;
import com.sunilOS.ORSProject3.util.EmailBuilder;
import com.sunilOS.ORSProject3.util.EmailMessage;
import com.sunilOS.ORSProject3.util.EmailUtility;
import com.sunilOS.ORSProject3.util.JDBCDataSource;

/**
 * JDBC Implement of user model
 * 
 * @author Anshul
 *
 */
public class UserModelJDBCImpl implements UserModelInt {

	private static Logger log = Logger.getLogger(UserModelJDBCImpl.class);

	private long Roleid;

	public long getRoleid() {
		return Roleid;
	}

	public void setRoleid(long Roleid) {
		this.Roleid = Roleid;
	}

	/**
	 * create id
	 * 
	 * @return pk
	 * @throws DatabaseException
	 */
	public Integer nextPK() throws DatabaseException {
		log.debug("Model nextPK Started");
		Connection conn = null;
		int pk = 0;

		try {
			conn = JDBCDataSource.getConnection();

			PreparedStatement stmt = conn.prepareStatement("Select max(id) from st_user");

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				pk = (int) rs.getLong(1);
			}
			rs.close();
		} catch (Exception e) {
			log.error("DatabaseException", e);
			throw new DatabaseException("Exception: Exception in getting pk");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return pk + 1;
	}

	/**
	 * add user
	 * 
	 * @param dto
	 * @return pk
	 * @throws ApplicationException
	 * @throws DuplicateRecordException
	 */
	public long add(UserDTO dto) throws ApplicationException, DuplicateRecordException {

		log.debug("Model add started");
		Connection conn = null;
		int pk = 0;

		UserDTO existbean = findByEmail(dto.getEmail());

		if (existbean != null) {
			throw new DuplicateRecordException("Login id already exist");

		}

		try {
			conn = JDBCDataSource.getConnection();

			pk = nextPK();
			System.out.println(pk + "in ModelJDBC");
			conn.setAutoCommit(false);

			PreparedStatement stmt = conn.prepareStatement("insert into st_user values(?,?,?,?,?,?,?,?,?,?,?,?,?)");
			stmt.setInt(1, pk);
			stmt.setString(2, dto.getFirstName());
			stmt.setString(3, dto.getLastName());
			stmt.setString(4, dto.getEmail());
			stmt.setString(5, dto.getPassword());
			stmt.setDate(6, new java.sql.Date(dto.getDob().getTime()));
			stmt.setString(7, dto.getMobileNo());
			stmt.setLong(8, dto.getRoleId());
			stmt.setString(9, dto.getGender());
			stmt.setString(10, dto.getCreatedBy());
			stmt.setString(11, dto.getModifiedBy());
			stmt.setTimestamp(12, dto.getCreatedDatetime());
			stmt.setTimestamp(13, dto.getModifiedDatetime());

			stmt.executeUpdate();
			conn.commit();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Database Exception..", e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				ex.printStackTrace();
				throw new ApplicationException("Exception:add roll back Exception" + ex.getMessage());

			}
			throw new ApplicationException("Exception in add user");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model add end");
		return pk;

	}

	/**
	 * delete user
	 * 
	 * @param dto
	 * @throws ApplicationException
	 */
	public void delete(UserDTO dto) throws ApplicationException {

		log.debug("Model delete started");
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement stmt = conn.prepareStatement("delete from st_user where id=?");

			stmt.setLong(1, dto.getId());
			stmt.executeUpdate();
			conn.commit();
			stmt.close();

		} catch (Exception e) {
			log.error("Database Exception ", e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception:Delete rollback Exception" + ex.getMessage());

			}
			throw new ApplicationException("Exception:Exception in delete user");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("model delete end");
	}

	/**
	 * find user by email
	 * 
	 * @param email
	 * @return dto
	 * @throws ApplicationException
	 */
	public UserDTO findByEmail(String email) throws ApplicationException {

		log.debug("Model findByLogin Started");
		Connection conn = null;
		UserDTO dto = null;

		StringBuffer sql = new StringBuffer("Select * from st_user where email=?");

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql.toString());
			stmt.setString(1, email);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				dto = new UserDTO();
				dto.setId(rs.getLong(1));
				dto.setFirstName(rs.getString(2));
				dto.setLastName(rs.getString(3));
				dto.setEmail(rs.getString(4));
				dto.setPassword(rs.getString(5));
				dto.setDob(rs.getDate(6));
				dto.setMobileNo(rs.getString(7));
				dto.setRoleId(rs.getLong(8));
				dto.setGender(rs.getString(9));
				dto.setCreatedBy(rs.getString(10));
				dto.setModifiedBy(rs.getString(11));
				dto.setCreatedDatetime(rs.getTimestamp(12));
				dto.setModifiedDatetime(rs.getTimestamp(13));

			}
			rs.close();

		} catch (Exception e) {
			e.printStackTrace();
			log.error("Database Exception", e);
			throw new ApplicationException("Exception:Exception in getting User by login");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model findByLogin End");
		return dto;
	}

	/**
	 * find user by pk
	 * 
	 * @param pk
	 * @return dto
	 * @throws ApplicationException
	 */
	public UserDTO findByPk(long pk) throws ApplicationException {
		log.debug("Model findByPK started");
		StringBuffer sql = new StringBuffer("select * from st_user where id=?");

		UserDTO dto = null;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			System.out.println("in login do get connection " + conn);
			PreparedStatement stmt = conn.prepareStatement(sql.toString());
			stmt.setLong(1, pk);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				dto = new UserDTO();
				dto.setId(rs.getLong(1));
				dto.setFirstName(rs.getString(2));
				dto.setLastName(rs.getString(3));
				dto.setEmail(rs.getString(4));
				dto.setPassword(rs.getString(5));
				dto.setDob(rs.getDate(6));
				dto.setMobileNo(rs.getString(7));
				dto.setRoleId(rs.getLong(8));
				dto.setGender(rs.getString(9));
				dto.setCreatedBy(rs.getString(10));
				dto.setModifiedBy(rs.getString(11));
				dto.setCreatedDatetime(rs.getTimestamp(12));
				dto.setModifiedDatetime(rs.getTimestamp(13));

			}
			rs.close();
		} catch (Exception e) {

			e.printStackTrace();
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception : Exception in getting User by pk");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model findByPK End");
		return dto;

	}

	/**
	 * update user
	 * 
	 * @param dto
	 * @throws ApplicationException
	 */
	public void update(UserDTO dto) throws ApplicationException, DuplicateRecordException {

		log.debug("Model update Started");
		Connection conn = null;

		UserDTO dtoExist = findByEmail(dto.getEmail());

		if (dtoExist != null && !(dtoExist.getId() == dto.getId())) {
			throw new DuplicateRecordException("Login Id already exist");
		}
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement stmt = conn.prepareStatement(
					"update st_user set first_name=?,last_name=?," + "email=?,dob=?,mobile_no=?,role_id=?,"
							+ "gender=?,created_by=?,modified_by=?,created_datetime=?,modified_datetime=? where id=?");

			stmt.setString(1, dto.getFirstName());
			stmt.setString(2, dto.getLastName());
			stmt.setString(3, dto.getEmail());
			stmt.setString(4, dto.getPassword());
			stmt.setDate(5, new java.sql.Date(dto.getDob().getTime()));
			stmt.setString(6, dto.getMobileNo());
			stmt.setLong(7, dto.getRoleId());
			stmt.setString(8, dto.getGender());
			stmt.setString(9, dto.getCreatedBy());
			stmt.setString(10, dto.getModifiedBy());
			stmt.setTimestamp(11, dto.getCreatedDatetime());
			stmt.setTimestamp(12, dto.getModifiedDatetime());
			stmt.setLong(13, dto.getId());

			stmt.executeUpdate();
			conn.commit();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Database Exception..", e);
			try {

				conn.rollback();
			} catch (Exception ex) {
				ex.printStackTrace();
				throw new ApplicationException("Exception:Delete rollback Exception" + ex.getMessage());

			}
			throw new ApplicationException("Exception in updating user");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model update end");

	}

	public List search(UserDTO dto) throws ApplicationException {
		return search(dto, 0, 0);
	}

	/**
	 * search user
	 * 
	 * @param dto
	 * @param pageNo
	 * @param pageSize
	 * @return list
	 * @throws ApplicationException
	 */
	public List search(UserDTO dto, int pageNo, int pageSize) throws ApplicationException {
		log.debug("Model search started");
		StringBuffer sql = new StringBuffer("select * from st_user where 1=1");


		if (dto != null) {
			if (dto.getId() > 0) {
				sql.append(" And id = " + dto.getId());
			}
			if (dto.getFirstName() != null && dto.getFirstName().length() > 0) {
				sql.append(" And first_name like '" + dto.getFirstName() + "%'");
			}
			if (dto.getLastName() != null && dto.getLastName().length() > 0) {
				sql.append(" And last_name like '" + dto.getLastName() + "%'");
			}
			if (dto.getEmail() != null && dto.getEmail().length() > 0) {
				sql.append(" And email like '" + dto.getEmail() + "%'");
			}
			if (dto.getPassword() != null && dto.getPassword().length() > 0) {
				sql.append(" And password like '" + dto.getPassword() + "%'");
			}
			if (dto.getDob() != null && dto.getDob().getDate() > 0) {
				sql.append(" And dob = " + dto.getDob());
			}
			if (dto.getMobileNo() != null && dto.getMobileNo().length() > 0) {
				sql.append(" And mobile_no = " + dto.getMobileNo());
			}
			if (dto.getRoleId() > 0) {
				sql.append(" AND role_id = " + dto.getRoleId());
			}
			if (dto.getGender() != null && dto.getGender().length() > 0) {
				sql.append(" And gender like '" + dto.getGender() + "%'");
			}

			if (pageSize > 0) {
				pageNo = (pageNo - 1) * pageSize;
				sql.append(" Limit " + pageNo + "," + pageSize);
			}
		}

		System.out.println(sql);
		ArrayList list = new ArrayList();
		Connection conn = null;
		
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql.toString());
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				dto = new UserDTO();
				dto.setId(rs.getLong(1));
				dto.setFirstName(rs.getString(2));
				dto.setLastName(rs.getString(3));
				dto.setEmail(rs.getString(4));
				dto.setPassword(rs.getString(5));
				dto.setDob(rs.getDate(6));
				dto.setMobileNo(rs.getString(7));
				dto.setRoleId(rs.getLong(8));
				dto.setGender(rs.getString(9));
				dto.setCreatedBy(rs.getString(10));
				dto.setModifiedBy(rs.getString(11));
				dto.setCreatedDatetime(rs.getTimestamp(12));
				dto.setModifiedDatetime(rs.getTimestamp(13));
				list.add(dto);
			}
			rs.close();
		} catch (Exception e) {
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception : Exception in getting User by pk");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		log.debug("Model search End");
		return list;

	}

	public List list() throws ApplicationException {
		return list(0, 0);
	}

	/**
	 * list of user
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @return list
	 * @throws ApplicationException
	 */
	public List list(int pageNo, int pageSize) throws ApplicationException {

		log.debug("Model list started");
		ArrayList list = new ArrayList();
		StringBuffer sql = new StringBuffer("Select * from st_user ");

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + "," + pageSize);
		}

		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql.toString());
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {

				UserDTO dto = new UserDTO();
				dto.setId(rs.getLong(1));
				dto.setFirstName(rs.getString(2));
				dto.setLastName(rs.getString(3));
				dto.setEmail(rs.getString(4));
				dto.setPassword(rs.getString(5));
				dto.setDob(rs.getDate(6));
				dto.setMobileNo(rs.getString(7));
				dto.setRoleId(rs.getLong(8));
				dto.setGender(rs.getString(9));
				dto.setCreatedBy(rs.getString(10));
				dto.setModifiedBy(rs.getString(11));
				dto.setCreatedDatetime(rs.getTimestamp(12));
				dto.setModifiedDatetime(rs.getTimestamp(13));

				list.add(dto);

			}
			rs.close();
		} catch (Exception e) {
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception : Exception in getting User");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		log.debug("Model list End");
		return list;

	}

	/**
	 * authenticate user
	 * 
	 * @param login
	 * @param password
	 * @return bean
	 * @throws ApplicationException
	 */
	public UserDTO authenticate(String email, String password) throws ApplicationException {

		log.debug("Model authenticate Started");
		Connection conn = null;
		StringBuffer sql = new StringBuffer("select * from st_user where email=? and password=?");
		UserDTO dto = null;

		try {
			conn = JDBCDataSource.getConnection();

			PreparedStatement stmt = conn.prepareStatement(sql.toString());
			stmt.setString(1, email);
			stmt.setString(2, password);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				dto = new UserDTO();
				dto.setId(rs.getLong(1));
				dto.setFirstName(rs.getString(2));
				dto.setLastName(rs.getString(3));
				dto.setEmail(rs.getString(4));
				dto.setPassword(rs.getString(5));
				dto.setDob(rs.getDate(6));
				dto.setMobileNo(rs.getString(7));
				dto.setRoleId(rs.getLong(8));
				dto.setGender(rs.getString(9));
				dto.setCreatedBy(rs.getString(10));
				dto.setModifiedBy(rs.getString(11));
				dto.setCreatedDatetime(rs.getTimestamp(12));
				dto.setModifiedDatetime(rs.getTimestamp(13));

			}
			System.out.println("my bean " + dto);
			System.out.println("my bean value " + dto.getFirstName());
		} catch (Exception e) {
			log.error("Database Exception", e);
			throw new ApplicationException("Exception:Exception in getting role");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model authenticate End");
		return dto;
	}

	/* *//**
			 * lock time table
			 * 
			 * @param login
			 * @return true or false
			 * @throws ApplicationException,RecordNotFoundException
			 *//*
				 * public boolean lock(String email) throws RecordNotFoundException,
				 * ApplicationException{
				 * 
				 * log.debug("Model lock started"); boolean flag=false; UserDTO dtoExist=null;
				 * try{ dtoExist=findByLogin(email); if(dtoExist!= null){
				 * dtoExist.setLock(UserDTO.ACTIVE); update(dtoExist); flag=true; }else{ throw
				 * new RecordNotFoundException("Login Id not exist"); }
				 * }catch(DuplicateRecordException e){ log.error("Application Exception ", e);
				 * throw new ApplicationException("Database Exception");
				 * 
				 * } log.debug("Service lock end"); return flag; }
				 */

	/**
	 * get role
	 * 
	 * @param bean
	 * @return list
	 * @throws ApplicationException
	 */
	public List getRoles(UserDTO dto) throws ApplicationException {
		log.debug("Model get Roles Started");

		StringBuffer sql = new StringBuffer("select * from st_user where role_id=?");
		Connection conn = null;
		List list = new ArrayList();
		try {

			conn = JDBCDataSource.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql.toString());
			stmt.setLong(1, dto.getRoleId());
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				dto = new UserDTO();
				dto.setId(rs.getLong(1));
				dto.setFirstName(rs.getString(2));
				dto.setLastName(rs.getString(3));
				dto.setEmail(rs.getString(4));
				dto.setPassword(rs.getString(5));
				dto.setDob(rs.getDate(6));
				dto.setMobileNo(rs.getString(7));
				dto.setRoleId(rs.getLong(8));
				dto.setGender(rs.getString(9));
				dto.setCreatedBy(rs.getString(10));
				dto.setModifiedBy(rs.getString(11));
				dto.setCreatedDatetime(rs.getTimestamp(12));
				dto.setModifiedDatetime(rs.getTimestamp(13));

				list.add(dto);
			}
			rs.close();
		} catch (Exception e) {
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception : Exception in get roles");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model get roles End");
		return list;
	}

	/**
	 * change password
	 * 
	 * @param id
	 * @param oldPassword
	 * @param newPassword
	 * @return true and false
	 * @throws RecordNotFoundException
	 * @throws ApplicationException
	 */
	public boolean changePassword(long id, String oldPassword, String newPassword)
			throws RecordNotFoundException, ApplicationException {

		log.debug("model changePassword Started");
		boolean flag = false;
		UserDTO dtoExist = null;

		dtoExist = findByPk(id);
		if (dtoExist != null && dtoExist.getPassword().equals(oldPassword)) {
			dtoExist.setPassword(newPassword);
			try {
				update(dtoExist);
			} catch (DuplicateRecordException e) {
				log.error(e);
				throw new ApplicationException("LoginId is already exist");
			}

			flag = true;

		} else {
			throw new RecordNotFoundException("Login not exist");
		}

		HashMap<String, String> map = new HashMap<String, String>();

		map.put("login", dtoExist.getEmail());
		map.put("password", dtoExist.getPassword());
		map.put("firstName", dtoExist.getFirstName());
		map.put("lastName", dtoExist.getLastName());

		String message = EmailBuilder.getChangePasswordMessage(map);

		EmailMessage msg = new EmailMessage();

		msg.setTo(dtoExist.getEmail());
		msg.setSubject("Rays Ors Password has been changed Successfully.");
		msg.setMessage(message);
		msg.setMessageType(EmailMessage.HTML_MSG);

		EmailUtility.sendMail(msg);

		log.debug("Model changePassword End");
		return flag;

	}

	public UserDTO updateAccess(UserDTO dto) throws ApplicationException {
		return null;
	}

	/**
	 * register user
	 * 
	 * @param bean
	 * @return pk
	 * @throws ApplicationException
	 * @throws DuplicateRecordException
	 */
	public long registerUser(UserDTO dto) throws ApplicationException, DuplicateRecordException {

		log.debug("Model add Started");

		long pk = add(dto);

		HashMap<String, String> map = new HashMap<String, String>();
		map.put("email", dto.getEmail());
		map.put("password", dto.getPassword());

		String message = EmailBuilder.getUserRegistrationMessage(map);

		EmailMessage msg = new EmailMessage();

		msg.setTo(dto.getEmail());
		msg.setSubject("Registration is successfull for Ors Project SunilOS");
		msg.setMessage(message);
		msg.setMessageType(EmailMessage.HTML_MSG);

		EmailUtility.sendMail(msg);

		return pk;
	}

	public static boolean resetPassword(UserDTO dto) {
		return false;
	}

	/*
	 * public boolean resetPassword(UserBean bean) throws ApplicationException {
	 * 
	 * String newPassword = String.valueOf(new Date().getTime()).substring(0,4);
	 * UserBean userData = findByPK(bean.getId());
	 * userData.setPassword(newPassword);
	 * 
	 * try { update(userData); } catch (DuplicateRecordException e) { return false;
	 * }
	 * 
	 * HashMap<String, String> map = new HashMap<String, String>(); map.put("login",
	 * bean.getLogin()); map.put("password", bean.getPassword());
	 * map.put("firstName", bean.getFirstName()); map.put("lastName",
	 * bean.getLastName());
	 * 
	 * String message = EmailBuilder.getForgetPasswordMessage(map);
	 * 
	 * EmailMessage msg = new EmailMessage();
	 * 
	 * msg.setTo(bean.getLogin()); msg.setSubject("Password has been reset");
	 * msg.setMessage(message); msg.setMessageType(EmailMessage.HTML_MSG);
	 * 
	 * EmailUtility.sendMail(msg);
	 * 
	 * return true; }
	 * 
	 */
	/**
	 * forgotpassword
	 * 
	 * @param email
	 * @return true and false
	 * @throws RecordNotFoundException
	 * @throws ApplicationException
	 */
	public boolean forgotPassword(String email) throws ApplicationException, RecordNotFoundException {

		UserDTO dto = new UserDTO();

		dto = findByEmail(email);
		boolean flag = false;

		if (dto == null) {
			throw new RecordNotFoundException("Email ID does not exists!");

		}

		HashMap<String, String> map = new HashMap<String, String>();

		map.put("email", dto.getEmail());
		map.put("password", dto.getPassword());
		map.put("firstName", dto.getFirstName());
		map.put("lastName", dto.getLastName());

		String message = EmailBuilder.getForgetPasswordMessage(map);

		EmailMessage msg = new EmailMessage();

		msg.setTo(email);
		msg.setSubject("Password reset");
		msg.setMessage(message);
		msg.setMessageType(EmailMessage.HTML_MSG);

		EmailUtility.sendMail(msg);

		flag = true;

		return flag;
	}

}
