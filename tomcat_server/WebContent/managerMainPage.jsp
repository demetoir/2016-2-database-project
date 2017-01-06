<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="tomcat_server.userCheck"%>
<%@page import="org.apache.catalina.User"%>
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
    <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    <title> YDC main page </title>
    <%@ page import="tomcat_server.checkLogin" %>
</head>

<body>
	<div>
	    <h1> manager main page</h1><br>
	</div>
	<div>
		<% 
			userCheck user= new userCheck();
			user.loadUserData(request, response);
			String id = user.getUserId();
			String name = user.getUserName();
			String type = user.getUserType();
		%>
	</div>

	<div>
		user information<br>
		user Id :<%= id %><br>
		user name :<%= name %><br>
		user type :<%= type %><br>
	</div>
	
	<div>
		<form action="managePlanet.jsp" method="post">
	        <input type="submit" value="manage Planet">
	        <input type="hidden" name ="id" value = <%=id %> >        
	    </form> <br>
	</div>
	
	<div>
		<form action="manageCargo_Owner.jsp" method="post">
	        <input type="submit" value="manage Cargo_Owner">
	        <input type="hidden" name ="id" value = <%=id %> >        
	    </form> <br>
	</div>
	
	<div>
		<form action="manageCargoShip.jsp" method="post">
	        <input type="submit" value="manage CargoShip">
	        <input type="hidden" name ="id" value = <%=id %> >        
	    </form> <br>
	</div>
	
	<div>
		<form action="manageCrew.jsp" method="post">
	        <input type="submit" value="manage Crew">
	        <input type="hidden" name ="id" value = <%=id %> >        
	    </form> <br>
	</div>
	
	<div>
		<form action="statisticPage.jsp" method="post">
	        <input type="submit" value="statistic Page">
	        <input type="hidden" name ="id" value = <%=id %> >        
	    </form> <br>
	</div>
	
	
	<div>
	    <form action="logout.jsp" method="post">
	        <input type="submit" value="logout">
	        <input type="hidden" name ="id" value = <%=id %> >        
	    </form> <br>	
	</div>	
	
</body>

</html>