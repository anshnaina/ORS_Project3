<%@page import="com.sunilOS.ORSProject3.ctl.ForgotPasswordCtl"%>
<%@page import="com.sunilOS.ORSProject3.util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
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

</style>
</head>
<body class ="p1">
<%@include file="Header.jsp"%>
<form action="<%=ORSView.FORGOT_PASSWORD_CTL%>" method="post">
<jsp:useBean id="dto" class="com.sunilOS.ORSProject3.dto.UserDTO" scope="request"></jsp:useBean>

<br>
<div align="center">
	<h2 style="text-shadow: #343a40;"><u><b>Forgot Password</b></u></h2>
</div>
<br>
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
				<br>
				
			<div align="center">
			<input type="text" name="email" value="<%=ServletUtility.getParameter("email", request)%>" placeholder="Enter Email Id" size = "25">
			 
			<input type="submit" class="btn btn-dark" name="operation" value="<%=ForgotPasswordCtl.OP_GO%>">
			<p><font color="red"><%=ServletUtility.getErrorMessage("email", request)%></font></p>
			
			</div>
	
</body>
</html>