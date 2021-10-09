package com.sunilOS.ORSProject3.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.sunilOS.ORSProject3.dto.RoleDTO;
import com.sunilOS.ORSProject3.exception.ApplicationException;
import com.sunilOS.ORSProject3.exception.DatabaseException;
import com.sunilOS.ORSProject3.exception.DuplicateRecordException;
import com.sunilOS.ORSProject3.util.JDBCDataSource;



/**
 * JDBC Implementation of role model
 * @author Anshul
 *
 */
public class RoleModelJDBCImpl implements RoleModelInt {

	private static Logger log=Logger.getLogger(RoleModelJDBCImpl.class);
	
	/**
	 * create id 
	 * @return pk
	 * @throws DatabaseException
	 */
  public Integer nextPK() throws DatabaseException{
		
		log.debug("Model nextPk started");
		
		Connection conn=null;
		int pk=0;
		try{
			conn=JDBCDataSource.getConnection();
			PreparedStatement stmt=conn.prepareStatement("Select max(id) from st_role");
			
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
	 * add new role 
	 * @param bean
	 * @return pk
	 * @throws ApplicationException
	 * @throws DuplicateRecordException
	 */
	public long add(RoleDTO dto) throws ApplicationException, DuplicateRecordException{
		log.debug("Model add Started ");
		
		Connection conn=null;
		int pk=0;
		
//		RoleBean duplicateRole=findByName(bean.getName());
//		if(duplicateRole!=null){
//			throw new DuplicateRecordException("Role already exist");
//			
//		}
		try{
			conn=JDBCDataSource.getConnection();
			pk=nextPK();
			conn.setAutoCommit(false);
			PreparedStatement stmt=conn.prepareStatement("insert into st_role values(?,?,?,?,?,?,?)");
			stmt.setInt(1, pk);
			stmt.setString(2, dto.getRoleName());
			stmt.setString(3, dto.getDescription());
			stmt.setString(4,dto.getCreatedBy());
			stmt.setString(5, dto.getModifiedBy());
			stmt.setTimestamp(6,dto.getCreatedDatetime());
			stmt.setTimestamp(7,dto.getModifiedDatetime());
			
			stmt.executeUpdate();
			conn.commit();
			stmt.close();
		
		}catch(Exception e){
			e.printStackTrace();
			log.error("Database Exception..",e);
			
			try{
				conn.rollback();
			}catch(Exception ex){
				throw new ApplicationException("Exception:add rollback exception"+ex.getMessage());
				
			}
			throw new ApplicationException("Exception:Exception in add Role");
			
		}finally{
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model and End");
		return pk;
	}
	
	/**
	 * delete role
	 * @param bean
	 * @throws ApplicationException
	 */
	public void delete(RoleDTO dto) throws ApplicationException{
		log.debug("Model deete Started");
		Connection conn=null;
		
		try{
			conn=JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement stmt= conn.prepareStatement("delete from st_role where id=?");
			stmt.setLong(1,dto.getId());
			stmt.executeUpdate();
			conn.commit();
			stmt.close();
			
		}catch(Exception e){
			log.error("Database Exception..",e);
			try{
				conn.rollback();
			}catch(Exception ex){
				throw new ApplicationException ("Exception: Delete rollback exception"+ex.getMessage());
				
			}throw new ApplicationException("Exception:Exception in delete Role");
			
		}finally{
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model delete Started ");
	}
	
	/**
	 * find role with the help of name
	 * @param name
	 * @return bean
	 * @throws ApplicationException
	 */
	public RoleDTO findByRoleName(String name) throws ApplicationException{
		log.debug("Model findBy EmailId Started");
		
		StringBuffer sql=new StringBuffer("select * from st_role where name=?");
		
		RoleDTO dto=null;
		Connection conn=null;
		
		try{
			conn=JDBCDataSource.getConnection();
			PreparedStatement stmt=conn.prepareStatement(sql.toString());
			stmt.setString(1,name);
			ResultSet rs=stmt.executeQuery();
			while(rs.next()){
				dto=new RoleDTO();
				dto.setId(rs.getLong(1));
				dto.setRoleName(rs.getString(2));
				dto.setDescription(rs.getString(3));
				dto.setCreatedBy(rs.getString(4));
				dto.setModifiedBy(rs.getString(5));
				dto.setCreatedDatetime(rs.getTimestamp(6));
				dto.setModifiedDatetime(rs.getTimestamp(7));
				
			}
			rs.close();
		}catch(Exception e){
			log.error("Database Exception.. ",e);
			throw new ApplicationException("Exception:Exception in getting User by emailId");
			
		}finally{
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model findBy EmailId End");
		return dto;
	}
	
	/**
	 * find by role with the help of role
	 * @param pk
	 * @return bean
	 * @throws ApplicationException
	 */
	public RoleDTO findByPK(long pk) throws ApplicationException{
		log.debug("Model findByPK started");
		
		StringBuffer sql=new StringBuffer("Select * from st_role where id=?");
		RoleDTO dto=null;
		Connection conn=null;
		try{
			conn=JDBCDataSource.getConnection();
			PreparedStatement stmt=conn.prepareStatement(sql.toString());
			stmt.setLong(1, pk);
			ResultSet rs=stmt.executeQuery();
			while(rs.next()){
				dto=new RoleDTO();
				dto.setId(rs.getLong(1));
				dto.setRoleName(rs.getString(2));
				dto.setDescription(rs.getString(3));
				dto.setCreatedBy(rs.getString(4));
				dto.setCreatedBy(rs.getString(5));
				dto.setCreatedDatetime(rs.getTimestamp(6));
				dto.setModifiedDatetime(rs.getTimestamp(7));
			}
			rs.close();
		}catch(Exception e){
			log.error("Database Exception.. ",e);
			throw new ApplicationException("Exception : Exception in getting User by PK");
			
		}finally{
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model findbyPk End");
		return dto;
	}
	
	/**
	 * update role 
	 * @param bean
	 * @throws ApplicationException
	 * @throws DuplicateRecordException
	 */
	public void update(RoleDTO dto) throws DuplicateRecordException, ApplicationException{
		log.debug("Model Update Started");
		Connection conn=null;
		
		RoleDTO duplicateRole=findByRoleName(dto.getRoleName());
		
		if(duplicateRole!=null && duplicateRole.getId()!=dto.getId()){
			throw new DuplicateRecordException("Role already exsits");
			
		}
		try{
			conn=JDBCDataSource.getConnection();
			 conn.setAutoCommit(false);
			PreparedStatement stmt=conn.prepareStatement("update st_role set name=?,description=?,created_by=?,modified_by=?,created_datetime=?,modified_datetime=? where id=?");
			
			
			stmt.setString(1, dto.getRoleName());
			stmt.setString(2, dto.getDescription());
			stmt.setString(3,dto.getCreatedBy());
			stmt.setString(4, dto.getModifiedBy());
			stmt.setTimestamp(5,dto.getCreatedDatetime());
			stmt.setTimestamp(6,dto.getModifiedDatetime());
			stmt.setLong(7, dto.getId());
			
			stmt.executeUpdate();
			conn.commit();
			stmt.close();
			
		}catch(Exception e){
			log.error("Database exception..",e);
			try{
				conn.rollback();
			}catch(Exception ex){
				throw new ApplicationException("Exception:delete rollback exception"+ex.getMessage());
				
				
			}
			throw new ApplicationException("Exception in updating Role");
			
		}finally{
			JDBCDataSource.closeConnection(conn);
			
		}log.debug("Model update End");
		
	}
	
	public List search(RoleDTO dto) throws ApplicationException{
		return search(dto,0,0);
	}
	
	/**
	 * search role
	 * @param dto
	 * @param pageNo
	 * @param pageSize
	 * @return list
	 * @throws ApplicationException
	 */
	public List search(RoleDTO dto,int pageNo,int pageSize) throws ApplicationException{
		log.debug("Model search Started");
		StringBuffer sql=new StringBuffer("select * from st_role where 1=1");
	
		if(dto!=null){
			if(dto.getId()>0){
				sql.append(" AND id= "+dto.getId());
				
			}
			if(dto.getRoleName()!=null && dto.getRoleName().length()>0){
				sql.append(" AND NAME like '"+dto.getRoleName()+"%'");
			}
			if(dto.getDescription()!=null &&dto.getDescription().length()>0){
				sql.append(" AND DESCRIPTION like '"+dto.getDescription()+"%'");
				
			}
			
		}
		if(pageSize>0){
			pageNo=(pageNo-1)*pageSize;
			sql.append(" limit "+pageNo+","+pageSize);
		}
		ArrayList list=new ArrayList();
		Connection conn=null;
		
		try{
			conn=JDBCDataSource.getConnection();
			PreparedStatement stmt=conn.prepareStatement(sql.toString());
			
			ResultSet rs=stmt.executeQuery();
			while(rs.next()){
				dto=new RoleDTO();
				dto.setId(rs.getLong(1));
				dto.setRoleName(rs.getString(2));
				dto.setDescription(rs.getString(3));
				dto.setCreatedBy(rs.getString(4));
				dto.setCreatedBy(rs.getString(5));
				dto.setCreatedDatetime(rs.getTimestamp(6));
				dto.setModifiedDatetime(rs.getTimestamp(7));
				list.add(dto);
			}
			rs.close();
		}catch(Exception e){
			log.error("Database Exception.. ",e);
			throw new ApplicationException("Exception : Exception in search Role");
			
			
		}finally{
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model search End");
		return list;
		
	}
	public List list() throws ApplicationException{
		return list(0,0);
	}
	
	/**
	 *list of role
	 * @param pageNo
	 * @param pageSize
	 * @return list
	 * @throws ApplicationException
	 */
	public List list(int pageNo,int pageSize) throws ApplicationException{
		log.debug("Model list Started");
		
		ArrayList list=new ArrayList();
		StringBuffer sql=new StringBuffer("select * from st_role");
	
		if(pageSize>0){
			pageNo=(pageNo-1)*pageSize;
			sql.append(" limit "+pageNo+","+pageSize);
		}
		Connection conn=null;
		try{
			conn=JDBCDataSource.getConnection();
			PreparedStatement stmt=conn.prepareStatement(sql.toString());
			
			ResultSet rs=stmt.executeQuery();
			while(rs.next()){
				RoleDTO dto=new RoleDTO();
				dto.setId(rs.getLong(1));
				dto.setRoleName(rs.getString(2));
				dto.setDescription(rs.getString(3));
				dto.setCreatedBy(rs.getString(4));
				dto.setCreatedBy(rs.getString(5));
				dto.setCreatedDatetime(rs.getTimestamp(6));
				dto.setModifiedDatetime(rs.getTimestamp(7));
				list.add(dto);
			}
			rs.close();
		}catch(Exception e){
			log.error("Database Exception.. ",e);
			throw new ApplicationException("Exception : Exception in getting list of Role");
			
			
		}finally{
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model list End");
		return list;
		
	}
	
}
