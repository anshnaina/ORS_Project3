package com.sunilOS.ORSProject3.model;

import java.util.List;

import com.sunilOS.ORSProject3.dto.MarksheetDTO;
import com.sunilOS.ORSProject3.exception.ApplicationException;
import com.sunilOS.ORSProject3.exception.DuplicateRecordException;


public interface MarksheetModelInt {

	
	public long add(MarksheetDTO dto) throws ApplicationException, DuplicateRecordException;
	public void update(MarksheetDTO dto) throws ApplicationException, DuplicateRecordException;
	public void delete(MarksheetDTO dto) throws ApplicationException;
	public MarksheetDTO findByRollNo(String rollNo) throws ApplicationException;
	public MarksheetDTO findByPk(long pk) throws ApplicationException;
	public List list() throws ApplicationException;
	public List list(int pageNo,int pageSize) throws ApplicationException;
	public List search(MarksheetDTO dto) throws ApplicationException;
	public List search(MarksheetDTO dto,int pageNo,int pageSize) throws ApplicationException;
	public List getMeritList(int pageNo,int pageSize) throws ApplicationException;
}
