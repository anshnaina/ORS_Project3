<%@page import="com.sunilOS.ORSProject3.ctl.CourseCtl"%>
<%@page import="com.sunilOS.ORSProject3.util.ServletUtility"%>
<%@page import="com.sunilOS.ORSProject3.util.HTMLUtility"%>
<%@page import="com.sunilOS.ORSProject3.util.DataUtility"%>
<%@page import="com.sunilOS.ORSProject3.ctl.ORSView"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="java.util.LinkedHashMap"%>

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
<form action = "<%=ORSView.COURSE_CTL%>" method="post" >
<jsp:useBean id="dto" class="com.sunilOS.ORSProject3.dto.CourseDTO" scope="request"></jsp:useBean>
<%@include file = "Header.jsp"%>
<div class="break">
<center>
     
      <div class="container">
        <div class="row justify-content-center">
          <div class="col-12 col-md-8 col-lg-6">
            <div class="card" style="border-radius: 2rem;">
              <div class="card-body p-5">
	
              
                  <div class="text-cente">
                  <%
									long id = DataUtility.getLong(request.getParameter("id"));

								if (id > 0) {
								%>
                    <h4 style="text-shadow:black;"><u><b>UPDATE COURSE</b></u></h4>
								<%
									} else {
								%>
                    <h4 style="text-shadow:black;"><u><b>ADD COURSE</b></u></h4>
								<%
									}
								%>
							</div>
                
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
				
		<span style="padding-right: 6cm;"><b>Course Name</b><span style="color: red;">*</span></span> 
		<br>
         <div class="input-group">
         <div class="input-group-prepend">
         <span class="input-group-text"><i class="fa fa-book-open grey-text" style="font-size: 1rem;"></i></span>
         </div>
          <input type="text" class="form-control" name="courseName" placeholder="Enter Course Name" value="<%=DataUtility.getStringData(dto.getCourseName())%>">
        </div>
         	<font color="red" style="padding-right: 4.6cm; font-size: .4cm;"><%=ServletUtility.getErrorMessage("courseName", request)%></font>
        <br>
              	  
		 <span style="padding-right: 6.6cm;"><b>Course Duration</b><span style="color: red;">*</span></span> 
		<br>
        <div class="input-group">
            <div class="input-group-prepend">
           		<span class="input-group-text"><i class="fa fa-graduation-cap grey-text" style="font-size: 1rem;"></i></span>
            </div>
            <%
						LinkedHashMap<String, String> cDurationMap = new LinkedHashMap<String, String>(); 
					
						cDurationMap.put("1 Year", "1 Year");
						cDurationMap.put("2 Years", "2 Years");
						cDurationMap.put("3 Years", "3 Years");
						cDurationMap.put("4 Years", "4 Years");
						
						%>
						<%=HTMLUtility.getList("courseDuration", dto.getCourseDuration(), cDurationMap, "Select Course Duration")%>
			</div>
         	<font color="red" style="padding-right: 5cm; font-size: .4cm;"><%=ServletUtility.getErrorMessage("courseDuration", request)%></font>
         	  
         	     <br>
        <span style="padding-right: 6.5cm;"><b>Course Description</b><span style="color: red;">*</span></span> 
		<br>
         <div class="input-group">
         <div class="input-group-prepend">
         <span class="input-group-text"><i class="fa fa-info-circle grey-text" style="font-size: 1rem;"></i></span>
         </div>
              <input type="text" class="form-control" name="courseDescription" placeholder="Enter Course Description" value="<%=DataUtility.getStringData(dto.getCourseDescription())%>">
        </div>
         	<font color="red" style="padding-right: 5cm; font-size: .4cm;"><%=ServletUtility.getErrorMessage("courseDescription", request)%></font>
     
     
               <br>
               <br>
        			<div class="text-center">
        			<%
						if (id > 0) {
					%>
                    <input type="submit" class="btn btn-primary" name="operation" value="<%=CourseCtl.OP_UPDATE%>">
                    <input type="submit" class="btn btn-secondary" name="operation" value="<%=CourseCtl.OP_CANCEL%>">
                    
                    <%
						} else {
					%>
					<input type="submit" class="btn btn-primary" name="operation" value="<%=CourseCtl.OP_SAVE%>">
                    <input type="submit" class="btn btn-secondary" name="operation" value="<%=CourseCtl.OP_RESET%>">
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