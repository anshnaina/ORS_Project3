package com.sunilOS.ORSProject3.ctl;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.sunilOS.ORSProject3.dto.BaseDTO;
import com.sunilOS.ORSProject3.dto.StudentDTO;
import com.sunilOS.ORSProject3.exception.ApplicationException;
import com.sunilOS.ORSProject3.model.CollegeModelInt;
import com.sunilOS.ORSProject3.model.ModelFactory;
import com.sunilOS.ORSProject3.model.StudentModelInt;
import com.sunilOS.ORSProject3.util.DataUtility;
import com.sunilOS.ORSProject3.util.PropertyReader;
import com.sunilOS.ORSProject3.util.ServletUtility;


/**
 * student functionality controller,to perform list and search operation
 * @author Anshul
 *
 */
@ WebServlet(name="StudentListCtl",urlPatterns={"/ctl/StudentListCtl"})
public class StudentListCtl extends BaseCtl {

   
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(StudentListCtl.class);
    
    protected void preload(HttpServletRequest request) {
		CollegeModelInt collegeModel = ModelFactory.getInstance().getCollegeModel();
		try {
			List collegeList = collegeModel.list();
			request.setAttribute("collegeList", collegeList);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    @Override
    protected BaseDTO populateDTO(HttpServletRequest request) {

        StudentDTO dto = new StudentDTO();
        dto.setId(DataUtility.getLong(request.getParameter("id")));
        dto.setFirstName(DataUtility.getString(request.getParameter("firstName")));
        dto.setLastName(DataUtility.getString(request.getParameter("lastName")));
        dto.setEmail(DataUtility.getString(request.getParameter("email")));
        dto.setCollegeId(DataUtility.getLong(request.getParameter("collegeId")));
        return dto;
    }

    /**
     * Contains Display logics
     */
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        log.debug("StudentListCtl doGet Start");
        List studentList = null;
        List nextStudentList = null;
        int pageNo = 1;

        int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));

        StudentDTO dto = (StudentDTO) populateDTO(request);

        String op = DataUtility.getString(request.getParameter("operation"));

        StudentModelInt model =ModelFactory.getInstance().getStudentModel();
        try {
        	studentList = model.search(dto, pageNo, pageSize);
        	nextStudentList = model.search(dto, pageNo+1, pageSize);
            if (studentList == null || studentList.size() == 0) {
                ServletUtility.setErrorMessage("No record found ", request);
            }if(nextStudentList==null||nextStudentList.size()==0){
				request.setAttribute("nextStudentListSize", 0);
				
			}else{
				request.setAttribute("nextStudentListSize", nextStudentList.size());
			}
            ServletUtility.setList(studentList, request);
            ServletUtility.setPageNo(pageNo, request);
            ServletUtility.setPageSize(pageSize, request);
            ServletUtility.forward(getView(), request, response);

        } catch (ApplicationException e) {
            log.error(e);
            ServletUtility.handleException(e, request, response);
            return;
        }
        log.debug("StudentListCtl doGet End");
    }

    /**
     * Contains Submit logics
     */
    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        log.debug("StudentListCtl doPost Start");
        List studentList = null;
        List nextStudentList = null;
        int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
        int pageSize = DataUtility.getInt(request.getParameter("pageSize"));
        pageNo = (pageNo == 0) ? 1 : pageNo;
        pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader
                .getValue("page.size")) : pageSize;

        StudentDTO dto = (StudentDTO) populateDTO(request);
        String op = DataUtility.getString(request.getParameter("operation"));
        StudentModelInt model =ModelFactory.getInstance().getStudentModel();
         String[] ids=request.getParameterValues("ids");
        try {

            if (OP_SEARCH.equalsIgnoreCase(op) || "Next".equalsIgnoreCase(op)
                    || "Previous".equalsIgnoreCase(op)) {

                if (OP_SEARCH.equalsIgnoreCase(op)) {
                    pageNo = 1;
                } else if (OP_NEXT.equalsIgnoreCase(op)) {
                    pageNo++;
                } else if (OP_PREVIOUS.equalsIgnoreCase(op) && pageNo > 1) {
                    pageNo--;
                }

            }else if (OP_NEW.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.STUDENT_CTL, request, response);
				return;
			}else if (OP_RESET.equalsIgnoreCase(op)) {

				ServletUtility.redirect(ORSView.STUDENT_LIST_CTL, request, response);
				return;
			} else if (OP_DELETE.equalsIgnoreCase(op)) {
				pageNo = 1;
				if (ids != null && ids.length > 0) {
					StudentDTO deletebean = new StudentDTO();
					for (String id : ids) {
						deletebean.setId(DataUtility.getLong(id));
						model.delete(deletebean);
						ServletUtility.setSuccessMessage("Data Delete Successfully", request);
					}
				} else {
					ServletUtility.setErrorMessage("Select at least one record", request);
				}
			}
            
			 if (OP_BACK.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.STUDENT_LIST_CTL, request, response);
				return;
			}
			 dto = (StudentDTO) populateDTO(request);
			studentList = model.search(dto, pageNo, pageSize);
            ServletUtility.setDTO(dto, request);
            nextStudentList = model.search(dto, pageNo+1, pageSize);
            if (studentList == null || studentList.size() == 0&&!OP_DELETE.equalsIgnoreCase(op)) {
                ServletUtility.setErrorMessage("No record found ", request);
            }if(nextStudentList==null||nextStudentList.size()==0){
				request.setAttribute("nextStudentListSize", 0);
				
			}else{
				request.setAttribute("nextStudentListSize", nextStudentList.size());
			}
            
            ServletUtility.setList(studentList, request);
            ServletUtility.setPageNo(pageNo, request);
            ServletUtility.setPageSize(pageSize, request);
            ServletUtility.forward(getView(), request, response);

        } catch (ApplicationException e) {
            log.error(e);
            ServletUtility.handleException(e, request, response);
            return;
        }
        log.debug("StudentListCtl doGet End");
    }

    @Override
    protected String getView() {
        return ORSView.STUDENT_LIST_VIEW;
    }
}

