package com.sunilOS.ORSProject3.model;

import java.util.List;

import com.sunilOS.ORSProject3.dto.UserDTO;
import com.sunilOS.ORSProject3.exception.ApplicationException;
import com.sunilOS.ORSProject3.exception.DuplicateRecordException;
import com.sunilOS.ORSProject3.exception.RecordNotFoundException;

public interface UserModelInt {

	public long add (UserDTO dto) throws ApplicationException, DuplicateRecordException;
	public void delete(UserDTO dto) throws ApplicationException;
	public void update(UserDTO dto) throws ApplicationException, DuplicateRecordException;
	public UserDTO findByPk(long pk) throws ApplicationException;
	public UserDTO findByEmail(String email) throws ApplicationException;
	public List list() throws ApplicationException;
	public List list(int pageNo,int PageSize) throws ApplicationException;
	public List search(UserDTO dto) throws ApplicationException;
	public List search(UserDTO dto,int pageNo,int PageSize) throws ApplicationException;
	public boolean changePassword(long pk, String newPassword, String oldPassword) throws ApplicationException, RecordNotFoundException, DuplicateRecordException;
	public UserDTO authenticate(String email, String password) throws ApplicationException;
	public boolean forgotPassword(String email) throws ApplicationException, RecordNotFoundException;
	public long registerUser (UserDTO dto) throws ApplicationException, DuplicateRecordException;	
}
