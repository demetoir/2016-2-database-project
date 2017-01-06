<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="tomcat_server.managePlanet"%>
<%@page import="com.sun.org.glassfish.gmbal.ManagedAttribute"%>
<%@page import="java.util.ArrayList"%>
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<title>YDC manage cargo Owner page</title>
<%@ page import="tomcat_server.userCheck"%>
<%@ page import="tomcat_server.manageCargo_Owner"%>
</head>

<body>
	<%
		userCheck user = new userCheck();
		user.loadUserData(request, response);
		String id = user.getUserId();
		String name = user.getUserName();
		String type = user.getUserType();
		manageCargo_Owner Manger = new manageCargo_Owner();
		Manger.register(request, response);
	%>
	<div>
		<h1>manage cargo Owner ship</h1>
	</div>
	<div>
		<%= user.printUserInformation() %>
	</div>
	<br>
	<div>
		<form action="logout.jsp" method="post">
			<input type="submit" value="logout"> <input type="hidden"
				name="id" value=<%=id%>>
		</form>
	</div>
	<br>
	<br>
	<div>
		<form action="managerMainPage.jsp" method="post">
			<input type="submit" value="go back to main page"> <input
				type="hidden" name="id" value=<%=id %>>
		</form>
	</div>
	
	
	<div>
		<%= Manger.printFailMsg(request) %>
	</div>
	
</body>

</html>