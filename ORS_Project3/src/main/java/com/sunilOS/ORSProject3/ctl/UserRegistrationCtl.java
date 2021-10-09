package com.sunilOS.ORSProject3.ctl;

import java.io.IOException;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.sunilOS.ORSProject3.dto.BaseDTO;
import com.sunilOS.ORSProject3.dto.RoleDTO;
import com.sunilOS.ORSProject3.dto.UserDTO;
import com.sunilOS.ORSProject3.exception.ApplicationException;
import com.sunilOS.ORSProject3.exception.DuplicateRecordException;
import com.sunilOS.ORSProject3.model.ModelFactory;
import com.sunilOS.ORSProject3.model.UserModelInt;
import com.sunilOS.ORSProject3.util.DataUtility;
import com.sunilOS.ORSProject3.util.DataValidator;
import com.sunilOS.ORSProject3.util.PropertyReader;
import com.sunilOS.ORSProject3.util.ServletUtility;


@WebServlet(name = "UserRegistrationCtl", urlPatterns = { "/UserRegistrationCtl" })
public class UserRegistrationCtl extends BaseCtl {
	private static final long serialVersionUID = 1L;
	public static final String OP_SIGN_UP = "SignUp";
	
	private static Logger log=Logger.getLogger(UserRegistrationCtl.class);

	@Override
	protected boolean validate(HttpServletRequest request) {
		
		log.debug("UserRegistrationCtl validate method started");

		boolean pass = true;

		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");

		if (DataValidator.isNull(firstName)) {
		request.setAttribute("firstName", PropertyReader.getValue("error.require", "First name"));
			pass = false;
		} else if (!DataValidator.isNameWithSpace(firstName)) {
	request.setAttribute("firstName", PropertyReader.getValue("error.alphabet", "First name"));
			pass = false;
		}
		if (DataValidator.isNull(lastName)) {
	request.setAttribute("lastName", PropertyReader.getValue("error.require", "Last name"));
			pass = false;
		} else if (!DataValidator.isName2(lastName)) {
	request.setAttribute("lastName", PropertyReader.getValue("error.alphabet", "Last name"));
			pass = false;
		} else if (DataValidator.isWhiteSpace(lastName)) {
			request.setAttribute("lastName", PropertyReader.getValue("error.whitespace", "Last name"));
			pass = false;
		}

		if (DataValidator.isNull(email)) {
	request.setAttribute("email", PropertyReader.getValue("error.require", "Email id"));
			pass = false;
		} else if (!DataValidator.isEmail(email)) {
	request.setAttribute("email", PropertyReader.getValue("error.email", "Email id"));
			pass = false;

		}
		if (DataValidator.isNull(request.getParameter("password"))) {
	request.setAttribute("password", PropertyReader.getValue("error.require", "Password"));
			pass = false;
		} else if (!DataValidator.isPasslength(request.getParameter("password"))) {
request.setAttribute("password", PropertyReader.getValue("error.pwdlength", "Password"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("confirmPassword"))) {
			request.setAttribute("confirmPassword", PropertyReader.getValue("error.require", "Confirm password"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("gender"))) {
			request.setAttribute("gender", PropertyReader.getValue("error.require", "Gender"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("dob"))) {
			request.setAttribute("dob", PropertyReader.getValue("error.require", "dob"));
			pass = false;
			}else if (!DataValidator.isDate(request.getParameter("dob"))) {
				request.setAttribute("dob", PropertyReader.getValue("error.date", "Date Of Birth"));
				pass = false;
			}else if (!DataValidator.ageLimit(request.getParameter("dob"))) {
				request.setAttribute("dob", "Age Must be greater then 18 year");
				pass = false;
			}
		
		if (DataValidator.isNull(request.getParameter("mobileNo"))) {
			request.setAttribute("mobileNo", PropertyReader.getValue("error.require", "Mobile no."));
			pass = false;
		} else if (!DataValidator.isPhoneNo(request.getParameter("mobileNo"))) {
			request.setAttribute("mobileNo", "Please Enter Valid Mobile Number");
			pass = false;
		}
		
		if (!request.getParameter("password").equals(request.getParameter("confirmPassword"))
				&& !"".equals(request.getParameter("confirmPassword"))) {

			request.setAttribute("confirmPassword", "Confirm password does not match with password");
			pass = false;

		}
	return pass;
	}

	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		log.debug("UserRegistrationCtl Do Get method started");

		
		ServletUtility.forward(getView(), req, resp);
	}

	@Override
	protected BaseDTO populateDTO(HttpServletRequest request) {
		
		log.debug("UserRegistrationCtl populate bean method started");

		UserDTO userDTO = new UserDTO();
		
		  // Default Role set while User Registration
		userDTO.setRoleId(RoleDTO.STUDENT);
		userDTO.setId(DataUtility.getLong(request.getParameter("id")));
		userDTO.setFirstName(DataUtility.getString(request.getParameter("firstName")));
		userDTO.setLastName(DataUtility.getString(request.getParameter("lastName")));
		userDTO.setEmail(DataUtility.getString(request.getParameter("email")));
		userDTO.setPassword(DataUtility.getString(request.getParameter("password")));
		userDTO.setConfirmPassword(DataUtility.getString(request.getParameter("confirmPassword")));
		userDTO.setGender(DataUtility.getString(request.getParameter("gender")));
		userDTO.setDob(DataUtility.getStringToDate(request.getParameter("dob")));
		userDTO.setMobileNo(DataUtility.getString(request.getParameter("mobileNo")));
	
		populateBean(userDTO, request);
		
		return userDTO;
	}
	
protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		log.debug("UserRegistrationCtl Do Post method started");

		String operation = request.getParameter("operation");
		UserModelInt userModel = ModelFactory.getInstance().getUserModel();
		
		if(operation.equals(OP_SIGN_UP))
		{
			UserDTO userDto = (UserDTO) populateDTO(request);
			try 
			{
				long pk = userModel.registerUser(userDto);
				ServletUtility.setSuccessMessage("You have been registered successfully!", request);
				ServletUtility.forward(getView(), request, response);
				return;
			}
			catch (DuplicateRecordException e)
			{
				ServletUtility.setDTO(userDto, request);
				ServletUtility.setErrorMessage("Given email is already registered with us!", request);
				ServletUtility.forward(getView(), request, response);
			}
			catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
			else if (operation.equals(OP_RESET))
		{
				ServletUtility.redirect(ORSView.USER_REGISTRATION_CTL, request, response);
		}
	}

	@Override
	protected String getView() 
	{
		return ORSView.USER_REGISTRATION_VIEW;
	}
}
