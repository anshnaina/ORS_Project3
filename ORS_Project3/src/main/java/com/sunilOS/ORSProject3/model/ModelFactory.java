package com.sunilOS.ORSProject3.model;

import java.util.HashMap;
import java.util.ResourceBundle;

public class ModelFactory {

	private static ResourceBundle rb = ResourceBundle.getBundle("in.co.rays.bundle.system");
	private static final String DATABASE = rb.getString("DATABASE");
	private static ModelFactory mFactory = null;
	private static HashMap map = new HashMap();
	
	private ModelFactory()
	{
	}
	
	public static ModelFactory getInstance()
	{
		if(mFactory == null)
		{
			mFactory = new ModelFactory();
		}
		return mFactory;
	}
	
	public RoleModelInt getRoleModel()
	{
		RoleModelInt roleModel = (RoleModelInt) map.get("roleModel");
		if(roleModel == null)
		{
			if (DATABASE.equals("Hibernate"))
			{
				roleModel = new RoleModelHibImpl();
			}
			if (DATABASE.equals("JDBC"))
			{
				roleModel = new RoleModelJDBCImpl();
			}
			map.put("roleModel", roleModel);
		}
		return roleModel;
	}
	
	public UserModelInt getUserModel()
	{
		UserModelInt userModel = (UserModelInt) map.get("userModel");
		if (userModel == null)
		{
			if (DATABASE.equals("Hibernate"))
			{
				userModel = new UserModelHibImpl();
			}
			if (DATABASE.equals("JDBC"))
			{
				userModel = new UserModelJDBCImpl();
			}
			map.put("userModel", userModel);
		}
		return userModel;
	}

	public CourseModelInt getCourseModel()
	{
		CourseModelInt courseModel = (CourseModelInt) map.get("courseModel");
		if (courseModel == null)
		{
			if (DATABASE.equals("Hibernate"))
			{
				courseModel = new CourseModelHibImpl();
			}
			if (DATABASE.equals("JDBC"))
			{
				courseModel = new CourseModelJDBCImpl();
			}
			map.put("courseModel", courseModel);
		}
		return courseModel;
	}

	public CollegeModelInt getCollegeModel() 
	{
		CollegeModelInt collegeModel = (CollegeModelInt) map.get("collegeModel");
		if (collegeModel == null)
		{
			if (DATABASE.equals("Hibernate"))
			{
				collegeModel = new CollegeModelHibImpl();
			}
			if (DATABASE.equals("JDBC"))
			{
				collegeModel = new CollegeModelJDBCImpl();
			}
			map.put("collegeModel", collegeModel);
		}
		return collegeModel;
	}

	public TimeTableModelInt getTimetableModel() 
	{
		TimeTableModelInt timeTableModel = (TimeTableModelInt) map.get("timeTableModel");
		if (timeTableModel == null) 
		{
			if ("Hibernate".equals(DATABASE)) 
			{
				timeTableModel = new TimeTableModelHibImpl();
			}
			if ("JDBC".equals(DATABASE)) 
			{
				timeTableModel = new TimeTableModelJDBCImpl();
			}
			map.put("timeTableModel", timeTableModel);
		}

		return timeTableModel;
	}

	public SubjectModelInt getSubjectModel() 
	{
		SubjectModelInt subjectModel = (SubjectModelInt) map.get("subjectModel");
		if (subjectModel == null) 
		{
			if ("Hibernate".equals(DATABASE)) 
			{
				 subjectModel = new SubjectModelHibImpl();
			}
			if ("JDBC".equals(DATABASE))
			{
				subjectModel = new SubjectModelJDBCImpl();
			}
			map.put("subjectModel", subjectModel);
		}

		return subjectModel;
	}

	public FacultyModelInt getFacultyModel() 
	{
		FacultyModelInt facultyModel = (FacultyModelInt) map.get("facultyModel");
		if (facultyModel == null) 
		{
			if ("Hibernate".equals(DATABASE)) 
			{
				facultyModel = new FacultyModelHibImpl();
			}
			if ("JDBC".equals(DATABASE)) 
			{
				facultyModel = new FacultyModelJDBCImpl();
			}
			map.put("facultyModel", facultyModel);
		}

		return facultyModel;
	}
	
	public StudentModelInt getStudentModel()
	{
		StudentModelInt studentModel = (StudentModelInt) map.get("studentModel");
		if (studentModel == null) 
		{
			if ("Hibernate".equals(DATABASE)) 
			{
				 studentModel = new StudentModelHibImpl();
			}
			if ("JDBC".equals(DATABASE)) 
			{
				 studentModel = new StudentModelJDBCImpl();
			}
			map.put("studentModel", studentModel);
		}

		return studentModel;
	}
	
	public MarksheetModelInt getMarksheetModel() 
	{
		MarksheetModelInt marksheetModel = (MarksheetModelInt) map.get("marksheetModel");
		if (marksheetModel == null) 
		{
			if ("Hibernate".equals(DATABASE)) 
			{
				 marksheetModel=new MarksheetModelHibImpl();

			}
			if ("JDBC".equals(DATABASE)) 
			{
				 marksheetModel=new MarksheetModelJDBCImpl();
			}
			map.put("marksheetModel", marksheetModel);
		}
		return marksheetModel;
	}
}
