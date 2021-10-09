package com.sunilOS.ORSProject3.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.sunilOS.ORSProject3.dto.CollegeDTO;
import com.sunilOS.ORSProject3.dto.CourseDTO;
import com.sunilOS.ORSProject3.dto.FacultyDTO;
import com.sunilOS.ORSProject3.dto.SubjectDTO;
import com.sunilOS.ORSProject3.exception.ApplicationException;
import com.sunilOS.ORSProject3.exception.DatabaseException;
import com.sunilOS.ORSProject3.exception.DuplicateRecordException;
import com.sunilOS.ORSProject3.util.JDBCDataSource;

/**
 * JDBC Implements of Faculty model
 * @author Anshul
 *
 */
public class FacultyModelJDBCImpl implements FacultyModelInt {
	
	private static Logger log= Logger.getLogger(FacultyModelJDBCImpl.class);
	
	/**
	 * new id create
	 * @return pk
	 * @throws DatabaseException
	 */
	public Integer nextPK() throws DatabaseException{
		
		log.debug("Model nextPk started");
		
		Connection conn=null;
		int pk=0;
		try{
			conn=JDBCDataSource.getConnection();
			PreparedStatement stmt=conn.prepareStatement("Select max(id) from st_faculty");
			
			ResultSet rs=stmt.executeQuery();
			
			while(rs.next()){
				pk=rs.getInt(1);
			}
			rs.close();
		}catch(Exception e){
			log.error("DataBase Exception",e);
			throw new DatabaseException("Exception: Exception in getting pk");
			
		}finally{
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model nextPK end");
		return pk+1;
	}
	
	/**
	 * add new faculty
	 * @param fdto
	 * @return pk
	 * @throws ApplicationException
	 * @throws DuplicateRecordException
	 */
	public long add(FacultyDTO fdto) throws ApplicationException{
		
		Connection conn=null;
		System.out.println("tttttttttttttttttttttttttttttttttt"+fdto);
		long pk = 0;
		CollegeModelJDBCImpl model = new CollegeModelJDBCImpl();
		CollegeDTO dto = model.findByPk(fdto.getCollegeId());
		String CollegeName = dto.getCollegeName();

		CourseModelJDBCImpl model1 = new CourseModelJDBCImpl();
		CourseDTO dto1 = model1.findByPk(fdto.getCourseId());
		String CourseName = dto1.getCourseName();

		SubjectModelJDBCImpl model2 = new SubjectModelJDBCImpl();
		SubjectDTO dto2 = model2.findByPk(fdto.getSubjectId());
		String SubjectName = dto2.getSubjectName();

//		FacultyDTO duplicataRole = findByName(fdto.getFirstName());
//		// Check if create Faculty already exist
//		if (duplicataRole != null) {
//			throw new DuplicateRecordException("Faculty already exists");
//		}
		
		try {
			pk = nextPK();
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn
					.prepareStatement("insert into st_faculty values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			ps.setLong(1, pk);
			ps.setString(2, fdto.getFirstName());
			ps.setString(3, fdto.getLastName());
			ps.setString(4, fdto.getGender());
			ps.setString(6, fdto.getQualification());
			ps.setString(7, fdto.getEmail());
			ps.setString(8, fdto.getMobileNo());
			ps.setLong(9, fdto.getCollegeId());
			ps.setString(10,CollegeName);
			ps.setLong(11, fdto.getCourseId());
			ps.setString(12,CourseName);
			ps.setLong(13, fdto.getSubjectId());
			ps.setString(14,SubjectName);
			ps.setString(15, fdto.getCreatedBy());
			ps.setString(16, fdto.getModifiedBy());
			ps.setTimestamp(17, fdto.getCreatedDatetime());
			ps.setTimestamp(18, fdto.getModifiedDatetime());
			int a = ps.executeUpdate();
			System.out.println("insert data" + a);
			ps.close();
			conn.commit();
			System.out.println("pppppppppppppppppppppppp"+a);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Database Exception..", e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : add rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in add Student");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model add End");
		return 0;

	}
	
	/**
	 * find faculty by email id
	 * @param emailId
	 * @return dto
	 * @throws ApplicationException 
	 */
	public FacultyDTO findByEmail(String email) throws ApplicationException {
		FacultyDTO fdto=null;
		
		Connection conn=null;
		try{
			conn=JDBCDataSource.getConnection();
		    PreparedStatement ps=conn.prepareStatement("select * from st_faculty where EMAIL=?");
			ps.setString(1, email);
			ResultSet rs=ps.executeQuery();
			while(rs.next()){
				fdto = new FacultyDTO();
				fdto.setId(rs.getLong(1));
				fdto.setFirstName(rs.getString(2));
				fdto.setLastName(rs.getString(3));
				fdto.setGender(rs.getString(4));
				fdto.setQualification(rs.getString(5));
				fdto.setEmail(rs.getString(6));
				fdto.setMobileNo(rs.getString(7));
				fdto.setCollegeId(rs.getLong(8));
				fdto.setCollegeName(rs.getString(9));
				fdto.setCourseId(rs.getLong(10));
				fdto.setCourseName(rs.getString(11));
				fdto.setSubjectId(rs.getLong(12));
				fdto.setSubjectName(rs.getString(13));
				fdto.setCreatedBy(rs.getString(14));   
		         fdto.setModifiedBy(rs.getString(15));
		         fdto.setCreatedDatetime(rs.getTimestamp(16));
		         fdto.setModifiedDatetime(rs.getTimestamp(17));

			}
			ps.close();
			conn.close();

			
		}catch (Exception e) 
	     {
	    	 throw new ApplicationException("exception in faculty findByEmail  add..... "+e.getMessage());                
	     } finally 
	     {
	       JDBCDataSource.closeConnection(conn);
	     }

		return fdto;
	}

	
	
	/**
	 * delete faculty
	 * @param dto
	 * @throws ApplicationException
	 */
	public void delete(FacultyDTO dto) throws ApplicationException{
		
		log.debug("Model delete started");
		Connection conn= null;
		try{
			conn=JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement stmt = conn.prepareStatement("Delete from st_faculty where ID =?");
			stmt.setLong(1, dto.getId());
			stmt.executeUpdate();
			conn.commit();
			stmt.close();
		} catch (Exception e) {
		log.error("Database Exception..", e);
		try {
		conn.rollback();
		} catch (Exception ex) {
		throw new ApplicationException("Exception : Delete rollback exception "+ ex.getMessage());
		}
		throw new ApplicationException("Exception : Exception in delete college");
		} finally {
		JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model delete end");
		}
	
	/**
	 * find faculty by name
	 * 
	 * @param firstName
	 * @return dto
	 * @throws ApplicationException
	 */
	public FacultyDTO findByName(String firstname) throws ApplicationException{
		log.debug("Model FindByName Started ");
		StringBuffer sql=new StringBuffer("Select * from st_faculty where first_name=?");
		
		
		FacultyDTO dto=null;
		Connection conn=null;
		try{
			conn=JDBCDataSource.getConnection();
			PreparedStatement stmt=conn.prepareStatement(sql.toString());
			stmt.setString(1,firstname);
			
			ResultSet rs=stmt.executeQuery();
			
			while(rs.next()){
				dto=new FacultyDTO();
				dto.setId(rs.getLong(1));
				dto.setFirstName(rs.getString(2));
				dto.setLastName(rs.getString(3));
				dto.setGender(rs.getString(4));
				dto.setQualification(rs.getString(5));
				dto.setEmail(rs.getString(6));
				dto.setMobileNo(rs.getString(7));
				dto.setCollegeId(rs.getLong(8));
				dto.setCollegeName(rs.getString(9));
				dto.setCourseId(rs.getLong(10));
				dto.setCourseName(rs.getString(11));
				dto.setSubjectId(rs.getLong(12));
				dto.setSubjectName(rs.getString(13));
				dto.setCreatedBy(rs.getString(14));
				dto.setModifiedBy(rs.getString(15));
				dto.setCreatedDatetime(rs.getTimestamp(16));
				dto.setModifiedDatetime(rs.getTimestamp(17));
				
			}rs.close();
			
			
		}catch(Exception e){
			log.error("Database Exception..",e);
			throw new ApplicationException("Exception:Exception in getting Faculty by Name");
			
			
		}finally{
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model  findByName Ended");
		return dto;
	}
	
	/**
	 * find information with the help of pk
	 * @param pk
	 * @return dto
	 * @throws ApplicationException
	 */
	public FacultyDTO findByPk(long pk) throws ApplicationException{
		log.debug("Model find by pk started");
		
		FacultyDTO dto=null;
		Connection conn=null;
		
		try{
			conn=JDBCDataSource.getConnection();
			PreparedStatement stmt=conn.prepareStatement("Select * from st_faculty where id=? ");
			stmt.setLong(1, pk);
			
			ResultSet rs=stmt.executeQuery();
			while(rs.next()){
				dto=new FacultyDTO();
				dto.setId(rs.getLong(1));
				dto.setFirstName(rs.getString(2));
				dto.setLastName(rs.getString(3));
				dto.setGender(rs.getString(4));
				dto.setQualification(rs.getString(5));
				dto.setEmail(rs.getString(6));
				dto.setMobileNo(rs.getString(7));
				dto.setCollegeId(rs.getLong(8));
				dto.setCollegeName(rs.getString(9));
				dto.setCourseId(rs.getLong(10));
				dto.setCourseName(rs.getString(11));
				dto.setSubjectId(rs.getLong(12));
				dto.setSubjectName(rs.getString(13));
				dto.setCreatedBy(rs.getString(14));
				dto.setModifiedBy(rs.getString(15));
				dto.setCreatedDatetime(rs.getTimestamp(16));
				dto.setModifiedDatetime(rs.getTimestamp(17));
				
			}rs.close();
			
		}catch(Exception e){
			log.error("Database Exception..",e);
			throw new ApplicationException("Exception:Exception in getting Faculty by pk");
		}finally{
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model  findByPK End");
		return dto;
		
	}
	
	/**
	 * update faculty information
	 * @param dto
	 * @throws ApplicationException
	 * @throws DatabaseException
	 */
	public void update(FacultyDTO dto) throws ApplicationException{
		log.debug("Model update Started");
		System.out.println("update method start");
		Connection conn=null;
		
		FacultyDTO dtoexist=findByName(dto.getFirstName());
		
		System.out.println("update method dto exist");
	
		CollegeModelJDBCImpl model = new CollegeModelJDBCImpl();
		CollegeDTO dto1 = model.findByPk(dto.getCollegeId());
		String CollegeName = dto1.getCollegeName();

		System.out.println("update method get CollegeName");
		CourseModelJDBCImpl model1 = new CourseModelJDBCImpl();
		CourseDTO dto2 = model1.findByPk(dto.getCourseId());
		String CourseName = dto2.getCourseName();

		System.out.println("update method get course name");
		SubjectModelJDBCImpl model2 = new SubjectModelJDBCImpl();
		SubjectDTO dto3 = model2.findByPk(dto.getSubjectId());
		String SubjectName = dto3.getSubjectName();

		
		try{
			conn=JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			System.out.println("update method get connection");
			PreparedStatement stmt=conn.prepareStatement("Update st_faculty set first_name=?,last_name=?,gender=?,qualification=?,email=?,mobile_no=?,college_id=?,college_name=?,course_id=?,course_name=?,subject_id=?,subject_name=?,created_by=?,modified_by=?,created_datetime=?,modified_datetime=? where id=?");

			stmt.setString(1, dto.getFirstName());
			stmt.setString(2,dto.getLastName());
			stmt.setString(3,dto.getGender());
			stmt.setString(4,dto.getQualification());
			stmt.setString(5,dto.getEmail());
			stmt.setString(6,dto.getMobileNo());
			stmt.setLong(7,dto.getCollegeId());
			stmt.setString(8,CollegeName);
			stmt.setLong(9, dto.getCourseId());
			stmt.setString(10,CourseName);
			stmt.setLong(11,dto.getSubjectId());
			stmt.setString(12,SubjectName);
			stmt.setString(13,dto.getCreatedBy());
			stmt.setString(14,dto.getModifiedBy());
			stmt.setTimestamp(15,dto.getCreatedDatetime());
			stmt.setTimestamp(16,dto.getModifiedDatetime());
			stmt.setLong(17, dto.getId());
			System.out.println("update method set all parameter");
			
			stmt.executeUpdate();
			System.out.println("update method execute query done");
			conn.commit();
			stmt.close();
		
		}catch(Exception e){
			e.printStackTrace();
			log.error("Database Exception..",e);
		
			try{
				System.out.println("update method rollback");
				conn.rollback();
			}catch(Exception ex){
		ex.printStackTrace();
			throw new ApplicationException("Exception:delete rollback exception"+ex.getMessage());
			
			}
			throw new ApplicationException("Exception in updating Faculty");
		}finally{
				JDBCDataSource.closeConnection(conn);
			}
			log.debug("Model update End");
			
	}	
	/**
	 * to search list of faculty
	 * @param dto
	 * @param pageNo
	 * @param pageSize
	 * @return list
	 * @throws ApplicationException
	 */  
	public List search(FacultyDTO dto, int pageNo, int pageSize)throws ApplicationException {
		log.debug("Model search Started");
		
		StringBuffer sql = new StringBuffer("select * from st_faculty where 1=1");
		
		if (dto != null) {
		if (dto.getId() > 0) {
		sql.append(" AND id = " + dto.getId());
		}
		
		if (dto.getFirstName() != null && dto.getFirstName().length() > 0) {
		sql.append(" AND First_NAME like '" + dto.getFirstName() + "%'");
		}
		if (dto.getLastName() != null && dto.getLastName().length() > 0) {
		sql.append(" AND Last_Name like '" + dto.getLastName() + "%'");
		}
		if (dto.getGender() != null && dto.getGender().length() > 0) {
			sql.append(" AND Gender like '" + dto.getGender() + "%'");
		}
		if (dto.getQualification() != null && dto.getQualification().length() > 0) {
			sql.append(" AND Qualification like '" + dto.getQualification() + "%'");
			}
		if (dto.getEmail() != null && dto.getEmail().length() > 0) {
			sql.append(" AND Email like '" + dto.getEmail() + "%'");
			}
		if (dto.getMobileNo() != null && dto.getMobileNo().length() > 0) {
			sql.append(" AND Mobile_No like '" + dto.getMobileNo() + "%'");
			}
		if (dto.getCollegeId() != 0) {
			sql.append(" AND College_Id like '" + dto.getCollegeId() + "%'");
			}
		if (dto.getCollegeName() != null && dto.getCollegeName().length() > 0) {
			sql.append(" AND College_Name like '" + dto.getCollegeName() + "%'");
			}
		if (dto.getCourseId() != 0 ) {
			sql.append(" AND Course_ID like '" + dto.getCourseId() + "%'");
			}
		if (dto.getCourseName() != null && dto.getCourseName().length() > 0) {
			sql.append(" AND Course_Name like '" + dto.getCourseName() + "%'");
			}
		if (dto.getSubjectId() != 0 ) {
			sql.append(" AND Subject_Id like '" + dto.getSubjectId() + "%'");
			}
		if (dto.getSubjectName() != null && dto.getSubjectName().length() > 0) {
			sql.append(" AND Subject_Name like '" + dto.getSubjectName() + "%'");
			}
		}
		
		// if page size is greater than zero then apply pagination
		if (pageSize > 0) {
		// Calculate start record index
		pageNo = (pageNo - 1) * pageSize;
		
		sql.append(" Limit " + pageNo + ", " + pageSize);
		// sql.append(" limit " + pageNo + "," + pageSize);
		}
		
		ArrayList list = new ArrayList();
		Connection conn = null;
		try {
		conn = JDBCDataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql.toString());
		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
		dto = new FacultyDTO();
		dto.setId(rs.getLong(1));
		dto.setCollegeId(rs.getLong(8));
		dto.setCollegeName(rs.getString(9));
		dto.setCourseId(rs.getLong(10));
		dto.setCourseName(rs.getString(11));
		dto.setFirstName(rs.getString(2));
		dto.setLastName(rs.getString(3));
		dto.setQualification(rs.getString(5));
		dto.setEmail(rs.getString(6));
		dto.setMobileNo(rs.getString(7));
		dto.setDob(rs.getDate(7));
		dto.setGender(rs.getString(4));
		dto.setCreatedBy(rs.getString(14));
		dto.setModifiedBy(rs.getString(15));
		dto.setCreatedDatetime(rs.getTimestamp(16));
		dto.setModifiedDatetime(rs.getTimestamp(17));
		list.add(dto);
		}
		rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		log.error("Database Exception..", e);
		throw new ApplicationException("Exception : Exception in search Faculty");
		} finally {
		JDBCDataSource.closeConnection(conn);
		}
		
		log.debug("Model search End");
		return list;
		}
		
	 public List search(FacultyDTO dto) throws ApplicationException {
	 return search(dto, 0, 0);
       }
		
	 public List list() throws ApplicationException {
	 return list(0, 0);
	   }
	 
	 /**
		 * to show list of faculty
		 * @param pageNo
		 * @param pageSize
		 * @return list
		 * @throws ApplicationException
		 */
	 public List list(int pageNo, int pageSize) throws ApplicationException {
		log.debug("Model list Started");
		ArrayList list = new ArrayList();
		StringBuffer sql = new StringBuffer("select * from st_faculty");
		// if page size is greater than zero then apply pagination
		if (pageSize > 0) {
		// Calculate start record index
		pageNo = (pageNo - 1) * pageSize;
		sql.append(" limit " + pageNo + "," + pageSize);
		}
		
		Connection conn = null;
		
		try {
		conn = JDBCDataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql.toString());
		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
		FacultyDTO dto = new FacultyDTO();
		dto.setId(rs.getLong(1));
		dto.setFirstName(rs.getString(2));
		dto.setLastName(rs.getString(3));
		dto.setGender(rs.getString(4));
		dto.setQualification(rs.getString(5));
		dto.setEmail(rs.getString(6));
		dto.setMobileNo(rs.getString(7));
		dto.setCollegeId(rs.getLong(8));
		dto.setCollegeName(rs.getString(9));
		dto.setCourseId(rs.getLong(10));
		dto.setCourseName(rs.getString(11));
		dto.setSubjectId(rs.getLong(12));
		dto.setSubjectName(rs.getString(13));
		dto.setCreatedBy(rs.getString(14));
		dto.setModifiedBy(rs.getString(15));
		dto.setCreatedDatetime(rs.getTimestamp(16));
		dto.setModifiedDatetime(rs.getTimestamp(17));
		list.add(dto);

		}
		rs.close();
		} catch (Exception e) {
		e.printStackTrace();
		log.error("Database Exception..", e);
		throw new ApplicationException("Exception : Exception in getting list of users");
		} finally {
		JDBCDataSource.closeConnection(conn);
		}
		
		log.debug("Model list End");
		return list;
	 }
		

	 }
