package tomcat_server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;




public class show_all_table {
	
	static boolean show_table(){
		DB_data db_data = new DB_data();
		
		try{
			Connection con = DriverManager.getConnection(db_data.getDB_URL(), db_data.getDB_ID(),db_data.getDB_PASSWORD());
			Statement stmt = con.createStatement();		
			
			ResultSet res = stmt.executeQuery("select * from user_data");
			System.out.println("id   password name  type");
			while(res.next()){			
				String id = res.getString(1);
				String password = res.getString(2);
				String name = res.getString(3);
				String type = res.getString(4);
				System.out.println(id + " " + password+ " " + name + " " + type);				
			}
			
			stmt.close();
			con.close();			
		}
		catch(Exception e){
				e.printStackTrace();
				return false;
		}		
		return true;		
	}
	
	
	public static void main(String[] args) {		
		show_table();
		
		return; 
	}

}
