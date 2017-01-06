<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
	<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    <title>YDC checking your account</title>
    <%@ page import = "tomcat_server.checkLogin" %>
</head>

<body>
    <font size=20> check account ...</font><br>

    <% 
        checkLogin check= new checkLogin();
    	check.check(request, response);
    %>
        login fail....<br>
        
        
</body>

</html>