<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="org.apache.catalina.User"%>
<%@page import="tomcat_server.userCheck"%>
<%@page import="tomcat_server.cargoOwnerMainPage"%>
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
    <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    <title> YDC cargo Owner main page </title>
    <%@ page import="tomcat_server.checkLogin" %>
</head>

<body>
	<div>
	    <h1> cargo Owner main page</h1><br>
	</div>
	<div>
		<% 
			userCheck user = new userCheck();
			user.loadUserData(request, response);
			String id = user.getUserId();
			String name = user.getUserName();
			String type = user.getUserType();
			cargoOwnerMainPage mainPage = new cargoOwnerMainPage(request, response);
		%>
	</div>

	<div>
		user information<br>
		user Id :<%= id %><br>
		user name :<%= name %><br>
		user type :<%= type %><br>
	</div>
	<div>
			<%= mainPage.printCargoOwnerData()%>
			<% mainPage.confirm(request, response);%>
			
	</div>



</body>

</html>