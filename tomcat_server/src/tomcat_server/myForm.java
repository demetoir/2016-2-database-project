package tomcat_server;

import javax.servlet.http.HttpServletRequest;

public class myForm {
	String user_ID  = "user_ID";
	String password  = "password";
	String name  = "name";
	String user_type  = "user_type";
	String phone_number  = "phone_number";
	String email_address  = "email_address";
	String address  = "address";
	String role  = "role";
	String crew_ID  = "crew_ID";
	String space_cargo_ship_id  = "space_cargo_ship_id";
	String position  = "position";
	String planet_ID = "planet_ID";
	String planet_name = "planet_name";
	String cordinate_X = "cordinate_X";
	String cordinate_Y = "cordinate_Y";
	String cordinate_Z = "cordinate_Z";
	String planet_type = "planet_type";
	String distance = "distance";
	String population = "planet_name";
	String 할인및할증률 = "할인및할증률";
	String cargo_owner_ID = "cargo_owner_ID";
	String planet_id = "planet_id";
	String company_name = "company_name";
	String grade = "grade";
	String credit = "credit";
	String cargo_ship_id = "cargo_ship_id";
	String capacity = "capacity";
	String velocity = "velocity";
	String max_flight_distance = "max_flight_distance";
	String cost = "cost";
	String cargo_ship_name = "cargo_ship_name";
	String cargo_ship_state = "cargo_ship_state";
	String cargo_ship_destination = "cargo_ship_destination";
	String cargo_ship_grade = "cargo_ship_grade";
	String cargo_id = "cargo_id";
	String destination = "destination";
	String cargo_state = "cargo_state";		
	String weight = "weight";
	String item_name = "item_name";
	String amount = "amount";
	String log_id = "log_id";

	String searchKey= "";
	String searchOption = "";
	
	myForm(HttpServletRequest request){
		this.user_ID  = request.getParameter("user_ID");
		this.password  = request.getParameter("password");
		this.name  = request.getParameter("name");
		this.user_type  = request.getParameter("user_type");
		this.phone_number  = request.getParameter("phone_number");
		this.email_address  = request.getParameter("email_address");
		this.address  = request.getParameter("address");
		this.role  = request.getParameter("role");
		this.crew_ID  = request.getParameter("crew_ID");
		this.space_cargo_ship_id  = request.getParameter("space_cargo_ship_id");
		this.position  = request.getParameter("position");
		this.planet_ID = request.getParameter("planet_ID");
		this.planet_name = request.getParameter("planet_name");
		this.cordinate_X = request.getParameter("cordinate_X");
		this.cordinate_Y = request.getParameter("cordinate_Y");
		this.cordinate_Z = request.getParameter("cordinate_Z");
		this.planet_type = request.getParameter("space_planet_typecargo_ship_id");
		this.distance = request.getParameter("distance"); 
		this.population = request.getParameter("population");
		this.할인및할증률 = request.getParameter("할인및할증률");
		this.cargo_owner_ID = request.getParameter("cargo_owner_ID");
		this.planet_id = request.getParameter("planet_id");
		this.company_name = request.getParameter("company_name");
		this.grade = request.getParameter("grade");
		this.credit = request.getParameter("credit");
		this.cargo_ship_id = request.getParameter("cargo_ship_id");
		this.capacity = request.getParameter("capacity");
		this.velocity = request.getParameter("velocity");
		this.max_flight_distance = request.getParameter("max_flight_distance");
		this.cost = request.getParameter("cost");
		this.cargo_ship_name = request.getParameter("cargo_ship_name");
		this.cargo_ship_state = request.getParameter("cargo_ship_state");
		this.cargo_ship_destination = request.getParameter("cargo_ship_destination");
		this.cargo_ship_grade = request.getParameter("cargo_ship_grade");
		this.cargo_id = request.getParameter("cargo_id");
		this.destination = request.getParameter("destination");
		this.cargo_state = request.getParameter("cargo_state");
		this.weight = request.getParameter("weight");
		this.item_name = request.getParameter("item_name");
		this.amount = request.getParameter("amount");
		this.log_id = request.getParameter("log_id");
		
		this.searchKey = request.getParameter("searchKey");
		this.searchOption = request.getParameter("searchOption");
	}

}
