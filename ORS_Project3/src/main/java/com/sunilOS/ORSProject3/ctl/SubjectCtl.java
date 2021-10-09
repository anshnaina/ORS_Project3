package com.sunilOS.ORSProject3.ctl;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.sunilOS.ORSProject3.dto.BaseDTO;
import com.sunilOS.ORSProject3.dto.SubjectDTO;
import com.sunilOS.ORSProject3.exception.ApplicationException;
import com.sunilOS.ORSProject3.exception.DuplicateRecordException;
import com.sunilOS.ORSProject3.model.CourseModelInt;
import com.sunilOS.ORSProject3.model.ModelFactory;
import com.sunilOS.ORSProject3.model.SubjectModelInt;
import com.sunilOS.ORSProject3.util.DataUtility;
import com.sunilOS.ORSProject3.util.DataValidator;
import com.sunilOS.ORSProject3.util.PropertyReader;
import com.sunilOS.ORSProject3.util.ServletUtility;


/**
 * subject functionality controller.to perform add,delete and update operation.
 * @author Anshul
 *
 */
@WebServlet(urlPatterns = { "/ctl/SubjectCtl" })
public class SubjectCtl extends BaseCtl {
	
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(SubjectCtl.class);

	protected void preload(HttpServletRequest request) {
		CourseModelInt model = ModelFactory.getInstance().getCourseModel();
		try {
			List list = model.list();
			request.setAttribute("courseList", list);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
		}
	}

	protected boolean validate(HttpServletRequest request) {
		log.debug("course ctl validate start");
		boolean pass = true;
		if (DataValidator.isNull(request.getParameter("courseId"))) {
			request.setAttribute("courseId", PropertyReader.getValue("error.require", "Course Name"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("subjectName"))) {
			request.setAttribute("subjectName", PropertyReader.getValue("error.require", "Subject Name"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("description"))) {
			request.setAttribute("description", PropertyReader.getValue("error.require", "Description"));
			pass = false;
		}
		log.debug("course ctl validate end");
		return pass;
	}

	protected BaseDTO populateDTO(HttpServletRequest request) {
		log.debug("course ctl populate bean start");
		SubjectDTO dto = new SubjectDTO();
		dto.setId(DataUtility.getLong(request.getParameter("id")));
		dto.setCourseId(DataUtility.getLong(request.getParameter("courseId")));
		dto.setSubjectName(DataUtility.getString(request.getParameter("subjectName")));
		dto.setDescription(DataUtility.getString(request.getParameter("description")));
		populateBean(dto, request);
		log.debug("course ctl populate bean end");

		return dto;

	}

	/**
	 * Display Logics inside this method
	 */

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		log.debug("course ctl doget start");
		SubjectModelInt model = ModelFactory.getInstance().getSubjectModel();
		long id = DataUtility.getLong(request.getParameter("id"));
		if (id > 0) {
			SubjectDTO dto;
			try {
				dto = model.findByPk(id);
			   ServletUtility.setDTO(dto, request);
			} catch (Exception e) {
				log.error(e);
				e.printStackTrace();
				ServletUtility.handleException(e, request, response);
				return;
			}
		}
		ServletUtility.forward(getView(), request, response);
		log.debug("course ctl doget end");
	}

	/**
	 * Submit logic inside it
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		log.debug("course ctl dopost start");
		System.out.println("inside dopost");
		String op = DataUtility.getString(request.getParameter("operation"));

		long id = DataUtility.getLong(request.getParameter("id"));
		System.out.println(op);
		SubjectModelInt model = ModelFactory.getInstance().getSubjectModel();
		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) 
		{
			System.out.println("inside save");
			SubjectDTO dto = (SubjectDTO) populateDTO(request);
			try {
				if (id > 0) {

					model.update(dto);
					ServletUtility.setDTO(dto, request);
					ServletUtility.setSuccessMessage("Data successfully updated", request);
				}
				
	else {
					long pk;
						pk = model.add(dto);
						ServletUtility.setDTO(dto, request);
						ServletUtility.setSuccessMessage("Data successfully saved", request);
		
			}
			}
			catch (Exception e) {
				log.error(e);
				e.printStackTrace();
			}
		}
//		else if(OP_DELETE.equalsIgnoreCase(op)){
//			SubjectDTO dto=(SubjectDTO) populateDTO(request);
//			try{
//				model.delete(dto);
//				ServletUtility.redirect(getView(), request, response);
//			}catch (ApplicationException e) {
//				log.error(e);
//				ServletUtility.handleException(e, request, response);
//				return;
//			}
//		}
		else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.SUBJECT_CTL, request, response);
			return;
		} else if (OP_CANCEL.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.SUBJECT_LIST_CTL, request, response);
			return;
		}
		ServletUtility.forward(getView(), request, response);
		log.debug("course ctl dopost end");
	}
		
	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.SUBJECT_VIEW;
	}

}
