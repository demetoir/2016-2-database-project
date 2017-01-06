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
	<hr>
	<br>
	
	<div>
		<div>
			 화주 정보 추가	<br>
		</div>
		<div>
			<form action="registerCargo_Owner.jsp" method="post">
				이름 : <input type="text" name="name"><br>
				planet_id : <%= Manger.printSelectOption_PlanetID() %> <br>
				company_name : <input type="text" name="company_name"><br>
				grade : <input type="text" name="grade"><br>
				phone_number : <input type="text" name="phone_number"><br>
				email_address : <input type="text" name="email_address"><br> 
				address : <input type="text" name="address"><br> 
				<input type="submit" value="register cargo_owner"> <br>
				<input type="hidden" name="id" value=<%=id%>>
			</form>
		</div>
	</div>
	<hr>
	<br>
	
	<div>
		<div>
			화주 정보 삭제
		</div>
		<div>
			<form action="deleteCargo_Owner.jsp" method="post">
				cargo_owner_ID : <input type="text" name="cargo_owner_ID"><br>				
				<input type="submit" value="delete cargo_owner"> <br>
				<input type="hidden" name="id" value=<%=id%>>
			</form>
		</div>
	</div>
	
	<hr>
	<br>
	
	<div>
		<div>
			화주 정보 검색
		</div>

		<div>
			<form action="searchCargo_Owner.jsp" method="post">
				<select name = "searchOption">
					<option> cargo_owner_ID </option> 				
					<option> user_ID </option>
					<option> space_cargo_ship_id </option>
					<option> company_name </option>
					<option> planet_id </option>					
					<option> grade </option>
					<option> credit </option>
										
					<option> name </option>
					<option> phone_number </option>
					<option> email_address </option>
					<option> address </option>
				</select>
				<input type="text" name="searchKey">				
				<input type="submit" value="search"> <br>
				<input type="hidden" name="id" value=<%=id%>>
			</form>
		</div>		
		<div>
			<%= Manger.printSearchResult(request)%>
		</div>
	</div>



	<hr>
	<br>
	<div>
		<div>
			화주 정보 수정	<br>
			빈칸은 정보 그대로 유지<br>
		</div>
		<div>
			<form action="updateCargo_Owner.jsp" method="post">
				cargo_owner_ID :  <input type="text" name="cargo_owner_ID"><br>
				name : <input type="text" name="name"><br>
				password : <input type="text" name="password"><br>
				phone_number : <input type="text" name="phone_number"><br>
				email_address : <input type="text" name="email_address"><br>
				address : <input type="text" name="address"><br>
				
				planet_id : <%= Manger.printSelectOption_PlanetID() %> <br>
				
				company_name <input type="text" name="company_name"><br>
				grade <input type="text" name="grade"><br>
				credit <input type="text" name="credit"><br>
				
				<input type="submit" value="update Cargo_Owner"> <br>
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
			전체 화주 정보
		</div>
		<div>
			<%= Manger.printAllTableTupleList()%>
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