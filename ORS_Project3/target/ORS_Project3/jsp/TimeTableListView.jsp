<%@ page language="java" contentType="text/html; charset=ISO-8859-1"  pageEncoding="ISO-8859-1"%>
<%@page import="com.sunilOS.ORSProject3.ctl.ORSView"%>
<%@page import="com.sunilOS.ORSProject3.ctl.BaseCtl"%>
<%@page import="com.sunilOS.ORSProject3.dto.TimeTableDTO"%>
<%@page import="com.sunilOS.ORSProject3.ctl.TimeTableCtl"%>
<%@page import="com.sunilOS.ORSProject3.ctl.TimeTableListCtl"%>
<%@page import="java.util.Iterator"%>
<%@page import="com.sunilOS.ORSProject3.util.ServletUtility"%>
<%@page import="com.sunilOS.ORSProject3.util.HTMLUtility"%>
<%@page import="com.sunilOS.ORSProject3.util.DataUtility"%>
<%@page import="java.util.List"%>
    
<!DOCTYPE html>
<html>
<head>
<title>Rays Technologies</title>
  <link href= "<%=ORSView.APP_CONTEXT%>/img/title.png" rel= "icon" type="icon">
  <link rel="stylesheet" href="<%=ORSView.APP_CONTEXT%>/css/FormView.css">
  <style>
.p1 
{
	background-image: url('<%=ORSView.APP_CONTEXT%>/img/bg-01.jpg');
	background-size: 100%;
}

</style>
</head>
<body class="p1">
<form action = "<%=ORSView.TIMETABLE_LIST_CTL%>" method = "post">
<%@include file = "Header.jsp"%>
<jsp:useBean id="dto" class="com.sunilOS.ORSProject3.dto.TimeTableDTO" scope="request"></jsp:useBean>

<div>
<center>
<br>
<h2 style="text-shadow: #343a40;"><u><b>TimeTable List</b></u></h2>

<br>
<%
if (!ServletUtility.getSuccessMessage(request).equals("")) 
	{
%>
					<H6 align="center" style = "width: 11.7cm; height: .5cm;">
					<div class="alert alert-success alert-dismissible">
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
					<H6 align="center" style = "width: 11.7cm; height: .5cm;">
					<div class="alert alert-danger alert-dismissible">
					<%=ServletUtility.getErrorMessage(request)%><button type="button" class="close" data-dismiss="alert">&times;</button>
					</div>
					</H6>
				<%
				}
				%>
<%
List courseList = (List) request.getAttribute("courseList");
List subjectList =(List) request.getAttribute("subjectList");
%>
<%
int pageNo = ServletUtility.getPageNo(request);
	int pageSize = ServletUtility.getPageSize(request);
	int index = ((pageNo - 1) * pageSize) + 1;
	int nextPageSize = DataUtility.getInt(request.getAttribute("nextTimeTableListSize").toString());
	List timeTableList = ServletUtility.getList(request);
	Iterator<TimeTableDTO> it = timeTableList.iterator();
	if (timeTableList.size() != 0) {
%>

<br>
<table>
<tr>
<td><%=HTMLUtility.getList("courseId", String.valueOf(dto.getCourseId()), courseList, "Select Course Name")%></td>
<td><%=HTMLUtility.getList("subjectId", String.valueOf(dto.getSubjectId()), subjectList, "Select Subject Name")%></td>
<td><input type = "date" name = "examDate" placeholder="Exam Date" value = "<%=DataUtility.getDateToString(dto.getExamDate())%>" class = "form-control" style = "width:5.5cm;"></td>
<td><input type = "submit" name = "operation" value="<%=TimeTableListCtl.OP_SEARCH%>" class = "srpnbtn"></td> 
<td><input type="submit" name="operation" value="<%=TimeTableListCtl.OP_RESET%>" class = "srpnbtn"></td>
</tr>
</table>

<br>
<table class="table table-sm table-hover" border="4" style="width: 80%;">
<thead>
<tr style="background-color: #343a40; color: white;">
<th scope="col"><input type = "checkbox" onclick="checkAll(this)"> Select All</th>
<th scope="col">S. No.</th>
<th scope="col">Course Name</th>
<th scope="col">Subject Name</th>
<th scope="col">Semester</th>
<th scope="col">Exam Date</th>
<th scope="col">Exam Time</th>
<th scope="col">Description</th>
<th scope="col">Edit</th>
</tr>
</thead>

<%
while(it.hasNext())
{
	TimeTableDTO timeTableDTO = (TimeTableDTO) it.next();
%>
<tbody>
<tr style="background-color: white">
	<td><input type = "checkbox" name = "ids" value="<%=timeTableDTO.getId()%>" ></td>
	<td><%=index++%></td>
	<td><%=timeTableDTO.getCourseName()%></td>
	<td><%=timeTableDTO.getSubjectName()%></td>
	<td><%=timeTableDTO.getSemester()%></td>
	<td><%=DataUtility.getDateToString(timeTableDTO.getExamDate())%></td>
	<td><%=timeTableDTO.getExamTime()%></td>
	<td><%=timeTableDTO.getDescription()%></td>
	<td><button type = "button" name = "operation" value = "<%=TimeTableListCtl.OP_EDIT%>" onclick="location.href='TimeTableCtl?id=<%=timeTableDTO.getId()%>'" class="fas fa-user-edit"></button></td>
</tr>

<%
	}
%>
</tbody>
</table>

</center>
<button style=" width:1cm; height: 1cm; margin-right: 32%; margin-left: 11%; background-color: red;" type="submit" name = "operation" value = "<%=TimeTableListCtl.OP_DELETE%>" class="fa fa-trash-alt btn"></button>

<input type = "submit" name = "operation" value = "<%=TimeTableListCtl.OP_PREVIOUS%>" <%=(pageNo > 1) ? "" : "disabled"%> class = "srpnbtn">

<input type = "submit" name = "operation" value = "<%=TimeTableListCtl.OP_NEXT%>" <%=(nextPageSize != 0) ? "" : "disabled"%> class = "srpnbtn">

<input style="margin-right: 6%; margin-left: 26%;" type = "submit" name = "operation" value = "<%=TimeTableListCtl.OP_NEW%>" class = "srpnbtn" >

<%
				}
				if (timeTableList.size() == 0) {
			%>
			<br>
			<br>
			<input type="submit" name="operation" value="<%=TimeTableListCtl.OP_BACK%>" class = "srpnbtn">

			<%
				}
			%>
<input type = "hidden" name = "pageNo" value="<%=pageNo%>"> 
<input type = "hidden" name = "pageSize" value="<%=pageSize%>">
</div>
<%@include file="Footer.jsp"%>
<br>
<br>
</form>
</body>
</html>