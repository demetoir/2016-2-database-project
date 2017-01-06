<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="tomcat_server.managePlanet"%>
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="tomcat_server.userCheck"%>
<title>search planet page</title>
</head>

<body>
	<% 
		userCheck user= new userCheck();
		user.loadUserData(request, response);
		String id = user.getUserId();
		String name = user.getUserName();
		String type = user.getUserType();
		//get_table
	%>
	<div>
		<h1>search Planet</h1>
	</div>
	<div>
		user information<br> user Id :
		<%= id %><br> user name :<%= name %><br> user type :<%= type %><br>
	</div>
	<br>
	<div>
		<%
			managePlanet planetManager = new managePlanet();
			planetManager.searchPlanet(request, response);		
		%>
	</div>

	<div>
		<form action="managerMainPage.jsp" method="post">
			<input type="submit" value="go back to main page"> <input
				type="hidden" name="id" value=<%=id %>>
		</form>
	</div>
	<br>
	<div>
		<form action="logout.jsp" method="post">
			<input type="submit" value="logout"> <input type="hidden"
				name="id" value=<%=id %>>
		</form>
	</div>
</body>

</html>