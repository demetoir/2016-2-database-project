<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="tomcat_server.managePlanet"%>
<%@page import="com.sun.org.glassfish.gmbal.ManagedAttribute"%>
<%@page import="java.util.ArrayList"%>
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<title>YDC manage cargo ship page</title>
<%@ page import="tomcat_server.userCheck"%>
<%@ page import="tomcat_server.manageCargoShip"%>
</head>

<body>
	<%
		userCheck user = new userCheck();
		user.loadUserData(request, response);
		String id = user.getUserId();
		String name = user.getUserName();
		String type = user.getUserType();
		manageCargoShip cargoShipManger = new manageCargoShip();
	%>
	<div>
		<h1>manage cargo ship</h1>
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
		<%= cargoShipManger.printFailMsg(request) %>
	</div>
	<hr>
	<br>
	<div>
		<div>
			화물선 정보 추가	<br>
		</div>
		<div>
			<form action="registerCargoship.jsp" method="post">
				화물선 이름 : <input type="text" name="cargo_ship_name"><br>
				화물선 등급: <input type="text" name="cargo_ship_grade"><br>
				최대 화물 용량 : <input type="text" name="capacity"><br> 
				최고 속도 : <input type="text" name="velocity"><br>
				최대 운항 거리 : <input type="text" name="max_flight_distance"> <br>
				운임 비용 : <input type="text" 	name="cost"> <br>
				<input type="submit" value="register cargoShip"> <br>
				<input type="hidden" name="id" value=<%=id%>>
			</form>
		</div>
	</div>
	<hr>
	<br>
	<div>
		<div>
			화물선 정보 삭제
		</div>
		<div>
			<form action="deleteCargoShip.jsp" method="post">
				화물선 id : <input type="text" name="cargo_ship_id"><br>				
				<input type="submit" value="delete CargoShip"> <br>
				<input type="hidden" name="id" value=<%=id%>>
			</form>
		</div>
	</div>
	<hr>
	<br>
	<div>
		<div>
			화물선 정보 검색
		</div>

		<div>
			<form action="searchCargoShip.jsp" method="post">
				<select name = "search option">
					<option> cargo_ship_id </option> 				
					<option> cargo_ship_name </option>
					<option> cargo_ship_grade </option>
					<option> capacity </option>
					<option> capacity </option>
					<option> max_flight_distance </option>
					<option> cargo_ship_state </option>
					<option> cargo_ship_destination</option>
					<option> cost</option>
				</select>
				<input type="text" name="search key">				
				<input type="submit" value="search"> <br>
				<input type="hidden" name="id" value=<%=id%>>
			</form>
		</div>		
		<div>
			<%= cargoShipManger.printSearchResult(request)%>
		</div>
	</div>
	
	<hr>
	<br>
	<div>
		<div>
			화물선 정보 수정	<br>
			빈칸은 정보 그대로 유지<br>
		</div>
		<div>
			<form action="updateCargoShip.jsp" method="post">
				화물선 id : <input type="text" name="cargo_ship_id"><br>
				화물선 이름 : <input type="text" name="cargo_ship_name"><br>
				화물선 등급 : <input type="text" name="cargo_ship_grade"><br>
				최대 화물 용량 : <input type="text" name="capacity"><br> 
				최고 속도 : <input type="text" name="velocity"><br>
				최대 운항 거리 : <input type="text" name="max_flight_distance"> <br>
				화물선 상태 : <input type="text" name="cargo_ship_state"><br> 
				운임 비용 : <input type="text" 	name="cost"> <br>
				<input type="submit" value="update CargoShip"> <br>
				<input type="hidden" name="id" value=<%=id%>>
			</form>
		</div>
	</div>
	<hr>
	
	<br>
	<div>
	
		<div>
			운항중이지 않은 화물선 목적지 설정
		</div>
			
		<div>
			<form action="selectCargoShipDestination.jsp" method="post">
				화물선 id : <%= cargoShipManger.printSelectOption_cargoShip() %>
				목적지 행성 id :<%= cargoShipManger.printSelectOption_destination()%> 					

				<input type="submit" value="select destination"> <br>
				<input type="hidden" name="id" value=<%=id%>>
			</form>
		</div>		

	</div>
	<br>
	<div>
		<div>
			전체 화물선 정보
		</div>
		<div>
			
				<%= cargoShipManger.printAllTableTupleList()%>
		
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