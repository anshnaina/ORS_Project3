package com.sunilOS.ORSProject3.ctl;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.sunilOS.ORSProject3.dto.BaseDTO;
import com.sunilOS.ORSProject3.dto.SubjectDTO;
import com.sunilOS.ORSProject3.exception.ApplicationException;
import com.sunilOS.ORSProject3.model.CourseModelInt;
import com.sunilOS.ORSProject3.model.ModelFactory;
import com.sunilOS.ORSProject3.model.SubjectModelInt;
import com.sunilOS.ORSProject3.util.DataUtility;
import com.sunilOS.ORSProject3.util.PropertyReader;
import com.sunilOS.ORSProject3.util.ServletUtility;
/**
 * subject functionality controller.to perform search and list operation.
 * @author Anshul
 *
 */
@WebServlet(name = "SubjectListCtl", urlPatterns = { "/ctl/SubjectListCtl" })
public class SubjectListCtl extends BaseCtl {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(SubjectListCtl.class);
     
	protected void preload(HttpServletRequest request) {
		
		CourseModelInt courseModel = ModelFactory.getInstance().getCourseModel();
		try{
			
			List courseList = courseModel.list();
			
			request.setAttribute("courseList", courseList);
		}catch(Exception e){
			log.error(e);
		}
	}
	
	
	protected BaseDTO populateDTO(HttpServletRequest request) {
		log.debug("subject ctl populate bean start");
		SubjectDTO dto = new SubjectDTO();
        dto.setId(DataUtility.getLong(request.getParameter("id")));
		dto.setCourseId(DataUtility.getLong(request.getParameter("courseId")));
		dto.setSubjectName(DataUtility.getString(request.getParameter("subjectName")));
		
		
		return dto;

	}
	 /**
     * Display Logics inside this method
     */

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		log.debug("subject ctl do get start");
		List subjectList = null;
		List nextSubjectList = null;
		int pageNo = 1;
		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));
		SubjectDTO dto = (SubjectDTO) populateDTO(request);
		SubjectModelInt model =ModelFactory.getInstance().getSubjectModel();
		try {
			subjectList = model.search(dto, pageNo, pageSize);
			nextSubjectList = model.search(dto, pageNo+1, pageSize);
			if (subjectList == null || subjectList.size() == 0) {
				ServletUtility.setErrorMessage("No record found", request);
			}if (nextSubjectList == null || nextSubjectList.size() == 0) {
				request.setAttribute("nextSubjectListSize", 0);

			} else {
				request.setAttribute("nextSubjectListSize", nextSubjectList.size());
			}
			ServletUtility.setList(subjectList, request);
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
		log.debug("subject ctl do get end");
	}
	/**
     * Submit logic inside it
     */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		log.debug("subject ctl dopost start");
		List subjectList = null;
		List nextSubjectList = null;
		int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
		int pageSize = DataUtility.getInt(request.getParameter("pageSize"));
		pageNo = (pageNo == 0) ? 1 : pageNo;
		pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader.getValue("page.size")) : pageSize;
		String[] ids = request.getParameterValues("ids");
		
		String op = DataUtility.getString(request.getParameter("operation"));
		SubjectDTO dto = (SubjectDTO) populateDTO(request);
		SubjectModelInt model =ModelFactory.getInstance().getSubjectModel();
        try{
        	if(OP_SEARCH.equalsIgnoreCase(op)||"Next".equalsIgnoreCase(op)||"Previous".equalsIgnoreCase(op)){
        		if(OP_SEARCH.equalsIgnoreCase(op)){
        			pageNo=1;
        		}else if("Next".equalsIgnoreCase(op)){
        			pageNo++;
        		}else if("Previous".equalsIgnoreCase(op)){
        			pageNo--;
        		}
        	}
        	else if (OP_RESET.equalsIgnoreCase(op)) {

    			ServletUtility.redirect(ORSView.SUBJECT_LIST_CTL, request, response);
    			return;
    		}else if(OP_NEW.equalsIgnoreCase(op)){
        		ServletUtility.redirect(ORSView.SUBJECT_CTL, request, response);
        		return;
        	}else if(OP_DELETE.equalsIgnoreCase(op)){
        		pageNo=1;
        		
        		if(ids !=null&& ids.length>0){
        			System.out.println("kjkjk____");
        			SubjectDTO deleteBean=new SubjectDTO();
        			for(String id:ids){
        				deleteBean.setId(DataUtility.getLong(id));
        				model.delete(deleteBean);
        				ServletUtility.setSuccessMessage("Data Delete Successfully", request);
        			}
        		}else {
					ServletUtility.setErrorMessage("Select at least one record", request);
				}
        	}
        	 if(OP_BACK.equalsIgnoreCase(op)){
        		ServletUtility.redirect(ORSView.SUBJECT_LIST_CTL, request, response);
        		return;
        	}
        	 dto = (SubjectDTO) populateDTO(request);
        	 subjectList = model.search(dto, pageNo, pageSize);
        	ServletUtility.setDTO(dto, request);
        	nextSubjectList = model.search(dto, pageNo+1, pageSize);
			
			if (subjectList == null || subjectList.size() == 0&&!OP_DELETE.equalsIgnoreCase(op)) {
				ServletUtility.setErrorMessage("No record found ", request);
			}if (nextSubjectList == null || nextSubjectList.size() == 0) {
				request.setAttribute("nextSubjectListSize", 0);

			} else {
				request.setAttribute("nextSubjectListSize", nextSubjectList.size());
			}
			ServletUtility.setList(subjectList, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			ServletUtility.forward(getView(), request, response);

		}
        catch (ApplicationException e) {
			log.error(e);
			ServletUtility.handleException(e, request, response);
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}
        
		log.debug("subject ctl do post end");
	}

	@Override
	protected String getView() {
		return ORSView.SUBJECT_LIST_VIEW;
	}

}

