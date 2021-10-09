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
import com.sunilOS.ORSProject3.model.ModelFactory;
import com.sunilOS.ORSProject3.model.RoleModelInt;
import com.sunilOS.ORSProject3.model.UserModelInt;
import com.sunilOS.ORSProject3.util.DataUtility;
import com.sunilOS.ORSProject3.util.PropertyReader;
import com.sunilOS.ORSProject3.util.ServletUtility;


/**
 * User List functionality controller.to perform Search and List operation.
 * @author Anshul
 *
 */
@WebServlet(name = "UserListCtl", urlPatterns = { "/ctl/UserListCtl" })
public class UserListCtl extends BaseCtl {

	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(UserListCtl.class);

	protected void preload(HttpServletRequest request) {
		RoleModelInt roleModel = ModelFactory.getInstance().getRoleModel();
		try {
			List roleList = roleModel.list();
			request.setAttribute("roleList", roleList);
		} catch (Exception e) {
			log.error(e);

		}
	}

	@Override
	protected BaseDTO populateDTO(HttpServletRequest request) {
		UserDTO dto = new UserDTO();

		dto.setFirstName(DataUtility.getString(request.getParameter("firstName")));
		dto.setLastName(DataUtility.getString(request.getParameter("lastName")));
		dto.setEmail(DataUtility.getString(request.getParameter("email")));
		dto.setRoleId(DataUtility.getLong(request.getParameter("roleId")));

		return dto;
	}

	/**
	 * Contains Display logics
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug("UserListCtl doGet Start");
		List userList;
		List nextUserList;
		int pageNo = 1;
		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));
		UserDTO dto = (UserDTO) populateDTO(request);
		// get the selected checkbox ids array for delete list
		UserModelInt model = ModelFactory.getInstance().getUserModel();
		try {
			userList = model.search(dto, pageNo, pageSize);
			nextUserList = model.search(dto, pageNo + 1, pageSize);
			if (userList == null || userList.size() == 0) {
				ServletUtility.setErrorMessage("No record found", request);
			}
			if (nextUserList == null || nextUserList.size() == 0) {
				request.setAttribute("nextUserListSize", 0);

			} else {
				request.setAttribute("nextUserListSize", nextUserList.size());
			}
			ServletUtility.setList(userList, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			ServletUtility.forward(getView(), request, response);
		} catch (ApplicationException e) {
			log.error(e);
			ServletUtility.handleException(e, request, response);
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}
		log.debug("UserListCtl doPOst End");
	}

	/**
	 * Contains Submit logics
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug("UserListCtl doPost Start");
		List userList = null;
		List nextUserList = null;
		int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
		int pageSize = DataUtility.getInt(request.getParameter("pageSize"));

		pageNo = (pageNo == 0) ? 1 : pageNo;
		pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader.getValue("page.size")) : pageSize;
		UserDTO dto = (UserDTO) populateDTO(request);
		String op = DataUtility.getString(request.getParameter("operation"));
		System.out.println("op---->" + op);

		// get the selected checkbox ids array for delete list
		String[] ids = request.getParameterValues("ids");
		UserModelInt model = ModelFactory.getInstance().getUserModel();
		try {

			if (OP_SEARCH.equalsIgnoreCase(op) || "Next".equalsIgnoreCase(op) || "Previous".equalsIgnoreCase(op)) {

				if (OP_SEARCH.equalsIgnoreCase(op)) {
					pageNo = 1;
				} else if (OP_NEXT.equalsIgnoreCase(op)) {
					pageNo++;
				} else if (OP_PREVIOUS.equalsIgnoreCase(op) && pageNo > 1) {
					pageNo--;
				}

			} else if (OP_NEW.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.USER_CTL, request, response);
				return;
			} else if (OP_RESET.equalsIgnoreCase(op)) {

				ServletUtility.redirect(ORSView.USER_LIST_CTL, request, response);
				return;
			} else if (OP_DELETE.equalsIgnoreCase(op)) {
				pageNo = 1;
				if (ids != null && ids.length > 0) {
					UserDTO deletedto = new UserDTO();
					for (String id : ids) {
						deletedto.setId(DataUtility.getLong(id));
						model.delete(deletedto);
						ServletUtility.setSuccessMessage("Data deleted successfully", request);
					}
				} else {
					ServletUtility.setErrorMessage("Select at least one record", request);
				}
			}
			if (OP_BACK.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.USER_LIST_CTL, request, response);
				return;
			}
			dto = (UserDTO) populateDTO(request);
			
			userList = model.search(dto, pageNo, pageSize);

			ServletUtility.setDTO(dto, request);
			nextUserList = model.search(dto, pageNo + 1, pageSize);

			if (userList == null || userList.size() == 0 && !OP_DELETE.equalsIgnoreCase(op))
			{
					ServletUtility.setErrorMessage("No record found", request);
			}
			
			if (nextUserList == null || nextUserList.size() == 0) {
				request.setAttribute("nextUserListSize", 0);

			} else {
				request.setAttribute("nextUserListSize", nextUserList.size());
			}
			ServletUtility.setList(userList, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			ServletUtility.forward(getView(), request, response);

		} catch (ApplicationException e) {
			log.error(e);
			ServletUtility.handleException(e, request, response);
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}
		log.debug("UserListCtl doGet End");
	}

	@Override
	protected String getView() {
		return ORSView.USER_LIST_VIEW;
	}

}
