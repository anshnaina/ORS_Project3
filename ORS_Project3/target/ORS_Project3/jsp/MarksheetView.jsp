<%@page import="com.sunilOS.ORSProject3.ctl.MarksheetCtl"%>
<%@page import="com.sunilOS.ORSProject3.util.ServletUtility"%>
<%@page import="com.sunilOS.ORSProject3.util.HTMLUtility"%>
<%@page import="com.sunilOS.ORSProject3.util.DataUtility"%>
<%@page import="com.sunilOS.ORSProject3.ctl.ORSView"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="java.util.HashMap"%>
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
<form action = "<%=ORSView.MARKSHEET_CTL%>" method="post" >
<jsp:useBean id="dto" class="com.sunilOS.ORSProject3.dto.MarksheetDTO" scope="request"></jsp:useBean>
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
                    <h4 style="text-shadow:black;"><u><b>UPDATE MARKSHEET</b></u></h4>
								<%
									} else {
								%>
                    <h4 style="text-shadow:black;"><u><b>ADD MARKSHEET</b></u></h4>
								<%
									}
								%>
							</div>
									<%
										List studentList = (List) request.getAttribute("studentList");
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
				
		<span style="padding-right: 7cm;"><b>Roll No.</b><span style="color: red;">*</span></span> 
		<br>
         <div class="input-group">
         <div class="input-group-prepend">
         <span class="input-group-text"><i class="fa fa-bookmark grey-text" style="font-size: 1rem;"></i></span>
         </div>
          <input type="text" class="form-control" name="rollNo" onchange = "capitalize(this)" placeholder="Enter Roll No." value="<%=DataUtility.getStringData(dto.getRollNo())%>">
        </div>
         	<font color="red" style="padding-right: 5.5cm; font-size: .4cm;"><%=ServletUtility.getErrorMessage("rollNo", request)%></font>
        <br>
        
        <span style="padding-right: 5.3cm;"><b>Student Name</b><span style="color: red;">*</span></span> 
		<br>
        <div class="input-group">
            <div class="input-group-prepend">
           		<span class="input-group-text"><i class="fa fa-user-graduate grey-text" style="font-size: 1rem;"></i></span>
            </div>
          	<% System.out.println("student id in jsp " + String.valueOf(dto.getStudentId())); %>
			<%=HTMLUtility.getList("studentId", String.valueOf(dto.getStudentId()), studentList, "Select Student Name")%>
			</div>
         	<font color="red" style="padding-right: 3.8cm; font-size: .4cm;"><%=ServletUtility.getErrorMessage("studentId", request)%></font>
         	
       <br>
       
       	<span style="padding-right: 7.2cm;"><b>Physics</b><span style="color: red;">*</span></span> 
		<br>
        <div class="input-group">
            <div class="input-group-prepend">
           		<span class="input-group-text"><i class="fab fa-product-hunt grey-text" style="font-size: 1rem;"></i></span>
            </div>
             <input type="text" class="form-control" name="physics" placeholder="Enter Physics Marks" value = "<%=DataUtility.getStringData(dto.getPhysics())%>">
        </div>
         	<font color="red" style="padding-right: 5.4cm; font-size: .4cm;"><%=ServletUtility.getErrorMessage("physics", request)%></font>
        
        <br>
        	<span style="padding-right: 6.7cm;"><b>Chemistry</b><span style="color: red;">*</span></span> 
		<br>
        <div class="input-group">
            <div class="input-group-prepend">
           		<span class="input-group-text"><i class="fa fa-copyright grey-text" style="font-size: 1rem;"></i></span>
            </div>
             <input type="text" class="form-control" name="chemistry" placeholder="Enter Chemistry Marks" value = "<%=DataUtility.getStringData(dto.getChemistry())%>">
        </div>
         	<font color="red" style="padding-right: 5cm; font-size: .4cm;"><%=ServletUtility.getErrorMessage("chemistry", request)%></font>
        
        <br>
        	<span style="padding-right: 7.3cm;"><b>Maths</b><span style="color: red;">*</span></span> 
		<br>
        <div class="input-group">
            <div class="input-group-prepend">
           		<span class="input-group-text"><i class="fab fa-monero grey-text" style="font-size: 1rem;"></i></span>
            </div>
             <input type="text" class="form-control" name="maths" placeholder="Enter Maths Marks" value = "<%=DataUtility.getStringData(dto.getMaths())%>">
        </div>
         	<font color="red" style="padding-right: 5.5cm; font-size: .4cm;"><%=ServletUtility.getErrorMessage("maths", request)%></font>
        
               <br>
               <br>
        			<div class="text-center">
        			<%
						if (id > 0) {
					%>
                    <input type="submit" class="btn btn-primary" name="operation" value="<%=MarksheetCtl.OP_UPDATE%>">
                    <input type="submit" class="btn btn-secondary" name="operation" value="<%=MarksheetCtl.OP_CANCEL%>">
                    
                    <%
						} else {
					%>
					<input type="submit" class="btn btn-primary" name="operation" value="<%=MarksheetCtl.OP_SAVE%>">
                    <input type="submit" class="btn btn-secondary" name="operation" value="<%=MarksheetCtl.OP_RESET%>">
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