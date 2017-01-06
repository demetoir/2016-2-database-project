package tomcat_server;

public class TableMetaData {

	String userTableName = "userTable";
	int MAXFEILDSIZE_user_ID = 50;
	String user_ID  = "user_ID";
	int MAXFEILDSIZE_password = 50;
	String password  = "password";
	int MAXFEILDSIZE_name = 50;
	String name  = "name";
	int MAXFEILDSIZE_user_type = 50;
	String user_type  = "user_type";
	int MAXFEILDSIZE_phone_number = 50;
	String phone_number  = "phone_number";
	int MAXFEILDSIZE_email_address = 100;
	String email_address  = "email_address";
	int MAXFEILDSIZE_address = 300;
	String address  = "address";
	
	
	String crewTableName = "crew";
	int MAXFEILDSIZE_role = 50;
	String role  = "role";
	int MAXFEILDSIZE_crew_ID = 50;
	String crew_ID  = "crew_ID";
	int MAXFEILDSIZE_space_cargo_ship_id = 50;
	String space_cargo_ship_id  = "space_cargo_ship_id";
	int MAXFEILDSIZE_position = 50;
	String position  = "position";
	
	
	String planetTableName = "planet";
	int MAXFEILDSIZE_planet_ID = 50;
	String planet_ID = "planet_ID";
	int MAXFEILDSIZE_planet_name = 50;
	String planet_name = "planet_name";
	String cordinate_X = "cordinate_X";
	String cordinate_Y = "cordinate_Y";
	String cordinate_Z = "cordinate_Z";
	int MAXFEILDSIZE_planet_type = 50;
	String planet_type = "planet_type";
	String distance = "distance";
	String population = "planet_name";
	String 할인및할증률 = "할인및할증률";
	
	
	String cargo_ownerTableName  = "cargo_owner";
	int MAXFEILDSIZE_cargo_owner_ID = 50;
	String cargo_owner_ID = "cargo_owner_ID";
	int MAXFEILDSIZE_planet_id = 50;		
	String planet_id = "planet_id";
	int MAXFEILDSIZE_company_name = 50;
	String company_name = "company_name";
	int MAXFEILDSIZE_grade = 50;
	String grade = "grade";
	String credit = "credit";
	
	
	

	String cargo_shipTableName = "cargo_ship";
	int MAXFEILDSIZE_cargo_ship_id = 50;
	String cargo_ship_id = "cargo_ship_id";
	String capacity = "capacity";
	String velocity = "velocity";
	String max_flight_distance = "max_flight_distance";
	String cost = "cost";
	String cargo_ship_name = "cargo_ship_name";
	int MAXFEILDSIZE_cargo_ship_state = 50;
	String cargo_ship_state = "cargo_ship_state";
	int MAXFEILDSIZE_cargo_ship_destination = 50;
	String cargo_ship_destination = "cargo_ship_destination";
	int MAXFEILDSIZE_cargo_ship_grade = 50;
	String cargo_ship_grade = "cargo_ship_grade";
	

	String cargoTableName = "cargo";
	int MAXFEILDSIZE_cargo_id = 50;
	String cargo_id = "cargo_id";
	int MAXFEILDSIZE_destination = 50;
	String destination = "destination";
	int MAXFEILDSIZE_cargo_state = 50;
	String cargo_state = "cargo_state";		
	int MAXFEILDSIZE_cargo_ship_name = 50;
	String weight = "weight";

	
	String necessary_itemTableName = "necessary_item";
	int MAXFEILDSIZE_item_name = 50;
	String item_name = "item_name";
	String amount = "amount";
	
	
	String delivery_logtableName = "delivery_log";
	int MAXFEILDSIZE_log_id = 50;
	String log_id = "log_id";
	
	String cargo_state_waiting_confirm = "waiting for confirm";
	String cargo_state_waiting_delivery = "waiting_delivery";
	String cargo_state_delivery = "delivering";
	String cargo_state_delivery_complete = "delivery_complete";
	
	String not_in_space_cargo_ship_id= "배정안됨";
	
	String shipped_cargoTablename = "shipped_cargo";
	
	String cargo_ship_state_ready = "ready";
	String cargo_ship_state_delivering = "delivering";
	String cargo_ship_state_delivery_complete = "delivery_complete";
	
	
	
}






















