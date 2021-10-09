<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="com.sunilOS.ORSProject3.util.ServletUtility"%>
<%@page import="com.sunilOS.ORSProject3.ctl.ORSView"%>
<%@page import="com.sunilOS.ORSProject3.dto.MarksheetDTO"%>
<%@page import="com.sunilOS.ORSProject3.ctl.MarksheetMeritListCtl"%>
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
<form action = "<%=ORSView.MARKSHEET_MERIT_LIST_CTL%>">
<%@include file = "Header.jsp"%>
<center>
<br>
<h2 style="text-shadow: #343a40;"><u><b>Marksheet Merit List</b></u></h2>
<br>
<table class="table table-sm table-hover" border="4" style="width: 80%;">
<thead>
<tr style="background-color: #343a40; color: white;">
<th scope="col">S.No.</th>
<th scope="col">Roll No.</th>
<th scope="col">Student Name</th>
<th scope="col">Physics</th>
<th scope="col">Maths</th>
<th scope="col">Chemistry</th>
<th scope="col">Total</th>
<th scope="col">Percentage(%)</th>
</tr>
</thead>
<tbody>
<%
int pageNo = ServletUtility.getPageNo(request);
int pageSize = ServletUtility.getPageSize(request);
List list = ServletUtility.getList(request);
Iterator<MarksheetDTO> it = list.iterator();
int i = 1;
while(it.hasNext())
{
	MarksheetDTO marksheetDTO = (MarksheetDTO) it.next();
	int total=marksheetDTO.getChemistry()+marksheetDTO.getMaths()+marksheetDTO.getPhysics();
	float percentage = (total * 100) / 300;
	%>
	<tr style="background-color: white">
	<td><%=i++%></td>
	<td><%=marksheetDTO.getRollNo()%></td>
	<td><%=marksheetDTO.getName()%></td>
	<td><%=marksheetDTO.getPhysics()%></td>
	<td><%=marksheetDTO.getChemistry()%></td>
	<td><%=marksheetDTO.getMaths()%></td>
	<td><%=total%></td>
	<td><%=percentage%></td>
	 </tr>
<%
}
%>
</tbody>
</table>


<a style="font-size: 16px; background-color: black; color: white;" href="/ORS_Project3/ctl/JasperCtl" class="btn" role="button" target = "blank">
<span class="fa fa-print mr-1"></span>Print</a>
<input type = "hidden" name = "pageNo" value="<%=pageNo%>"> 
<input type = "hidden" name = "pageSize" value="<%=pageSize%>">

</center>
<%@include file = "Footer.jsp"%>
<br>
<br>
</form>
</body>
</html>