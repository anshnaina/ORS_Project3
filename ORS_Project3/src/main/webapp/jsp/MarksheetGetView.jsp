<%@page import="com.sunilOS.ORSProject3.util.DataUtility"%>
<%@page import="com.sunilOS.ORSProject3.ctl.MarksheetGetCtl"%>
<%@page import="com.sunilOS.ORSProject3.util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Rays Technologies</title>
  <link href= "<%=ORSView.APP_CONTEXT%>/img/title.png" rel= "icon" type="icon">
  <link rel="stylesheet" href="<%=ORSView.APP_CONTEXT%>/css/FormView.css">
  <style>
.p1 
{
	background-image: url('<%=ORSView.APP_CONTEXT%>/img/bg-01.jpg');
	background-size: 100%;
}
.po1 
{
	font-size: 18px;
	text-align:center;
	height: 100px;
}

</style>
</head>
<body class="p1">
<%@include file="Header.jsp"%>
<form action="<%=ORSView.MARKSHEET_GET_CTL%>" method="post">
<jsp:useBean id="dto" class="com.sunilOS.ORSProject3.dto.MarksheetDTO" scope="request"></jsp:useBean>

<br>
			<div align="center">
				<h2 style="text-shadow: #343a40;"><u><b>Get Marksheet</b></u></h2>
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
			<br>
			<div align="center">
			<input type="text" name="rollNo" value="<%=ServletUtility.getParameter("rollNo", request)%>" placeholder="Enter Roll No.">
			 
			<input type="submit" class="btn btn-dark" name="operation" value="<%=MarksheetGetCtl.OP_GO%>">
			<p><font color="red"><%=ServletUtility.getErrorMessage("rollNo", request)%></font></p>
			
			</div>
</center>			
				
				<%
							if (dto.getRollNo() != null && dto.getRollNo().trim().length() > 0) {
						%>
			
			<div class="container" >
			
			<table  width="100%" style="background-color: white;" border="4px">
				<tr >
					<th id="po1" colspan="2" >Roll No.</th>
					<td id="po1" align="center" colspan="3"><%=DataUtility.getStringData(dto.getRollNo())%></td>
				</tr>
				<tr>
					<th id="po1" colspan="2">Name</th>
					<td id="po1" align="center" colspan="3"><%=DataUtility.getStringData(dto.getName())%></td>
				</tr>
				<tr style="background-color: silver;">
					<th>Subject</th>
					<th align="center">Max Marks</th>
					<th align="center">Min Marks</th>
					<th align="center">Marks</th>
					<th align="center">Grade</th>
				</tr>
				
				<tr>
					<th id="po1">Physics</th>
					<td align="center">100</td>
					<td align="center">35</td>
					<td id="po1" align="center"><%=DataUtility.getStringData(String.valueOf(dto.getPhysics()))%>
						<%
							float physics = dto.getPhysics();
								if (dto.getPhysics() < 35) {
						%><span style="color: red;">*</span> <%
 	}
 %></td>
					<td align="center">
						<%
							if (physics >= 90) {
						%> <b>A++</b> <%
 	} else if (physics >= 80) {
 %> <b>A</b> <%
 	} else if (physics >= 70) {
 %> <b>B++</b> <%
 	} else if (physics >= 60) {
 %> <b>B</b> <%
 	} else if (physics >= 50) {
 %> <b>C++</b> <%
 	} else if (physics >= 40) {
 %> <b>C</b> <%
 	} else if (physics >= 35) {
 %> <b>D</b> <%
 	} else if (physics >= 0) {
 %> <font color="red"><b>F</b></font> <%
 	}
 %>
					</td>
				</tr>
				<tr>
					<th id="po1">Chemistry</th>
					<td align="center">100</td>
					<td align="center">35</td>
					<td id="po1" align="center"><%=DataUtility.getStringData(String.valueOf(dto.getChemistry()))%>
						<%
							float chemistry = dto.getChemistry();
								if (dto.getChemistry() < 35) {
						%><span style="color: red;">*</span> <%
 	}
 %></td>
					<td align="center">
						<%
							if (chemistry >= 90) {
						%> <b>A++</b> <%
 	} else if (chemistry >= 80) {
 %> <b>A</b> <%
 	} else if (chemistry >= 70) {
 %> <b>B++</b> <%
 	} else if (chemistry >= 60) {
 %> <b>B</b> <%
 	} else if (chemistry >= 50) {
 %> <b>C++</b> <%
 	} else if (chemistry >= 40) {
 %> <b>C</b> <%
 	} else if (chemistry >= 35) {
 %> <b>D</b> <%
 	} else if (chemistry >= 0) {
 %> <font color="red"><b>F</b></font> <%
 	}
 %>
					</td>
				</tr>
				<tr>
					<th id="po1">Maths</th>
					<td align="center">100</td>
					<td align="center">35</td>
					<td id="po1" align="center"><%=DataUtility.getStringData(String.valueOf(dto.getMaths()))%>
						<%
							float marks = dto.getMaths();
								if (marks <= 35) {
						%><span style="color: red;">*</span> <%
 	}
 %></td>
					<td align="center">
						<%
							if (marks >= 90) {
						%> <b>A++</b> <%
 	} else if (marks >= 80) {
 %> <b>A</b> <%
 	} else if (marks >= 70) {
 %> <b>B++</b> <%
 	} else if (marks >= 60) {
 %> <b>B</b> <%
 	} else if (marks >= 50) {
 %> <b>C++</b> <%
 	} else if (marks >= 40) {
 %> <b>C</b> <%
 	} else if (marks >= 35) {
 %> <b>D</b> <%
 	} else if (marks >= 0) {
 %> <font color="red"><b>F</b></font> <%
 	}
 %>
					</td>

				</tr>
				<tr>
					<th id="po1" colspan="2">Total</th>

					<td id="po1" align="center" colspan="3">
						<%
							int total = ((dto.getMaths()) + (dto.getPhysics()) + (dto.getChemistry()));
								float percentage = (total * 100) / 300;
						%><%=total%></td>
				</tr>
				<%
					if ((dto.getMaths() > 35) && (dto.getPhysics() > 35) && (dto.getChemistry() > 35)) {
				%>
				<tr>

					<th id="po1" colspan="2">Percentage</th>

					<td id="po1" align="center" colspan="3"><%=percentage%>%</td>
				</tr>
				<tr>
					<th id="po1" align="center" colspan="2"><font color="blue">Grade:</font>
						<%
							if (percentage >= 90) {
						%> <b>A++</b> <%
 	} else if (percentage >= 80) {
 %> <b>A</b> <%
 	} else if (percentage >= 70) {
 %> <b>B++</b> <%
 	} else if (percentage >= 60) {
 %> <b>B</b> <%
 	} else if (percentage >= 50) {
 %> <b>C++</b> <%
 	} else if (percentage >= 40) {
 %> <b>C</b> <%
 	} else if (percentage >= 35) {
 %> <b>D</b> <%
 	} else if (percentage >= 0) {
 %> <font color="red"><b>F</b></font> <%
 	}
 %></th>
					<td id="po1" align="center" colspan="3">
						<%
							if (percentage >= 35) {
						%> <font color="green"><b>PASS</b></font> <%
 	}
 %>
					</td>
				</tr>
				<%
					} else {
				%>
				<tr>
					<th id="po1" align="center" colspan="2"><font color="blue">Final Grade:</font><font
						color="red"><b>F</b></font></th>
					<td id="po1" align="center" colspan="3"><font color="red"><b>FAIL</b></font></td>
				</tr>

				<%
					}
				%>
			</table>

			<%
				}
			%>
	
			</div>

</body>
<%@include file="Footer.jsp"%>
</html>