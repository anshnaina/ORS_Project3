package com.sunilOS.ORSProject3.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.sunilOS.ORSProject3.dto.CollegeDTO;
import com.sunilOS.ORSProject3.dto.StudentDTO;
import com.sunilOS.ORSProject3.exception.ApplicationException;
import com.sunilOS.ORSProject3.exception.DatabaseException;
import com.sunilOS.ORSProject3.exception.DuplicateRecordException;
import com.sunilOS.ORSProject3.util.JDBCDataSource;


/**
 * JDBC Implement of student model
 * @author Anshul
 *
 */
public class StudentModelJDBCImpl implements StudentModelInt {

	private static Logger log=Logger.getLogger(StudentModelJDBCImpl.class);
	
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
			 PreparedStatement stmt=conn.prepareStatement("select max(id) from st_student");
			 ResultSet rs=stmt.executeQuery();
			 while(rs.next()){
				 pk=rs.getInt(1);
				 
			 }
			 rs.close();
		 }catch(Exception e){
			 log.error("Database Exception..",e);
			 throw new DatabaseException("Exception: Exception in getting PK");
			 
		 }finally{
			 JDBCDataSource.closeConnection(conn);
		 }
		 log.debug("Model nextPK End");
		 return pk+1;
	}
	/**
	 * add student
	 * @param bean
	 * @return pk
	 * @throws DuplicateRecordException
	 * @throws ApplicationException
	 */
	public long add(StudentDTO dto) throws ApplicationException, DuplicateRecordException{
		log.debug("Model add started");
		Connection conn=null;
		CollegeModelJDBCImpl cModel=new CollegeModelJDBCImpl();
		CollegeDTO collegeDTO=cModel.findByPk(dto.getCollegeId());
		dto.setCollegeName(collegeDTO.getCollegeName());
		
		StudentDTO duplicateName=findByEmail(dto.getEmail());
		int pk=0;
		if (duplicateName!=null){
			throw new DuplicateRecordException("Email already exist");
		}
		try{
			conn=JDBCDataSource.getConnection();
			pk=nextPK();
			System.out.println(pk+" in ModelJDBC");
			conn.setAutoCommit(false);
			PreparedStatement stmt=conn.prepareStatement("insert into st_student values(?,?,?,?,?,?,?,?,?,?,?,?)");
			stmt.setInt(1, pk);
			stmt.setLong(2,dto.getCollegeId());
			stmt.setString(3,dto.getCollegeName());
			stmt.setString(4,dto.getFirstName());
			stmt.setString(5,dto.getLastName());
			stmt.setDate(6,new java.sql.Date(dto.getDob().getTime()));
			stmt.setString(7,dto.getMobileNo());
			stmt.setString(8,dto.getEmail());
			stmt.setString(9,dto.getCreatedBy());
			stmt.setString(10,dto.getModifiedBy());
			stmt.setTimestamp(11,dto.getCreatedDatetime());
			stmt.setTimestamp(12,dto.getModifiedDatetime());
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
				throw new ApplicationException("Exception: add rollback exception "+ex.getMessage());
			}
			throw new ApplicationException("Exception:Exception in add student");
			
			
		}finally{
			JDBCDataSource.closeConnection(conn);
			
		}
		log.debug("Model add End");
		return pk;
	}
	/** 
	 * delete student
	 * @param bean
	 * @throws ApplicationException
	 */
	public void delete(StudentDTO dto) throws ApplicationException{
		log.debug("Model delete Started");
		
		Connection conn=null;
		
		try{
			conn=JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement stmt=conn.prepareStatement("delete from st_student where ID=?");
			stmt.setLong(1, dto.getId());
			stmt.executeUpdate();
			conn.commit();
			stmt.close();
			
		}catch(Exception e){
			log.error("Database exception..",e);
			try{
				conn.rollback();
				
			}catch(Exception ex){
				throw new ApplicationException("Exception: Delete rollback exception"+ex.getMessage());
				
			}throw new ApplicationException("Exception in delete Student");
			
		}finally{
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model delete end");
		
	}
	/**
	 * find student with the help of emailId
	 * @param email
	 * @return bean
	 * @throws ApplicationException
	 */
	public StudentDTO findByEmail(String email) throws ApplicationException{
		
		log.debug("Model findBy Email Started");
		StringBuffer sql=new StringBuffer("Select * from st_student where email=?");
		
		StudentDTO dto=null;
		Connection conn=null;
		try{
			
			conn=JDBCDataSource.getConnection();
			PreparedStatement stmt=conn.prepareStatement(sql.toString());
			stmt.setString(1, email);
			ResultSet rs=stmt.executeQuery();
			while(rs.next()){
				dto=new StudentDTO();
				dto.setId(rs.getLong(1));
				dto.setCollegeId(rs.getLong(2));
				dto.setCollegeName(rs.getString(3));
				dto.setFirstName(rs.getString(4));
				dto.setLastName(rs.getString(5));
				dto.setDob(rs.getDate(6));
				dto.setMobileNo(rs.getString(7));
				dto.setEmail(rs.getString(8));
				dto.setCreatedBy(rs.getString(9));
				dto.setModifiedBy(rs.getString(10));
				dto.setCreatedDatetime(rs.getTimestamp(11));
				dto.setModifiedDatetime(rs.getTimestamp(12));
				
			}
			rs.close();
		}catch(Exception e){
		log.error("Database Exception.. ",e);	
		throw new ApplicationException("Exception: Exception in getting User by Email");
		
		}finally{
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model findBy Email End");
		return dto;
		
		}
	
	/**
	 * find student with the help of pk
	 * @param pk
	 * @return bean
	 * @throws ApplicationException
	 */
	public StudentDTO findByPk(long pk) throws ApplicationException{
		
		log.debug("Model findBy pk started");
		StringBuffer sql=new StringBuffer("Select * from st_student where id=?");
		
		StudentDTO dto=null;
		Connection conn=null;
		
		try{
			conn=JDBCDataSource.getConnection();
			PreparedStatement stmt=conn.prepareStatement(sql.toString());
			stmt.setLong(1, pk);
			ResultSet rs= stmt.executeQuery();
			while(rs.next()){
				dto=new StudentDTO();
				dto.setId(rs.getLong(1));
				dto.setCollegeId(rs.getLong(2));
				dto.setCollegeName(rs.getString(3));
				dto.setFirstName(rs.getString(4));
				dto.setLastName(rs.getString(5));
				dto.setDob(rs.getDate(6));
				dto.setMobileNo(rs.getString(7));
				dto.setEmail(rs.getString(8));
				dto.setCreatedBy(rs.getString(9));
				dto.setModifiedBy(rs.getString(10));
				dto.setCreatedDatetime(rs.getTimestamp(11));
				dto.setModifiedDatetime(rs.getTimestamp(12));
				
			}
			rs.close();
		}catch(Exception e){
			log.error("Database Exception..",e);
			e.printStackTrace();
			throw new ApplicationException("Exception:Exception in getting User by pk");
			
		}finally{
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model findBy Pk end");
		return dto;
	}
	/**
	 * update student
	 * @param bean
	 * @throws ApplicationException
	 * @throws DuplicateRecordException
	 */
	public void update(StudentDTO dto) throws ApplicationException, DuplicateRecordException{
		System.out.println("update start");
		log.debug("Model update Started");
		Connection conn=null;
		
		StudentDTO beanExist=findByEmail(dto.getEmail());
		
		System.out.println("update method beaExxist check");
		if(beanExist !=null&& beanExist.getId()!=dto.getId()){
			System.out.println("duplicate record mfoumd");
			throw new DuplicateRecordException("Email Id is already exist");
			
		}
		CollegeModelJDBCImpl cModel=new CollegeModelJDBCImpl();
		CollegeDTO collegeDTO=cModel.findByPk(dto.getCollegeId());
		dto.setCollegeName(collegeDTO.getCollegeName());
		System.out.println("get collegename");
		
		
		try{
			conn=JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement stmt=conn.prepareStatement("Update st_student set college_id=?,college_name=?,first_name=?,last_name=?,mobile_no=?,email=?,created_by=?,modified_by=?,created_datetime=?,modified_datetime=? where id=?");
			stmt.setLong(1,dto.getCollegeId());
			System.out.println("update method "+dto.getCollegeId());
			stmt.setString(2,dto.getCollegeName());
			stmt.setString(3,dto.getFirstName());
			stmt.setString(4,dto.getLastName());
			System.out.println("update method "+dto.getLastName());
			stmt.setString(5,dto.getMobileNo());
			stmt.setString(6,dto.getEmail());
			System.out.println("update method "+dto.getEmail());
			stmt.setString(7,dto.getCreatedBy());
			stmt.setString(8,dto.getModifiedBy());
			stmt.setTimestamp(9,dto.getCreatedDatetime());
			stmt.setTimestamp(10,dto.getModifiedDatetime());
			stmt.setLong(11,dto.getId());
			
			stmt.executeUpdate();
			System.out.println("update method execute query done");
			conn.commit();
			stmt.close();
			
		}catch(Exception e){
			log.error("DatabaseException..",e);
			try{
				System.out.println("transaction rollback");
				conn.rollback();
//				e.printStackTrace();
				
			}catch(Exception ex){
				throw new ApplicationException("Exception: delete rollback excption"+ex.getMessage());
				
			}
			throw new ApplicationException("Exception in update student");
			
		}finally{
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("model update end");
		
	}
		public List search(StudentDTO dto) throws ApplicationException{
			return search(dto,0,0);
		}
		
		/**
		 * search student
		 * @param bean
		 * @param pageNo
		 * @param pageSize
		 * @return list
		 * @throws ApplicationException
		 */
		public List search(StudentDTO dto,int pageNo,int pageSize) throws ApplicationException{
			log.debug("Model search Started");
		StringBuffer sql=new StringBuffer("select * from st_student where 1=1");
		
		if(dto!=null){
			if (dto.getId()>0){
				sql.append(" And id= "+dto.getId());
			}
			if(dto.getFirstName()!=null&& dto.getFirstName().length()>0){
				sql.append(" And first_name like '"+dto.getFirstName()+"%'");
				
			}
			if(dto.getLastName()!=null&& dto.getLastName().length()>0){
				sql.append(" And last_name like '"+dto.getLastName()+"%'");
			}
			if(dto.getDob()!=null&& dto.getDob().getDate()>0){
				sql.append(" And Dob= "+dto.getDob());
			}
			if(dto.getMobileNo()!=null&&dto.getMobileNo().length()>0){
				sql.append(" And mobile_no like '"+dto.getMobileNo()+"%'");
				
			}
			if(dto.getEmail()!=null&&dto.getEmail().length()>0){
				sql.append(" And Email like '"+dto.getEmail()+"%'");
			}
			if (dto.getCollegeId() > 0) {
				sql.append(" AND COLLEGE_ID = " + dto.getCollegeId());
			}
			if(dto.getCollegeName()!=null&&dto.getCollegeName().length()>0){
				sql.append(" And College_name= '"+dto.getCollegeName()+"%'");
				
			}
			
		}
			if(pageSize>0){
				pageNo=(pageNo-1)*pageSize;
			sql.append(" Limit "+pageNo+","+pageSize);
			
			}
			ArrayList list = new ArrayList();
			Connection conn=null;
			
			try{
				conn=JDBCDataSource.getConnection();
				PreparedStatement stmt=conn.prepareStatement(sql.toString());
				ResultSet rs=stmt.executeQuery();
				 while (rs.next()) {
		                dto = new StudentDTO();
		                dto.setId(rs.getLong(1));
		                dto.setCollegeId(rs.getLong(2));
		                dto.setCollegeName(rs.getString(3));
		                dto.setFirstName(rs.getString(4));
		                dto.setLastName(rs.getString(5));
		                dto.setDob(rs.getDate(6));
		                dto.setMobileNo(rs.getString(7));
		                dto.setEmail(rs.getString(8));
		                dto.setCreatedBy(rs.getString(9));
		                dto.setModifiedBy(rs.getString(10));
		                dto.setCreatedDatetime(rs.getTimestamp(11));
		                dto.setModifiedDatetime(rs.getTimestamp(12));
		                list.add(dto);
		            }
				 rs.close();
			
			}catch(Exception e){
				e.printStackTrace();
				  log.error("Database Exception..", e);
		            throw new ApplicationException("Exception : Exception in search Student");
		            
			}finally{
				JDBCDataSource.closeConnection(conn);
			}
			log.debug("Model Serach End");
			return list;
		
	}	
		
		public List list() throws ApplicationException {
	        return list(0, 0);
	    }
		/**
		 * 
		 * list of student
		 * @param pageNo
		 * @param pageSize
		 * @return list
		 * @throws ApplicationException
		 */
		 public List list(int pageNo, int pageSize) throws ApplicationException{
			 
			 log.debug("Model list Started");
		 
			 ArrayList list=new ArrayList();
			 StringBuffer sql= new StringBuffer("select * from st_student");
			 
			 if (pageSize > 0) {
		         
		          pageNo = (pageNo - 1) * pageSize;
		          sql.append(" limit " + pageNo + "," + pageSize);
		        }
			 Connection conn=null;
			 try{
				 conn = JDBCDataSource.getConnection();
		            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
		            ResultSet rs = pstmt.executeQuery();
		            while (rs.next()) {
		                StudentDTO dto = new StudentDTO();
		                dto.setId(rs.getLong(1));
		                dto.setCollegeId(rs.getLong(2));
		                dto.setCollegeName(rs.getString(3));
		                dto.setFirstName(rs.getString(4));
		                dto.setLastName(rs.getString(5));
		                dto.setDob(rs.getDate(6));
		                dto.setMobileNo(rs.getString(7));
		                dto.setEmail(rs.getString(8));
		                dto.setCreatedBy(rs.getString(9));
		                dto.setModifiedBy(rs.getString(10));
		                dto.setCreatedDatetime(rs.getTimestamp(11));
		                dto.setModifiedDatetime(rs.getTimestamp(12));
		                list.add(dto);
		            }
		            rs.close();	 
			 }catch(Exception e){
			   log.error("Database Exception..", e);
		       throw new ApplicationException("Exception : Exception in getting list of Student");
			}finally{
				JDBCDataSource.closeConnection(conn);
			}
			  log.debug("Model list End");
		        return list;
		 }
	
}
