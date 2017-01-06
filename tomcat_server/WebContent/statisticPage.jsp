<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="tomcat_server.statisticPage"%>
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
	    <h1> manage statistic page</h1><br>
	</div>
	<div>
		<% 
			userCheck user= new userCheck();
			user.loadUserData(request, response);
			String id = user.getUserId();
			String name = user.getUserName();
			String type = user.getUserType();
			statisticPage mainPage = new statisticPage();  
		%>
	</div>

	<div>
		user information<br>
		user Id :<%= id %><br>
		user name :<%= name %><br>
		user type :<%= type %><br>
	</div>
	<div>
	    <form action="logout.jsp" method="post">
	        <input type="submit" value="logout">
	        <input type="hidden" name ="id" value = <%=id %> >        
	    </form> <br>	
	</div>	
	<br>
	<div>
		<form action="managerMainPage.jsp" method="post">
			<input type="submit" value="go back to main page"> 
			<input type="hidden" name="id" value=<%=id %>>
		</form>
	</div>
	
	
	<hr>
	<div>
		<div>
				총수입 : <%=  mainPage.printTotalIncome() %><br>	
		</div>
	</div>
	
	<hr>
	<div>
		<div>
			화주별 운송량 및지불금액<br> 
		</div>
		<div>
			<%=  mainPage.printStaticCargoOwner() %><br>	
		</div>
	</div>
	
	<hr>
	<div>
		<div>
			수송선 운송량 및 수입 금액<br> 
		</div>
		<div>
			<%=  mainPage.printStaticCargoShip() %><br>	
		</div>
	</div>
	
	<hr>
	<div>
		<div>
			모든 log 출력<br> 
		</div>
		<div>
			<%=  mainPage.printLog() %><br>	
		</div>
	</div>
	
	
	
	
	

	
</body>

</html>