<%@page import="com.sunilOS.ORSProject3.ctl.TimeTableCtl"%>
<%@page import="com.sunilOS.ORSProject3.util.ServletUtility"%>
<%@page import="com.sunilOS.ORSProject3.util.HTMLUtility"%>
<%@page import="com.sunilOS.ORSProject3.util.DataUtility"%>
<%@page import="com.sunilOS.ORSProject3.ctl.ORSView"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.LinkedHashMap"%>
<%@page import="java.util.List"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Rays Technologies</title>
<link href= "<%=ORSView.APP_CONTEXT%>/img/title.png" rel= "icon" type="icon">
<style>
.p1 
{
	background-image: url('<%=ORSView.APP_CONTEXT%>/img/bg-01.jpg');
	background-size: 100%;
}

.break
{
  	margin: 10px;        
}
</style>
</head>
<body class="p1">
<form action = "<%=ORSView.TIMETABLE_CTL%>" method="post" >
<jsp:useBean id="dto" class="com.sunilOS.ORSProject3.dto.TimeTableDTO" scope="request"></jsp:useBean>
<%@include file = "Header.jsp"%>
<div class="break">
<center>
     
      <div class="container">
        <div class="row justify-content-center">
          <div class="col-12 col-md-8 col-lg-6">
            <div class="card" style="border-radius: 2rem;">
              <div class="card-body p-5">
	
                  <div class="text-center">
                  <%
                  long id = DataUtility.getLong(request.getParameter("id"));

                                    						if (id > 0) {
                  %>
                    <h4 style="text-shadow:black;"><u><b>UPDATE TIME TABLE</b></u></h4>
								<%
								} else {
								%>
                    <h4 style="text-shadow:black;"><u><b>ADD TIME TABLE</b></u></h4>
								<%
								}
								%>
							</div>
									<%
									List courseList = (List) request.getAttribute("courseList");
																								List subjectList = (List) request.getAttribute("subjectList");
									%>
                
<%
                if (!ServletUtility.getSuccessMessage(request).equals("")) 
                                			{
                %>
					<H6 align="center" style = "width: 11.7cm; position: fixed;">
					<div class="alert alert-success alert-dismissible ">
					<%=ServletUtility.getSuccessMessage(request)%><button type="button" class="close" data-dismiss="alert">&times;</button>
					</div>
					</H6>	
				<%
					}
					%>				
				
				<%
												if (!ServletUtility.getErrorMessage(request).equals("")) 
																									{
												%>
					<H6 align="center" style = "width: 11.7cm; position: fixed;">
					<div class="alert alert-danger alert-dismissible">
					<%=ServletUtility.getErrorMessage(request)%><button type="button" class="close" data-dismiss="alert">&times;</button>
					</div>
					</H6>
				<%
				}
				%>
				<input type="hidden" name="id" value="<%=dto.getId()%>">
				<input type="hidden" name="createdBy" value="<%=dto.getCreatedBy()%>"> 
				<input type="hidden" name="modifiedBy" value="<%=dto.getModifiedBy()%>"> 
				<input type="hidden" name="createdDatetime" value="<%=DataUtility.getTimestamp(dto.getCreatedDatetime())%>">
				<input type="hidden" name="modifiedDatetime" value="<%=DataUtility.getTimestamp(dto.getModifiedDatetime())%>">
				<br>
				<br>
				
		
       
       	
        <span style="padding-right: 6.8cm;"><b>Course</b><span style="color: red;">*</span></span> 
		<br>
        <div class="input-group">
            <div class="input-group-prepend">
           		<span class="input-group-text"><i class="fa fa-book-open grey-text" style="font-size: 1rem;"></i></span>
            </div>
          	
			<%=HTMLUtility.getList("courseId", String.valueOf(dto.getCourseId()), courseList, "Select Course Name")%>
			</div>
         	<font color="red" style="padding-right: 4cm; font-size: .4cm;"><%=ServletUtility.getErrorMessage("courseId", request)%></font>
         	
         	  <br>
         	  
         	  <span style="padding-right: 6.7cm;"><b>Subject</b><span style="color: red;">*</span></span> 
		<br>
		
        <div class="input-group">
            <div class="input-group-prepend">
           		<span class="input-group-text"><i class="fa fa-book grey-text" style="font-size: 1rem;"></i></span>
            </div>
          	
			<%=HTMLUtility.getList("subjectId", String.valueOf(dto.getSubjectId()), subjectList, "Select Subject Name")%>
			</div>
         	<font color="red" style="padding-right: 4cm; font-size: .4cm;"><%=ServletUtility.getErrorMessage("subjectId", request)%></font>
         	
         	  <br>
         
         <span style="padding-right: 6.5cm;"><b>Semester</b><span style="color: red;">*</span></span> 
		<br>
        <div class="input-group">
            <div class="input-group-prepend">
           		<span class="input-group-text"><i class="fas fa-hourglass-half grey-text" style="font-size: 1rem;"></i></span>
            </div>
            <%
            HashMap <String, String> SemMap = new HashMap();
                        				SemMap.put("1", "1");
                        				SemMap.put("2", "2");
                        				SemMap.put("3", "3");
                        				SemMap.put("4", "4");
                        				SemMap.put("5", "5");
                        				SemMap.put("6", "6");
                        				SemMap.put("7", "7");
                        				SemMap.put("8", "8");
            %>
						<%=HTMLUtility.getList("semesterId", dto.getSemester(), SemMap, "Select Semester")%>
			</div>
         	<font color="red" style="padding-right: 4.8cm; font-size: .4cm;"><%=ServletUtility.getErrorMessage("semesterId", request)%></font>
         	  
         	  <br>
         	  
         	   <span style="padding-right: 6.4cm;"><b>Exam Date</b><span style="color: red;">*</span></span> 
		<br>
        <div class="input-group">
            <div class="input-group-prepend">
           		<span class="input-group-text"><i class="fa fa-calendar grey-text" style="font-size: 1rem;"></i></span>
            </div>
                <input type="date" class="form-control" name="examDate" value="<%=DataUtility.getDateToString(dto.getExamDate())%>">
        </div>
         	<font color="red" style="padding-right: 4.7cm; font-size: .4cm;"><%=ServletUtility.getErrorMessage("examDate", request)%></font>
             
             <br>
         	   <span style="padding-right: 6.2cm;"><b>Exam Time</b><span style="color: red;">*</span></span> 
		<br>
        <div class="input-group">
            <div class="input-group-prepend">
           		<span class="input-group-text"><i class="fa fa-clock grey-text" style="font-size: 1rem;"></i></span>
            </div>
           <%
           LinkedHashMap<String, String> examTimeMap = new LinkedHashMap<String, String>();
                      				
                      					examTimeMap.put("07:00AM-10:00AM", "07:00AM-10:00AM");
                      					examTimeMap.put("08:00AM-11:00AM", "08:00AM-11:00AM");
                      					examTimeMap.put("09:00AM-12:00PM", "09:00AM-12:00PM");
                      					examTimeMap.put("10:00AM-01:00PM", "10:00AM-01:00PM");
                      					examTimeMap.put("11:00AM-02:00PM", "11:00AM-02:00PM");
                      					examTimeMap.put("12:00PM-03:00PM", "12:00PM-03:00PM");
                      					examTimeMap.put("01:00PM-04:00PM", "01:00PM-04:00PM");
                      					examTimeMap.put("02:00PM-05:00PM", "02:00PM-05:00PM");
           %> <%=HTMLUtility.getList("examTime", dto.getExamTime(), examTimeMap, "Select Exam Time")%></td>
			</div>
         	<font color="red" style="padding-right: 4.4cm; font-size: .4cm;"><%=ServletUtility.getErrorMessage("examTime", request)%></font>
         	  
        <br>
        <span style="padding-right: 6.4cm;"><b>Description</b><span style="color: red;">*</span></span> 
		<br>
         <div class="input-group">
         <div class="input-group-prepend">
         <span class="input-group-text"><i class="fa fa-info-circle grey-text" style="font-size: 1rem;"></i></span>
         </div>
          <input type="text" class="form-control" name="description" placeholder="Enter Description" value="<%=DataUtility.getStringData(dto.getDescription())%>">
        </div>
         	<font color="red" style="padding-right: 4.8cm; font-size: .4cm;"><%=ServletUtility.getErrorMessage("description", request)%></font>
     
        
         	  
               <br>
               <br>
        			<div class="text-center">
        			<%
        			if (id > 0) {
        			%>
                    <input type="submit" class="btn btn-primary" name="operation" value="<%=TimeTableCtl.OP_UPDATE%>">
                    <input type="submit" class="btn btn-secondary" name="operation" value="<%=TimeTableCtl.OP_CANCEL%>">
                    
                    <%
                                        } else {
                                        %>
					<input type="submit" class="btn btn-primary" name="operation" value="<%=TimeTableCtl.OP_SAVE%>">
                    <input type="submit" class="btn btn-secondary" name="operation" value="<%=TimeTableCtl.OP_RESET%>">
                    <%
						}
					%>
                  </div>
                  
              </div>
            </div>
          </div>
        </div>
</div>
</center>
<br>
</div>
<%@include file = "Footer.jsp"%>
</form>
</body>
</html>