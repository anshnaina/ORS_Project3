package com.sunilOS.ORSProject3.model;

import java.util.List;

import com.sunilOS.ORSProject3.dto.RoleDTO;
import com.sunilOS.ORSProject3.exception.ApplicationException;
import com.sunilOS.ORSProject3.exception.DuplicateRecordException;

public interface RoleModelInt {

	
	public long add (RoleDTO dto) throws ApplicationException, DuplicateRecordException;
	public void update (RoleDTO dto) throws ApplicationException, DuplicateRecordException;
	public void delete (RoleDTO dto) throws ApplicationException;
	public RoleDTO findByPK (long pk) throws ApplicationException;
	public RoleDTO findByRoleName (String name) throws ApplicationException;
	public List list() throws ApplicationException;
	public List list(int pageNo, int pageSize) throws ApplicationException;
	public List search (RoleDTO dto) throws ApplicationException;
	public List search (RoleDTO dto, int pageNo, int pageSize) throws ApplicationException;
	
}
