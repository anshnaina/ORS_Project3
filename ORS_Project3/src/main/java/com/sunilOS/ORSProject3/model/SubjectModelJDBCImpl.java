package com.sunilOS.ORSProject3.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.sunilOS.ORSProject3.dto.CourseDTO;
import com.sunilOS.ORSProject3.dto.SubjectDTO;
import com.sunilOS.ORSProject3.exception.ApplicationException;
import com.sunilOS.ORSProject3.exception.DatabaseException;
import com.sunilOS.ORSProject3.exception.DuplicateRecordException;
import com.sunilOS.ORSProject3.util.JDBCDataSource;



/**
 * JDBC Implement of subject
 * @author Anshul
 *
 */

	public class SubjectModelJDBCImpl implements SubjectModelInt {
	
	private static Logger log=Logger.getLogger(SubjectModelJDBCImpl.class);
	
	/**
	 * create id
	 * @return pk
	 * @throws DatabaseException
	 */
	public Integer nextPK() throws DatabaseException{
		log.debug("Model nextPK Started");
		Connection conn=null;
		int pk=0;
		
		try{
			conn=JDBCDataSource.getConnection();
		
		PreparedStatement stmt=conn.prepareStatement("select max(id) from st_subject");
		
		ResultSet rs=stmt.executeQuery();
		
		while(rs.next()){
			pk=rs.getInt(1);
		}
		rs.close();
	  }catch(Exception e){
		  log.error("Database Exception",e);
		  throw new DatabaseException("Exception:Exception is getting PK");
		  
	  }finally{
		  JDBCDataSource.closeConnection(conn);
	  }
		
		log.debug("Model nextPK End");
		return pk+1;
	}
	
	
	/**
	 * add subject
	 * @param bean
	 * @return pk
	 * @throws DuplicateRecordException
	 * @throws ApplicationException
	 */
	public long add(SubjectDTO dto) throws ApplicationException, DuplicateRecordException{
		
		log.debug("Model add Started");
		Connection conn=null;
		int pk=0;
		CourseModelJDBCImpl cmodel =new CourseModelJDBCImpl();
		CourseDTO cDTO = cmodel.findByPk(dto.getCourseId());
		dto.setCourseName(cDTO.getCourseName());
		
		SubjectDTO duplicateSubjectName=findBySubjectName(dto.getSubjectName());
		
		if(duplicateSubjectName!=null){
			throw new DuplicateRecordException("Subject Name Already Exists");
			
		}
		try{
			conn=JDBCDataSource.getConnection();
			
			pk=nextPK();
			conn.setAutoCommit(false);
			PreparedStatement stmt=conn.prepareStatement("Insert into st_subject values(?,?,?,?,?,?,?,?,?)");
			
			
			stmt.setInt(1, pk);
			stmt.setString(2,dto.getSubjectName());
			stmt.setString(3,dto.getDescription());
			stmt.setString(4,dto.getCourseName());
			stmt.setLong(5,dto.getCourseId());
			stmt.setString(6,dto.getCreatedBy());
			stmt.setString(7,dto.getModifiedBy());
			stmt.setTimestamp(8,dto.getCreatedDatetime());
			stmt.setTimestamp(9,dto.getModifiedDatetime());
			
			stmt.executeUpdate();
			conn.commit();
			stmt.close();
		
			
		}catch(Exception e){
			log.error("Database Exception..",e);
			e.printStackTrace();
			try{
				conn.rollback();
			}catch(Exception ex){
			ex.printStackTrace();
			throw new ApplicationException("Exception:add rollback exception"+ex.getMessage());
			} 
			 throw new ApplicationException("Exception : Exception in add Subject");
			}finally{
				JDBCDataSource.closeConnection(conn);
			}
			log.debug("Model add End");
			return pk;
		
		
	}

	/**
	 * delete subject
	 * @param bean
	 * @throws ApplicationException
	 */
	public void delete(SubjectDTO dto) throws ApplicationException{
	log.debug("Model Delete Started");
	Connection conn=null;
	try{
		conn=JDBCDataSource.getConnection();
		conn.setAutoCommit(false);
		PreparedStatement stmt=conn.prepareStatement("delete from st_subject where id=?");
		stmt.setLong(1,dto.getId());
		stmt.executeUpdate();
		conn.commit();
		stmt.close();
		
	}catch(Exception e){
		log.error("Database Exception..",e);
		try{
			conn.rollback();
		}catch(Exception ex){
			throw new ApplicationException("Exception:Delete RollBack Exception"+ex.getMessage());
		
		} throw new ApplicationException("Exception:Exception in delete Subject");	
		}finally{ JDBCDataSource.closeConnection(conn);
	
		}log.debug("Model delete end");
	
	}

	/**
	 * find subject by name
	 * @param subjectname
	 * @return bean
	 * @throws ApplicationException
	 */
	public SubjectDTO findBySubjectName(String subjectName) throws ApplicationException{
	log.debug("Model FindByName Started ");
	StringBuffer sql=new StringBuffer("Select * from st_subject where subject_name=?");
	
	SubjectDTO dto=null;
	Connection conn=null;
	try{
		conn=JDBCDataSource.getConnection();
		PreparedStatement stmt=conn.prepareStatement(sql.toString());
		stmt.setString(1,subjectName);
		
		ResultSet rs=stmt.executeQuery();
		
		while(rs.next()){
			dto=new SubjectDTO();
			dto.setId(rs.getLong(1));
			dto.setSubjectName(rs.getString(2));
			dto.setDescription(rs.getString(3));
			dto.setCourseName(rs.getString(4));
			dto.setCourseId(rs.getLong(5));
			dto.setCreatedBy(rs.getString(6));
			dto.setModifiedBy(rs.getString(7));
			dto.setCreatedDatetime(rs.getTimestamp(8));
			dto.setModifiedDatetime(rs.getTimestamp(9));
		}rs.close();
		
		
	}catch(Exception e){
		log.error("Database Exception..",e);
		throw new ApplicationException("Exception:Exception in getting Subject by Name");
		
		
	}finally{
		JDBCDataSource.closeConnection(conn);
	}
	log.debug("Model  findByName Ended");
	return dto;
}

/**
 * find subject by pk
 * @param pk
 * @return bean
 * @throws ApplicationException
 */

public SubjectDTO findByPk(long pk) throws ApplicationException{
	log.debug("Model findByPK Started");
	
	StringBuffer sql=new StringBuffer("select * from st_subject where id=?");
	
	SubjectDTO dto=null;
	Connection conn=null;
	
	try{
		conn=JDBCDataSource.getConnection();
		PreparedStatement stmt=conn.prepareStatement(sql.toString());
		stmt.setLong(1,pk);
		
		ResultSet rs=stmt.executeQuery();
		
		while(rs.next()){
			dto=new SubjectDTO();
			dto.setId(rs.getLong(1));
			dto.setSubjectName(rs.getString(2));
			dto.setDescription(rs.getString(3));
			dto.setCourseName(rs.getString(4));
			dto.setCourseId(rs.getLong(5));
			dto.setCreatedBy(rs.getString(6));
			dto.setModifiedBy(rs.getString(7));
			dto.setCreatedDatetime(rs.getTimestamp(8));
			dto.setModifiedDatetime(rs.getTimestamp(9));
		}rs.close();

	
	}catch(Exception e){
		log.error("Database Exception..",e);
		throw new ApplicationException("Exception:Exception in getting Subject by pk");
		
		
	}finally{
		JDBCDataSource.closeConnection(conn);
	}
	log.debug("Model  findByPK End");
	return dto;
		
  }
/**
 * update subject
 * @param bean
 * @throws ApplicationException
 * @throws DuplicateRecordException
 */
	public void update(SubjectDTO dto) throws DuplicateRecordException, ApplicationException{
	
	log.debug("Model update started");
	
	Connection conn=null;
	CourseModelJDBCImpl cmodel=new CourseModelJDBCImpl();
	CourseDTO cDTO = cmodel.findByPk(dto.getCourseId());
	dto.setCourseName(cDTO.getCourseName());
	
	SubjectDTO dtoExist=findBySubjectName(dto.getSubjectName());
	
	if(dtoExist!=null&&dtoExist.getId()!=dto.getId()){
		throw new DuplicateRecordException("Subject is already exist");
		
	}
	try{
		conn=JDBCDataSource.getConnection();
		conn.setAutoCommit(false);
		
		PreparedStatement stmt=conn.prepareStatement("Update st_subject set subject_name=?,description=?,Course_name=?,course_id=?,subject_id=?,created_by=?,modified_by=?,created_datetime=?,modified_datetime=? where id=?");
		
		
		stmt.setString(1,dto.getSubjectName());
		stmt.setString(2,dto.getDescription());
		stmt.setString(3,dto.getCourseName());
		stmt.setLong(4,dto.getCourseId());
		stmt.setString(5,dto.getCreatedBy());
		stmt.setString(6,dto.getModifiedBy());
		stmt.setTimestamp(7,dto.getCreatedDatetime());
		stmt.setTimestamp(8,dto.getModifiedDatetime());
		stmt.setLong(9, dto.getId());
		
		
		stmt.executeUpdate();
		conn.commit();
		stmt.close();
	
		
	}catch(Exception e){
		log.error("Database Exception..",e);
	
		try{
			conn.rollback();
		}catch(Exception ex){
	ex.printStackTrace();
		throw new ApplicationException("Exception:delete rollback exception"+ex.getMessage());
		
		}
		throw new ApplicationException("Exception in updating Subject");
	}finally{
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model update End");
	
	}

/**
 * search subject
 * @param bean
 * @param pageNo
 * @param pageSize
 * @return list
 * @throws ApplicationException
 */
	public List search(SubjectDTO dto, int pageNo, int pageSize)throws ApplicationException {
	log.debug("Model search Started");
	StringBuffer sql = new StringBuffer("SELECT * FROM ST_SUBJECT WHERE 1=1");
	
	if (dto != null) {
	if (dto.getId() > 0) {
	sql.append(" AND id = " + dto.getId());
	}
	if (dto.getSubjectName() != null && dto.getSubjectName().length() > 0) {
	sql.append(" AND SUBJECT_NAME like '" + dto.getSubjectName() + "%'");
	}
	if (dto.getDescription() != null && dto.getDescription().length() > 0) {
	sql.append(" AND Description like '" + dto.getDescription() + "%'");
	}
	if (dto.getCourseName() != null && dto.getCourseName().length() > 0) {
	sql.append(" AND COURSE_NAME like '" + dto.getCourseName() + "%'");
	}
	if (dto.getCourseId() != 0 && dto.getCourseId() > 0) {
		sql.append(" AND COURSE_ID like '" + dto.getCourseId() + "%'");
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
	dto = new SubjectDTO();
	dto.setId(rs.getLong(1));
	dto.setSubjectName(rs.getString(2));
	dto.setDescription(rs.getString(3));
	dto.setCourseName(rs.getString(4));
	dto.setCourseId(rs.getLong(5));
	dto.setCreatedBy(rs.getString(6));
	dto.setModifiedBy(rs.getString(7));
	dto.setCreatedDatetime(rs.getTimestamp(8));
	dto.setModifiedDatetime(rs.getTimestamp(9));
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
	
	public List search(SubjectDTO dto) throws ApplicationException {
	return search(dto, 0, 0);
	}
	
	public List list() throws ApplicationException {
	return list(0, 0);
	}
	/**
	 * list of subject
	 * @param pageNo
	 * @param pageSize
	 * @return list
	 * @throws ApplicationException
	 */
	public List list(int pageNo, int pageSize) throws ApplicationException {
	log.debug("Model list Started");
	ArrayList list = new ArrayList();
	StringBuffer sql = new StringBuffer("select * from st_subject");
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
	SubjectDTO dto = new SubjectDTO();
	dto.setId(rs.getLong(1));
	dto.setSubjectName(rs.getString(2));
	dto.setDescription(rs.getString(3));
	dto.setCourseName(rs.getString(4));
	dto.setCourseId(rs.getLong(5));
	dto.setCreatedBy(rs.getString(6));
	dto.setModifiedBy(rs.getString(7));
	dto.setCreatedDatetime(rs.getTimestamp(8));
	dto.setModifiedDatetime(rs.getTimestamp(9));
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
