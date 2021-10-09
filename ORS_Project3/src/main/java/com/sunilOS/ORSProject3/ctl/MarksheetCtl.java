package com.sunilOS.ORSProject3.ctl;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.sunilOS.ORSProject3.dto.BaseDTO;
import com.sunilOS.ORSProject3.dto.MarksheetDTO;
import com.sunilOS.ORSProject3.exception.ApplicationException;
import com.sunilOS.ORSProject3.exception.DuplicateRecordException;
import com.sunilOS.ORSProject3.model.MarksheetModelInt;
import com.sunilOS.ORSProject3.model.ModelFactory;
import com.sunilOS.ORSProject3.model.StudentModelInt;
import com.sunilOS.ORSProject3.util.DataUtility;
import com.sunilOS.ORSProject3.util.DataValidator;
import com.sunilOS.ORSProject3.util.PropertyReader;
import com.sunilOS.ORSProject3.util.ServletUtility;


/**
 * Marksheet functionality controller.to perform add,delete and update operation
 * @author Anshul
 *
 */
@WebServlet(urlPatterns = { "/ctl/MarksheetCtl" })
public class MarksheetCtl extends BaseCtl {

	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(MarksheetCtl.class);

	protected void preload(HttpServletRequest request) {
		StudentModelInt model = ModelFactory.getInstance().getStudentModel();
		try {
			List studentList = model.list();
			request.setAttribute("studentList", studentList);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
		}
	}

	protected boolean validate(HttpServletRequest request) {
		log.debug("marksheet validate bean start");
		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("rollNo"))) {
			request.setAttribute("rollNo", PropertyReader.getValue("error.require", "Roll No"));
			pass = false;
		} else if (!DataValidator.isRollNo(request.getParameter("rollNo"))) {
			request.setAttribute("rollNo", "Please Enter Valid Roll No");
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("studentId"))) {
			request.setAttribute("studentId", PropertyReader.getValue("error.require", "Student Name"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("physics"))) {
			request.setAttribute("physics", "Physics marks is required");
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("maths"))) {
			request.setAttribute("maths", "Maths marks is required");
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("chemistry"))) {
			request.setAttribute("chemistry", "Chemistry marks is required");
			pass = false;
		}

		if (DataValidator.isNotNull(request.getParameter("physics"))
				&& !DataValidator.isInteger(request.getParameter("physics"))) {
			request.setAttribute("physics", "Physics marks must be a number");
			pass = false;

		}
		if (DataUtility.getInt(request.getParameter("physics")) > 100
				|| DataUtility.getInt(request.getParameter("physics")) < 0) {

			request.setAttribute("physics", "marks must be between 0-100");
			pass = false;
		}
		if (DataValidator.isNotNull(request.getParameter("chemistry"))
				&& !DataValidator.isInteger(request.getParameter("chemistry"))) {
			request.setAttribute("chemistry", "Chemistry marks must be a number");
			pass = false;
		}
		if (DataUtility.getInt(request.getParameter("chemistry")) > 100
				|| DataUtility.getInt(request.getParameter("chemistry")) < 0) {
			request.setAttribute("chemistry", "marks must be between 0-100");
			pass = false;
		}
		if (DataValidator.isNotNull(request.getParameter("maths"))
				&& !DataValidator.isInteger(request.getParameter("maths"))) {
			request.setAttribute("maths", "Maths marks must be a number");
			pass = false;
		}
		if (DataUtility.getInt(request.getParameter("maths")) > 100
				|| DataUtility.getInt(request.getParameter("maths")) < 0) {

			request.setAttribute("maths", "marks must be between 0-100");
			pass = false;
		}
		log.debug("marksheet validate bean end");
		return pass;

	}

	protected BaseDTO populateDTO(HttpServletRequest request) {
		log.debug("marksheet populate bean start");
		MarksheetDTO dto = new MarksheetDTO();
//		String id2=request.getParameter("studentName");
//		String id3=id2.trim();
		dto.setId(DataUtility.getLong(request.getParameter("id")));
		dto.setRollNo(request.getParameter("rollNo"));
		dto.setStudentId(DataUtility.getLong(request.getParameter("studentId")));
		//dto.setStudentId(DataUtility.getLong(id3));		
		dto.setPhysics(DataUtility.getInt(request.getParameter("physics")));
		dto.setChemistry(DataUtility.getInt(request.getParameter("chemistry")));
		dto.setMaths(DataUtility.getInt(request.getParameter("maths")));
		populateBean(dto, request);
		log.debug("marksheet populate bean end");
		return dto;

	}

	/**
	 * Display Logics 
	 */

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		log.debug("marksheet ctl doget  start");
		String op = DataUtility.getString(request.getParameter("operation"));
		long id = DataUtility.getLong(request.getParameter("id"));
		MarksheetModelInt model = ModelFactory.getInstance().getMarksheetModel();
		if (id > 0) {
			MarksheetDTO dto;
			try {
				dto = model.findByPk(id);
				ServletUtility.setDTO(dto, request);
			} catch (ApplicationException e) {
				e.printStackTrace();
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}
		}
		ServletUtility.forward(getView(), request, response);
		log.debug("marksheet ctl doget  end");
	}

	/**
	 * Submit logic 
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		log.debug("marksheet ctl dopost  start");
		String op = DataUtility.getString(request.getParameter("operation"));
		long id = DataUtility.getLong(request.getParameter("id"));
		System.out.println("--------"+id);
		MarksheetModelInt model = ModelFactory.getInstance().getMarksheetModel();
		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {
			MarksheetDTO dto = (MarksheetDTO) populateDTO(request);
			try {
				if (id > 0) {
					model.update(dto);
					ServletUtility.setSuccessMessage("Data is successfully Updated", request);
					ServletUtility.setDTO(dto, request);
				} else {					
					long pk = model.add(dto);
						dto.setId(pk);
					ServletUtility.setSuccessMessage("Data is successfully saved", request);
				}
				

			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			} catch (DuplicateRecordException e) {
				ServletUtility.setDTO(dto, request);
				ServletUtility.setErrorMessage("Roll no already exists", request);
			}

		}
//		else if (OP_DELETE.equalsIgnoreCase(op)) {
//			MarksheetDTO dto = (MarksheetDTO) populateDTO(request);
//			try {
//				model.delete(dto);
//				ServletUtility.redirect(ORSView.MARKSHEET_LIST_CTL, request, response);
//				return;
//			} catch (ApplicationException e) {
//				System.out.println("in catch");
//				log.error(e);
//				ServletUtility.handleException(e, request, response);
//				return;
//			}
//
//		}
		else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.MARKSHEET_CTL, request, response);
			return;
		} else if (OP_CANCEL.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.MARKSHEET_LIST_CTL, request, response);
			return;
		}
		ServletUtility.forward(getView(), request, response);

		log.debug("marksheet ctl dopost  end");
	}

	@Override
	protected String getView() {
		return ORSView.MARKSHEET_VIEW;
	}


}
