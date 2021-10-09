<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
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
<body class = "p1">
	<form action = "<%=ORSView.WELCOME_CTL%>">

<%@include file="Header.jsp"%>
  <center>
		<img alt="Online Result System" src="<%=ORSView.APP_CONTEXT%>/img/ORSLogo.png" height="300" width="600">
		<h2 style="color: white;"><b>Welcome to ORS</b></h2>
	</center>
<%@include file="Footer.jsp"%>
	</form>
</body>
</html>