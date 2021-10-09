package com.sunilOS.ORSProject3.ctl;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.sunilOS.ORSProject3.dto.BaseDTO;
import com.sunilOS.ORSProject3.dto.UserDTO;
import com.sunilOS.ORSProject3.exception.ApplicationException;
import com.sunilOS.ORSProject3.exception.DuplicateRecordException;
import com.sunilOS.ORSProject3.model.ModelFactory;
import com.sunilOS.ORSProject3.model.RoleModelInt;
import com.sunilOS.ORSProject3.model.UserModelInt;
import com.sunilOS.ORSProject3.util.DataUtility;
import com.sunilOS.ORSProject3.util.DataValidator;
import com.sunilOS.ORSProject3.util.PropertyReader;
import com.sunilOS.ORSProject3.util.ServletUtility;

/**
 * User functionality controller.to perform add,delete and update operation.
 * @author Anshul
 *
 */
@WebServlet(urlPatterns = { "/ctl/UserCtl" })
public class UserCtl extends BaseCtl {
	private static Logger log = Logger.getLogger(UserCtl.class);

	protected void preload(HttpServletRequest request) {
		RoleModelInt model = ModelFactory.getInstance().getRoleModel();
		try {
			List roleList = model.list();
			request.setAttribute("roleList", roleList);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	protected boolean validate(HttpServletRequest request) {
		boolean pass = true;
		System.out.println("validate started");
		if (DataValidator.isNull(request.getParameter("firstName"))) {
			request.setAttribute("firstName", PropertyReader.getValue("error.require", "First Name"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("lastName"))) {
			request.setAttribute("lastName", PropertyReader.getValue("error.require", "Last Name"));
			pass = false;
		} 
		if (DataValidator.isNull(request.getParameter("roleId"))) {
			request.setAttribute("roleId", PropertyReader.getValue("error.require", "Role"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("gender"))) {
			request.setAttribute("gender", PropertyReader.getValue("error.require", "Gender"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("mobileNo"))) {
			request.setAttribute("mobileNo", PropertyReader.getValue("error.require", "Mobile No."));
			pass = false;
		} else if (!DataValidator.isPhoneNo(request.getParameter("mobileNo"))) {
			request.setAttribute("mobileNo", "Please Enter Valid Mobile Number");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("email"))) {
			request.setAttribute("email", PropertyReader.getValue("error.require", "Email Id"));
			pass = false;
		} else if (!DataValidator.isEmail(request.getParameter("email"))) {
			request.setAttribute("email", PropertyReader.getValue("error.email", "Email Id "));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("dob"))) {
			request.setAttribute("dob", PropertyReader.getValue("error.require", "DOB"));
			pass = false;
		}else if (!DataValidator.isDate(request.getParameter("dob"))) {
			request.setAttribute("dob", PropertyReader.getValue("error.date", "DOB"));
			pass = false;
		}else if (!DataValidator.ageLimit(request.getParameter("dob"))) {
			request.setAttribute("dob", "Age Must be greater then 18 year");
			pass = false;
		}

		return pass;

	}

	protected BaseDTO populateDTO(HttpServletRequest request) {
		UserDTO dto = new UserDTO();
         String dates=request.getParameter("dob");
          String date=dates.trim();
		dto.setId(DataUtility.getLong(request.getParameter("id")));

		dto.setRoleId(DataUtility.getLong(request.getParameter("roleId")));

		dto.setFirstName(DataUtility.getString(request.getParameter("firstName")));

		dto.setLastName(DataUtility.getString(request.getParameter("lastName")));

		dto.setEmail(DataUtility.getString(request.getParameter("email")));

		dto.setGender(DataUtility.getString(request.getParameter("gender")));
		
		dto.setMobileNo(DataUtility.getString(request.getParameter("mobileNo")));
             
		dto.setDob(DataUtility.getStringToDate(date));
		
		populateBean(dto, request);
		
		log.debug("UserRegistrationCtl Method populatedto Ended");

		return dto;

	}
	
	/**
	 * Contain Display Logic
	 */

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
//		log.debug("UserCtl Method doGet Started");
		String op = DataUtility.getString(request.getParameter("operation"));
		// get model
		UserModelInt model = ModelFactory.getInstance().getUserModel();
		long id = DataUtility.getLong(request.getParameter("id"));
		if (id > 0 || op != null) {
			UserDTO dto;
			try {
				dto = model.findByPk(id);
				ServletUtility.setDTO(dto, request);
			} catch (Exception e) {
				e.printStackTrace();
//				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}
		}
		ServletUtility.forward(getView(), request, response);
	}
	
	/**
	 * Contain Submit Logic
	 */

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String op = DataUtility.getString(request.getParameter("operation"));
		// get model
		UserModelInt model = ModelFactory.getInstance().getUserModel();
		long id = DataUtility.getLong(request.getParameter("id"));
		if (OP_SAVE.equalsIgnoreCase(op)||OP_UPDATE.equalsIgnoreCase(op)) {
			UserDTO dto = (UserDTO) populateDTO(request);
			try {
				if (id > 0)
				{
					model.update(dto);
					ServletUtility.setDTO(dto, request);
					ServletUtility.setSuccessMessage("Data has successfully Updated", request);
					ServletUtility.forward(getView(), request, response);
					return;
				}
				else 
				{
					model.add(dto);
					ServletUtility.setDTO(dto, request);
					ServletUtility.setSuccessMessage("Data has successfully saved", request);
					ServletUtility.forward(getView(), request, response);
					return;
				}
				}
			 
			catch (DuplicateRecordException e) {
				ServletUtility.setDTO(dto, request);
				ServletUtility.setErrorMessage("Given email is already registered with us!", request);
				ServletUtility.forward(getView(), request, response);
			}
				
			catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
				} 
			}
		
	else if (OP_DELETE.equalsIgnoreCase(op)) {

			UserDTO dto = (UserDTO) populateDTO(request);
			try {
				model.delete(dto);
				ServletUtility.redirect(ORSView.USER_LIST_CTL, request, response);
				return;
			} catch (ApplicationException e) {
//				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.USER_LIST_CTL, request, response);
			return;
		} else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.USER_CTL, request, response);
			return;
		}

//		log.debug("UserCtl Method doPostEnded");
	}

	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.USER_VIEW;
	}

}
