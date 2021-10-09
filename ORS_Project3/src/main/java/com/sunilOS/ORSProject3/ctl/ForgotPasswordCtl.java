package com.sunilOS.ORSProject3.ctl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sunilOS.ORSProject3.dto.BaseDTO;
import com.sunilOS.ORSProject3.dto.UserDTO;
import com.sunilOS.ORSProject3.exception.ApplicationException;
import com.sunilOS.ORSProject3.exception.RecordNotFoundException;
import com.sunilOS.ORSProject3.model.ModelFactory;
import com.sunilOS.ORSProject3.model.UserModelInt;
import com.sunilOS.ORSProject3.util.DataUtility;
import com.sunilOS.ORSProject3.util.DataValidator;
import com.sunilOS.ORSProject3.util.PropertyReader;
import com.sunilOS.ORSProject3.util.ServletUtility;

/**
 * Forgot password ctl.To perform password send in email
 * 
 * @author Anshul
 *
 */

@WebServlet(urlPatterns = { "/ForgotPasswordCtl" })
public class ForgotPasswordCtl extends BaseCtl {

	private static final long serialVersionUID = 1L;
//	private static Logger log = Logger.getLogger(ForgetPasswordCtl.class);

	protected boolean validate(HttpServletRequest request) {
		boolean pass = true;
		if (DataValidator.isNull(request.getParameter("email"))) {
			request.setAttribute("email", PropertyReader.getValue("error.require", "Email Id"));
			pass = false;
		} else if (!DataValidator.isEmail(request.getParameter("email"))) {
			request.setAttribute("email", PropertyReader.getValue("error.email", "Email"));
			pass = false;
		}
		return pass;

	}

	protected BaseDTO populateDTO(HttpServletRequest request) {
		UserDTO dto = new UserDTO();
		dto.setEmail(DataUtility.getString(request.getParameter("email")));
		return dto;

	}

	/**
	 *  Display Concepts
	 *
	 */

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
//		log.debug("do get method started");
		ServletUtility.forward(getView(), request, response);
	}
	
	/**
	 * Submit Concepts
	 */

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
//		log.debug("do get method started");
		String op = request.getParameter("operation");
		UserModelInt userModel = ModelFactory.getInstance().getUserModel();
		UserDTO dto = (UserDTO) populateDTO(request);
		if (OP_GO.equalsIgnoreCase(op)) {
			try {
				boolean flag = userModel.forgotPassword(dto.getEmail());
				ServletUtility.setSuccessMessage("Password has been sent to your email id", request);
			} catch (RecordNotFoundException e) {
				ServletUtility.setErrorMessage(e.getMessage(), request);
//				log.error(e);
			} catch (ApplicationException e) {
				System.out.println(e.getMessage());
//				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}
			ServletUtility.forward(getView(), request, response);
		}

	}

	@Override
	protected String getView() {
		return ORSView.FORGOT_PASSWORD_VIEW;
	}
}
