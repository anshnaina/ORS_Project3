package com.sunilOS.ORSProject3.ctl;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.sunilOS.ORSProject3.dto.BaseDTO;
import com.sunilOS.ORSProject3.dto.TimeTableDTO;
import com.sunilOS.ORSProject3.exception.ApplicationException;
import com.sunilOS.ORSProject3.model.CourseModelInt;
import com.sunilOS.ORSProject3.model.ModelFactory;
import com.sunilOS.ORSProject3.model.SubjectModelInt;
import com.sunilOS.ORSProject3.model.TimeTableModelInt;
import com.sunilOS.ORSProject3.util.DataUtility;
import com.sunilOS.ORSProject3.util.DataValidator;
import com.sunilOS.ORSProject3.util.PropertyReader;
import com.sunilOS.ORSProject3.util.ServletUtility;


/**
 * TimeTable functionality controller.to perform add,delete and update operation.
 * @author Anshul
 *
 */
@WebServlet(urlPatterns={"/ctl/TimeTableCtl"})
public class TimeTableCtl extends BaseCtl{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(TimeTableCtl.class);

	protected void preload(HttpServletRequest request) {
		CourseModelInt courseModel = ModelFactory.getInstance().getCourseModel();
		SubjectModelInt  subjectModel = ModelFactory.getInstance().getSubjectModel();
		try {
			List courseList = courseModel.list();
			List subjectList = subjectModel.list();
			request.setAttribute("courseList", courseList);
			request.setAttribute("subjectList", subjectList);
		} catch (Exception e) {
			log.error(e);
		}
	}

	protected boolean validate(HttpServletRequest request) {
		log.debug("time table validate start");
		boolean pass = true;
		String examDate = request.getParameter("examDate");
		if (DataValidator.isNull(request.getParameter("description"))) {
			request.setAttribute("description", PropertyReader.getValue("error.require", "Description"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("courseId"))) {
			request.setAttribute("courseId", PropertyReader.getValue("error.require", "Course Name"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("subjectId"))) {
			request.setAttribute("subjectId", PropertyReader.getValue("error.require", "Subject Name"));
			pass = false;
		}
		

		if (DataValidator.isNull(request.getParameter("semesterId"))) {
			request.setAttribute("semesterId", PropertyReader.getValue("error.require", "Semester"));
			pass = false;
		}

		if (DataValidator.isNull(examDate)) {
			request.setAttribute("examDate", PropertyReader.getValue("error.require", "Exam Date"));
			pass = false;
		} else if (!DataValidator.isDate(examDate)) {
			request.setAttribute("examDate", PropertyReader.getValue("error.date", "Exam Date"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("examTime"))) {
			request.setAttribute("examTime", PropertyReader.getValue("error.require", "Exam Time"));
			pass = false;
		}
		log.debug("time table validate end");
		return pass;
	}

	protected BaseDTO populateDTO(HttpServletRequest request) {
		log.debug("time table populate start");
		TimeTableDTO dto = new TimeTableDTO();
		dto.setId(DataUtility.getLong(request.getParameter("id")));
		dto.setCourseId(DataUtility.getLong(request.getParameter("courseId")));
		dto.setDescription(DataUtility.getString(request.getParameter("description")));
		dto.setSemester(DataUtility.getString(request.getParameter("semesterId")));
		dto.setSubjectId(DataUtility.getLong(request.getParameter("subjectId")));
		dto.setExamDate(DataUtility.getStringToDate(request.getParameter("examDate")));
		dto.setExamTime(DataUtility.getString(request.getParameter("examTime")));
		populateBean(dto,request);
		log.debug("time table populate end");
		
		return dto;
	}

	/**
	 * Display Logics inside this method
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		log.debug("time table do get start");
		String op = DataUtility.getString(request.getParameter("operation"));
		long id = DataUtility.getLong(request.getParameter("id"));
		TimeTableModelInt model =ModelFactory.getInstance().getTimetableModel() ;
		if (id > 0 || op != null) {
			TimeTableDTO dto;
			try {
				dto = model.findByPk(id);
				ServletUtility.setDTO(dto, request);
			} catch (Exception e) {
				e.printStackTrace();
				log.debug(e);
				ServletUtility.handleException(e, request, response);
				return;
			}

		}
		ServletUtility.forward(getView(), request, response);
		log.debug("time table doget end");
	}

	/**
	 * Submit logic inside it
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		System.out.println("method post..............");
		log.debug("time table dopost start");
		String op = DataUtility.getString(request.getParameter("operation"));
		long id = DataUtility.getLong(request.getParameter("id"));
		TimeTableModelInt model = ModelFactory.getInstance().getTimetableModel();
		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {
			TimeTableDTO dto = (TimeTableDTO) populateDTO(request);
			TimeTableDTO dto1 = null;
			TimeTableDTO dto2 = null;
			TimeTableDTO dto3 = null;
			try {
				if (id > 0) {
					 model.update(dto);
					ServletUtility.setDTO(dto, request);
					 ServletUtility.setSuccessMessage("Data successfully updated", request);
				} else {
					try {
						dto1 = model.checkByCourseName(dto.getCourseId(), dto.getExamDate());
						dto2 = model.checkBySubjectName(dto.getCourseId(), dto.getSubjectId(), dto.getExamDate());
						dto3 = model.checkBySemester(dto.getCourseId(), dto.getSubjectId(), dto.getSemester(),
								dto.getExamDate());
						if (dto1 == null && dto2 == null && dto3 == null) {
							System.out.println("hhhhhhhhhhh");
							model.add(dto);
							ServletUtility.setSuccessMessage("Data is successfully saved", request);
						} else {
//							ServletUtility.setDto(dto, request);
							ServletUtility.setErrorMessage("Exam already exist!", request);

						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
				//ServletUtility.setBean(bean, request);

			} catch (Exception e) {
				ServletUtility.setDTO(dto, request);
				ServletUtility.setErrorMessage("Login id already exists", request);
			}
		} else if (OP_DELETE.equalsIgnoreCase(op)) {

			TimeTableDTO dto = (TimeTableDTO) populateDTO(request);
			try {
				model.delete(dto);
				ServletUtility.redirect(ORSView.TIMETABLE_LIST_CTL, request, response);
				return;
			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.TIMETABLE_LIST_CTL, request, response);
			return;
		} else if (OP_RESET.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.TIMETABLE_CTL, request, response);
			return;
		}
		ServletUtility.forward(getView(), request, response);
		log.debug("time table dopost end");
	}

	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.TIMETABLE_VIEW;
	}

}
