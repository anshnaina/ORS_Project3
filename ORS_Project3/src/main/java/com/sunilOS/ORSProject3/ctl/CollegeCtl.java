package com.sunilOS.ORSProject3.ctl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sunilOS.ORSProject3.dto.BaseDTO;
import com.sunilOS.ORSProject3.dto.CollegeDTO;
import com.sunilOS.ORSProject3.exception.ApplicationException;
import com.sunilOS.ORSProject3.exception.DuplicateRecordException;
import com.sunilOS.ORSProject3.model.CollegeModelInt;
import com.sunilOS.ORSProject3.model.ModelFactory;
import com.sunilOS.ORSProject3.util.DataUtility;
import com.sunilOS.ORSProject3.util.DataValidator;
import com.sunilOS.ORSProject3.util.PropertyReader;
import com.sunilOS.ORSProject3.util.ServletUtility;

/**
 * college functionality ctl. To perform add,delete ,update operation
 * @author Anshul
 * 
 */

@WebServlet(urlPatterns = {"/ctl/CollegeCtl"})
public class CollegeCtl extends BaseCtl
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
//	private static Logger log = Logger.getLogger(CollegeCtl.class);

	protected boolean validate(HttpServletRequest request) {
		System.out.println("in validataion");
		boolean pass = true;
		if (DataValidator.isNull(request.getParameter("collegeName"))) {
			
			request.setAttribute("collegeName", PropertyReader.getValue("error.require", "College Name"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("city"))) {
			request.setAttribute("city", PropertyReader.getValue("error.require", "City"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("state"))) {
			request.setAttribute("state", PropertyReader.getValue("error.require", "State"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("address"))) {
			request.setAttribute("address", PropertyReader.getValue("error.require", "Address"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("mobileNo"))) {
			request.setAttribute("mobileNo", PropertyReader.getValue("error.require", "Mobile No"));
			pass = false;
			
		}
		System.out.println("out off validation "+pass);
		return pass;
		
	}

	protected BaseDTO populateDTO(HttpServletRequest request) {
		CollegeDTO dto = new CollegeDTO();
		System.out.println(request.getParameter("mobileNo"));
		dto.setId(DataUtility.getLong(request.getParameter("id")));
		dto.setCollegeName(request.getParameter("collegeName"));
		dto.setCity(request.getParameter("city"));
		dto.setAddress(request.getParameter("address"));
		dto.setState(request.getParameter("state"));
		dto.setPhoneNo(request.getParameter("mobileNo"));
		populateBean(dto, request);
		return dto;
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String op = request.getParameter("operation");
		long id = DataUtility.getLong(request.getParameter("id"));
		CollegeModelInt model=ModelFactory.getInstance().getCollegeModel();
		if (id > 0 || op != null) {
			CollegeDTO dto;
			try {
			  dto=model.findByPk(id);
			  ServletUtility.setDTO(dto, request);
				
			} catch (ApplicationException e) {
//				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}

		}
		ServletUtility.forward(getView(), request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
       String op=request.getParameter("operation");
       long id=DataUtility.getLong(request.getParameter("id"));
       CollegeModelInt model=ModelFactory.getInstance().getCollegeModel();
       if (OP_SAVE.equalsIgnoreCase(op)||OP_UPDATE.equalsIgnoreCase(op)) {
    	   CollegeDTO dto = (CollegeDTO) populateDTO(request);
			System.out.println("...===+" + id + ">>>>>>..." + dto);
			try {
				if (id > 0) {
					model.update(dto);
					ServletUtility.setDTO(dto, request);
					ServletUtility.setSuccessMessage("Data succefully update", request);
					ServletUtility.forward(getView(), request, response);
				} else {
					System.out.println("college add" + dto + "id...." + id);
					long pk = model.add(dto);
					ServletUtility.setSuccessMessage("Data successfully saved", request);
					ServletUtility.forward(getView(), request, response);
				}
				
			} catch (ApplicationException e) {
				e.printStackTrace();
//				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			} catch (DuplicateRecordException e) {
				ServletUtility.setDTO(dto, request);
				ServletUtility.setErrorMessage("College already exists", request);
				ServletUtility.forward(getView(), request, response);
			} 
		} 
//       else if (OP_DELETE.equalsIgnoreCase(op)) {
//			CollegeDTO dto = (CollegeDTO) populateDTO(request);
//			try {
//				model.delete(dto);
//				ServletUtility.forward(ORSView.COLLEGE_LIST_CTL, request, response);
//				return;
//			} catch (Exception e) {
////				log.error(e);
//				ServletUtility.handleException(e, request, response);
//				return;
//			}
//		}
       

   	else if (OP_RESET.equalsIgnoreCase(op)) {

   			ServletUtility.redirect(ORSView.COLLEGE_CTL, request, response);
   			return;

   		}

	else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.COLLEGE_LIST_CTL, request, response);
			return;

		}
		
	}

	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.COLLEGE_VIEW;
	}


}
