package com.sunilOS.ORSProject3.ctl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.sunilOS.ORSProject3.dto.BaseDTO;
import com.sunilOS.ORSProject3.dto.RoleDTO;
import com.sunilOS.ORSProject3.dto.UserDTO;
import com.sunilOS.ORSProject3.exception.ApplicationException;
import com.sunilOS.ORSProject3.model.ModelFactory;
import com.sunilOS.ORSProject3.model.RoleModelInt;
import com.sunilOS.ORSProject3.model.UserModelInt;
import com.sunilOS.ORSProject3.util.DataUtility;
import com.sunilOS.ORSProject3.util.DataValidator;
import com.sunilOS.ORSProject3.util.PropertyReader;
import com.sunilOS.ORSProject3.util.ServletUtility;

/**
 * login functionality controller. performs login operation
 * @author Anshul
 *
 */

@WebServlet(urlPatterns = { "/LoginCtl" })
public class LoginCtl extends BaseCtl {
	private static final long serialVersionUID = 1L;
       
	public static final String OP_SIGN_IN = "SignIn";
	public static final String OP_SIGN_UP = "SignUp";
	public static final String OP_LOG_OUT = "LogOut";
	
	private static Logger log = Logger.getLogger(LoginCtl.class);
	
	@Override
		protected boolean validate(HttpServletRequest request) {
			
			boolean pass = true;
			String operation = DataUtility.getString(request.getParameter("operation"));
			if (operation.equalsIgnoreCase(OP_SIGN_UP) || operation.equalsIgnoreCase(OP_LOG_OUT))
			{
				return pass;
			}
			if (operation.equals(OP_SIGN_IN))
			{
			if (DataValidator.isNull(request.getParameter("email")))
			{
				request.setAttribute("email", PropertyReader.getValue("error.require", "Email id"));
				pass = false;
			}
			else if (!DataValidator.isEmail(request.getParameter("email")))
			{
				request.setAttribute("email", PropertyReader.getValue("error.email", "Email id"));
				pass = false;
			}
			if (DataValidator.isNull(request.getParameter("password")))
			{
				request.setAttribute("password", PropertyReader.getValue("error.require", "Password"));
				pass = false;
			}
			}
			return pass;
		}
	
	@Override
	protected BaseDTO populateDTO(HttpServletRequest request) {
		
		UserDTO dto = new UserDTO();
		dto.setId(DataUtility.getLong(request.getParameter("id")));
		dto.setEmail(DataUtility.getString(request.getParameter("email")));
		dto.setPassword(DataUtility.getString(request.getParameter("password")));
		return dto;
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		log.debug("LoginCtl Do Get method started");

		HttpSession session = request.getSession();
		String operation = DataUtility.getString(request.getParameter("operation"));

		if (OP_LOG_OUT.equals(operation)) {
			session.invalidate();
			ServletUtility.setSuccessMessage("YOU HAVE BEEN LOGGED-OUT SUCCESSFULLY", request);
			ServletUtility.forward(getView(), request, response);
			return;
		}

		ServletUtility.forward(getView(), request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		HttpSession session = request.getSession(true);
		String operation = DataUtility.getString(request.getParameter("operation"));
		UserModelInt userModel = ModelFactory.getInstance().getUserModel();
		RoleModelInt roleModel = ModelFactory.getInstance().getRoleModel();
		
		if (operation.equalsIgnoreCase(OP_SIGN_IN))
		{
			UserDTO userDto = (UserDTO) populateDTO(request);
			try
			{
				userDto = userModel.authenticate(userDto.getEmail(), userDto.getPassword());
				if (userDto != null)
				{
					session.setAttribute("user", userDto);
					long roleId = userDto.getRoleId();
					RoleDTO roleDto = roleModel.findByPK(roleId);
					if (roleDto != null)
					{
						session.setAttribute("role", roleDto.getRoleName());
					}
					String uri = request.getParameter("uri");
					if(uri == null || uri.equalsIgnoreCase("null"))
					{
						ServletUtility.redirect(ORSView.WELCOME_CTL, request, response);
						return;
					}
					else 
					{
						ServletUtility.redirect(uri, request, response);
						return;
					}
					}
				
				else
				{
					userDto = (UserDTO) populateDTO(request);
					ServletUtility.setDTO(userDto, request);
					ServletUtility.setErrorMessage("Invalid loginId and password", request);
				}
			}
			catch (ApplicationException e)
			{
				log.error(e);
				e.printStackTrace();
				ServletUtility.handleException(e, request, response);
				return;
			}
		}
		else if (operation.equalsIgnoreCase(OP_SIGN_UP)) {

			ServletUtility.redirect(ORSView.USER_REGISTRATION_CTL, request, response);
			return;

		}
		ServletUtility.forward(getView(), request, response);
	}
	
	@Override
	protected String getView() {
		
		return ORSView.LOGIN_VIEW;
	}
}
