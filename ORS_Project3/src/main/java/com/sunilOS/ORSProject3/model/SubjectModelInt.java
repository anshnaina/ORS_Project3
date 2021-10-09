package com.sunilOS.ORSProject3.model;

import java.util.List;

import com.sunilOS.ORSProject3.dto.SubjectDTO;
import com.sunilOS.ORSProject3.exception.ApplicationException;
import com.sunilOS.ORSProject3.exception.DuplicateRecordException;


public interface SubjectModelInt {

	
	public long add(SubjectDTO dto) throws ApplicationException, DuplicateRecordException;
	public void update(SubjectDTO dto) throws ApplicationException, DuplicateRecordException;
	public void delete(SubjectDTO dto) throws ApplicationException;
	public SubjectDTO findByPk(long pk) throws ApplicationException;
	public SubjectDTO findBySubjectName(String name) throws ApplicationException;
	public List list() throws ApplicationException;
	public List list(int pageNo,int pageSize) throws ApplicationException;
	public List search(SubjectDTO dto) throws ApplicationException;
	public List search(SubjectDTO dto,int pageNo,int pageSize) throws ApplicationException;
	
	
}
