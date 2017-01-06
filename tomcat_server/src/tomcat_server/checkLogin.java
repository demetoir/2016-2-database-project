package tomcat_server;
import java.util.Objects;
import java.io.IOException;
import java.sql.*;
import tomcat_server.DB_data;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;

// �������� jspȣ��(RequestDispatcherŬ���� ���)  ���α׷��� 
// 2007/08/09 12:28
// ����http://blog.naver.com/takezer0/50020746417
// �������� jspȣ��(RequestDispatcherŬ���� ���)
// �������� jspȣ��(forward�� include�� �ΰ��� ���)
// * ������: �� �޼ҵ� ��� jsp�������� ȣ���� �� �ִ� �޼ҵ������� forward()�޼ҵ带 �̿��ϸ�
// jsp�������� ȣ���ϴ� ���� ���� ���α׷��� ������ ���߰� jsp�������� �Ѿ �װ����� �����ϰ� ���α׷��� ������ ������ include()�޼ҵ带 �̿��ϸ� �ش� jsp�������� ����ǰ� �ٽ� ������ ���� ���α׷��� ����Ǵ� ���̴�.
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
        	if (Objects.equals(db_user_type, "�߾Ӱ�����")){
        		nextPageURL = "/managerMainPage.jsp";
        	}
        	else if (Objects.equals(db_user_type, "ȭ��")){
        		nextPageURL = "/cargoOwnerMainPage.jsp";
        	}
        	else if (Objects.equals(db_user_type, "����")){
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
