package com.sunilOS.ORSProject3.ctl;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sunilOS.ORSProject3.dto.BaseDTO;
import com.sunilOS.ORSProject3.dto.CollegeDTO;
import com.sunilOS.ORSProject3.exception.ApplicationException;
import com.sunilOS.ORSProject3.model.CollegeModelInt;
import com.sunilOS.ORSProject3.model.ModelFactory;
import com.sunilOS.ORSProject3.util.DataUtility;
import com.sunilOS.ORSProject3.util.PropertyReader;
import com.sunilOS.ORSProject3.util.ServletUtility;

/**
 * college list ctl.to perform search and show list operation
 * 
 * @author Anshul
 *
 */
@WebServlet(name = "CollegeListCtl", urlPatterns = { "/ctl/CollegeListCtl" })
public class CollegeListCtl extends BaseCtl {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
//	private static Logger log = Logger.getLogger(CollegeListCtl.class);

	protected BaseDTO populateDTO(HttpServletRequest request) {
		System.out.println("college list populate Bean");
//		log.debug("college list populate bean start");
		CollegeDTO dto = new CollegeDTO();
		dto.setId(DataUtility.getLong(request.getParameter("id")));
		dto.setCity(DataUtility.getString(request.getParameter("city")));
		dto.setCollegeName(DataUtility.getString(request.getParameter("collegeName")));

		return dto;
	}

	/**
	 * Display Logics inside this method
	 */

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		System.out.println("college list do get start");
//		log.debug("college list do get start");

		int pageNo = 1;
		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));
		CollegeDTO dto = (CollegeDTO) populateDTO(request);
		CollegeModelInt model = ModelFactory.getInstance().getCollegeModel();
		List collegeList = null;
		List nextCollegeList = null;
		try {
			collegeList = model.search(dto, pageNo, pageSize);
			nextCollegeList = model.search(dto, pageNo + 1, pageSize);

			if (collegeList == null || collegeList.size() == 0) {
				ServletUtility.setErrorMessage("No record found ", request);
			}
			if (nextCollegeList == null || nextCollegeList.size() == 0) {
				request.setAttribute("nextCollegeListSize", 0);
			} else {
				request.setAttribute("nextCollegeListSize", nextCollegeList.size());
			}

			ServletUtility.setList(collegeList, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			ServletUtility.forward(getView(), request, response);
		} catch (ApplicationException e) {
//			log.error(e);
			System.out.println(e);
			ServletUtility.handleException(e, request, response);
			return;
		}

//		log.debug("college list do get end");

	}

	/**
	 * Submit logic inside it
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		System.out.println("college list do post start");
//		log.debug("college list do post start");
		List collegeList;
		List nextCollegeList;
		int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
		int pageSize = DataUtility.getInt(request.getParameter("pageSize"));

		pageNo = (pageNo == 0) ? 1 : pageNo;

		pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader.getValue("page.size")) : pageSize;
		CollegeDTO dto = (CollegeDTO) populateDTO(request);
		String op = DataUtility.getString(request.getParameter("operation"));

		String[] ids = request.getParameterValues("ids");
		CollegeModelInt model = ModelFactory.getInstance().getCollegeModel();
		try {
			if (OP_SEARCH.equalsIgnoreCase(op) || "next".equalsIgnoreCase(op) || "previous".equalsIgnoreCase(op)) {
				if (OP_SEARCH.equalsIgnoreCase(op)) {
					pageNo = 1;

				} else if (OP_NEXT.equalsIgnoreCase(op)) {
					pageNo++;
				} else if (OP_PREVIOUS.equalsIgnoreCase(op)) {
					pageNo--;
				}
			} else if (OP_NEW.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.COLLEGE_CTL, request, response);
				return;
			} else if (OP_RESET.equalsIgnoreCase(op)) {

				ServletUtility.redirect(ORSView.COLLEGE_LIST_CTL, request, response);
				return;
			} else if (OP_BACK.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.COLLEGE_LIST_CTL, request, response);
				return;
			} else if (OP_DELETE.equalsIgnoreCase(op)) {
				pageNo = 1;
				if (ids != null && ids.length > 0) {
					CollegeDTO deletedto = new CollegeDTO();
					for (String id : ids) {
						deletedto.setId(DataUtility.getLong(id));
						model.delete(deletedto);
						ServletUtility.setSuccessMessage("Data Deleted Successfully", request);
//						ServletUtility.forward(getView(), request, response);

					}
				} else {
					ServletUtility.setErrorMessage("Select at least one record", request);
//					ServletUtility.forward(getView(), request, response);
				}
			}
			dto = (CollegeDTO) populateDTO(request);
			collegeList = model.search(dto, pageNo, pageSize);
			ServletUtility.setDTO(dto, request);
			nextCollegeList = model.search(dto, pageNo + 1, pageSize);
			if (collegeList == null || collegeList.size() == 0 && !OP_DELETE.equalsIgnoreCase(op)) 
				{
					ServletUtility.setErrorMessage("No record found ", request);
				}
			
			if (nextCollegeList == null || nextCollegeList.size() == 0) {
				request.setAttribute("nextCollegeListSize", 0);
			} else {
				request.setAttribute("nextCollegeListSize", nextCollegeList.size());
			}

			ServletUtility.setList(collegeList, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			ServletUtility.forward(getView(), request, response);
		} catch (ApplicationException e) {
//			log.error(e);
			ServletUtility.handleException(e, request, response);
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}

//		log.debug("college list do post end");
	}

	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.COLLEGE_LIST_VIEW;
	}

}
