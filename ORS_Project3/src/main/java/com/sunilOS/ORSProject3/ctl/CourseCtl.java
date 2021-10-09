package com.sunilOS.ORSProject3.ctl;

import java.io.IOException;

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
import com.sunilOS.ORSProject3.util.DataValidator;
import com.sunilOS.ORSProject3.util.PropertyReader;
import com.sunilOS.ORSProject3.util.ServletUtility;


/**
 * Course functionality ctl.to perform add,delete ,update operation
 * @author Anshul
 *
 */

@WebServlet(urlPatterns={"/ctl/CourseCtl"})
public class CourseCtl extends BaseCtl{
	private static Logger log = Logger.getLogger(CourseCtl.class);

	protected boolean validate(HttpServletRequest request) {
		log.debug("course ctl validate start");
		boolean pass = true;
		if (DataValidator.isNull(request.getParameter("courseName"))) {
			request.setAttribute("courseName", PropertyReader.getValue("error.require", "Course name"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("courseDescription"))) {
			request.setAttribute("courseDescription", PropertyReader.getValue("error.require", "Course description"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("courseDuration"))) {
			request.setAttribute("courseDuration", PropertyReader.getValue("error.require", "Course duration"));
			pass = false;
		}
		log.debug("course ctl validate end");
		return pass;
	}

	protected BaseDTO populateDTO(HttpServletRequest request) {
		log.debug("course ctl populate bean start");
		CourseDTO dto = new CourseDTO();
		dto.setId(DataUtility.getLong(request.getParameter("id")));
		dto.setCourseName(DataUtility.getString(request.getParameter("courseName")));
		dto.setCourseDescription(DataUtility.getString(request.getParameter("courseDescription")));
		dto.setCourseDuration(DataUtility.getString(request.getParameter("courseDuration")));
		populateBean(dto, request);
		log.debug("course ctl populate bean end");
				
		return dto;

	}
	
	
	 /**
     * Display logic inside it
     */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		log.debug("course ctl do get start");
		String op = DataUtility.getString(request.getParameter("operation"));
		long id = DataUtility.getLong(request.getParameter("id"));
		CourseModelInt model = ModelFactory.getInstance().getCourseModel();
		if (id > 0 || op != null) {
			CourseDTO dto;
			try {
				dto = model.findByPk(id);
				ServletUtility.setDTO(dto, request);
			} catch (Exception e) {
				e.printStackTrace();
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}
		}
		ServletUtility.forward(getView(), request, response);
		log.debug("course ctl do get end");
	}
	 /**
     * Submit logic inside it
     */

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		log.debug("course ctl do post start");
		String op = DataUtility.getString(request.getParameter("operation"));
		long id = DataUtility.getLong(request.getParameter("id"));
		CourseModelInt model =ModelFactory.getInstance().getCourseModel() ;
		if (OP_SAVE.equalsIgnoreCase(op)||OP_UPDATE.equalsIgnoreCase(op)) {
			CourseDTO dto=(CourseDTO) populateDTO(request);
			try {
				if (id > 0) {
				   model.update(dto);
				   dto.setId(id);
					ServletUtility.setSuccessMessage("Data successfully updated", request);
					ServletUtility.setDTO(dto, request);
				} else {
				
					try {
						 model.add(dto);
						ServletUtility.setSuccessMessage("Data successfully saved", request);
//						ServletUtility.setDto(dto, request);
					} catch (ApplicationException e) {
						log.error(e);
						ServletUtility.handleException(e, request, response);
						return;
					} 
//					catch (DuplicateRecordException e) {
//						ServletUtility.setDto(dto, request);
//						ServletUtility.setErrorMessage("course  already exists", request);
//					}
				}
				
			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			} 
		}else if(OP_DELETE.equalsIgnoreCase(op)){
			CourseDTO dto=(CourseDTO) populateDTO(request);
			try{
				model.delete(dto);
				ServletUtility.redirect(ORSView.COURSE_LIST_CTL, request, response);
				return;
			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}
	}else if(OP_CANCEL.equalsIgnoreCase(op)){
		ServletUtility.redirect(ORSView.COURSE_LIST_CTL, request, response);
		return;
		
	}else if(OP_RESET.equalsIgnoreCase(op)){
		ServletUtility.redirect(ORSView.COURSE_CTL, request, response);
		return;
		
	}
		ServletUtility.forward(getView(), request, response);

	log.debug("course ctl do post end");

	}
	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.COURSE_VIEW;
	}

}
