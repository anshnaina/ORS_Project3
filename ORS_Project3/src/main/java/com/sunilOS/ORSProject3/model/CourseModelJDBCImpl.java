package com.sunilOS.ORSProject3.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.sunilOS.ORSProject3.dto.CourseDTO;
import com.sunilOS.ORSProject3.exception.ApplicationException;
import com.sunilOS.ORSProject3.exception.DatabaseException;
import com.sunilOS.ORSProject3.exception.DuplicateRecordException;
import com.sunilOS.ORSProject3.util.JDBCDataSource;


/**
 * JDBC Implements of Course model
 * @author Anshul
 *
 */
public class CourseModelJDBCImpl implements CourseModelInt {
	
	public static Logger log=Logger.getLogger("CourseModel.class");
	
	
	/**
	 * create id
	 * 
	 * @return pk
	 * @throws DatabaseException
	 */
	public Integer nextPK() throws DatabaseException{
		log.debug("Model nextPK Started");
		
		Connection conn=null;
		int pk=0;
		
		try{
			conn=JDBCDataSource.getConnection();
		    PreparedStatement stmt=conn.prepareStatement("Select max(id) from st_course");
			
		    ResultSet rs=stmt.executeQuery();
		    
		    while(rs.next()){
		    	pk=rs.getInt(1);
		    }
		    stmt.close();
		    rs.close();
		}catch(Exception e){
			log.error("Daatabase Exception..",e);
			throw new DatabaseException("Exception:Exception in getting pk");
			
		}finally{
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model pk end");
		return pk+1;
	}
	
	/**
	 * add new course
	 * 
	 * @param b
	 * @return pk
	 * @throws ApplicationException
	 * @throws DuplicateRecordException
	 */
	public long add(CourseDTO dto) throws ApplicationException{
		
		log.debug("Model add Started");
		Connection conn=null;
		int pk=0;
	
		try{
			conn=JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			pk=nextPK();
			PreparedStatement stmt=conn.prepareStatement("Insert into st_course values(?,?,?,?,?,?,?,?)");
		
		stmt.setInt(1, pk);
		stmt.setString(2,dto.getCourseName());
		stmt.setString(3,dto.getCourseDescription());
		stmt.setString(4,dto.getCourseDuration());
		stmt.setString(5,dto.getCreatedBy());
		stmt.setString(6, dto.getModifiedBy());
		stmt.setTimestamp(7,dto.getCreatedDatetime());
		stmt.setTimestamp(8,dto.getModifiedDatetime());
		
		stmt.executeUpdate();
		conn.commit();
		stmt.close();
		
	}catch(Exception e){
		log.error("Database Exception..",e);
		e.printStackTrace();
		try{
			conn.rollback();
		}catch(Exception ex){
			throw new ApplicationException("Exception: add rollback exception"+ex.getMessage());
		}
		throw new ApplicationException("Exception: Exception in add college");
		}finally{
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model add end");
		return pk;
	}
	
	/**
	 * delete course information in table
	 * 
	 * @param b
	 * @throws ApplicationException
	 */
	public void delete(CourseDTO dto) throws ApplicationException{
		log.debug("Model delete Started");
		Connection conn=null;
		
		try{
			conn=JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement stmt=conn.prepareStatement("delete from st_course where id=?");
			stmt.setLong(1, dto.getId());
			stmt.executeUpdate();
			conn.commit();
			stmt.close();
		}catch(Exception e){
			log.error("DatabaseException ",e);
			try{
				conn.rollback();
			}catch(Exception ex){
				throw new ApplicationException("Exception:Delete rollback exception "+ex.getMessage());
				
			}throw new ApplicationException("Exception in delete course");
		}finally{
			JDBCDataSource.closeConnection(conn);
		}log.debug("model delete end");
	}
	
	/**
	 * find course by name
	 * 
	 * @param courseName
	 * @return dto
	 * @throws ApplicationException
	 */
	public CourseDTO findByCourseName(String courseName) throws ApplicationException{
		log.debug("Model find by name started");
		
		StringBuffer sql=new StringBuffer("select * from st_course where course_name=?");
		
		CourseDTO dto=null;
		Connection conn=null;
		try{
			conn=JDBCDataSource.getConnection();
			PreparedStatement stmt=conn.prepareStatement(sql.toString());
			stmt.setString(1, courseName);
			ResultSet rs=stmt.executeQuery();
			
			while(rs.next()){
				dto=new CourseDTO();
				
				dto.setId(rs.getLong(1));
				dto.setCourseName(rs.getString(2));
				dto.setCourseDescription(rs.getString(3));
				dto.setCourseDuration(rs.getString(4));
				dto.setCreatedBy(rs.getString(5));
				dto.setModifiedBy(rs.getString(6));
				dto.setCreatedDatetime(rs.getTimestamp(7));
				dto.setModifiedDatetime(rs.getTimestamp(8));
				
				
			}rs.close();
		}catch(Exception e){
			log.error("Database Exception..",e);
			throw new ApplicationException("Exception:Exception in getting Course by Name");
			
			
		}finally{
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model  findByName Ended");
		return dto;
	}
	
	/**
	 * find information by pk
	 * 
	 * @param pk
	 * @return dto
	 * @throws ApplicationException
	 */
	
	public CourseDTO findByPk(long pk) throws ApplicationException{
		log.debug("Model find by pk started");
		
		StringBuffer sql=new StringBuffer("select * from st_course where id=?");
		CourseDTO dto=null;
		Connection conn=null;
		
		try{
			conn=JDBCDataSource.getConnection();
			PreparedStatement stmt=conn.prepareStatement(sql.toString());
			stmt.setLong(1,pk);
			
			ResultSet rs=stmt.executeQuery();
			
			while(rs.next()){
				dto=new CourseDTO();
				dto.setId(rs.getLong(1));
				dto.setCourseName(rs.getString(2));
				dto.setCourseDescription(rs.getString(3));
				dto.setCourseDuration(rs.getString(4));
				dto.setCreatedBy(rs.getString(5));
				dto.setModifiedBy(rs.getString(6));
				dto.setCreatedDatetime(rs.getTimestamp(7));
				dto.setModifiedDatetime(rs.getTimestamp(8));
				
				
			}rs.close();
		}catch(Exception e){
			log.error("Database Exception..",e);
			throw new ApplicationException("Exception:Exception in getting Course by pk");
			
			
		}finally{
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model  findByPK End");
		return dto;
		
		
	}
	
	/**
	 * update course information
	 * @param b
	 * @throws ApplicationException
	 */
public void update(CourseDTO dto) throws ApplicationException{
		
		log.debug("Model update started");
		
		Connection conn=null;
			
		try{
			conn=JDBCDataSource.getConnection();
			
			
			PreparedStatement stmt=conn.prepareStatement("Update st_course set course_name=?,course_description=?,course_duration=?,created_by=?,modified_by=?,created_datetime=?,modified_datetime=? where id=?");
			conn.setAutoCommit(false);
			
			stmt.setString(1,dto.getCourseName());
			
			stmt.setString(2,dto.getCourseDescription());
			stmt.setString(3,dto.getCourseDuration());
			stmt.setString(4,dto.getCreatedBy());
			stmt.setString(5, dto.getModifiedBy());
			stmt.setTimestamp(6,dto.getCreatedDatetime());
			stmt.setTimestamp(7,dto.getModifiedDatetime());

			stmt.setLong(8,dto.getId());
			
			stmt.executeUpdate();
			conn.commit();
			stmt.close();
		
			
		}catch(Exception e){
			log.error("Database Exception..",e);
		
			/*try{
				conn.rollback();
			}catch(Exception ex){
		ex.printStackTrace();
			throw new ApplicationException("Exception:delete rollback exception"+ex.getMessage());
			
			}
			throw new ApplicationException("Exception in updating college");
		}finally{
				JDBCDataSource.closeConnection(conn);
			}*/
		}
			JDBCDataSource.closeConnection(conn);
			log.debug("Model update End");
			
		
		}

/**
 * search list of course detail
 * 
 * @param cbean1
 * @param pageNo
 * @param pageSize
 * @return list
 * @throws ApplicationException
 */
		public List search(CourseDTO dto, int pageNo, int pageSize)throws ApplicationException {
		
			log.debug("Model search Started");
		
			StringBuffer sql = new StringBuffer("SELECT * FROM ST_COURSE WHERE 1=1");
		/*if (bean != null) {
		if (bean.getId() > 0) {
		sql.append(" AND id = " + bean.getId());
		}*/
		if(dto!=null){
			if(dto.getId()>0){
		sql.append(" AND id= "+dto.getId());
				
		}
		
		if (dto.getCourseName() != null && dto.getCourseName().length() > 0) {
		sql.append(" AND COURSE_NAME like '" + dto.getCourseName() + "%'");
		}
		if (dto.getCourseDescription() != null && dto.getCourseDescription().length() > 0) {
		sql.append(" AND COURSE_DESCRIPTION like '" + dto.getCourseDescription() + "%'");
		}
		if (dto.getCourseDuration() != null && dto.getCourseDuration().length() > 0) {
//			sql.append(" AND Duration like '" + bean.getDuration() + "%'");
			sql.append(" AND COURSE_DURATION like '"+dto.getCourseDuration()+ "%'");
		}
		}
		
		// if page size is greater than zero then apply pagination
		if (pageSize > 0) {
		// Calculate start record index
		pageNo = (pageNo - 1) * pageSize;
		
		
		sql.append(" Limit " + pageNo + "," + pageSize);
		// sql.append(" limit " + pageNo + "," + pageSize);
		}
		
		ArrayList<CourseDTO> list = new ArrayList<CourseDTO>();
		Connection conn = null;
		try {
		conn = JDBCDataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql.toString());
		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
		dto = new CourseDTO();
		dto.setId(rs.getLong(1));
		dto.setCourseName(rs.getString(2));
		dto.setCourseDescription(rs.getString(3));
		dto.setCourseDuration(rs.getString(4));
		dto.setCreatedBy(rs.getString(5));
		dto.setModifiedBy(rs.getString(6));
		dto.setCreatedDatetime(rs.getTimestamp(7));
		dto.setModifiedDatetime(rs.getTimestamp(8));
		list.add(dto);
		}
		rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		log.error("Database Exception..", e);
		throw new ApplicationException("Exception : Exception in search course");
		} finally {
		JDBCDataSource.closeConnection(conn);
		}
		
		log.debug("Model search End");
		return list;
		}
		
		public List search(CourseDTO dto) throws ApplicationException {
		return search(dto, 0, 0);
		}
		
		public List list() throws ApplicationException {
		return list(0, 0);
		}
		
		/**
		 * to show course list
		 * 
		 * @param pageNo
		 * @param pageSize
		 * @return list
		 * @throws ApplicationException
		 */
		public List list(int pageNo, int pageSize) throws ApplicationException {
		log.debug("Model list Started");
		ArrayList list = new ArrayList();
		StringBuffer sql = new StringBuffer("select * from st_course");
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
		CourseDTO dto = new CourseDTO();
		dto.setId(rs.getLong(1));
		dto.setCourseName(rs.getString(2));
		dto.setCourseDescription(rs.getString(3));
		dto.setCourseDuration(rs.getString(4));
		dto.setCreatedBy(rs.getString(5));
		dto.setModifiedBy(rs.getString(6));
		dto.setCreatedDatetime(rs.getTimestamp(7));
		dto.setModifiedDatetime(rs.getTimestamp(8));
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