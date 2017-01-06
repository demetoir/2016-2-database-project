<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="tomcat_server.userCheck"%>
<%@page import="tomcat_server.crewMainPage"%>
<%@page import="org.apache.catalina.User"%>
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
    <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    <title> YDC crew main page </title>
    <%@ page import="tomcat_server.checkLogin" %>
</head>

<body>
	<div>
	    <h1> crew main page</h1><br>
	</div>
	
	<div>
		<% 
			userCheck user = new userCheck();
			user.loadUserData(request, response);
			String id = user.getUserId();
			String name = user.getUserName();
			String type = user.getUserType();
			crewMainPage mainPage = new crewMainPage(request, response);
			mainPage.setCargoShipDelivering(request, response);
		%>
	</div>
	<div>
	    <form action="logout.jsp" method="post">
	        <input type="submit" value="logout">
	        <input type="hidden" name ="id" value = <%=id %> >        
	    </form> <br>
	</div>
	
</body>

</html>