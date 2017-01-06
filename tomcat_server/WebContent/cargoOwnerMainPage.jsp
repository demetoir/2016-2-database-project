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
	
	<hr>
	<div>
		화주 정보<br>
		<%= mainPage.printCargoOwnerData() %><br>
	</div>
	
	<hr>
	<div>
		<%= mainPage.printFailMsg(request) %>
	</div>

	<hr>
	<div>
		<div>
			화물 등록			
		</div>
		<div>
			<form action="registerCargo.jsp" method="post">
				weight : <input type="text" name="weight"><br>
				목적지 행성 id : <%= mainPage.printSelectOption_destination() %><br>
				이용 화물선 id : <%= mainPage.printSelectOption_cargo_ship_id() %><br>
				<input type="submit" value="register Cargo"> <br>
				<input type="hidden" name="id" value=<%=id%>>
			</form>
		</div>
	</div>
	
		
	<div>
		<div>
			화물 등록 확인
		</div>
	</div>
	<div>
		<form action="confrimCargo.jsp" method="post">
 			cargo_id : <input type="text" name="cargo_id"><br>
			<input type="submit" value="confrim Cargo"> <br>
			<input type="hidden" name="id" value=<%=id%>>
		</form>
	</div>
	
	<hr>
	<div>
		<div>
	 	화물 정보 수정
	 	</div>
	 	<div>
	 		<form action="updateCargo.jsp" method="post">
	 			cargo_id : <input type="text" name="cargo_id"><br>
				weight : <input type="text" name="weight"><br>
				목적지 id : <%= mainPage.printSelectOption_destination() %><br>
				화물선 id : <%= mainPage.printSelectOption_cargo_ship_id() %><br>
				<input type="submit" value="update Cargo"> <br>
				<input type="hidden" name="id" value=<%=id%>>
			</form>
		</div>
	</div>
	
	<hr>
	<div>
		<div>
		 화물 정보 삭제
		</div>
		<div>
			<form action="deleteCargo.jsp" method="post">
	 			cargo_id : <input type="text" name="cargo_id"><br>
				<input type="submit" value="delete Cargo"> <br>
				<input type="hidden" name="id" value=<%=id%>>
			</form>
		</div>
	</div>
	
	<hr>
	<div>
		<div>
		 	비용 절감 화물선 검색	 
		</div>
		
		<div>
		 	<form action="searchCheepCargoShip.jsp" method="post">
	 			cargo_id : <input type="text" name="cargo_id"><br>
				<input type="submit" value="search Cheep CargoShip"> <br>
				<input type="hidden" name="id" value=<%=id%>>
			</form>
		</div>
		<div>
			<%= mainPage.print_searchCheepCargoShip(request, response) %>	
		</div>
		
	</div>
	
	<hr>
	<div>
		<div>
		 화물 정보 검색
		</div>
		<div>
			<form action="seachCargo.jsp" method="post">
				<select name = "searchOption">
					<option> cargo_id </option> 				
					<option> weight </option>
					<option> destination </option>
					<option> cargo_ship_id </option>
					<option> cargo_state </option>
				</select>
	 			<input type="text" name="searchKey"><br>
				<input type="submit" value="seach Cargo"> <br>
				<input type="hidden" name="id" value=<%=id%>>
			</form>
		</div>
		<div>
			 <%= mainPage.printSearchResult(request) %>
		</div>
	</div>

	
	<hr>
	<div>
		<div>
			등록한 화물 목록
		</div>
		<div>
			<%= mainPage.printAllTableTupleList()%>
		</div>		
	</div>
	
	<div>
	    <form action="logout.jsp" method="post">
	        <input type="submit" value="logout">
	        <input type="hidden" name ="id" value = <%=id %> >        
	    </form> <br>	
	</div>
</body>

</html>