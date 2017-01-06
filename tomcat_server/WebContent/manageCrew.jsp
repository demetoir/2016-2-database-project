<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="tomcat_server.managePlanet"%>
<%@page import="com.sun.org.glassfish.gmbal.ManagedAttribute"%>
<%@page import="java.util.ArrayList"%>
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<title>YDC manage crew page</title>
<%@ page import="tomcat_server.userCheck"%>
<%@ page import="tomcat_server.manageCrew"%>
</head>

<body>
	<%
		userCheck user = new userCheck();
		user.loadUserData(request, response);
		String id = user.getUserId();
		String name = user.getUserName();
		String type = user.getUserType();
		manageCrew crewManger = new manageCrew();
	%>
	<div>
		<h1>manage crew ship</h1>
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
		<%= crewManger.printFailMsg(request) %>
	</div>
	<hr>
	<br>
	
	<div>
		<div>
			 선원 정보 추가	<br>
		</div>
		<div>
			<form action="registerCrew.jsp" method="post">
				이름 : <input type="text" name="name"><br>
				직위 : <input type="text" name="position"><br>
				역할 : <input type="text" name="role"><br> 
				phone_number : <input type="text" name="phone_number"><br> 
				email_address : <input type="text" name="email_address"><br> 
				address : <input type="text" name="address"><br> 
				<input type="submit" value="register crew"> <br>
				<input type="hidden" name="id" value=<%=id%>>
			</form>
		</div>
	</div>
	<hr>
	<br>
	
	<div>
		<div>
			선원 정보 삭제
		</div>
		<div>
			<form action="deleteCrew.jsp" method="post">
				crew id : <input type="text" name="crew_ID"><br>				
				<input type="submit" value="delete crew"> <br>
				<input type="hidden" name="id" value=<%=id%>>
			</form>
		</div>
	</div>
	
	<hr>
	<br>
	
	<div>
		<div>
			선원 정보 검색
		</div>

		<div>
			<form action="searchCrew.jsp" method="post">
				<select name = "search option">
					<option> crew_ID </option> 				
					<option> user_ID </option>
					<option> space_cargo_ship_id </option>
					<option> position </option>
					<option> role </option>
					<option> name </option>
					<option> phone_number </option>
					<option> email_address </option>
					<option> address </option>
				</select>
				<input type="text" name="search key">				
				<input type="submit" value="search"> <br>
				<input type="hidden" name="id" value=<%=id%>>
			</form>
		</div>		
		<div>
			<%= crewManger.printSearchResult(request)%>
		</div>
	</div>



	<hr>
	<br>
	<div>
		<div>
			화물선 선원 정보 수정	<br>
			빈칸은 정보 그대로 유지<br>
		</div>
		<div>
			<form action="updateCrew.jsp" method="post">
				crew_ID :  <input type="text" name="crew_ID"><br>
				name : <input type="text" name="name"><br>
				position : <input type="text" name="position"><br>
				role : <input type="text" name="role"><br> 
				password : <input type="text" name="password"><br>
				phone_number : <input type="text" name="phone_number"><br>
				email_address : <input type="text" name="email_address"><br>
				address : <input type="text" name="address"><br>
				<input type="submit" value="update Crew"> <br>
				<input type="hidden" name="id" value=<%=id%>>
			</form>
		</div>
	</div>
	<hr>

	
	<br>
	<div>
		<div>
			화물선에 선원 배정<br>
		</div>
		<div>
			<form action="setCrewToCargoShip.jsp" method="post">
				선원 id : <%= crewManger.printSelectOption_crewID() %> 
				화물선  id : <%= crewManger.printSelectOption_cargoShipID() %>
				
				<input type="submit" value="set crew to cargoShip"> <br>
				<input type="hidden" name="id" value=<%=id%>>
			</form>
		</div>
	</div>
	<hr>
	
	
	<br>
	<div>
	
	</div>
	<br>
	<div>
		<div>
			전체 선원 정보
		</div>
		<div>
			<%= crewManger.printAllTableTupleList()%>
		</div>
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