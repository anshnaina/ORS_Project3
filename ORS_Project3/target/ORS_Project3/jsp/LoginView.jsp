<%@page import="com.sunilOS.ORSProject3.util.ServletUtility"%>
<%@page import="com.sunilOS.ORSProject3.util.DataUtility"%>
<%@page import="com.sunilOS.ORSProject3.ctl.ORSView"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Rays Technologies</title>
    <link href= "<%=ORSView.APP_CONTEXT%>/img/title.png" rel= "icon" type="icon">
<Style>

.main-content{
	width: 55%;
	border-radius: 30px;
	box-shadow: 0 5px 5px rgba(0,0,0,.4);
	margin: 5em auto;
	display: flex;
}
.company__info{
	background-color: #343a40;
	border-top-left-radius: 20px;
	border-bottom-left-radius: 20px;
	display: flex;
	flex-direction: column;
	justify-content: center;
	color: #fff;
}

.login_form{
	background-color: #fff;
	border-top-right-radius:20px;
	border-bottom-right-radius:20px;
	border-top:1px solid #ccc;
	border-right:1px solid #ccc;
}

.btn{
	transition: all .5s ease;
	width: 50%;
	border-radius: 30px;
	font-weight: 600;
	background-color: #fff;
	border: 1px solid #008080;
	margin-top: 1.5em;
	}
.p1 
{
	background-image: url('<%=ORSView.APP_CONTEXT%>/img/bg-01.jpg');
		background-size: 100%;
	
}

</Style>
</head>

<body class="p1">
<form method="post" action="<%=ORSView.LOGIN_CTL%>">
<jsp:useBean id="dto" class="com.sunilOS.ORSProject3.dto.UserDTO" scope="request"></jsp:useBean>

<%@include file="Header.jsp"%>
</center>
	<div class="container-fluid">
		<div class="main-content text-center">
			<div class="col-md-4 text-center company__info">
				<img src="<%=ORSView.APP_CONTEXT%>/img/main_logo.png">
			</div>
			<div class="col-md-8 login_form">
			
		<!-- 	<div class="col-md-8 col-xs-12 col-sm-12 login_form"> -->
		
				<div>
					
					<div>
					<br>
					<br>
					
				
				<%
					if (!ServletUtility.getSuccessMessage(request).equals("")) 
					{
				%>
					<H6 align="center" style = "width: 11.5cm; position: fixed;">
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
					<H6 align="center" style = "width: 11.5cm; position: fixed;">
					<div class="alert alert-danger alert-dismissible">
					<%=ServletUtility.getErrorMessage(request)%><button type="button" class="close" data-dismiss="alert">&times;</button>
					</div>
					</H6>
				<%
					}
				%>
			<br>
			<br>
			<br>
			
    		<div class="input-group">
  			<div class="input-group-prepend">
 		   	<span class="input-group-text"><i class="fa fa-envelope grey-text"  style="font-size: 1.5rem;" ></i></span>
  			</div>
 			<input type="text" class="form-control" placeholder="Enter Email" name="email" value="<%=DataUtility.getStringData(dto.getEmail())%>">
			</div>
 			<p style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("email", request)%></font></p>
			
			<br>
			<br>
			
			<div class="input-group">
  			<div class="input-group-prepend">
 		   	<span class="input-group-text"><i class="fa fa-lock grey-text" style="font-size: 1.5rem;"></i></span>
  			</div>	
 			<input type="password" class="form-control" placeholder="Enter Password" name="password" value="<%=DataUtility.getStringData(dto.getPassword())%>">
			</div>
 			<p style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("password", request)%></font></p>
								 


					
					<br>
					
					<div class="text-center">
                    <button class="btn btn-primary btn-sm"  style="width: 8cm" name = "operation" value = "<%=LoginCtl.OP_SIGN_IN%>" ><b>LOGIN</b></button>
                    <p><a href="<%=ORSView.FORGOT_PASSWORD_CTL%>" class="text-muted">Forgot Password ?</a></p>
                  </div>
					</br>
					</br>
				</div>
			</div>
		</div>
	
	
		</form>
	</center>
	<%@include file="Footer.jsp"%>
</body>
</body>
</html>