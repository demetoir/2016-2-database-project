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
		%>
	</div>
	<div>
	    <form action="logout.jsp" method="post">
	        <input type="submit" value="logout">
	        <input type="hidden" name ="id" value = <%=id %> >        
	    </form> <br>
	
	</div>
	<hr>	
	<div>
		<%=  mainPage.printFailMsg(request) %>
	</div>
	<hr>
	<div>
		user information<br>
		user Id :<%= id %><br>
		user name :<%= name %><br>
		user type :<%= type %><br>
	</div>
	<hr>
	<div>
		<%=  mainPage.printcrewData() %>
	</div>
	<hr>
	<div>
		<%=  mainPage.printCargoShipData() %>
	</div>
	<hr>
	<div>
		필요물품 <br>
	</div>
	<div>
		<%=  mainPage.printNecessary_item() %><br>
		
		<form action="requestNecessary_item.jsp" method="post">
			필요 항목 이름 : <input type="text" name=item_name><br>
			필요 항목 수: <input type="text" name=amount><br>
			<input type="submit" value="request Necessary item"> <br>
			<input type="hidden" name="id" value=<%=id%>>
		</form>
	</div>
	<hr>	

	

		
	<hr>
	<div>
	 	화물선 상태 설정
	 	<div>
		 	<form action="setCargoShipReady.jsp" method="post">
		        <input type="submit" value="set CargoShip Ready">
		        <input type="hidden" name ="id" value = <%=id %> >        
		    </form>
	 	</div>
		<div>
		 	<form action="setCargoShipDelivering.jsp" method="post">
		        <input type="submit" value="set Cargo Ship delivering">
		        <input type="hidden" name ="id" value = <%=id %> >        
		    </form> 
	 	</div>
	 	<div>
		 	<form action="setCargoShipDeliveryComplete.jsp" method="post">
		        <input type="submit" value="set Cargo Ship delivery complete">
		        <input type="hidden" name ="id" value = <%=id %> >        
		    </form>
	 	</div>	 	
	</div>
	
	
	<hr>
	<div>
	 	선적 가능 화물
	</div>	
	<div>
		<%= mainPage.printShipAble_cargo() %>
	</div>
	<hr>
	<div>
		<div>
		 	화물선 선적된 화물 목록
		</div>
		<div>
			<div>
		 		화물선 현재 용량 / 화물선 최대 용량<br>
			</div>
			
			<%= mainPage.printCargoShipCapacityStatus() %>
		</div>
		
	 	<div>
			<%= mainPage.printShipped_cargo() %>
		</div>
	</div>	
	
	
	
	<hr>
	<div>
	 	<div> 화물선 화물 선적</div> 
	 	<div>
			<form action="shippingCargo.jsp" method="post">
				화물 id : <input type="text" name=cargo_id><br>
				<input type="submit" value="shipping Cargo"> <br>
				<input type="hidden" name="id" value=<%=id%>>
			</form>
	 	</div>
	 </div>
	<hr>

	<hr>
	<div>
	 	<div> 화물선 선적 화물 제거</div> 
	 	<div>
			<form action="unShippingCargo.jsp" method="post">
				화물 id : <input type="text" name=cargo_id><br>
				<input type="submit" value="unShipping Cargo"> <br>
				<input type="hidden" name="id" value=<%=id%>>
			</form>
	 	</div>
	 </div>

	<div>
		<div>
		 	 화물선 필요 물품 요청 
		</div>
	 	<div>
		 	
		</div>
	</div>	
	
	
	
	
	<hr>
	<div>
		<div>
		 	모든 화물 목록
		</div>
	 	<div>
			<%= mainPage.printAllCargo() %>
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