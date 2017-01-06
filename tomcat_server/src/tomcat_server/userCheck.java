package tomcat_server;
import java.io.IOException;
import java.sql.*;
import javax.servlet.http.*;
import tomcat_server.DB_data;

public class userCheck {
	private String user_id = null;
	private String user_type = null;
	private String user_name = null;
	
	public String getUserId(){
		return this.user_id;
	}
	public String getUserType(){
		return this.user_type;
	}
	public String getUserName(){
		return this.user_name;
	}
	
	private boolean isSessionOk(HttpServletRequest request, HttpServletResponse response){
		String id = request.getParameter("id");
		System.out.println(id);
		HttpSession session = request.getSession();
		System.out.println(session.getAttribute(id));
		if (session.getAttribute(id) == null){
			System.out.println("no pemission");
			try {
				response.sendRedirect("noPermission.jsp");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}
		return true;
	}
	
	public void loadUserData(HttpServletRequest request, HttpServletResponse response){
		this.user_id = null;
		this.user_type = null;
		this.user_name = null;
		DB_data db_data = new DB_data();
		
		this.user_id = request.getParameter("id");
		if (this.isSessionOk(request, response) == false){
			return ;
		}

		System.out.println(String.format("main page user id : %s ", user_id));

		try{
			Connection con = DriverManager.getConnection(db_data.getDB_URL(),db_data.getDB_ID(), db_data.getDB_PASSWORD());
			Statement stmt = con.createStatement();		
			String str_sql = "select * from userTable ";
			str_sql += String.format("where user_id = '%s' ", user_id);
			ResultSet res = stmt.executeQuery(str_sql);
			
			while (res.next()){	
				this.user_type = res.getString("user_type");
				this.user_name = res.getString("name");
				System.out.println(this.user_id + " " + this.user_name+ " "+this.user_type);
			}
			stmt.close();
			con.close();			
		}
		catch(Exception e){
				e.printStackTrace();
		}	

	}
	public String printUserInformation(){
		String ret  = "";
		
		ret = "user information<br> user Id :<%=id%><br> user name :<%=name%><br> user type :<%=type%><br>";
		ret = String.format("user information<br> user Id : %s <br>"
				+ " user name : %s <br> user type :%s <br>", user_id, user_name, user_type);
		
		return ret;
	}
}
