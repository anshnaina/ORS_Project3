<%@page import="com.sunilOS.ORSProject3.ctl.CollegeCtl"%>
<%@page import="com.sunilOS.ORSProject3.util.ServletUtility"%>
<%@page import="com.sunilOS.ORSProject3.util.HTMLUtility"%>
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
<form action = "<%=ORSView.COLLEGE_CTL%>" method="post" >
<jsp:useBean id="dto" class="com.sunilOS.ORSProject3.dto.CollegeDTO" scope="request"></jsp:useBean>
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
                    <h4 style="text-shadow:black;"><u><b>UPDATE COLLEGE</b></u></h4>
								<%
									} else {
								%>
                    <h4 style="text-shadow:black;"><u><b>ADD COLLEGE</b></u></h4>
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
				
		<span style="padding-right: 5.7cm;"><b>College Name</b><span style="color: red;">*</span></span> 
		<br>
         <div class="input-group">
         <div class="input-group-prepend">
         <span class="input-group-text"><i class="fa fa-university grey-text" style="font-size: 1rem;"></i></span>
         </div>
          <input type="text" class="form-control" name="collegeName" placeholder="Enter College Name" value="<%=DataUtility.getStringData(dto.getCollegeName())%>">
        </div>
         	<font color="red" style="padding-right: 4.3cm; font-size: .4cm;"><%=ServletUtility.getErrorMessage("collegeName", request)%></font>
        <br>
       
       	<span style="padding-right: 6.7cm;"><b>Address</b><span style="color: red;">*</span></span> 
		<br>
        <div class="input-group">
            <div class="input-group-prepend">
           		<span class="input-group-text"><i class="fa fa-map-marked grey-text" style="font-size: 1rem;"></i></span>
            </div>
             <input type="text" class="form-control" name="address" placeholder="Enter Address" value="<%=DataUtility.getStringData(dto.getAddress())%>">
        </div>
         	<font color="red" style="padding-right: 5.2cm; font-size: .4cm;"><%=ServletUtility.getErrorMessage("address", request)%></font>
        
        <br>
        	<span style="padding-right: 7.8cm;"><b>City</b><span style="color: red;">*</span></span> 
		<br>
        <div class="input-group">
            <div class="input-group-prepend">
           		<span class="input-group-text"><i class="fa fa-building grey-text" style="font-size: 1rem;"></i></span>
            </div>
             <input type="text" class="form-control" name="city" placeholder="Enter City" value="<%=DataUtility.getStringData(dto.getCity())%>">
        </div>
         	<font color="red" style="padding-right: 6cm; font-size: .4cm;"><%=ServletUtility.getErrorMessage("city", request)%></font>
        
        <br>
        
        	<span style="padding-right: 7.2cm;"><b>State</b><span style="color: red;">*</span></span> 
		<br>
        <div class="input-group">
            <div class="input-group-prepend">
           		<span class="input-group-text"><i class="fa fa-city grey-text" style="font-size: 1rem;"></i></span>
            </div>
             <input type="text" class="form-control" name="state" placeholder="Enter State" value="<%=DataUtility.getStringData(dto.getState())%>">
        </div>
         	<font color="red" style="padding-right: 5.5cm; font-size: .4cm;"><%=ServletUtility.getErrorMessage("state", request)%></font>
        
        <br>
        
        <span style="padding-right: 6.2cm;"><b>Mobile No.</b><span style="color: red;">*</span></span> 
		<br>
  		<div class="input-group">
            <div class="input-group-prepend">
           		<span class="input-group-text"><i class="fa fa-phone grey-text" style="font-size: 1rem;"></i></span>
            </div>
            <input type="text" class="form-control" name="mobileNo" placeholder="Enter Mobile No." value="<%=DataUtility.getStringData(dto.getPhoneNo())%>">
        </div>
         	<font color="red" style="padding-right: 4.8cm; font-size: .4cm;"><%=ServletUtility.getErrorMessage("mobileNo", request)%></font>

               <br>
               <br>
        			<div class="text-center">
        			<%
						if (id > 0) {
					%>
                    <input type="submit" class="btn btn-primary" name="operation" value="<%=CollegeCtl.OP_UPDATE%>">
                    <input type="submit" class="btn btn-secondary" name="operation" value="<%=CollegeCtl.OP_CANCEL%>">
                    
                    <%
						} else {
					%>
					<input type="submit" class="btn btn-primary" name="operation" value="<%=CollegeCtl.OP_SAVE%>">
                    <input type="submit" class="btn btn-secondary" name="operation" value="<%=CollegeCtl.OP_RESET%>">
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