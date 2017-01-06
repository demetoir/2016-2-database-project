package tomcat_server;
import java.util.Objects;
import java.io.IOException;
import java.sql.*;
import tomcat_server.DB_data;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;

// 서블릿에서 jsp호출(RequestDispatcher클래스 사용)  프로그래밍 
// 2007/08/09 12:28
// 복사http://blog.naver.com/takezer0/50020746417
// 서블릿에서 jsp호출(RequestDispatcher클래스 사용)
// 서블릿에서 jsp호출(forward와 include의 두가지 방식)
// * 차이점: 두 메소드 모두 jsp페이지를 호출할 수 있는 메소드이지만 forward()메소드를 이용하면
// jsp페이지를 호출하는 순간 서블릿 프로그램은 실행을 멈추고 jsp페이지로 넘어가 그곳에서 실행하고 프로그램이 끝나게 되지만 include()메소드를 이용하면 해당 jsp페이지가 실행되고 다시 나머지 서블릿 프로그램이 실행되는 것이다.
// 1. forward
// RequestDispatcher rd = this.getServletContext().getRequestDispatcher("jspFile");
// request.setAttribute("name", "value");
// rd.forward(request, response);
// 2. include
// RequestDispatcher rd = this.getServletContext().getRequestDispatcher("jspFile");
// rd.include(request, response);

public class checkLogin {
    public void check(HttpServletRequest request, HttpServletResponse response){
    	DB_data db_data = new DB_data();
        boolean isSuccess = false;
        String nextPageURL = null;
        String db_id = null;
        String db_pwd = null;
        String db_user_type = null;
        String id = request.getParameter("id");
        String pwd = request.getParameter("pwd");
        
        System.out.println("check login id : " + id +"  "+ "pwd :" + pwd );

        try{
            Connection con = DriverManager.getConnection(
            		db_data.getDB_URL(), 
            		db_data.getDB_ID(), 
            		db_data.getDB_PASSWORD());
            Statement stmt = con.createStatement();		
            String str_sql = "select user_id, password, user_type from userTable ";
            str_sql += String.format("where user_id = '%s' and password = '%s'", id, pwd);
            
            ResultSet res = stmt.executeQuery(str_sql);

			while (res.next()) {
				db_id = res.getString("user_id");
				db_pwd = res.getString("password");
				db_user_type = res.getString("user_type");
				System.out.println("check db id : " + db_id + "  " + "pwd :" + db_pwd);
				if (Objects.equals(db_id, id) && Objects.equals(db_pwd, pwd)) {
					isSuccess = true;
					System.out.println("true");
				}
			}

            stmt.close();
            con.close();			
        }
        catch(Exception e){
            e.printStackTrace();
        }	
        if (isSuccess){
        	if (Objects.equals(db_user_type, "중앙관리자")){
        		nextPageURL = "/managerMainPage.jsp";
        	}
        	else if (Objects.equals(db_user_type, "화주")){
        		nextPageURL = "/cargoOwnerMainPage.jsp";
        	}
        	else if (Objects.equals(db_user_type, "선원")){
        		nextPageURL = "/crewMainPage.jsp";
        	}
        	RequestDispatcher rd = request.getServletContext().getRequestDispatcher(nextPageURL);
			request.setAttribute("id", id);
			HttpSession session = request.getSession();
			session.setAttribute(id, id);
			              
			try {
				rd.forward(request, response);
			} catch (ServletException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
        else{
        	System.out.println("login fail");
        }
    }
}
