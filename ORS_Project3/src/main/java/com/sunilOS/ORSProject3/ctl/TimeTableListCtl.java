package com.sunilOS.ORSProject3.ctl;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.sunilOS.ORSProject3.dto.BaseDTO;
import com.sunilOS.ORSProject3.dto.TimeTableDTO;
import com.sunilOS.ORSProject3.exception.ApplicationException;
import com.sunilOS.ORSProject3.model.CourseModelInt;
import com.sunilOS.ORSProject3.model.ModelFactory;
import com.sunilOS.ORSProject3.model.SubjectModelInt;
import com.sunilOS.ORSProject3.model.TimeTableModelInt;
import com.sunilOS.ORSProject3.util.DataUtility;
import com.sunilOS.ORSProject3.util.PropertyReader;
import com.sunilOS.ORSProject3.util.ServletUtility;



/**
 * TimeTable functionality controller.to perform search and list operation.
 * @author Anshul
 *
 */
@WebServlet(name = "TimeTableListCtl", urlPatterns = { "/ctl/TimeTableListCtl" })
public class TimeTableListCtl extends BaseCtl {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(TimeTableListCtl.class);
      
	protected void preload(HttpServletRequest request){
		CourseModelInt courseModel=ModelFactory.getInstance().getCourseModel();
		SubjectModelInt subjectModel=ModelFactory.getInstance().getSubjectModel();
		try{
			List courseList=courseModel.list();
			List subjectList=subjectModel.list();
			request.setAttribute("subjectList", subjectList);
			request.setAttribute("courseList", courseList);
		}catch(Exception e){
			log.error(e);
		}
	}
	@Override
	protected BaseDTO populateDTO(HttpServletRequest request) {
		TimeTableDTO dto = new TimeTableDTO();
        dto.setId(DataUtility.getLong(request.getParameter("id")));
		//dto.setCourseName(DataUtility.getString(request.getParameter("courseId")));
		dto.setCourseId(DataUtility.getLong(request.getParameter("courseId")));
		dto.setExamDate(DataUtility.getStringToDate(request.getParameter("examDate")));
		//dto.setSubName(DataUtility.getString(request.getParameter("subjectId")));
		dto.setSubjectId(DataUtility.getInt(request.getParameter("subjectId")));
        
		return dto;
	}

	/**
	 * Contains Display logics
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug("Time table ctl doGet Start");
		List timeTableList = null;
		List nextTimeTableList = null;
		int pageNo = 1;
		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));

		TimeTableDTO dto = (TimeTableDTO) populateDTO(request);
		TimeTableModelInt model = ModelFactory.getInstance().getTimetableModel();
		try {
			timeTableList = model.search(dto, pageNo, pageSize);
			nextTimeTableList = model.search(dto, pageNo + 1, pageSize);


			if (timeTableList == null || timeTableList.size() == 0) {
				ServletUtility.setErrorMessage("No record found ", request);
			}
			if (nextTimeTableList == null || nextTimeTableList.size() == 0) {
				request.setAttribute("nextTimeTableListSize", 0);

			} else {
				request.setAttribute("nextTimeTableListSize", nextTimeTableList.size());
			}
			ServletUtility.setList(timeTableList, request);
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
		log.debug("Time table ctl doPOst End");
	}

	/**
	 * Contains Submit logics
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug("Time table ctl doPost Start");
		List timeTableList = null;
		List nextTimeTableList = null;
		int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
		int pageSize = DataUtility.getInt(request.getParameter("pageSize"));

		pageNo = (pageNo == 0) ? 1 : pageNo;
		pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader.getValue("page.size")) : pageSize;
		TimeTableDTO dto = (TimeTableDTO) populateDTO(request);
		String op = DataUtility.getString(request.getParameter("operation"));
		// get the selected checkbox ids array for delete list
		String[] ids = request.getParameterValues("ids");
		TimeTableModelInt model = ModelFactory.getInstance().getTimetableModel();
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
				ServletUtility.redirect(ORSView.TIMETABLE_CTL, request, response);
				return;
			} else if (OP_RESET.equalsIgnoreCase(op)) {

				ServletUtility.redirect(ORSView.TIMETABLE_LIST_CTL, request, response);
				return;
			} else if (OP_DELETE.equalsIgnoreCase(op)) {
				pageNo = 1;
				if (ids != null && ids.length > 0) {
					TimeTableDTO deletebean = new TimeTableDTO();
					for (String id : ids) {
						deletebean.setId(DataUtility.getLong(id));
						model.delete(deletebean);
						ServletUtility.setSuccessMessage("Data deleted successfully", request);
					}
				} else {
					ServletUtility.setErrorMessage("Select at least one record", request);
				}
				 
			}
			if (OP_BACK.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.TIMETABLE_LIST_CTL, request, response);
				return;
			} 
			dto = (TimeTableDTO)populateDTO(request);
			timeTableList = model.search(dto, pageNo, pageSize);
			
			ServletUtility.setDTO(dto, request);

			nextTimeTableList = model.search(dto, pageNo + 1, pageSize);
			ServletUtility.setList(timeTableList, request);
			if (timeTableList == null || timeTableList.size() == 0 && !OP_DELETE.equalsIgnoreCase(op)) {
				ServletUtility.setErrorMessage("No record found ", request);
			}
			if (nextTimeTableList == null || nextTimeTableList.size() == 0) {
				request.setAttribute("nextTimeTableListSize", 0);

			} else {
				request.setAttribute("nextTimeTableListSize", nextTimeTableList.size());
			}
			ServletUtility.setList(timeTableList, request);
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
		log.debug("Time table ctl doGet End");
	}

	@Override
	protected String getView() {
		return ORSView.TIMETABLE_LIST_VIEW;
	}

}

