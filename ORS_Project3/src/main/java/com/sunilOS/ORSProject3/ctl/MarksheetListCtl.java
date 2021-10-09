package com.sunilOS.ORSProject3.ctl;

import java.io.IOException;
import java.util.List;

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
import com.sunilOS.ORSProject3.util.PropertyReader;
import com.sunilOS.ORSProject3.util.ServletUtility;


/**
 * marksheet functionality ctl.to show list of marksheet
 * 
 * @author Anshul
 *
 */
@WebServlet(name = "MarksheetListCtl", urlPatterns = { "/ctl/MarksheetListCtl" })
public class MarksheetListCtl extends BaseCtl {

	
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(MarksheetListCtl.class);


    @Override
    protected BaseDTO populateDTO(HttpServletRequest request) {
        MarksheetDTO dto = new MarksheetDTO();
        dto.setId(DataUtility.getLong(request.getParameter("id")));
        dto.setRollNo(DataUtility.getString(request.getParameter("rollNo")));
        //dto.setStudentId(DataUtility.getLong(request.getParameter("studentId")));
        dto.setName(DataUtility.getString(request.getParameter("name")));      
        return dto;
    }

    /**
     * ContainsDisplaylogics
     */
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
    	
    	List marksheetList = null;
    	List nextMarksheetList = null;
    	
    	int pageNo = 1;
		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));

        MarksheetDTO dto = (MarksheetDTO) populateDTO(request);

        MarksheetModelInt model = ModelFactory.getInstance().getMarksheetModel();
        try {
        	marksheetList = model.search(dto, pageNo, pageSize);
            nextMarksheetList = model.search(dto, pageNo + 1, pageSize);
        
        if (marksheetList == null || marksheetList.size() == 0) {
            ServletUtility.setErrorMessage("No record found", request);
        }if(nextMarksheetList==null||nextMarksheetList.size()==0){
			request.setAttribute("nextMarksheetListSize", 0);
			
		}else{
			request.setAttribute("nextMarksheetListSize", nextMarksheetList.size());
		}
        ServletUtility.setList(marksheetList, request);
        ServletUtility.setPageNo(pageNo, request);
        ServletUtility.setPageSize(pageSize, request);
        ServletUtility.forward(getView(), request, response);
        

    }
    
    catch (ApplicationException e) {
		log.error(e);
		ServletUtility.handleException(e, request, response);
		return;

	}
        log.debug("MarksheetListCtl doGet End");
	}

    /**
     * Contains Submit logics
     */
    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

        log.debug("MarksheetListCtl doPost Start");

        List marksheetList = null;
        List nextMarksheetList = null;

    	int pageNo = 1;
		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));

        MarksheetDTO dto = (MarksheetDTO) populateDTO(request);

        String op = DataUtility.getString(request.getParameter("operation"));

        // get the selected checkbox ids array for delete list
        String[] ids = request.getParameterValues("ids");

        MarksheetModelInt model = ModelFactory.getInstance().getMarksheetModel();

        try {

            if (OP_SEARCH.equalsIgnoreCase(op) || OP_NEXT.equalsIgnoreCase(op)
                    || OP_PREVIOUS.equalsIgnoreCase(op)) {

                if (OP_SEARCH.equalsIgnoreCase(op)) {
                    pageNo = 1;
                } else if (OP_NEXT.equalsIgnoreCase(op)) {
                    pageNo++;
                } else if (OP_PREVIOUS.equalsIgnoreCase(op) && pageNo > 1) {
                    pageNo--;
                }

            } else if (OP_NEW.equalsIgnoreCase(op)) {
                ServletUtility.redirect(ORSView.MARKSHEET_CTL, request,
                        response);
                return;
            } else if (OP_RESET.equalsIgnoreCase(op)) {

    			ServletUtility.redirect(ORSView.MARKSHEET_LIST_CTL, request, response);
    			return;
    		}else if (OP_BACK.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.MARKSHEET_LIST_CTL, request, response);
				return;
			}else if (OP_DELETE.equalsIgnoreCase(op)) {
                pageNo = 1;
                if (ids != null && ids.length > 0) {
                    MarksheetDTO deletebean = new MarksheetDTO();
                    for (String id : ids) {
                        deletebean.setId(DataUtility.getLong(id));
                        model.delete(deletebean);
                        ServletUtility.setSuccessMessage("Data deleted successfully", request);
                    }
                } else {
                    ServletUtility.setErrorMessage(
                            "Select at least one record", request);
                }
            }
            dto = (MarksheetDTO) populateDTO(request);
            marksheetList = model.search(dto, pageNo, pageSize);
            ServletUtility.setDTO(dto, request);
            nextMarksheetList = model.search(dto, pageNo+1, pageSize);
            if (marksheetList == null || marksheetList.size() == 0&&!OP_DELETE.equalsIgnoreCase(op)) {
                ServletUtility.setErrorMessage("No record found ", request);
            }if(nextMarksheetList==null||nextMarksheetList.size()==0){
				request.setAttribute("nextMarksheetListSize", 0);
				
			}else{
				request.setAttribute("nextMarksheetListSize", nextMarksheetList.size());
			}
            ServletUtility.setList(marksheetList, request);
            ServletUtility.setPageNo(pageNo, request);
            ServletUtility.setPageSize(pageSize, request);
            ServletUtility.forward(getView(), request, response);
        } catch (ApplicationException e) {
            log.error(e);
            ServletUtility.handleException(e, request, response);
            return;
        }

        log.debug("MarksheetListCtl doPost End");
    }

    @Override
    protected String getView() {
        return ORSView.MARKSHEET_LIST_VIEW;
    }
}

