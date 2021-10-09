package com.sunilOS.ORSProject3.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.sunilOS.ORSProject3.dto.CollegeDTO;
import com.sunilOS.ORSProject3.exception.ApplicationException;
import com.sunilOS.ORSProject3.exception.DatabaseException;
import com.sunilOS.ORSProject3.exception.DuplicateRecordException;
import com.sunilOS.ORSProject3.util.JDBCDataSource;



/**
 * JDBC Implements of College model
 * @author Anshul
 *
 */
public class CollegeModelJDBCImpl implements CollegeModelInt {
	private static Logger log = Logger.getLogger(CollegeModelJDBCImpl.class);

	public long nextPK() throws DatabaseException {
		log.debug(" pk start");
		Connection con = null;
		long pk = 0;
		try {
			con = JDBCDataSource.getConnection();
			PreparedStatement ps = con.prepareStatement("select max(id) from ST_COLLEGE");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				pk = rs.getLong(1);
			}
		} catch (Exception e) {
			log.error(e);
			throw new DatabaseException("Database Exception" + e);

		} finally {
			JDBCDataSource.closeConnection(con);
		}
		log.debug(" pk is end");
		return pk + 1;
	}

	public long add(CollegeDTO dto) throws ApplicationException, DuplicateRecordException {
		// TODO Auto-generated method stub
		Connection conn = null;
		long pk = 0;
		 CollegeDTO duplicateCollegeName = findByCollegeName(dto.getCollegeName());

	        if (duplicateCollegeName != null) {
	            throw new DuplicateRecordException("College Name already exists");
	        }
		try {
			conn = JDBCDataSource.getConnection();
			pk = nextPK();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement("INSERT INTO ST_COLLEGE VALUES(?,?,?,?,?,?,?,?,?,?)");

			ps.setLong(1, pk);
			ps.setString(2, dto.getCollegeName());
			ps.setString(3, dto.getAddress());
			ps.setString(4, dto.getCity());
			ps.setString(5, dto.getState());
			ps.setString(6, dto.getPhoneNo());
			ps.setString(7, dto.getCreatedBy());
			ps.setString(8, dto.getModifiedBy());
			ps.setTimestamp(9, dto.getCreatedDatetime());
			ps.setTimestamp(10, dto.getModifiedDatetime());
			ps.executeUpdate();
			conn.commit();
			ps.close();
		} catch (Exception e) {
			log.error("Database Exception..", e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				ex.printStackTrace();
				throw new ApplicationException("Exception : add rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in add College");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model add End");
		return pk;
	}

	public void delete(CollegeDTO dto) throws ApplicationException {
		// TODO Auto-generated method stub
		Connection conn = null;
		try {

			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement("DELETE FROM ST_COLLEGE WHERE ID=?");
			ps.setLong(1, dto.getId());
			conn.commit();
			ps.close();

		} catch (Exception e) {
			log.error("Database Exception..", e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : Delete rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in delete college");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model delete Started");
	}

	public void update(CollegeDTO dto) throws ApplicationException, DuplicateRecordException {
		// TODO Auto-generated method stub
		Connection conn = null;
		CollegeDTO dtoExist = findByCollegeName(dto.getCollegeName());

        // Check if updated College already exist
        if (dtoExist != null && dtoExist.getId() != dto.getId()) {

            throw new DuplicateRecordException("College is already exist");
        }
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(
					"UPDATE ST_COLLEGE SET COLLEGE_NAME=?,ADDRESS=?,STATE=?,CITY=?,PHONE_NO=?,CREATED_BY=?,MODIFIED_BY=?,CREATED_DATETIME=?,MODIFIED_DATETIME=? WHERE ID=?");
			ps.setString(1, dto.getCollegeName());
			ps.setString(2, dto.getAddress());
			ps.setString(3, dto.getState());
			ps.setString(4, dto.getCity());
			ps.setString(5, dto.getPhoneNo());
			ps.setString(6, dto.getCreatedBy());
			ps.setString(7, dto.getModifiedBy());
			ps.setTimestamp(8, dto.getCreatedDatetime());
			ps.setTimestamp(9, dto.getModifiedDatetime());
			ps.setLong(10, dto.getId());
			ps.executeUpdate();
			conn.commit(); // End transaction
			ps.close();

		} catch (Exception e) {
			log.error("Database Exception..", e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : Delete rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception in updating College ");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model update End");
	}

	public List list() throws ApplicationException {
		// TODO Auto-generated method stub
		return list(0, 0);
	}

	public List list(int pageNo, int pageSize) throws ApplicationException {
		// TODO Auto-generated method stub
		ArrayList list = new ArrayList();
		StringBuffer sql = new StringBuffer("select * from ST_COLLEGE");
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
				CollegeDTO dto = new CollegeDTO();
				dto.setId(rs.getLong(1));
				dto.setCollegeName(rs.getString(2));
				dto.setAddress(rs.getString(3));
				dto.setState(rs.getString(4));
				dto.setCity(rs.getString(5));
				dto.setPhoneNo(rs.getString(6));
				dto.setCreatedBy(rs.getString(7));
				dto.setModifiedBy(rs.getString(8));
				dto.setCreatedDatetime(rs.getTimestamp(9));
				dto.setModifiedDatetime(rs.getTimestamp(10));
				list.add(dto);
			}
			rs.close();
		} catch (Exception e) {
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception : Exception in getting list of users");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		log.debug("Model list End");
		return list;
	}

	public List search(CollegeDTO dto) throws ApplicationException {
		return search(dto, 0, 0);
	}

	public List search(CollegeDTO dto, int pageNo, int pageSize) throws ApplicationException {

		StringBuffer sql = new StringBuffer("SELECT * FROM ST_COLLEGE WHERE 1=1");
		if (dto != null) {
			if (dto.getId() > 0) {
				sql.append(" AND id = " + dto.getId());
			}
			if (dto.getCollegeName() != null && dto.getCollegeName().length() > 0) {
				sql.append(" AND COLLEGE_NAME like '" + dto.getCollegeName() + "%'");
			}
			if (dto.getAddress() != null && dto.getAddress().length() > 0) {
				sql.append(" AND ADDRESS like '" + dto.getAddress() + "%'");
			}
			if (dto.getState() != null && dto.getState().length() > 0) {
				sql.append(" AND STATE like '" + dto.getState() + "%'");
			}
			if (dto.getCity() != null && dto.getCity().length() > 0) {
				sql.append(" AND CITY like '" + dto.getCity() + "%'");
			}
			if (dto.getPhoneNo() != null && dto.getPhoneNo().length() > 0) {
				sql.append(" AND PHONE_NO = " + dto.getPhoneNo());
			}
		} // if page size is greater than zero then apply pagination
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
				dto = new CollegeDTO();
				dto.setId(rs.getLong(1));
				dto.setCollegeName(rs.getString(2));
				dto.setAddress(rs.getString(3));
				dto.setState(rs.getString(4));
				dto.setCity(rs.getString(5));
				dto.setPhoneNo(rs.getString(6));
				dto.setCreatedBy(rs.getString(7));
				dto.setModifiedBy(rs.getString(8));
				dto.setCreatedDatetime(rs.getTimestamp(9));
				dto.setModifiedDatetime(rs.getTimestamp(10));
				list.add(dto);
			}
			rs.close();
		} catch (Exception e) {
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception : Exception in search college");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		log.debug("Model search End");
		return list;

	}

	


	public CollegeDTO findByPk(long pk) throws ApplicationException {
		// TODO Auto-generated method stub
		Connection conn = null;
		CollegeDTO dto = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM ST_COLLEGE WHERE ID=?");
			ps.setLong(1, pk);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				dto = new CollegeDTO();
				dto.setId(rs.getLong(1));
				dto.setCollegeName(rs.getString(2));
				dto.setAddress(rs.getString(3));
				dto.setState(rs.getString(4));
				dto.setCity(rs.getString(5));
				dto.setPhoneNo(rs.getString(6));
				dto.setCreatedBy(rs.getString(7));
				dto.setModifiedBy(rs.getString(8));
				dto.setCreatedDatetime(rs.getTimestamp(9));
				dto.setModifiedDatetime(rs.getTimestamp(10));

			}
			rs.close();
		} catch (Exception e) {
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception : Exception in getting College by pk");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model findByPK End");
		return dto;
	}

	public CollegeDTO findByCollegeName(String name) throws ApplicationException {
		// TODO Auto-generated method stub
		Connection conn = null;
		CollegeDTO dto = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM ST_COLLEGE WHERE NAME=? ");
			ps.setString(1, name);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				dto = new CollegeDTO();
				dto.setId(rs.getLong(1));
				dto.setCollegeName(rs.getString(2));
				dto.setAddress(rs.getString(3));
				dto.setState(rs.getString(4));
				dto.setCity(rs.getString(5));
				dto.setPhoneNo(rs.getString(6));
				dto.setCreatedBy(rs.getString(7));
				dto.setModifiedBy(rs.getString(8));
				dto.setCreatedDatetime(rs.getTimestamp(9));
				dto.setModifiedDatetime(rs.getTimestamp(10));
			}

		} catch (Exception e) {
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception : Exception in getting College by Name");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model findByName End");
		return dto;
	}

}
