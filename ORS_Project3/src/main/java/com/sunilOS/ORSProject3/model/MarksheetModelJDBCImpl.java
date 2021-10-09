package com.sunilOS.ORSProject3.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.sunilOS.ORSProject3.dto.MarksheetDTO;
import com.sunilOS.ORSProject3.dto.StudentDTO;
import com.sunilOS.ORSProject3.exception.ApplicationException;
import com.sunilOS.ORSProject3.exception.DatabaseException;
import com.sunilOS.ORSProject3.exception.DuplicateRecordException;
import com.sunilOS.ORSProject3.util.JDBCDataSource;


/**
 * JDBC Implementation of Marksheet model
 * 
 * @author Anshul
 *
 */
public class MarksheetModelJDBCImpl implements MarksheetModelInt {

	Logger log = Logger.getLogger(MarksheetModelJDBCImpl.class);

	/**
	 * add new id
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

			PreparedStatement stmt = conn.prepareStatement("select max(id) from st_marksheet");

			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				pk = rs.getInt(1);
			}
			rs.close();
		} catch (Exception e) {
			log.error(e);
			throw new DatabaseException("Exception in Marksheet getting PK");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model nextPK End");
		return pk + 1;

	}

	/**
	 * add new marksheet
	 * 
	 * @param bean
	 * @return pk
	 * @throws ApplicationException
	 * @throws DuplicateRecordException
	 */
	public long add(MarksheetDTO dto) throws ApplicationException, DuplicateRecordException {
		log.debug("Model add Started");

		Connection conn = null;

			StudentModelJDBCImpl sModel = new StudentModelJDBCImpl();
			StudentDTO studentDTO = sModel.findByPk(dto.getStudentId());
			dto.setName(studentDTO.getFirstName() + "" + studentDTO.getLastName());
	
			int pk = 0;
			
		try {
			conn = JDBCDataSource.getConnection();
			pk = nextPK();
			conn.setAutoCommit(false);
			PreparedStatement stmt = conn.prepareStatement("insert into st_marksheet values(?,?,?,?,?,?,?,?,?,?,?)");

			stmt.setInt(1, pk);
			stmt.setString(2, dto.getRollNo());
			stmt.setLong(3, dto.getStudentId());
			stmt.setString(4, dto.getName());
			stmt.setInt(5, dto.getPhysics());
			stmt.setInt(6, dto.getChemistry());
			stmt.setInt(7, dto.getMaths());
			stmt.setString(8, dto.getCreatedBy());
			stmt.setString(9, dto.getModifiedBy());
			stmt.setTimestamp(10, dto.getCreatedDatetime());
			stmt.setTimestamp(11, dto.getModifiedDatetime());

			stmt.executeUpdate();
			System.out.println("bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb add method");
			conn.commit();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
			try {
				conn.rollback();
			} catch (Exception ex) {

				throw new ApplicationException("add rollback exception" + ex.getMessage());
			}
			throw new ApplicationException("Exception in add marksheet");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model add end");
		return pk;
	}

	/**
	 * delete marksheet information
	 * 
	 * @param bean
	 * @throws ApplicationException
	 */
	public void delete(MarksheetDTO dto) throws ApplicationException {
		log.debug("Model delete Started");
		System.out.println("marksheet model Method delete Started");
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement stmt = conn.prepareStatement("delete from st_marksheet where id=?");
			stmt.setLong(1, dto.getId());
			System.out.println("Deleted Marksheet");
			stmt.executeUpdate();
			conn.commit();
			stmt.close();
		} catch (Exception e) {
			log.error(e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				log.error(ex);
				throw new ApplicationException("Delete rollback exception" + ex.getMessage());

			}
			throw new ApplicationException("Exception in delete marksheet");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Mdel delete end");
	}

	/**
	 * find information with the help of rollno
	 * 
	 * @param rollNo
	 * @return bean
	 * @throws ApplicationException
	 */
	public MarksheetDTO findByRollNo(String rollNo) throws ApplicationException {
		log.debug("Model findByRollNo Started");

		StringBuffer sql = new StringBuffer("select * from st_marksheet where roll_no=?");

		MarksheetDTO dto = null;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql.toString());
			stmt.setString(1, rollNo);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				dto = new MarksheetDTO();
				dto.setId(rs.getLong(1));
				dto.setRollNo(rs.getString(2));
				dto.setStudentId(rs.getLong(3));
				dto.setName(rs.getString(4));
				dto.setPhysics(rs.getInt(5));
				dto.setChemistry(rs.getInt(6));
				dto.setMaths(rs.getInt(7));
				dto.setCreatedBy(rs.getString(8));
				dto.setModifiedBy(rs.getString(9));
				dto.setCreatedDatetime(rs.getTimestamp(10));
				dto.setModifiedDatetime(rs.getTimestamp(11));
			}
			rs.close();
		} catch (Exception e) {
			log.error(e);
			throw new ApplicationException("Exception in getting marksheet by rollno");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model findByRollNo end");
		return dto;
	}

	/**
	 * find information with the help of pk
	 * 
	 * @param pk
	 * @return bean
	 * @throws ApplicationException
	 */
	public MarksheetDTO findByPk(long pk) throws ApplicationException {
		log.debug("Model findByPK Started");
		System.out.println("marksheet model Method findbypk Started");
		StringBuffer sql = new StringBuffer("select *from st_marksheet where id=?");

		MarksheetDTO dto = null;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql.toString());
			stmt.setLong(1, pk);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				dto = new MarksheetDTO();
				dto.setId(rs.getLong(1));
				dto.setRollNo(rs.getString(2));
				dto.setStudentId(rs.getLong(3));
				dto.setName(rs.getString(4));
				dto.setPhysics(rs.getInt(5));
				dto.setChemistry(rs.getInt(6));
				dto.setMaths(rs.getInt(7));
				dto.setCreatedBy(rs.getString(8));
				dto.setModifiedBy(rs.getString(9));
				dto.setCreatedDatetime(rs.getTimestamp(10));
				dto.setModifiedDatetime(rs.getTimestamp(11));
			}
			rs.close();
		} catch (Exception e) {
			log.error(e);
			throw new ApplicationException("Exception in getting marksheet by pk");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model findByPK end");
		return dto;

	}

	/**
	 * update marksheet information
	 * 
	 * @param bean
	 * @throws ApplicationException
	 * @throws DuplicateRecordException
	 */
	public void update(MarksheetDTO dto) throws ApplicationException, DuplicateRecordException {

		log.debug("Model Update Started");
		System.out.println("marksheet model Method update Started");

		Connection conn = null;
		MarksheetDTO dtoExist = findByRollNo(dto.getRollNo());

		/*
		 * if (beanExist!=null&&beanExist.getId()!=bean.getId()){
		 * 
		 * throw new DuplicateRecordException("rollno is already exist"); }
		 */
		StudentModelJDBCImpl sModel = new StudentModelJDBCImpl();
		StudentDTO studentDTO = sModel.findByPk(dto.getStudentId());
		dto.setName(studentDTO.getFirstName() + "" + studentDTO.getLastName());

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement stmt = conn.prepareStatement(
					"update st_marksheet set roll_no=?,student_id=?,name=?,physics=?,chemistry=?,maths=?,created_by=?,modified_by=?,created_datetime=?,modified_datetime=? where id=?");

			stmt.setString(1, dto.getRollNo());
			stmt.setLong(2, dto.getStudentId());
			stmt.setString(3, dto.getName());
			stmt.setInt(4, dto.getPhysics());
			stmt.setInt(5, dto.getChemistry());
			stmt.setInt(6, dto.getMaths());
			stmt.setString(7, dto.getCreatedBy());
			stmt.setString(8, dto.getModifiedBy());
			stmt.setTimestamp(9, dto.getCreatedDatetime());
			stmt.setTimestamp(10, dto.getModifiedDatetime());
			stmt.setLong(11, dto.getId());

			stmt.executeUpdate();
			conn.commit();
			stmt.close();
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (Exception ex) {
				ex.printStackTrace();
				throw new ApplicationException("update rollback exception" + ex.getMessage());
			}

			throw new ApplicationException("Exception in updating marksheet");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model add end");

	}

	public List search(MarksheetDTO dto) throws ApplicationException {
		System.out.println("marksheet model Method search 0 Started");
		return search(dto, 0, 0);
	}

	/**
	 * search marksheet
	 * 
	 * @param marksheet
	 * @param pageNo
	 * @param pageSize
	 * @return list
	 * @throws ApplicationException
	 */
	public List search(MarksheetDTO dto, int pageNo, int pageSize) throws ApplicationException {
		Connection con = null;
		StringBuffer sql = new StringBuffer("select * from st_marksheet where 1=1");
		if (dto != null) {
			if (dto.getId() > 0) {
				sql.append(" AND ID = " + dto.getId());
			}
			if ((dto.getRollNo() != null) && (dto.getRollNo().length() > 0)) {

				sql.append(" AND ROLL_NO like '" + dto.getRollNo() + "%'");
			}
			if (dto.getStudentId() > 0) {
				sql.append(" AND STUDENT_ID = " + dto.getStudentId());
			}
			if (dto.getName() != null && dto.getName().length() > 0) {
				sql.append(" AND NAME like '" + dto.getName() + "%'");
			}
			/*
			 * if (marksheet.getPhysics() > 0) { sql.append(" AND PHYSICS = " +
			 * marksheet.getPhysics()); } if (marksheet.getChemistry() > 0) {
			 * sql.append(" AND CHEMISTRY = " + marksheet.getChemistry()); } if
			 * (marksheet.getMaths() > 0) { sql.append(" AND MATHS = " +
			 * marksheet.getMaths()); }
			 */
		}
		if (pageSize > 0) {
			// Calculate start record index
			pageNo = (pageNo - 1) * pageSize;

			sql.append(" Limit " + pageNo + "," + pageSize);

			// sql.append(" limit " + pageNo + "," + pageSize);
		}
		ArrayList list = new ArrayList();
		try {

			con = JDBCDataSource.getConnection();

			PreparedStatement ps = con.prepareStatement(sql.toString());

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				MarksheetDTO mDTO = new MarksheetDTO();
				mDTO.setId(rs.getLong(1));
				mDTO.setRollNo(rs.getString(2));
				mDTO.setStudentId(rs.getLong(3));
				mDTO.setName(rs.getString(4));
				mDTO.setPhysics(rs.getInt(5));
				mDTO.setChemistry(rs.getInt(6));
				mDTO.setMaths(rs.getInt(7));
				list.add(mDTO);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Database Exception..", e);

			// throw new ApplicationException("Exception : Exception in search time table");
		} finally {
			JDBCDataSource.closeConnection(con);
		}

		log.debug("Model search End");

		return list;

	}

	/*
	 * public List search(MarksheetBean bean) throws ApplicationException{ return
	 * search(bean,0,0); }
	 */

	/*
	 * public List search(MarksheetBean bean1,int pageNo,int pageSize) throws
	 * ApplicationException{ log.debug("Model search Started");
	 * 
	 * StringBuffer sql=new StringBuffer("select * from st_marksheet where 1=1");
	 * 
	 * MarksheetBean bean;
	 * 
	 * if(bean1!=null){ System.out.println("service"+bean1.getName());
	 * if(bean1.getId()>0){ sql.append(" AND id= "+bean1.getId());
	 * 
	 * } if(bean1.getRollNo()!=null&&bean1.getRollNo().length()>0){
	 * sql.append(" AND roll_no like '"+bean1.getRollNo()+"%'"); }
	 * if(bean1.getName()!=null&&bean1.getName().length()>0){
	 * sql.append(" AND name like '"+bean1.getName()+"%'"); }
	 * if(bean1.getPhysics()!=null &&bean1.getPhysics()>0){
	 * sql.append(" AND physics= "+bean1.getPhysics());
	 * 
	 * } if(bean1.getChemistry()!=null && bean1.getChemistry()>0){
	 * sql.append(" AND chemistry= "+bean1.getChemistry()); }
	 * if(bean1.getMaths()!=null&&bean1.getMaths()>0){
	 * sql.append(" AND maths= "+bean1.getMaths()); }
	 * 
	 * } if(pageSize>0){ pageNo=(pageNo-1)*pageSize;
	 * 
	 * sql.append(" Limit "+pageNo+","+pageSize); } ArrayList list=new ArrayList();
	 * Connection conn=null; try{ conn=JDBCDataSource.getConnection();
	 * PreparedStatement stmt=conn.prepareStatement(sql.toString()); ResultSet
	 * rs=stmt.executeQuery();
	 * 
	 * while(rs.next()){ bean=new MarksheetBean(); bean.setId(rs.getLong(1));
	 * bean.setRollNo(rs.getString(2)); bean.setStudentId(rs.getLong(3));
	 * bean.setName(rs.getString(4)); bean.setPhysics(rs.getInt(5));
	 * bean.setChemistry(rs.getInt(6)); bean.setMaths(rs.getInt(7));
	 * bean.setCreatedBy(rs.getString(8)); bean.setModifiedBy(rs.getString(9));
	 * bean.setCreatedDatetime(rs.getTimestamp(10));
	 * bean.setModifiedDatetime(rs.getTimestamp(11)); } rs.close(); }catch(Exception
	 * e){ log.error(e); e.printStackTrace(); throw new
	 * ApplicationException("Update rollback exception"+e.getMessage());
	 * 
	 * }finally{ JDBCDataSource.closeConnection(conn); }
	 * log.debug("Model search end"); return list;
	 * 
	 * 
	 * }
	 */
	public List list() throws ApplicationException {
		System.out.println("marksheet model Method List(0) Started");
		return list(0, 0);
	}

	/**
	 * get List of Marksheet with pagination
	 *
	 * @return list : List of Marksheets
	 * @param pageNo   : Current Page No.
	 * @param pageSize : Size of Page
	 * @throws DatabaseException
	 */

	public List list(int pageNo, int pageSize) throws ApplicationException {

		System.out.println("marksheet model Method List(1) Started");
		log.debug("Model list Started");

		ArrayList list = new ArrayList();
		StringBuffer sql = new StringBuffer("select * from st_marksheet");
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
				MarksheetDTO dto = new MarksheetDTO();
				dto.setId(rs.getLong(1));
				dto.setRollNo(rs.getString(2));
				dto.setStudentId(rs.getLong(3));
				dto.setName(rs.getString(4));
				dto.setPhysics(rs.getInt(5));
				dto.setChemistry(rs.getInt(6));
				dto.setMaths(rs.getInt(7));
				dto.setCreatedBy(rs.getString(8));
				dto.setModifiedBy(rs.getString(9));
				dto.setCreatedDatetime(rs.getTimestamp(10));
				dto.setModifiedDatetime(rs.getTimestamp(11));

				list.add(dto);
			}
			rs.close();
		} catch (Exception e) {
			log.error(e);
			throw new ApplicationException("Exception in getting list of marksheet");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model list End");
		return list;
	}

	/**
	 * get merit list
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @return list
	 * @throws ApplicationException
	 */
	public List getMeritList(int pageNo, int pageSize) throws ApplicationException {

		System.out.println("marksheet model Method  getMeritList Started");
		log.debug("Model Meritlist Started");

		ArrayList list = new ArrayList();
		StringBuffer sql = new StringBuffer(
				"select id,roll_no,name,physics,chemistry,maths,(physics+chemistry+maths)as total from st_marksheet order by total desc");
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
				MarksheetDTO dto = new MarksheetDTO();
				dto.setId(rs.getLong(1));
				dto.setRollNo(rs.getString(2));
				dto.setName(rs.getString(3));
				dto.setPhysics(rs.getInt(4));
				dto.setChemistry(rs.getInt(5));
				dto.setMaths(rs.getInt(6));

				list.add(dto);
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
			throw new ApplicationException("Exception in getting Meritlist of marksheet");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model Meritlist End");
		return list;

	}


}
