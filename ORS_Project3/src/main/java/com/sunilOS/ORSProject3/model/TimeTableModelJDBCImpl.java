package com.sunilOS.ORSProject3.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.sunilOS.ORSProject3.dto.CourseDTO;
import com.sunilOS.ORSProject3.dto.SubjectDTO;
import com.sunilOS.ORSProject3.dto.TimeTableDTO;
import com.sunilOS.ORSProject3.exception.ApplicationException;
import com.sunilOS.ORSProject3.exception.DatabaseException;
import com.sunilOS.ORSProject3.exception.DuplicateRecordException;
import com.sunilOS.ORSProject3.util.JDBCDataSource;


/**
 * JDBC Implementation of timetable
 * @author Anshul
 *
 */
public class TimeTableModelJDBCImpl implements TimeTableModelInt {

	private static Logger log=Logger.getLogger(TimeTableModelJDBCImpl.class);
	
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
		
		PreparedStatement stmt=conn.prepareStatement("Select max(id) from st_timetable");
		
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
	 * add timetable
	 * 
	 * @param dto
	 * @return pk
	 * @throws ApplicationException
	 */
	public long add(TimeTableDTO dto) throws ApplicationException
	{
		
		log.debug("Model add Started");
		Connection conn=null;
		int pk=0;
		
		java.util.Date d = dto.getExamDate();
		long l = d.getTime();
		java.sql.Date date = new java.sql.Date(l);

		
		System.out.println("IN add method date set");
		// get course Name and Subject Name by id
		CourseModelJDBCImpl Cmodel = new CourseModelJDBCImpl();
		CourseDTO cDTO = null;
		cDTO = Cmodel.findByPk(dto.getCourseId());
		dto.setCourseName(cDTO.getCourseName());
		
		System.out.println("in add method course name and id set");

		SubjectModelJDBCImpl sModel = new SubjectModelJDBCImpl();
		SubjectDTO Sbean = sModel.findByPk(dto.getSubjectId());
		dto.setSubjectName(Sbean.getSubjectName());
		System.out.println("in add method subject name and id set");
		
		try{
			conn=JDBCDataSource.getConnection();
			System.out.println("in add method get connection");
			pk=nextPK();
			conn.setAutoCommit(false);
			PreparedStatement stmt=conn.prepareStatement("INSERT INTO ST_TIMETABLE VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)");
			
			System.out.println("in add statement created");
			stmt.setLong(1, pk);
			stmt.setLong(2,dto.getSubjectId());
			stmt.setString(3,dto.getSubjectName());
			stmt.setLong(4,dto.getCourseId());
			stmt.setString(5,dto.getCourseName());
			stmt.setString(6,dto.getSemester());
			stmt.setDate(7,date);
			stmt.setString(8,dto.getExamTime());
			stmt.setString(9,dto.getDescription());
			stmt.setString(10,dto.getCreatedBy());
			stmt.setString(11,dto.getModifiedBy());
			stmt.setTimestamp(12,dto.getCreatedDatetime());
			stmt.setTimestamp(13,dto.getModifiedDatetime());
			System.out.println("in add all parameter set");
			stmt.executeUpdate();
			System.out.println("executeupdate query done");
			conn.commit();
			stmt.close();
		
			
		}catch(Exception e){
			log.error("Database Exception..",e);
			e.printStackTrace();
			try{
				conn.rollback();
			}catch(Exception ex){
			ex.printStackTrace();
			System.out.println("connection roll back in add method");
			throw new ApplicationException("Exception:add rollback exception"+ex.getMessage());
			} 
			 throw new ApplicationException("Exception : Exception in add TimeTable");
			}finally{
				JDBCDataSource.closeConnection(conn);
			}
			log.debug("Model add End");
			return pk;
		
		
	}
	/**
	 * delete timetable
	 * 
	 * @param dto
	 * @throws ApplicationException
	 */
	public void delete(TimeTableDTO dto) throws ApplicationException{
		log.debug("Model Delete Started");
		Connection conn=null;
		try{
			conn=JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement stmt=conn.prepareStatement("delete from st_timetable where id=?");
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
			
			} throw new ApplicationException("Exception:Exception in delete TimeTable");	
			}finally{ JDBCDataSource.closeConnection(conn);
		
			}log.debug("Model delete end");
		
		}
	
	public TimeTableDTO findByName(String subjectName) throws ApplicationException{
		log.debug("Model FindByName Started ");
		StringBuffer sql=new StringBuffer("Select * from st_timetable where subject_name=?");
		
		TimeTableDTO dto=null;
		Connection conn=null;
		try{
			conn=JDBCDataSource.getConnection();
			PreparedStatement stmt=conn.prepareStatement(sql.toString());
			stmt.setString(1,subjectName);
			
			ResultSet rs=stmt.executeQuery();
			
			while(rs.next()){
				dto=new TimeTableDTO();
				dto.setId(rs.getLong(1));
				dto.setSubjectId(rs.getLong(2));
				dto.setSubjectName(rs.getString(3));
				dto.setCourseId(rs.getLong(4));
				dto.setCourseName(rs.getString(5));
				dto.setSemester(rs.getString(6));
				dto.setExamDate(rs.getDate(7));
				dto.setExamTime(rs.getString(8));
				dto.setDescription(rs.getString(9));
				dto.setCreatedBy(rs.getString(10));
				dto.setModifiedBy(rs.getString(11));
				dto.setCreatedDatetime(rs.getTimestamp(12));
				dto.setModifiedDatetime(rs.getTimestamp(13));
			}rs.close();
			
			
		}catch(Exception e){
			log.error("Database Exception..",e);
			throw new ApplicationException("Exception:Exception in getting TimeTable by SubName");
			
			
		}finally{
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model  findByName Ended");
		return dto;
	}
	
	/**
	 * find time table by pk
	 * 
	 * @param pk
	 * @return dto
	 * @throws ApplicationException
	 */
	public TimeTableDTO findByPk(long pk) throws ApplicationException{
		log.debug("Model findByPK Started");
		
		StringBuffer sql=new StringBuffer("select * from st_timetable where id=?");
		
		TimeTableDTO dto=null;
		Connection conn=null;
		
		try{
			conn=JDBCDataSource.getConnection();
			PreparedStatement stmt=conn.prepareStatement(sql.toString());
			stmt.setLong(1,pk);
			
			ResultSet rs=stmt.executeQuery();
			
			while(rs.next()){
				dto=new TimeTableDTO();
				dto.setId(rs.getLong(1));
				dto.setSubjectId(rs.getLong(2));
				dto.setSubjectName(rs.getString(3));
				dto.setCourseId(rs.getLong(4));
				dto.setCourseName(rs.getString(5));
				dto.setSemester(rs.getString(6));
				dto.setExamDate(rs.getDate(7));
				dto.setExamTime(rs.getString(8));
				dto.setDescription(rs.getString(9));
				dto.setCreatedBy(rs.getString(10));
				dto.setModifiedBy(rs.getString(11));
				dto.setCreatedDatetime(rs.getTimestamp(12));
				dto.setModifiedDatetime(rs.getTimestamp(13));
			}rs.close();
	
		
		}catch(Exception e){
			log.error("Database Exception..",e);
			throw new ApplicationException("Exception:Exception in getting TimeTable by pk");
			
			
		}finally{
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model  findByPK End");
		return dto;
		
				
	}
	/**
	 * update timetable
	 * 
	 * @param bean
	 * @throws ApplicationException
	 * @throws DatabaseException
	 */
	public void update(TimeTableDTO dto) throws ApplicationException{
		System.out.println("update 1");
		log.debug("Model update started");
		
		Connection conn=null;
		java.util.Date d = dto.getExamDate();
		long l = d.getTime();
		java.sql.Date date = new java.sql.Date(l);
		
		try{
			System.out.println("update 4");
			conn=JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			System.out.println("update 5");
			PreparedStatement stmt=conn.prepareStatement("Update st_timetable set Subject_id=?,Subject_name=?,Course_Id=?,Course_Name=?,Semester=?,Exam_Date=?,Exam_Time=?,Description=?,created_by=?,modified_by=?,created_datetime=?,modified_datetime=? where id=?");
			System.out.println("update 6");
			
			stmt.setLong(1,dto.getSubjectId());
			stmt.setString(2,dto.getSubjectName());
			stmt.setLong(3,dto.getCourseId());
			stmt.setString(4,dto.getCourseName());
			stmt.setString(5,dto.getSemester());
			stmt.setDate(6,date);
			stmt.setString(7,dto.getExamTime());
			stmt.setString(8,dto.getDescription());
			stmt.setString(9,dto.getCreatedBy());
			stmt.setString(10,dto.getModifiedBy());
			stmt.setTimestamp(11,dto.getCreatedDatetime());
			stmt.setTimestamp(12,dto.getModifiedDatetime());
			stmt.setLong(13, dto.getId());
			System.out.println("update 7");
			stmt.executeUpdate();
			System.out.println("update 8");
			conn.commit();
		}catch(Exception e){
			e.printStackTrace();
			log.error("Database Exception..",e);
		
			try{
				conn.rollback();
			}catch(Exception ex){
		ex.printStackTrace();
		System.out.println("update 9");
			throw new ApplicationException("Exception:delete rollback exception"+ex.getMessage());
			
			}
			throw new ApplicationException("Exception in updating TimeTable");
		}finally{
			System.out.println("update 10");
				JDBCDataSource.closeConnection(conn);
			}
			log.debug("Model update End");
		
		}
	/**
	 * search time table
	 * 
	 * @param bean
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @throws ApplicationException
	 */
	public List search(TimeTableDTO dto, int pageNo, int pageSize)throws ApplicationException {
		log.debug("Model search Started");
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_TimeTable WHERE 1=1");
		
		
		
		if (dto != null) {
		if (dto.getId() > 0) {
		sql.append(" AND id = " + dto.getId());
		}
		if (dto.getSubjectId() != 0 && dto.getSubjectId() > 0) {
		sql.append(" AND SUBJECT_ID like '" + dto.getSubjectId() + "%'");
		}
		if (dto.getSubjectName() != null && dto.getSubjectName().length() > 0) {
		sql.append(" AND SUBJECT_NAME like '" + dto.getSubjectName() + "%'");
		}
		if (dto.getCourseName() != null && dto.getCourseName().length() > 0) {
		sql.append(" AND Course_Name like '" + dto.getCourseName() + "%'");
		}
		if (dto.getCourseId() != 0 && dto.getCourseId() > 0) {
			System.out.println("zzzzzzzzzzzzzzzzzzzzzzzzzzz    "+dto.getCourseId());
			sql.append(" AND COURSE_ID = " + dto.getCourseId() );
			
			}
		if (dto.getSemester() != null && dto.getSemester().length() > 0) {
			sql.append(" AND COURSE_NAME like '" + dto.getSemester() + "%'");
			}
		
		if ((dto.getExamDate() != null) && (dto.getExamDate().getDate() > 0)) {
			Date date = new Date(dto.getExamDate().getTime());
             System.out.println(">>>>"+date);
			sql.append(" AND EXAM_DATE = '" + date+"'");
		}
		if (dto.getExamTime() != null && dto.getExamTime().length() > 0) {
			sql.append(" AND Exam_Time like '" + dto.getExamTime() + "%'");
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
		dto = new TimeTableDTO();
		dto.setId(rs.getLong(1));
		dto.setSubjectId(rs.getLong(2));
		dto.setSubjectName(rs.getString(3));
		dto.setCourseId(rs.getLong(4));
		dto.setCourseName(rs.getString(5));
		dto.setSemester(rs.getString(6));
		dto.setExamDate(rs.getDate(7));
		dto.setExamTime(rs.getString(8));
		dto.setDescription(rs.getString(9));
		dto.setCreatedBy(rs.getString(10));
		dto.setModifiedBy(rs.getString(11));
		dto.setCreatedDatetime(rs.getTimestamp(12));
		dto.setModifiedDatetime(rs.getTimestamp(13));
		list.add(dto);
		}
		rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		log.error("Database Exception..", e);
		throw new ApplicationException("Exception : Exception in search timetable");
		} finally {
		JDBCDataSource.closeConnection(conn);
		}
		
		log.debug("Model search End");
		return list;
		}

		public List search(TimeTableDTO dto) throws ApplicationException {
		return search(dto, 0, 0);
		}
		
		public List list() throws ApplicationException {
		return list(0, 0);
		}
		
		/**
		 * list of time table
		 * 
		 * @param pageNo
		 * @param pageSize
		 * @return
		 * @throws ApplicationException
		 */
		public List list(int pageNo, int pageSize) throws ApplicationException {
		log.debug("Model list Started");
		ArrayList list = new ArrayList();
		StringBuffer sql = new StringBuffer("select * from St_TimeTable");
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
		TimeTableDTO dto = new TimeTableDTO();
		dto = new TimeTableDTO();
		dto.setId(rs.getLong(1));
		dto.setSubjectId(rs.getLong(2));
		dto.setSubjectName(rs.getString(3));
		dto.setCourseId(rs.getLong(4));
		dto.setCourseName(rs.getString(5));
		dto.setSemester(rs.getString(6));
		dto.setExamDate(rs.getDate(7));
		dto.setExamTime(rs.getString(8));
		dto.setDescription(rs.getString(9));
		dto.setCreatedBy(rs.getString(10));
		dto.setModifiedBy(rs.getString(11));
		dto.setCreatedDatetime(rs.getTimestamp(12));
		dto.setModifiedDatetime(rs.getTimestamp(13));
		list.add(dto);
		}
		rs.close();
		} catch (Exception e) {
		e.printStackTrace();
		log.error("Database Exception..", e);
		throw new ApplicationException("Exception : Exception in getting list ");
		} finally {
		JDBCDataSource.closeConnection(conn);
		}
		
		log.debug("Model list End");
		return list;
		
		}
		/**
		 * @param CourseId
		 * @param ExamDate
		 * @return tbean
		 * @throws ApplicationException
		 */
		public TimeTableDTO checkByCourseName(long CourseId, java.util.Date ExamDate) throws ApplicationException {
			PreparedStatement ps = null;
			ResultSet rs = null;
			TimeTableDTO tDTO = null;
			Date Exdate = new Date(ExamDate.getTime());

			StringBuffer sql = new StringBuffer("SELECT * FROM ST_TIMETABLE WHERE COURSE_ID=? " + "AND EXAM_DATE=?");

			try {
				Connection con = JDBCDataSource.getConnection();
				ps = con.prepareStatement(sql.toString());
				ps.setLong(1, CourseId);
				ps.setDate(2, Exdate);
				rs = ps.executeQuery();

				while (rs.next()) {
					tDTO = new TimeTableDTO();
					tDTO.setId(rs.getLong(1));
					tDTO.setSubjectId(rs.getLong(2));
					tDTO.setSubjectName(rs.getString(3));
					tDTO.setCourseId(rs.getLong(4));
					tDTO.setCourseName(rs.getString(5));
					tDTO.setSemester(rs.getString(6));
					tDTO.setExamDate(rs.getDate(7));
					tDTO.setExamTime(rs.getString(8));
					tDTO.setDescription(rs.getString(9));
				}
			} catch (Exception e) {
				throw new ApplicationException("Exception in timeTable model checkByCourseName..." + e.getMessage());
			}
			return tDTO;
		}

		/**
		 * @param CourseId
		 * @param SubjectId
		 * @param ExamDAte
		 * @return tbean
		 * @throws ApplicationException
		 */
		public TimeTableDTO checkBySubjectName(long CourseId, long SubjectId, java.util.Date ExamDAte)throws ApplicationException {

			PreparedStatement ps = null;
			ResultSet rs = null;
			TimeTableDTO tDTO = null;
			Date ExDate = new Date(ExamDAte.getTime());
			StringBuffer sql = new StringBuffer(
					"SELECT * FROM st_timetable WHERE COURSE_ID=? AND SUB_ID=? AND" + " EXAM_DATE=?");

			try {
				Connection con = JDBCDataSource.getConnection();
				ps = con.prepareStatement(sql.toString());
				ps.setLong(1, CourseId);
				ps.setLong(2, SubjectId);
				ps.setDate(3, ExDate);
				rs = ps.executeQuery();

				while (rs.next()) {
					tDTO = new TimeTableDTO();
					tDTO.setId(rs.getLong(1));
					tDTO.setSubjectId(rs.getLong(2));
					tDTO.setSubjectName(rs.getString(3));
					tDTO.setCourseId(rs.getLong(4));
					tDTO.setCourseName(rs.getString(5));
					tDTO.setSemester(rs.getString(6));
					tDTO.setExamDate(rs.getDate(7));
					tDTO.setExamTime(rs.getString(8));
					tDTO.setDescription(rs.getString(9));
				}
			} catch (Exception e) {
				throw new ApplicationException("Exception in timeTable model checkBySubjectName..." + e.getMessage());
			}
			return tDTO;
		}

		/**
		 * @param CourseId
		 * @param SubjectId
		 * @param semester
		 * @param ExamDAte
		 * 
		 * 
		 */
		public TimeTableDTO checkBySemester(long CourseId, long SubjectId, String semester, java.util.Date ExamDAte)
				throws ApplicationException {
			PreparedStatement ps = null;
			ResultSet rs = null;
			TimeTableDTO tDTO = null;
			Date ExDate = new Date(ExamDAte.getTime());

			StringBuffer sql = new StringBuffer(
					"SELECT * FROM st_timetable WHERE COURSE_ID=? AND SUB_ID=? AND" + " SEMESTER=? AND EXAM_DATE=?");

			try {
				Connection con = JDBCDataSource.getConnection();
				ps = con.prepareStatement(sql.toString());
				ps.setLong(1, CourseId);
				ps.setLong(2, SubjectId);
				ps.setString(3, semester);
				ps.setDate(4, ExDate);
				rs = ps.executeQuery();

				while (rs.next()) {
					tDTO = new TimeTableDTO();
					tDTO.setId(rs.getLong(1));
					tDTO.setSubjectId(rs.getLong(2));
					tDTO.setSubjectName(rs.getString(3));
					tDTO.setCourseId(rs.getLong(4));
					tDTO.setCourseName(rs.getString(5));
					tDTO.setSemester(rs.getString(6));
					tDTO.setExamDate(rs.getDate(7));
					tDTO.setExamTime(rs.getString(8));
					tDTO.setDescription(rs.getString(9));
				}
			} catch (Exception e) {
				throw new ApplicationException("Exception in timeTable model checkBySubjectName..." + e.getMessage());
			}
			return tDTO;
		}

		/**
		 * @param ExamTime
		 * @param CourseId
		 * @param SubjectId
		 * @param semester
		 * @param ExamDAte
		 * @return tDTO
		 * @throws ApplicationException
		 */
		public static TimeTableDTO checkByExamTime(long CourseId, long SubjectId, String semester, java.util.Date ExamDAte,
				String ExamTime) throws ApplicationException {
			
			PreparedStatement ps = null;
			ResultSet rs = null;
			TimeTableDTO tDTO = null;
			Date ExDate = new Date(ExamDAte.getTime());
			StringBuffer sql = new StringBuffer("SELECT * FROM st_timetable WHERE COURSE_ID=? AND SUB_ID=? AND"
					+ " SEMESTER=? AND EXAM_DATE=? AND EXAM_TIME=?");

			try {
				Connection con = JDBCDataSource.getConnection();
				ps = con.prepareStatement(sql.toString());
				ps.setLong(1, CourseId);
				ps.setLong(2, SubjectId);
				ps.setString(3, semester);
				ps.setDate(4, ExDate);
				ps.setString(5, ExamTime);
				rs = ps.executeQuery();

				while (rs.next()) {
					tDTO = new TimeTableDTO();
					tDTO.setId(rs.getLong(1));
					tDTO.setSubjectId(rs.getLong(2));
					tDTO.setSubjectName(rs.getString(3));
					tDTO.setCourseId(rs.getLong(4));
					tDTO.setCourseName(rs.getString(5));
					tDTO.setSemester(rs.getString(6));
					tDTO.setExamDate(rs.getDate(7));
					tDTO.setExamTime(rs.getString(8));
					tDTO.setDescription(rs.getString(9));
				}
			} catch (Exception e) {
				throw new ApplicationException("Exception in timeTable model checkByexamTime..." + e.getMessage());
			}
			return tDTO;
		}
		


		}
