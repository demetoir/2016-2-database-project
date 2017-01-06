<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="tomcat_server.managePlanet"%>
<%@page import="com.sun.org.glassfish.gmbal.ManagedAttribute"%>
<%@page import="java.util.ArrayList"%>
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<title>YDC register planet page</title>
<%@ page import="tomcat_server.userCheck"%>
<%@ page import="tomcat_server.managePlanet"%>
</head>

<body>
	<%
		userCheck user = new userCheck();
		user.loadUserData(request, response);
		String id = user.getUserId();
		String name = user.getUserName();
		String type = user.getUserType();
		managePlanet planetManager = new managePlanet();
	%>
	<div>
		<h1>register Planet</h1>
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
	<div>
		<form action="managerMainPage.jsp" method="post">
			<input type="submit" value="go back to main page"> <input
				type="hidden" name="id" value=<%=id %>>
		</form>
	</div>
	<hr>
	
	<div>
		<%=planetManager.printFailMsg(request) %>
	</div>
	<hr>
	<br>
	<div>
		<div>
			행성 정보 추가	<br>
		</div>
		<div>
			<form action="registerPlanet.jsp" method="post">
				planet name : <input type="text" name="planetName"><br>
				X 좌표 : <input type="text" name="cordinate_x"><br>
				Y 좌표 : <input type="text" name="cordinate_y"><br>
				Z 좌표 : <input type="text" name="cordinate_z"><br> 
				planet type : <input type="text" name="planetType"><br>
				distance : <input type="text" name="distance"> <br>
				population : <input type="text" name="population"><br> 
				할인 및 할증률 : <input type="text" 	name="discount"> <br>
				<input type="submit" value="register planet"> <br>
				<input type="hidden" name="id" value=<%=id%>>
			</form>
		</div>
	</div>
	<hr>
	<br>
	<div>
		<div>
			행성 정보 삭제
		</div>
		<div>
			<form action="deletePlanet.jsp" method="post">
				삭제할 행성 id : <input type="text" name="planetID"><br>				
				<input type="submit" value="delete planet"> <br>
				<input type="hidden" name="id" value=<%=id%>>
			</form>
		</div>
	</div>
	<hr>
	<br>
	<div>
		<div>
			행성 정보 검색
		</div>
		<div>
			<form action="searchPlanet.jsp" method="post">
				<select name = "search option">
					<option> planet ID </option> 				
					<option> planet name </option>
					<option> X 좌표 </option>
					<option> Y 좌표 </option>
					<option> Z 좌표 </option>
					<option> planet type </option>
					<option> distance </option>
					<option> population </option>
					<option> 할인 및 할증률 </option>
				</select>
				<input type="text" name="search key">				
				<input type="submit" value="search"> <br>
				<input type="hidden" name="id" value=<%=id%>>
			</form>
		</div>		
		<div>
			<table border="1">
				<%=planetManager.printSearchResult(request)%>
			</table>
		</div>
	</div>
	
	<hr>
	<br>
	<div>
		<div>
			행성 정보 수정	<br>
			빈칸은 정보 그대로 유지
		</div>
		<div>
			<form action="changePlanet.jsp" method="post">
				planet ID : <input type="text" name="planetID"> <br>
				planet name : <input type="text" name="planetName"><br>
				X 좌표 : <input type="text" name="cordinate_x"><br>
				Y 좌표 : <input type="text" name="cordinate_y"><br>
				Z 좌표 : <input type="text" name="cordinate_z"><br> 
				planet type : <input type="text" name="planetType"><br>
				distance : <input type="text" name="distance"> <br>
				population : <input type="text" name="population"><br> 
				할인 및 할증률 : <input type="text" 	name="discount"> <br>
				<input type="submit" value="change planet"> <br>
				<input type="hidden" name="id" value=<%=id%>>
			</form>
		</div>
	</div>
	<hr>
	
	<br>
	<div>
		<div>
			전체 행성 정보
		</div>
		<div>
			<table border="1">
				<%=planetManager.printAllTableTupleList()%>
			</table>
		</div>
	</div>
	

	<br>
	<div>
		<form action="logout.jsp" method="post">
			<input type="submit" value="logout"> <input type="hidden"
				name="id" value=<%=id%>>
		</form>
	</div>
	<br>
	<div>
		<form action="managerMainPage.jsp" method="post">
			<input type="submit" value="go back to main page"> <input
				type="hidden" name="id" value=<%=id %>>
		</form>
	</div>
	
	
</body>

</html>