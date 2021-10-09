package com.sunilOS.ORSProject3.ctl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sunilOS.ORSProject3.dto.UserDTO;
import com.sunilOS.ORSProject3.exception.ApplicationException;
import com.sunilOS.ORSProject3.exception.DuplicateRecordException;
import com.sunilOS.ORSProject3.exception.RecordNotFoundException;
import com.sunilOS.ORSProject3.model.ModelFactory;
import com.sunilOS.ORSProject3.model.UserModelInt;
import com.sunilOS.ORSProject3.util.DataUtility;
import com.sunilOS.ORSProject3.util.DataValidator;
import com.sunilOS.ORSProject3.util.PropertyReader;
import com.sunilOS.ORSProject3.util.ServletUtility;


/**
 * change password operation functionality perform
 * @author Anshul
 *
 */

@WebServlet(urlPatterns = {"/ctl/ChangePasswordCtl"})
public class ChangePasswordCtl extends BaseCtl{

	
	private static final long serialVersionUID = 1L;
	
	
	protected boolean validate(HttpServletRequest request) {
//		log.debug("change password validate method start");
		boolean pass = true;
		String operation = request.getParameter("operation");
		if (OP_CHANGE_MY_PROFILE.equalsIgnoreCase(operation)) {
			return pass;
		}
		
		if (DataValidator.isNull(request.getParameter("oldPassword"))) {
			request.setAttribute("oldPassword",  PropertyReader.getValue("error.require", "Old Password"));
			pass = false;
		} else if (!DataValidator.isPassword(request.getParameter("oldPassword"))) {
			request.setAttribute("oldPassword", "Please Enter valid Password");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("newPassword"))) {
			request.setAttribute("newPassword", PropertyReader.getValue("error.require", "New Password"));
			pass = false;
		} else if (!DataValidator.isPassword(request.getParameter("newPassword"))) {
			request.setAttribute("newPassword", "Please Enter vaild Password");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("confirmPassword"))) {
			request.setAttribute("confirmPassword", PropertyReader.getValue("error.require", "Confirm Password"));
			pass = false;
		}
		if (!request.getParameter("newPassword").equals(request.getParameter("confirmPassword"))
				&& !"".equals(request.getParameter("confirmPassword"))) {
			ServletUtility.setErrorMessage("New and confirm passwords not matched", request);
			pass = false;

		}
//		log.debug("validate method end");
		return pass;
	}

	
	 /**
     * Display Logics inside this method
     */
	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException
	{ 
		ServletUtility.forward(getView(), request, response);	
	}
	
	
	/**
     * Submit logic inside it
     */
	
	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException{
		HttpSession session=request.getSession();
//		log.debug("change password do post start");
		String op=DataUtility.getString(request.getParameter("operation"));
		UserModelInt model=ModelFactory.getInstance().getUserModel();
		
		UserDTO UserBean=(UserDTO)session.getAttribute("user");
		String newPassword=request.getParameter("newPassword");
		String oldPassword=request.getParameter("oldPassword");
		long id=UserBean.getId();
		if(OP_SAVE.equalsIgnoreCase(op)){
			try{
				model.changePassword(id,newPassword,oldPassword);
				ServletUtility.setSuccessMessage("Password has been changed successfully", request);
			}catch (ApplicationException e) {
//                log.error(e);
                ServletUtility.handleException(e, request, response);
                return;

            } catch (RecordNotFoundException e) {
                ServletUtility.setErrorMessage("Old password is Invalid", request);
            } catch (Exception e) {
				e.printStackTrace();
			}
			
		}  else if (OP_CHANGE_MY_PROFILE.equalsIgnoreCase(op)) {
            ServletUtility.redirect(ORSView.MY_PROFILE_CTL, request, response);
            return;
        }

        ServletUtility.forward(ORSView.CHANGE_PASSWORD_VIEW, request, response);
//        log.debug("ChangePasswordCtl Method doGet Ended");
		
	}

	@Override
	protected String getView() {
		return ORSView.CHANGE_PASSWORD_VIEW;
	}

}
