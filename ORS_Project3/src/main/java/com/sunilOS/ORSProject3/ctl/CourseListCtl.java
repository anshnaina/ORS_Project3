package com.sunilOS.ORSProject3.ctl;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.sunilOS.ORSProject3.dto.BaseDTO;
import com.sunilOS.ORSProject3.dto.CourseDTO;
import com.sunilOS.ORSProject3.exception.ApplicationException;
import com.sunilOS.ORSProject3.model.CourseModelInt;
import com.sunilOS.ORSProject3.model.ModelFactory;
import com.sunilOS.ORSProject3.util.DataUtility;
import com.sunilOS.ORSProject3.util.PropertyReader;
import com.sunilOS.ORSProject3.util.ServletUtility;


/**
 * course list functionality ctl. search and delete,show list operation
 * @author Anshul
 *
 */

@WebServlet(name = "CourseListCtl", urlPatterns = { "/ctl/CourseListCtl" })
public class CourseListCtl extends BaseCtl {
	private static Logger log = Logger.getLogger("CourseListCtl.class");
	
	
	protected BaseDTO populateDTO(HttpServletRequest request) {
		CourseDTO dto = new CourseDTO();
		dto.setId(DataUtility.getLong(request.getParameter("id")));
		dto.setCourseName(DataUtility.getString(request.getParameter("courseName")));
		dto.setCourseDuration(DataUtility.getString(request.getParameter("courseDuration")));
		return dto;

	}

	/**
	 * Display Logics inside this method
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		log.debug("Course ctl do get start");
		List courseList=null;
		List nextCourseList=null;
		int pageNo = 1;
		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));
		CourseDTO dto = (CourseDTO) populateDTO(request);
		CourseModelInt model = ModelFactory.getInstance().getCourseModel();
		try {
			courseList = model.search(dto, pageNo, pageSize);
			ServletUtility.setDTO(dto, request);
			nextCourseList = model.search(dto, pageNo + 1, pageSize);
			if (courseList == null || courseList.size() == 0) {
				ServletUtility.setErrorMessage("No record found", request);
			}
			if (nextCourseList == null || nextCourseList.size() == 0) {
				request.setAttribute("nextCourseListSize", 0);
			} else {
				request.setAttribute("nextCourseListSize", nextCourseList.size());
			}
			ServletUtility.setList(courseList, request);
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

		log.debug("Course ctl do get end");
	}

	/**
	 * Submit logic inside it
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		log.debug("Course List do post start");
		List courseList=null;
		List nextCourseList=null;
		int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
		int pageSize = DataUtility.getInt(request.getParameter("pageSize"));

		pageNo = (pageNo == 0) ? 1 : pageNo;
		pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader.getValue("page.size")) : pageSize;
		CourseDTO dto = (CourseDTO) populateDTO(request);
		String op = DataUtility.getString(request.getParameter("operation"));
		String[] ids = request.getParameterValues("ids");
		CourseModelInt model =ModelFactory.getInstance().getCourseModel();
		try {
			if (OP_SEARCH.equalsIgnoreCase(op) || "Next".equalsIgnoreCase(op) || "Previous".equalsIgnoreCase(op)) {

				if (OP_SEARCH.equalsIgnoreCase(op)) {
					pageNo = 1;
				} else if (OP_NEXT.equalsIgnoreCase(op)) {
					pageNo++;
				} else if (OP_PREVIOUS.equalsIgnoreCase(op) && pageNo > 1) {
					pageNo--;
				}
			} else if (OP_NEW.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.COURSE_CTL, request, response);
				return;
			} else if (OP_RESET.equalsIgnoreCase(op)) {

				ServletUtility.redirect(ORSView.COURSE_LIST_CTL, request, response);
				return;
			} else if (OP_BACK.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.COURSE_LIST_CTL, request, response);
				return;
			} else if (OP_DELETE.equalsIgnoreCase(op)) {
				pageNo = 1;
				if (ids != null && ids.length > 0) {
					CourseDTO deletebean = new CourseDTO();
					for (String id : ids) {
						deletebean.setId(DataUtility.getLong(id));
						model.delete(deletebean);
						ServletUtility.setSuccessMessage("Data deleted successfully", request);
					}

				} else {
					ServletUtility.setErrorMessage("Select atleast one record", request);
				}
			}
			dto = (CourseDTO) populateDTO(request);
			courseList = model.search(dto, pageNo, pageSize);
			ServletUtility.setDTO(dto, request);
			 nextCourseList = model.search(dto, pageNo + 1, pageSize);
			if (courseList == null || courseList.size() == 0&&!OP_DELETE.equalsIgnoreCase(op)) {
				ServletUtility.setErrorMessage("No record found", request);
			}
			if (nextCourseList == null || nextCourseList.size() == 0) {
				request.setAttribute("nextCourseListSize", "0");
			} else {
				request.setAttribute("nextCourseListSize", nextCourseList.size());
			}
			ServletUtility.setList(courseList, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			ServletUtility.forward(getView(), request, response);
		} catch (ApplicationException e) {
			log.error(e);
			ServletUtility.handleException(e, request, response);
			return;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		log.debug("Course List do post end");
	}

	@Override
	protected String getView() {
		return ORSView.COURSE_LIST_VIEW;
	}

}

