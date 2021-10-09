package com.sunilOS.ORSProject3.ctl;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.sunilOS.ORSProject3.dto.BaseDTO;
import com.sunilOS.ORSProject3.dto.FacultyDTO;
import com.sunilOS.ORSProject3.exception.ApplicationException;
import com.sunilOS.ORSProject3.model.CollegeModelInt;
import com.sunilOS.ORSProject3.model.CourseModelInt;
import com.sunilOS.ORSProject3.model.FacultyModelInt;
import com.sunilOS.ORSProject3.model.ModelFactory;
import com.sunilOS.ORSProject3.util.DataUtility;
import com.sunilOS.ORSProject3.util.PropertyReader;
import com.sunilOS.ORSProject3.util.ServletUtility;


/**
 * faculty list functionality ctl.To perform show,search and delete operation
 * @author Anshul
 *
 */

@WebServlet(name = "FacultyListCtl", urlPatterns = { "/ctl/FacultyListCtl" })
public class FacultyListCtl extends BaseCtl {
	
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(FacultyListCtl.class);
     
	protected void preload(HttpServletRequest request) {
		CollegeModelInt collegeModel=ModelFactory.getInstance().getCollegeModel();
		CourseModelInt courseModel=ModelFactory.getInstance().getCourseModel();
		try {
			List collegeList=collegeModel.list();
			request.setAttribute("collegeList", collegeList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	protected BaseDTO populateDTO(HttpServletRequest request) {
		log.debug("Faculty Ctl populateBean start");
		FacultyDTO dto = new FacultyDTO();
        dto.setId(DataUtility.getLong(request.getParameter("id")));
		dto.setFirstName(DataUtility.getString(request.getParameter("firstName")));
		dto.setLastName(DataUtility.getString(request.getParameter("lastName")));
		dto.setEmail(DataUtility.getString(request.getParameter("email")));
		dto.setCollegeId(DataUtility.getLong(request.getParameter("collegeId")));
       
		log.debug("Faculty Ctl populateBean end");
		return dto;

	}

	/**
	 * contain display logic
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		log.debug("Faculty Ctl do get start");
		List facultyList = null;
		List nextFacultyList = null;
		int pageNo = 1;
		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));
		FacultyDTO dto = (FacultyDTO) populateDTO(request);
		FacultyModelInt model = ModelFactory.getInstance().getFacultyModel();
		try {
			facultyList = model.search(dto, pageNo, pageSize);
			nextFacultyList = model.search(dto, pageNo + 1, pageSize);
			if (facultyList == null || facultyList.size() == 0) {
				ServletUtility.setErrorMessage("No record found", request);
			}
			if (nextFacultyList == null || nextFacultyList.size() == 0) {
				request.setAttribute("nextFacultyListSize", 0);

			} else {
				request.setAttribute("nextFacultyListSize", nextFacultyList.size());
			}
			ServletUtility.setList(facultyList, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			ServletUtility.forward(getView(), request, response);
		} catch (ApplicationException e) {
			log.error(e);
			ServletUtility.handleException(e, request, response);
			return;

		} catch (Exception e) {
			e.printStackTrace();
		}

		log.debug("Faculty Ctl do get end");
	}

	/**
	 * Contains submit logic
	 */

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		log.debug("Faculty Ctl do post start");
		List facultyList = null;
		List nextFacultyList = null;
		int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
		int pageSize = DataUtility.getInt(request.getParameter("pageSize"));
		String op = DataUtility.getString(request.getParameter("operation"));
		pageNo = (pageNo == 0) ? 1 : pageNo;
		pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader.getValue("page.size")) : pageSize;
		FacultyDTO dto = (FacultyDTO) populateDTO(request);
		FacultyModelInt model = ModelFactory.getInstance().getFacultyModel();
		String[] ids = request.getParameterValues("ids");
		try {
			if (OP_SEARCH.equalsIgnoreCase(op) || "Next".equalsIgnoreCase(op) || "Previous".equalsIgnoreCase(op)) {
				if (OP_SEARCH.equalsIgnoreCase(op)) {
					pageNo = 1;
				} else if ("Next".equalsIgnoreCase(op)) {
					pageNo++;
				} else if ("Previous".equalsIgnoreCase(op) && pageNo > 1) {
					pageNo--;
				}
			} else if (OP_NEW.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.FACULTY_CTL, request, response);
				return;
			} else if (OP_BACK.equalsIgnoreCase(op)) {
				System.out.println("kiljjj");
				ServletUtility.redirect(ORSView.FACULTY_LIST_CTL, request, response);
				return;
			} else if (OP_RESET.equalsIgnoreCase(op)) {

				ServletUtility.redirect(ORSView.FACULTY_LIST_CTL, request, response);
				return;
			} else if (OP_DELETE.equalsIgnoreCase(op)) {
				System.out.println("helloooo"+ids);
				pageNo = 1;
				if (ids != null && ids.length > 0) {
					FacultyDTO deleteBean = new FacultyDTO();
					for (String id : ids) {
						deleteBean.setId(DataUtility.getLong(id));
						model.delete(deleteBean);
						ServletUtility.setSuccessMessage("Data Delete Successfully", request);
					}
				} else {
					ServletUtility.setErrorMessage("select at least one record", request);
				}
			}
			dto = (FacultyDTO) populateDTO(request);
			facultyList = model.search(dto, pageNo, pageSize);
			ServletUtility.setDTO(dto, request);
			nextFacultyList = model.search(dto, pageNo + 1, pageSize);
			if (facultyList == null || facultyList.size() == 0&&!OP_DELETE.equalsIgnoreCase(op)) {
				ServletUtility.setErrorMessage("NO Record Found", request);

			}
			if (nextFacultyList == null || nextFacultyList.size() == 0) {
				request.setAttribute("nextFacultyListSize", 0);

			} else {
				request.setAttribute("nextFacultyListSize", nextFacultyList.size());
			}
			ServletUtility.setList(facultyList, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			ServletUtility.forward(getView(), request, response);

		} catch (ApplicationException e) {
			log.error(e);
			ServletUtility.handleException(e, request, response);
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}
		log.debug("Faculty Ctl do post end");
	}

	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.FACULTY_LIST_VIEW;
	}

}

