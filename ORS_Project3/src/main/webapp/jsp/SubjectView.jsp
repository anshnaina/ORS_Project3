<%@page import="com.sunilOS.ORSProject3.ctl.SubjectCtl"%>
<%@page import="com.sunilOS.ORSProject3.util.ServletUtility"%>
<%@page import="com.sunilOS.ORSProject3.util.HTMLUtility"%>
<%@page import="com.sunilOS.ORSProject3.util.DataUtility"%>
<%@page import="com.sunilOS.ORSProject3.ctl.ORSView"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
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
<form action = "<%=ORSView.SUBJECT_CTL%>" method="post" >
<jsp:useBean id="dto" class="com.sunilOS.ORSProject3.dto.SubjectDTO" scope="request"></jsp:useBean>
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
                    <h4 style="text-shadow:black;"><u><b>UPDATE SUBJECT</b></u></h4>
								<%
									} else {
								%>
                    <h4 style="text-shadow:black;"><u><b>ADD SUBJECT</b></u></h4>
								<%
									}
								%>
							</div>
									<%
										List courseList = (List) request.getAttribute("courseList");
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
			
			
		<span style="padding-right: 5.9cm;"><b>Subject Name</b><span style="color: red;">*</span></span> 
		<br>
         <div class="input-group">
         <div class="input-group-prepend">
         <span class="input-group-text"><i class="fa fa-book grey-text" style="font-size: 1rem;"></i></span>
         </div>
          <input type="text" class="form-control" name="subjectName" placeholder="Enter Subject Name" value="<%=DataUtility.getStringData(dto.getSubjectName())%>">
        </div>
         	<font color="red" style="padding-right: 4.5cm; font-size: .4cm;"><%=ServletUtility.getErrorMessage("subjectName", request)%></font>
       
         	<br>
        <span style="padding-right: 5.5cm;"><b>Course Name</b><span style="color: red;">*</span></span> 
		<br>
        <div class="input-group">
            <div class="input-group-prepend">
           		<span class="input-group-text"><i class="fa fa-book-open grey-text" style="font-size: 1rem;"></i></span>
            </div>
          	
			<%=HTMLUtility.getList("courseId", String.valueOf(dto.getCourseId()), courseList, "Select Course Name")%>
			</div>
         	<font color="red" style="padding-right: 4cm; font-size: .4cm;"><%=ServletUtility.getErrorMessage("courseId", request)%></font>

		 <br>
        
        <span style="padding-right: 6.5cm;"><b>Description</b><span style="color: red;">*</span></span> 
		<br>
         <div class="input-group">
         <div class="input-group-prepend">
         <span class="input-group-text"><i class="fa fa-info-circle grey-text" style="font-size: 1rem;"></i></span>
         </div>
          <input type="text" class="form-control" name="description" placeholder="Enter Description" value="<%=DataUtility.getStringData(dto.getDescription())%>">
   
        </div>
         	<font color="red" style="padding-right: 5cm; font-size: .4cm;"><%=ServletUtility.getErrorMessage("description", request)%></font>
         	
               <br>
               <br>
        			<div class="text-center">
        			<%
						if (id > 0) {
					%>
                    <input type="submit" class="btn btn-primary" name="operation" value="<%=SubjectCtl.OP_UPDATE%>">
                    <input type="submit" class="btn btn-secondary" name="operation" value="<%=SubjectCtl.OP_CANCEL%>">
                    
                    <%
						} else {
					%>
					<input type="submit" class="btn btn-primary" name="operation" value="<%=SubjectCtl.OP_SAVE%>">
                    <input type="submit" class="btn btn-secondary" name="operation" value="<%=SubjectCtl.OP_RESET%>">
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