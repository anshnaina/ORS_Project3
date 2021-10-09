<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="com.sunilOS.ORSProject3.ctl.LoginCtl"%>
<%@page import="com.sunilOS.ORSProject3.dto.UserDTO"%>
<%@page import="com.sunilOS.ORSProject3.ctl.ORSView"%>
<%@page import="com.sunilOS.ORSProject3.dto.RoleDTO"%>

<!DOCTYPE html>
<html>
<head>
  <link rel="stylesheet"
	href="https://use.fontawesome.com/releases/v5.6.3/css/all.css" integrity="sha384-UHRtZLI+pbxtHCWp1t77Bi1L4ZtiqrqD80Kn4Z8NTSRyMA2Fd33n5dQ8lWUE00s/" crossorigin="anonymous">
	<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>
	
 <script>
  
  function checkAll(bx) {
		var form = bx.form;
		var isChecked = bx.checked;
		for (var i = 0; i < form.length; i++) {
			if (form[i].type == 'checkbox') {
				form[i].checked = isChecked;
			}
		}
	}
	
	function capitalize(name) {
		var s = name.value;
		name.value = s.substring(0, 1).toUpperCase() + s.substring(1);
	}
	
</script>
<style>

.border-md-right {
  border-right: 2px solid silver;
  padding-right: .3cm;
}

</style>
</head>
<body>
<%
		UserDTO userDto = (UserDTO) session.getAttribute("user");

		boolean userLoggedIn = userDto != null;

		String welcomeMsg = "Hi, ";

		if (userLoggedIn) {
			String role = (String) session.getAttribute("role");
			welcomeMsg += userDto.getFirstName() + " (" + role + ")";
		} else {
			welcomeMsg += "Guest";
		}
	%>
<div class="header">
<nav class="navbar navbar-dark bg-dark navbar-expand">

<a class="navbar-brand mb-0 h1 border-md-right" href="<%=ORSView.WELCOME_CTL%>">
<img src="<%=ORSView.APP_CONTEXT%>/img/main_logo.png" alt="Rays" height="25" href="#"> Welcome  </a>
		<button class="navbar-toggler" type="button" data-toggle="collapse"
			data-target="#navbarNav" aria-controls="navbarNav"
			aria-expanded="false" aria-label="Toggle navigation">
			<span class="navbar-toggler-icon"></span>
		</button>
		<div class="collapse navbar-collapse" id="navbarNav">
			<ul class="nav navbar-nav mr-auto">
				<a class="nav-link" href="#"> 
				<span class="sr-only">(current)</span>
				</a>
    <%
					if (userLoggedIn) {
				%>
				<%
					if (userDto.getRoleId() == RoleDTO.ADMIN) {
				%>
      
      <li class="nav-item dropdown" style="padding-left: 5px;"><a
					class="nav-link dropdown-toggle" href="#" id="navbarDropdown"
					role="button" data-toggle="dropdown" aria-haspopup="true"
					aria-expanded="false"> <font style="color: white;">User</font>
				</a>
					<div class="dropdown-menu" aria-labelledby="navbarDropdown">
						<a class="dropdown-item" href="<%=ORSView.USER_CTL%>"><i
							class="fas fa-user-alt"></i> Add User</a> <a class="dropdown-item"
							href="<%=ORSView.USER_LIST_CTL%>"><i
							class="fas fa-users"></i> User List</a>
					</div>
				</li>
				
				<li class="nav-item dropdown" style="padding-left: 5px"><a
					class="nav-link dropdown-toggle" href="#" id="navbarDropdown"
					role="button" data-toggle="dropdown" aria-haspopup="true"
					aria-expanded="false"> <font style="color: white;">Role</font>
				</a>
					<div class="dropdown-menu" aria-labelledby="navbarDropdown">
						<a class="dropdown-item" href="<%=ORSView.ROLE_CTL%>"><i
							class="fas fa-user-tie"></i> Add Role</a> <a class="dropdown-item"
							href="<%=ORSView.ROLE_LIST_CTL%>"><i
							class="fas fa-user-friends"></i> Role List</a>
					</div></li>
				<li class="nav-item dropdown" style="padding-left: 5px"><a
					class="nav-link dropdown-toggle" href="#" id="navbarDropdown"
					role="button" data-toggle="dropdown" aria-haspopup="true"
					aria-expanded="false"> <font style="color: white;">College</font>
				</a>
					<div class="dropdown-menu" aria-labelledby="navbarDropdown">
						<a class="dropdown-item" href="<%=ORSView.COLLEGE_CTL%>"><i
							class="fas fa-university"></i> Add College</a> <a
							class="dropdown-item" href="<%=ORSView.COLLEGE_LIST_CTL%>"><i
							class="fas fa-building"></i> College List </a>
					</div></li>
							
				<li class="nav-item dropdown" style="padding-left: 5px"><a
					class="nav-link dropdown-toggle" href="#" id="navbarDropdown"
					role="button" data-toggle="dropdown" aria-haspopup="true"
					aria-expanded="false"> <font style="color: white;">Student</font>
				</a>
					<div class="dropdown-menu" aria-labelledby="navbarDropdown">
						<a class="dropdown-item" href="<%=ORSView.STUDENT_CTL%>"><i
							class="fas fa-user-graduate"></i> Add Student</a> <a
							class="dropdown-item" href="<%=ORSView.STUDENT_LIST_CTL%>"><i
							class="fas fa-list-alt"></i> Student List</a>
					</div></li>
				<li class="nav-item dropdown" style="padding-left: 5px"><a
					class="nav-link dropdown-toggle" href="#" id="navbarDropdown"
					role="button" data-toggle="dropdown" aria-haspopup="true"
					aria-expanded="false"> <font style="color: white;">Course
					</font></a>
					<div class="dropdown-menu" aria-labelledby="navbarDropdown">
						<a class="dropdown-item" href="<%=ORSView.COURSE_CTL%>"><i
							class="fas fa-book-open"></i> Add Course</a> <a class="dropdown-item"
							href="<%=ORSView.COURSE_LIST_CTL%>"><i
							class="fas fa-list-ul"></i> Course List </a>
					</div></li>
				<li class="nav-item dropdown" style="padding-left: 5px"><a
					class="nav-link dropdown-toggle" href="#" id="navbarDropdown"
					role="button" data-toggle="dropdown" aria-haspopup="true"
					aria-expanded="false"> <font style="color: white;">Subject</font>
				</a>
					<div class="dropdown-menu" aria-labelledby="navbarDropdown">
						<a class="dropdown-item" href="<%=ORSView.SUBJECT_CTL%>"><i
							class="fas fa-book"></i> Add Subject</a> <a
							class="dropdown-item" href="<%=ORSView.SUBJECT_LIST_CTL%>"> <i
							class="fas fa-swatchbook"></i> Subject List
						</a>
					</div></li>
					
				<li class="nav-item dropdown" style="padding-left: 5px"><a
					class="nav-link dropdown-toggle" href="#" id="navbarDropdown"
					role="button" data-toggle="dropdown" aria-haspopup="true"
					aria-expanded="false"> <font style="color: white;">Faculty
					</font></a>
					<div class="dropdown-menu" aria-labelledby="navbarDropdown">
						<a class="dropdown-item" href="<%=ORSView.FACULTY_CTL%>"><i
							class="fas fa-chalkboard-teacher"></i> Add Faculty</a> <a class="dropdown-item"
							href="<%=ORSView.FACULTY_LIST_CTL%>"><i class="	fas fa-user-tie"></i> Faculty
							List</a>
					</div></li>
					
					
				<li class="nav-item dropdown" style="padding-left: 5px"><a
					class="nav-link dropdown-toggle" href="#" id="navbarDropdown"
					role="button" data-toggle="dropdown" aria-haspopup="true"
					aria-expanded="false"> <font style="color: white;">Marksheet
					</font></a>
					<div class="dropdown-menu" aria-labelledby="navbarDropdown">
						<a class="dropdown-item" href="<%=ORSView.MARKSHEET_CTL%>"><i
							class="fa fa-file"></i> Add Marksheet</a> <a class="dropdown-item"
							href="<%=ORSView.MARKSHEET_LIST_CTL%>"><i
							class="fas fa-paste"></i> Marksheet List</a> <a class="dropdown-item"
							href="<%=ORSView.MARKSHEET_MERIT_LIST_CTL%>"><i
							class="	fa fa-file-alt"></i> Marksheet Merit List </a> <a
							class="dropdown-item" href="<%=ORSView.MARKSHEET_GET_CTL%>"><i
							class="fa fa-copy"></i> Get Marksheet</a>
					</div>
				</li>


				<li class="nav-item dropdown" style="padding-left: 5px;">
				<a class="nav-link dropdown-toggle" href="#" id="navbarDropdown"
					role="button" data-toggle="dropdown" aria-haspopup="true"
					aria-expanded="false"> <font style="color: white;">Time
							Table</font>
				</a>
					<div class="dropdown-menu" aria-labelledby="navbarDropdown">
						<a class="dropdown-item" href="<%=ORSView.TIMETABLE_CTL%>"><i
							class="fas fa-clock"></i> Add TimeTable</a> <a class="dropdown-item"
							href="<%=ORSView.TIMETABLE_LIST_CTL%>"><i
							class="far fa-calendar-check"></i> TimeTable List</a>
					</div>
					</li>
				<li class="nav-item dropdown border-md-right" style="padding-left: 5px;">
						<a class="nav-link" href="<%=ORSView.JAVA_DOC_VIEW%>"
					role="button" aria-haspopup="true"
					aria-expanded="false" target="blank"> <font style="color: white;">Java Doc</font>
				</a>
				</li>
				<%
					}
					}
				%>
				<%
						if (userLoggedIn) {
				%>
				<li class="nav-item dropdown" style="padding-left:5px;">
				<a class="nav-link dropdown-toggle" href="#" id="navbarDropdown"
					role="button" data-toggle="dropdown" aria-haspopup="true"
					aria-expanded="false">
					<font style="color: white;"><%=welcomeMsg%></font></a>
					<div class="dropdown-menu" aria-labelledby="navbarDropdown">
						
						<a class="dropdown-item" href="<%=ORSView.LOGIN_CTL%>?operation=<%=LoginCtl.OP_LOG_OUT%>">
						<i class="fas fa-sign-out-alt"></i> Logout </a> <a class="dropdown-item"
							href="<%=ORSView.MY_PROFILE_CTL%>"><i class="fas fa-user-tie"></i> My Profile</a> 
							<a class="dropdown-item" href="<%=ORSView.CHANGE_PASSWORD_CTL%>"> 
							<i class="fas fa-unlock-alt"></i> Change Password</a>
						</div></li>
   						
						<%
							} else {
						%>
   					
   				<li class="nav-item dropdown" style="padding-left: 5px;">
						<a class="nav-link" href="<%=ORSView.LOGIN_CTL%>"
					role="button" aria-haspopup="true"
					aria-expanded="false"> 
					<i class="fas fa-sign-in-alt"></i>
					<font style="color: white;"> Login</font>
				</a>
				</li>
        
         	  <li class="nav-item dropdown border-md-right" style="padding-left: 10px; padding-right: 19cm;">
						<a class="nav-link" href="<%=ORSView.USER_REGISTRATION_CTL%>"
					role="button" aria-haspopup="true"
					aria-expanded="false">
					<i class="fas fa-registered"></i>
					<font style="color: white;"> User Registration</font>
				</a>
				</li>
				
				<li class="nav-item dropdown active" style="padding-left: 5px;">
						<a class="nav-link" aria-haspopup="true" aria-expanded="false">
						<i class="fas fa-user"></i>
					<font style="color: white;">  <%=welcomeMsg%></font>
						</a>
				</li>
				<%
					}
				%>
     </ul>
    </nav>
</div>
 
 </body>
</html>