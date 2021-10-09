package com.sunilOS.ORSProject3.ctl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.sunilOS.ORSProject3.dto.BaseDTO;
import com.sunilOS.ORSProject3.dto.MarksheetDTO;
import com.sunilOS.ORSProject3.exception.ApplicationException;
import com.sunilOS.ORSProject3.model.MarksheetModelInt;
import com.sunilOS.ORSProject3.model.ModelFactory;
import com.sunilOS.ORSProject3.util.DataUtility;
import com.sunilOS.ORSProject3.util.DataValidator;
import com.sunilOS.ORSProject3.util.PropertyReader;
import com.sunilOS.ORSProject3.util.ServletUtility;


/**
 * Get marksheet functionality ctl.to perform  get marksheet opeation
 * @author Anshul
 *
 */

@WebServlet(name = "MarksheetGetCtl", urlPatterns = { "/ctl/MarksheetGetCtl" })
public class MarksheetGetCtl extends BaseCtl {
	
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(MarksheetGetCtl.class);

	protected boolean validate(HttpServletRequest request) {
		log.debug("get marksheet validate start");
		boolean pass = true;
		if (DataValidator.isNull(request.getParameter("rollNo"))) {
			request.setAttribute("rollNo", PropertyReader.getValue("error.require", "Roll No"));
			pass = false;
		}
		log.debug("get marksheet validate start");
		return pass;
	}

	protected BaseDTO populateDTO(HttpServletRequest request) {
		log.debug("get Marksheet populate bean start");
		MarksheetDTO dto = new MarksheetDTO();

		dto.setId(DataUtility.getLong(request.getParameter("id")));
		dto.setRollNo(DataUtility.getString(request.getParameter("rollNo")));
		dto.setName(DataUtility.getString(request.getParameter("name")));
		dto.setPhysics(DataUtility.getInt(request.getParameter("physics")));
		dto.setChemistry(DataUtility.getInt(request.getParameter("chemistry")));
		dto.setMaths(DataUtility.getInt(request.getParameter("maths")));

		return dto;
	}
	 /**
     * Concept of Display method
     *
     */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		log.debug("marksheet ctl do get start");
		ServletUtility.forward(getView(), request, response);
		log.debug("marksheet ctl do get end");
	}
	 /**
     * Concept of submit method
     *
     */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		log.debug("marksheet ctl do post start");
		System.out.println("get marksheet do post <><>>");
		String op = DataUtility.getString(request.getParameter("operation"));
		long id = DataUtility.getLong(request.getParameter("id"));
		MarksheetModelInt model = ModelFactory.getInstance().getMarksheetModel();
		MarksheetDTO dto = (MarksheetDTO) populateDTO(request);
		if (OP_GO.equalsIgnoreCase(op)) {
			try {
				dto = model.findByRollNo(dto.getRollNo());
				if (dto != null) {
					ServletUtility.setDTO(dto, request);
				} else {
					ServletUtility.setErrorMessage("Roll No does not exist", request);
				}
			} catch (ApplicationException e) {
				e.printStackTrace();
				log.equals(e);
				ServletUtility.handleException(e, request, response);
				return;
			}
		}
		ServletUtility.forward(getView(), request, response);
		log.debug("marksheet ctl do post end");
	}

	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.MARKSHEET_GET_VIEW;
	}

}

