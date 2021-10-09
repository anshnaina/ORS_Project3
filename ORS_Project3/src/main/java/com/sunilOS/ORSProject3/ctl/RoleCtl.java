package com.sunilOS.ORSProject3.ctl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.sunilOS.ORSProject3.dto.BaseDTO;
import com.sunilOS.ORSProject3.dto.RoleDTO;
import com.sunilOS.ORSProject3.exception.ApplicationException;
import com.sunilOS.ORSProject3.exception.DuplicateRecordException;
import com.sunilOS.ORSProject3.model.ModelFactory;
import com.sunilOS.ORSProject3.model.RoleModelInt;
import com.sunilOS.ORSProject3.util.DataUtility;
import com.sunilOS.ORSProject3.util.DataValidator;
import com.sunilOS.ORSProject3.util.PropertyReader;
import com.sunilOS.ORSProject3.util.ServletUtility;


/**
 * role functionality controller.to perform add,delete ,update operation
 * @author Anshul
 *
 */
@WebServlet(urlPatterns = { "/ctl/RoleCtl" })
public class RoleCtl extends BaseCtl {

	private static final long serialVersionUID = 1L;


	private static Logger log = Logger.getLogger(RoleCtl.class);

	protected boolean validate(HttpServletRequest request) {

		log.debug("RoleCtl Method validate Started");

		boolean pass = true;
		if (DataValidator.isNull(request.getParameter("roleName"))) {
			request.setAttribute("roleName", PropertyReader.getValue("error.require", "Role Name"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("description"))) {
			request.setAttribute("description", PropertyReader.getValue("error.require", "Description"));
			pass = false;
		}

		log.debug("RoleCtl Method validate Ended");

		return pass;
	}

	protected BaseDTO populateDTO(HttpServletRequest request) {
		RoleDTO dto = new RoleDTO();
		dto.setId(DataUtility.getLong(request.getParameter("id")));

		dto.setRoleName(DataUtility.getString(request.getParameter("roleName")));
		dto.setDescription(DataUtility.getString(request.getParameter("description")));
		populateBean(dto, request);
		return dto;

	}
	
	/**
	 * Display Logic
	 */

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String op = request.getParameter("operation");
		long id = DataUtility.getLong(request.getParameter("id"));
		RoleModelInt model = ModelFactory.getInstance().getRoleModel();
		if (id > 0 || op != null) {
			RoleDTO dto;
			try {
				dto = model.findByPK(id);
				ServletUtility.setDTO(dto, request);

			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}

		}
		ServletUtility.forward(getView(), request, response);
	}

	
	/**
	 * Submit Logic
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String op = request.getParameter("operation");
		long id = DataUtility.getLong(request.getParameter("id"));
		RoleModelInt model = ModelFactory.getInstance().getRoleModel();
		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

			RoleDTO dto = (RoleDTO) populateDTO(request);
			try {
				if (id > 0) {
					model.update(dto);
					ServletUtility.setDTO(dto, request);
					ServletUtility.setSuccessMessage("Data is successfully Update", request);
					ServletUtility.forward(getView(), request, response);
					return;
				} 
				else 
					{
						long pk = model.add(dto);
						ServletUtility.setDTO(dto, request);
						ServletUtility.setSuccessMessage("Data is successfully saved", request);
						ServletUtility.forward(getView(), request, response);
						return;
					} 

				
			} 
			catch (DuplicateRecordException e) {
				ServletUtility.setDTO(dto, request);
				ServletUtility.setErrorMessage("Role already exists", request);
				ServletUtility.forward(getView(), request, response);

			}
			catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}
			

		} else if (OP_DELETE.equalsIgnoreCase(op)) {

			RoleDTO dto = (RoleDTO) populateDTO(request);
			try {
				model.delete(dto);
				ServletUtility.redirect(ORSView.ROLE_LIST_CTL, request, response);
				return;
			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.ROLE_LIST_CTL, request, response);
			return;

		} else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.ROLE_CTL, request, response);
			return;

		}


		log.debug("RoleCtl Method doPOst Ended");
	}

	@Override
	protected String getView() {
		return ORSView.ROLE_VIEW;
	}



}
