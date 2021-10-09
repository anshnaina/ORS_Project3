package com.sunilOS.ORSProject3.ctl;

/**
 * Base controller class of project. It contain 
 * (1) Generic operations 
 * (2)Generic constants 
 * (3) Generic work flow
 * @author Anshul
 *
 */

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import org.apache.log4j.Logger;

import com.sunilOS.ORSProject3.dto.BaseDTO;
import com.sunilOS.ORSProject3.dto.UserDTO;
import com.sunilOS.ORSProject3.util.DataUtility;
import com.sunilOS.ORSProject3.util.DataValidator;
import com.sunilOS.ORSProject3.util.ServletUtility;


public abstract class BaseCtl extends HttpServlet {
	private static final long serialVersionUID = 1L;
//	private static Logger log = Logger.getLogger(BaseCtl.class);
	
	public static final String OP_SAVE = "Save";
    public static final String OP_CANCEL = "Cancel";
    public static final String OP_DELETE = "Delete";
    public static final String OP_LIST = "List";
    public static final String OP_SEARCH = "Search";
    public static final String OP_EDIT = "Edit";
    public static final String OP_VIEW = "View";
    public static final String OP_NEXT = "Next";
    public static final String OP_PREVIOUS = "Previous";
    public static final String OP_RESET = "Reset";
    public static final String OP_NEW = "New";
    public static final String OP_GO = "Go";
    public static final String OP_BACK = "Back";
    public static final String OP_LOG_OUT = "Logout";
	public static final String OP_UPDATE = "Update";
	public static final String OP_CHANGE_MY_PROFILE = "MyProfile";

    /**
     * Success message key constant
     */
    public static final String MSG_SUCCESS = "success";

    /**
     * Error message key constant
     */
    public static final String MSG_ERROR = "error";

    /**
     * Validates input data entered by User
     * 
     * @param request
     * @return
     */
    protected boolean validate(HttpServletRequest request) {
        return true;
    }

    /**
     * Loads list and other data required to display at HTML form
     * 
     * @param request
     */
    protected void preload(HttpServletRequest request) {
  }

    /**
     * Populates bean object from request parameters
     * 
     * @param request
     * @return
     */
    protected BaseDTO populateDTO(HttpServletRequest request) {
        return null;
    }

    /**
	 * Populates Generic attributes in DTO
	 *
	 * @param dto
	 * @param request
	 * @return BaseBean
	 */
	protected BaseDTO populateBean(BaseDTO dto, HttpServletRequest request) {
		String createdBy = request.getParameter("createdBy");
		String modifiedBy = null;

		UserDTO userDTO = (UserDTO) request.getSession().getAttribute("user");

		if (userDTO == null) {
			// If record is created without login
			createdBy = "root";
			modifiedBy = "root";

		} else {
			modifiedBy = userDTO.getEmail();	
			// if record is created first time
			if ("null".equalsIgnoreCase(createdBy) || DataValidator.isNull(createdBy)) {
				createdBy = modifiedBy;
			}
		}
		dto.setCreatedBy(createdBy);
		dto.setModifiedBy(modifiedBy);

		long cdt = DataUtility.getLong(request.getParameter("createdDateTime"));

		if (cdt > 0) 
		{
			dto.setCreatedDatetime(DataUtility.getCurrentTimestamp());
		}
		else
		{
			dto.setCreatedDatetime(DataUtility.getCurrentTimestamp());
		}
			dto.setModifiedDatetime(DataUtility.getCurrentTimestamp());

		return dto;

	}
	
    
    @Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
    	
	//	log.debug("BaseCtl Service Method Starts");

		// Load the preloaded data required to display at HTML form
		preload(request);

		String operation = DataUtility.getString(request.getParameter("operation"));
		
		// Check if operation is not DELETE, VIEW, CANCEL, and NULL then
		// perform input data validation

		if (DataValidator.isNotNull(operation) && !OP_CANCEL.equalsIgnoreCase(operation) && !OP_VIEW.equalsIgnoreCase(operation)
				&& !OP_DELETE.equalsIgnoreCase(operation) && !OP_RESET.equalsIgnoreCase(operation)) {
			// Check validation, If fail then send back to page with error
			// messages
			try {
				if (!validate(request)) {
					BaseDTO baseDTO = populateDTO(request);
					ServletUtility.setDTO(baseDTO, request);
					ServletUtility.forward(getView(), request, response);
					return;

				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		super.service(request, response);
	}
	/**
	 * Returns the VIEW page of this Controller
	 *
	 * @return String
	 */
	protected abstract String getView();

}
