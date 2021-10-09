<%@page import="com.sunilOS.ORSProject3.ctl.UserCtl"%>
<%@page import="com.sunilOS.ORSProject3.util.ServletUtility"%>
<%@page import="com.sunilOS.ORSProject3.util.HTMLUtility"%>
<%@page import="com.sunilOS.ORSProject3.util.DataUtility"%>
<%@page import="com.sunilOS.ORSProject3.ctl.ORSView"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
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
	background-size: 110%;
}

.break
{
  	margin: 10px;        
}

</style>
</head>
<body class="p1">
<form action = "<%=ORSView.USER_CTL%>" method="post" >
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
                  <%
									long id = DataUtility.getLong(request.getParameter("id"));

								if (id > 0) {
								%>
                    <h4 style="text-shadow:black;"><u><b>UPDATE USER</b></u></h4>
								<%
									} else {
								%>
                    <h4 style="text-shadow:black;"><u><b>ADD USER</b></u></h4>
								<%
									}
								%>
							</div>
									<%
										List roleList = (List) request.getAttribute("roleList");
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
				
		<span style="padding-right: 6.5cm;"><b>First Name</b><span style="color: red;">*</span></span> 
		<br>
         <div class="input-group">
         <div class="input-group-prepend">
         <span class="input-group-text"><i class="fa fa-user grey-text" style="font-size: 1rem;"></i></span>
         </div>
          <input type="text" class="form-control" name="firstName" onchange = "capitalize(this)" placeholder="Enter First Name" value="<%=DataUtility.getStringData(dto.getFirstName())%>">
        </div>
         	<font color="red" style="padding-right: 5cm; font-size: .4cm;"><%=ServletUtility.getErrorMessage("firstName", request)%></font>
        <br>
       
       	<span style="padding-right: 6.5cm;"><b>Last Name</b><span style="color: red;">*</span></span> 
		<br>
        <div class="input-group">
            <div class="input-group-prepend">
           		<span class="input-group-text"><i class="fa fa-user grey-text" style="font-size: 1rem;"></i></span>
            </div>
             <input type="text" class="form-control" name="lastName" onchange = "capitalize(this)" placeholder="Enter Last Name" value="<%=DataUtility.getStringData(dto.getLastName())%>">
        </div>
         	<font color="red" style="padding-right: 5cm; font-size: .4cm;"><%=ServletUtility.getErrorMessage("lastName", request)%></font>
        
        <br>
        
        <span style="padding-right: 7.4cm;"><b>Email</b><span style="color: red;">*</span></span> 
		<br>
        <div class="input-group">
        	<div class="input-group-prepend">
        		<span class="input-group-text"><i class="fa fa-envelope grey-text" style="font-size: 1rem;"></i></span>
        	</div>
        	  <input type="text" class="form-control" name="email" placeholder="Enter Email" value="<%=DataUtility.getStringData(dto.getEmail())%>">
        </div>
         	<font color="red" style="padding-right: 5cm; font-size: .4cm;"><%=ServletUtility.getErrorMessage("email", request)%></font>
         
        <br>
        
         <span style="padding-right: 6cm;"><b>Date of Birth</b><span style="color: red;">*</span></span> 
		<br>
        <div class="input-group">
            <div class="input-group-prepend">
           		<span class="input-group-text"><i class="fa fa-calendar grey-text" style="font-size: 1rem;"></i></span>
            </div>
                <input type="date" class="form-control" name="dob" value="<%=DataUtility.getDateToString(dto.getDob())%>">
        </div>
         	<font color="red" style="padding-right: 6cm; font-size: .4cm;"><%=ServletUtility.getErrorMessage("dob", request)%></font>
             
        <br>
        
        <span style="padding-right: 6.2cm;"><b>Mobile No.</b><span style="color: red;">*</span></span> 
		<br>
  		<div class="input-group">
            <div class="input-group-prepend">
           		<span class="input-group-text"><i class="fa fa-phone grey-text" style="font-size: 1rem;"></i></span>
            </div>
            <input type="text" class="form-control" name="mobileNo" placeholder="Enter Mobile No." value="<%=DataUtility.getStringData(dto.getMobileNo())%>">
        </div>
         	<font color="red" style="padding-right: 5cm; font-size: .4cm;"><%=ServletUtility.getErrorMessage("mobileNo", request)%></font>
        
		<br>
		 <span style="padding-right: 7.2cm;"><b>Role</b><span style="color: red;">*</span></span> 
		<br>
        <div class="input-group">
            <div class="input-group-prepend">
           		<span class="input-group-text"><i class="fas fa-user-tag grey-text" style="font-size: 1rem;"></i></span>
            </div>
          	
			<%=HTMLUtility.getList("roleId", String.valueOf(dto.getRoleId()), roleList, "Select Role")%>
			</div>
         	<font color="red" style="padding-right: 5cm; font-size: .4cm;"><%=ServletUtility.getErrorMessage("roleId", request)%></font>
         	
         	  <br>
         	  
		 <span style="padding-right: 6.8cm;"><b>Gender</b><span style="color: red;">*</span></span> 
		<br>
        <div class="input-group">
            <div class="input-group-prepend">
           		<span class="input-group-text"><i class="fa fa-venus-mars grey-text" style="font-size: 1rem;"></i></span>
            </div>
            <%
				HashMap<String, String> genMap = new HashMap<String, String>();
				genMap.put("Male", "Male");
	  			genMap.put("Female", "Female");
	  				  		
			%>	
			<%=HTMLUtility.getList("gender", dto.getGender(), genMap, "Select Gender")%>
			</div>
         	<font color="red" style="padding-right: 5cm; font-size: .4cm;"><%=ServletUtility.getErrorMessage("gender", request)%></font>
         	  
               <br>
               <br>
        			<div class="text-center">
        			<%
						if (id > 0) {
					%>
                    <input type="submit" class="btn btn-primary" name="operation" value="<%=UserCtl.OP_UPDATE%>">
                    <input type="submit" class="btn btn-secondary" name="operation" value="<%=UserCtl.OP_CANCEL%>">
                    
                    <%
						} else {
					%>
					<input type="submit" class="btn btn-primary" name="operation" value="<%=UserCtl.OP_SAVE%>">
                    <input type="submit" class="btn btn-secondary" name="operation" value="<%=UserCtl.OP_RESET%>">
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