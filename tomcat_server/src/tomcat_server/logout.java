package tomcat_server;

import java.io.IOException;

import javax.servlet.http.*;

public class logout {
	public void DoLogout(HttpServletRequest request, HttpServletResponse response){
		System.out.println("logout");
		HttpSession session = request.getSession();
		String id = request.getParameter("id");
		System.out.print(id);
		session.removeAttribute(id);
		
		try {
			response.sendRedirect("loginPage.jsp");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
	}
}
