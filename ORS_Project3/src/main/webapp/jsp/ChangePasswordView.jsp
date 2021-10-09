<%@page import="com.sunilOS.ORSProject3.ctl.ChangePasswordCtl"%>
<%@page import="com.sunilOS.ORSProject3.util.ServletUtility"%>
<%@page import="com.sunilOS.ORSProject3.util.HTMLUtility"%>
<%@page import="com.sunilOS.ORSProject3.util.DataUtility"%>
<%@page import="com.sunilOS.ORSProject3.ctl.ORSView"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="java.util.HashMap"%>
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
<form action = "<%=ORSView.CHANGE_PASSWORD_CTL%>" method="post" >
<jsp:useBean id="dto" class="com.sunilOS.ORSProject3.dto.UserDTO" scope="request"></jsp:useBean>
<%@include file = "Header.jsp"%>
<div class="break">
<center>
     
      <div class="container">
        <div class="row justify-content-center">
          <div class="col-12 col-md-8 col-lg-6">
            <div class="card" style="border-radius: 2rem;">
              <div class="card-body p-5">
	
                  <div class="text-cente">
                    <h4 style="text-shadow:black;"><u><b>Change Password</b></u></h4>
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
			
			
			<span style="padding-right: 5.7cm;"><b>Old Password</b><span style="color: red;">*</span></span> 
		<br>
		
        <div class="input-group">
            <div class="input-group-prepend">
           		<span class="input-group-text"><i class="fa fa-key grey-text" style="font-size: 1rem;"></i></span>
            </div>
              <input type="password" class="form-control" name="oldPassword" placeholder="Enter Old Password" value="<%=DataUtility.getString(request.getParameter("oldPassword") == null ? "" : DataUtility.getString(request.getParameter("oldPassword")))%>">
        </div>
         	<font color="red" style="padding-right: 4.5cm; font-size: .4cm;"><%=ServletUtility.getErrorMessage("oldPassword", request)%></font>
        
        <br>
			 
		<span style="padding-right: 5.6cm;"><b>New Password</b><span style="color: red;">*</span></span> 
		<br>

        <div class="input-group">
            <div class="input-group-prepend">
           		<span class="input-group-text"><i class="fa fa-key grey-text" style="font-size: 1rem;"></i></span>
            </div>
              <input type="password" class="form-control" name="newPassword" placeholder="Enter New Password" value="<%=DataUtility.getString(request.getParameter("newPassword") == null ? "" : DataUtility.getString(request.getParameter("newPassword")))%>">
        </div>
         	<font color="red" style="padding-right: 4.2cm; font-size: .4cm;"><%=ServletUtility.getErrorMessage("newPassword", request)%></font>
        
        <br>
        
         <span style="padding-right: 4.9cm;"><b>Confirm Password</b><span style="color: red;">*</span></span> 
		<br>
        <div class="input-group">
            <div class="input-group-prepend">
           		<span class="input-group-text"><i class="fa fa-key grey-text" style="font-size: 1rem;"></i></span>
            </div>
               <input type="password" class="form-control" name="confirmPassword" placeholder="Confirm Password" value="<%=DataUtility.getString(request.getParameter("confirmPassword") == null ? "" : DataUtility.getString(request.getParameter("confirmPassword")))%>">
        </div>
         	<font color="red" style="padding-right: 3.7cm; font-size: .4cm;"><%=ServletUtility.getErrorMessage("confirmPassword", request)%></font>
        
  
               <br>
               <br>
        			<div class="text-center">
                    <input type="submit" class="btn btn-primary" name="operation" value="<%=ChangePasswordCtl.OP_SAVE%>">
                    <input type="submit" class="btn btn-secondary" name="operation" value="<%=ChangePasswordCtl.OP_CHANGE_MY_PROFILE%>">
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