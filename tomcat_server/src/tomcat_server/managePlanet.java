package tomcat_server;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class managePlanet {
	public class planetTuple {
		String planet_ID;
		String planet_name;
		float cordinate_X;
		float cordinate_Y;
		float cordinate_Z;
		String planet_type;
		float distance;
		String population;
		float 할인및할증률;
	}
	public class planetRegisterForm{
		String planet_ID;
		String planet_name;
		String cordinate_X;
		String cordinate_Y;
		String cordinate_Z;
		String planet_type;
		String distance;
		String population;
		String 할인및할증률;
		planetRegisterForm(HttpServletRequest request){
			this.planet_ID = null;
			this.planet_name = request.getParameter("planetName");
			this.cordinate_X = request.getParameter("cordinate_x");
			this.cordinate_Y = request.getParameter("cordinate_y");
			this.cordinate_Z = request.getParameter("cordinate_z");
			this.planet_type = request.getParameter("planetType");
			this.distance = request.getParameter("distance");
			this.population = request.getParameter("population");
			this.할인및할증률 = request.getParameter("discount");
		}
	}
	
	public class planetDeleteValueForm{
		String planetID;
		planetDeleteValueForm(HttpServletRequest request){
			this.planetID = request.getParameter("planetID");
		}		
	}
	
	// TODO
	public class planetChangeValueForm{
		String planet_ID;
		String planet_name;
		String cordinate_X;
		String cordinate_Y;
		String cordinate_Z;
		String planet_type;
		String distance;
		String population;
		String 할인및할증률;
		
		planetChangeValueForm(HttpServletRequest request){
			this.planet_ID = request.getParameter("planetID");
			this.planet_name = request.getParameter("planetName");
			this.cordinate_X = request.getParameter("cordinate_x");
			this.cordinate_Y = request.getParameter("cordinate_y");
			this.cordinate_Z = request.getParameter("cordinate_z");
			this.planet_type = request.getParameter("planetType");
			this.distance = request.getParameter("distance");
			this.population = request.getParameter("population");
			this.할인및할증률 = request.getParameter("discount");
		}

	}
	
	public class planetSearchForm{
		String searchKey;
		String optionName;
		planetSearchForm(HttpServletRequest request){
			this.searchKey = request.getParameter("search key");
			this.optionName = request.getParameter("search option");
		}
	}
	
	
	ArrayList<planetTuple> tupleList;
	ArrayList<planetTuple> searchList;
	private String getSQLStatement_select_all() {
		String ret = "select * from planet";
		return ret;
	}
	private String getSQLStatement_insert_value(planetRegisterForm form){
		String SQLFormat = "insert into planet values( '%s', "
				+ "'%s' , "
				+ "%s, "
				+ "%s, "
				+ "%s, "
				+ "'%s', "
				+ "%s, "
				+ "%s, "
				+ "%s)";
		String ret = String.format(SQLFormat,
				form.planet_ID, 
				form.planet_name,
				form.cordinate_X, 
				form.cordinate_Y, 
				form.cordinate_Z, 
				form.planet_type, 
				form.distance, 
				form.population,
				form.할인및할증률);
		return ret;
		
	}
	private String getSQLStatement_delete(planetDeleteValueForm form){
		String SQLFormat = "delete from  planet where %s='%s'";
		String ret = String.format(SQLFormat,
				"planet_ID",
				form.planetID
				);
		
		return ret;
	}
	private String getSQLStatement_changeValue(planetChangeValueForm form) {
		String SQLFormat = "update planet set %s where %s";
	
		String PlanetName = String.format("planet_name = '%s'", form.planet_name);
		String cordinate_X = String.format("cordinate_X = %s", form.cordinate_X );
		String cordinate_Y = String.format("cordinate_Y = %s", form.cordinate_Y );
		String cordinate_Z = String.format("cordinate_Z = %s", form.cordinate_Z );
		String planet_type = String.format("planet_type = '%s'", form.planet_type);
		String distance = String.format("distance = %s", form.distance);
		String population = String.format("population = '%s'", form.population);
		String 할인및할증률 = String.format("할인및할증률 = %s", form.할인및할증률);	
		
		ArrayList<String> setClauseList = new ArrayList<String>();
		
		if (form.planet_name.length() != 0 ){
			setClauseList.add(PlanetName);		
		}
		if(form.cordinate_X.length() != 0){
			setClauseList.add(cordinate_X);
		}
		if (form.cordinate_Y.length() != 0){
			setClauseList.add(cordinate_Y);
		}
		if (form.cordinate_Z.length() != 0){
			setClauseList.add(cordinate_Z);
		}
		if(form.planet_type.length() !=0){
			setClauseList.add(planet_type);
		}
		if(form.distance.length() != 0 ){
			setClauseList.add(distance);
		}
		if(form.population.length() !=0 ){
			setClauseList.add(population);
		}
		if(form.할인및할증률.length() !=0){
			setClauseList.add(할인및할증률);
		}
		
		String setClause = setClauseList.get(0);
		for(int i = 1 ; i < setClauseList.size(); i++){
			setClause += ", "+ setClauseList.get(i); 	
		}
				
		String whereClause = String.format("planet_ID = '%s'", form.planet_ID );
				
		String ret = String.format(SQLFormat, setClause, whereClause);
		return ret;
	}
	
	
	public String printFailMsg(HttpServletRequest request){
		String msg = (String) request.getAttribute("failmsg") ;
		if (msg == null) msg = "";
		return msg;
	}
	
	private String printPlanetTable(ArrayList<planetTuple> tupleList){
		String ret  = "";
		ret += "<tr> "
				+ "<th>planet id</th>"
				+ "<th>planet_name</th>"
				+ "<th>cordinate_X</th>"
				+ "<th>cordinate_Y</th>"
				+ "<th>cordinate_Z</th>"
				+ "<th>planet_type</th>"
				+ "<th>distance</th>"
				+ "<th>population</th>"
				+ "<th>할인및할증률</th>"
				+ "</tr>";
			for (int i =0; i < tupleList.size(); i++){
				planetTuple tuple = tupleList.get(i);
				ret += "<tr>";			
				ret += String.format("<th>%s</th>", tuple.planet_ID);
				ret += String.format("<th>%s</th>", tuple.planet_name);
				ret += String.format("<th>%f</th>", tuple.cordinate_X);
				ret += String.format("<th>%f</th>", tuple.cordinate_Y);
				ret += String.format("<th>%f</th>", tuple.cordinate_Z);
				ret += String.format("<th>%s</th>", tuple.planet_type);
				ret += String.format("<th>%f</th>", tuple.distance);
				ret += String.format("<th>%s</th>", tuple.population);
				ret += String.format("<th>%f</th>", tuple.할인및할증률);				
				ret += "</tr>";				
			}
		ret += "</table>";
		
		return ret;
	}
	
	private boolean isSearchData(planetTuple tuple, String SearchKey, String option ){
		boolean ret = true;
		float f;
		switch (option) {
		case "planet ID":
			ret = tuple.planet_ID.contains(SearchKey);
			break;
		case "planet name":
			ret = tuple.planet_name.contains(SearchKey);
			break;
		case "X 좌표":
			f = Float.parseFloat(SearchKey);
			if (tuple.cordinate_X == f)
				ret =  true;
			else 
				ret = false;
			break;
		case "Y 좌표":
			f = Float.parseFloat(SearchKey);
			if (tuple.cordinate_Y == f)
				ret =  true;
			else 
				ret = false;
			break;
		case "Z 좌표":
			f = Float.parseFloat(SearchKey);
			if (tuple.cordinate_Z == f)
				ret =  true;
			else 
				ret = false;
			break;
		case "planet type":
			ret = tuple.planet_type.contains(SearchKey);
			break;
			
		case "distance":
			f = Float.parseFloat(SearchKey);
			if (tuple.distance == f)
				ret =  true;
			else 
				ret = false;
			break;
			
		case "population":
			if (tuple.equals(searchList))
				ret  = true;
			else 
				ret = false;
			break;
		case "할인 및 할증률":
			f = Float.parseFloat(SearchKey);
			if (tuple.할인및할증률 == f)
				ret =  true;
			else 
				ret = false;
			break;
			
		default:
			ret = false;
			break;
		}
		
		return ret;
	}
	
	private ArrayList<planetTuple> parseList (ArrayList<planetTuple> list, String SearchKey, String option){
		ArrayList<planetTuple> ret = new ArrayList<planetTuple>(); 
		for(int i =0; i<list.size(); i++){
			planetTuple tuple = list.get(i);
			if(isSearchData(tuple, SearchKey, option)){
				ret.add(tuple);
			}
		}
		
		return ret; 
	}
	public String printSearchResult(HttpServletRequest request){
		String ret = "";
		String searchSQLstatement = (String) request.getAttribute("searchSQLstatement");
		String searchKey = (String)request.getAttribute("searchKey");
		String searchOption = (String)request.getAttribute("searchOption");
		
		if(searchSQLstatement == null) return ret;
		
		ArrayList<planetTuple> List = makePlanetTupleList(searchSQLstatement); 				
		ArrayList<planetTuple> afterParseList = parseList(List, searchKey, searchOption);		
		ret = printPlanetTable(afterParseList);		
		
		return ret;
	}
	
	public String printAllTableTupleList(){
		String ret = "";
		String SQLStatement = getSQLStatement_select_all();
		ArrayList<planetTuple> List = makePlanetTupleList(SQLStatement); 
		ret = printPlanetTable( List);		
		return ret;
	}
	
	
	public ArrayList<planetTuple> makePlanetTupleList(String SQLStatment) {
		ArrayList<planetTuple>  ret = new ArrayList<planetTuple>(); 
		DB_data db_data = new DB_data();

		try {
			Connection con = DriverManager.getConnection(
					db_data.getDB_URL(), 
					db_data.getDB_ID(), 
					db_data.getDB_PASSWORD()
					);
			Statement stmt = con.createStatement();
			String str_sql = getSQLStatement_select_all();
			ResultSet res = stmt.executeQuery(str_sql);
			
			while (res.next()) {
				planetTuple tuple = new planetTuple();
				tuple.planet_ID = res.getString("planet_ID");
				tuple.planet_name = res.getString("planet_name");
				tuple.cordinate_X = res.getFloat("cordinate_X");
				tuple.cordinate_Y = res.getFloat("cordinate_Y");
				tuple.cordinate_Z = res.getFloat("cordinate_Z");
				tuple.planet_type = res.getString("planet_type");
				tuple.distance = res.getFloat("distance");
				tuple.population = res.getString("population");
				tuple.할인및할증률 = res.getFloat("할인및할증률");
				ret.add(tuple);
			}
			
			stmt.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return ret;
	}

	
	String ValidFormMsg = null;
	private boolean isValidPlanetRegisterForm(planetRegisterForm form) {
		ValidFormMsg = null;
		
		if (form.planet_name.length() > 40) {
			ValidFormMsg = "Too Long Planet Name";
			return false;
		}
		
		if (form.planet_type.length() > 40) {
			ValidFormMsg = "Too Long Planet Type";
			return false;
		}

		try {
			Float.parseFloat(form.cordinate_X);
		} catch (Exception e) {
			ValidFormMsg = "좌표 x는 숫자가 아님";
			return false;
		}

		try {
			Float.parseFloat(form.cordinate_Y);
		} catch (Exception e) {
			ValidFormMsg = "좌표 y는 숫자가 아님";
			return false;
		}

		try {
			Float.parseFloat(form.cordinate_Z);
		} catch (Exception e) {
			ValidFormMsg = "좌표 z는 숫자가 아님";
			return false;
		}

		try {
			Float.parseFloat(form.distance);
		} catch (Exception e) {
			ValidFormMsg = "distance 는 숫자가 아님";
			return false;
		}

		try {
			Float.parseFloat(form.할인및할증률);
		} catch (Exception e) {
			ValidFormMsg = "할인 및 할증률은 숫자가 아님";
			return false;
		}

		return true;
	}
	
	private boolean isValiedPlanetDeleteValueForm( planetDeleteValueForm form ){
		boolean ret = true;
		this.ValidFormMsg = null;
		if (form.planetID.length() > 40){
			ValidFormMsg = "too long planet id";
			ret = false;
		}
		
		return ret;
	}
	
	private boolean isValiedplanetChangeValueForm( planetChangeValueForm form){
		boolean isChanged = false;
		
		if (form.planet_ID.length() != 0){
			if (form.planet_ID.length() > 40){
				ValidFormMsg = "Too Long Planet ID";
				return false;
			}
		}
		if (form.planet_ID.length() == 0){
			ValidFormMsg = "Planet ID 입력 되지않음";
			return false;
		}
		if(form.planet_name.length() != 0){
			if (form.planet_name.length() > 40) {
				ValidFormMsg = "Too Long Planet Name";
				return false;
			}
			isChanged = true;
		}
		
		if(form.planet_type.length() != 0){
			if (form.planet_type.length() > 40) {
				ValidFormMsg = "Too Long Planet Type";
				return false;
			}
			isChanged = true;
		}
		
		if(form.cordinate_X.length() != 0){
			try {
				Float.parseFloat(form.cordinate_X);
			} catch (Exception e) {
				ValidFormMsg = "좌표 x는 숫자가 아님";
				return false;
			}
			isChanged = true;
		}
		if(form.cordinate_Y.length() != 0){
			try {
				Float.parseFloat(form.cordinate_Y);
			} catch (Exception e) {
				ValidFormMsg = "좌표 y는 숫자가 아님";
				return false;
			}
			isChanged = true;
		}
		if(form.cordinate_Z.length() != 0){
			try {
				Float.parseFloat(form.cordinate_Z);
			} catch (Exception e) {
				ValidFormMsg = "좌표 z는 숫자가 아님";
				return false;
			}
			isChanged = true;
		}
		if(form.distance.length() !=0){
			try {
				Float.parseFloat(form.distance);
			} catch (Exception e) {
				ValidFormMsg = "distance 는 숫자가 아님";
				return false;
			}
			isChanged = true;
		}
		
		if(form.할인및할증률.length() != 0){
			try {
				Float.parseFloat(form.할인및할증률);
			} catch (Exception e) {
				ValidFormMsg = "할인 및 할증률은 숫자가 아님";
				return false;
			}
			isChanged = true;
		}
		
		if(isChanged == true)
			return true;
		else 
			return false;
	}
	
	private boolean isValidPlanetSearchFrom( planetSearchForm form){
		boolean ret = true;
		if(form.searchKey.length() > 40){
			ValidFormMsg = "TOO long searchKey";
			return false;
		}
		if (form.searchKey.length() == 0|| form.searchKey == null){
			ValidFormMsg = "no search key";
			return false;
		}
		
		return ret;
	}
	
	private String generateNewId(planetRegisterForm form) {
		
		return form.planet_name +"_"+ System.currentTimeMillis();
	}
	
	public void registerPlanet(HttpServletRequest request, HttpServletResponse response){
		DB_data db_data = new DB_data();
		String nextPageURL = "/managePlanet.jsp";
		String userId = request.getParameter("id");		
		planetRegisterForm form = new planetRegisterForm(request);

		if (isValidPlanetRegisterForm(form) == true) {
			form.planet_ID = generateNewId(form);
			try {
				Connection con = DriverManager.getConnection(
						db_data.getDB_URL(), 
						db_data.getDB_ID(),
						db_data.getDB_PASSWORD());
				Statement stmt = con.createStatement();
				
				String str_sql = getSQLStatement_insert_value(form);
				stmt.executeQuery(str_sql);
				stmt.close();
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		RequestDispatcher rd = request.getServletContext().getRequestDispatcher(nextPageURL);
		request.setAttribute("id", userId);
		request.setAttribute("failmsg", this.ValidFormMsg);
		
		try {
			rd.forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void deletePlanet(HttpServletRequest request, HttpServletResponse response){
		DB_data db_data = new DB_data();
		String nextPageURL = "/managePlanet.jsp";
		String userId = request.getParameter("id");		
		planetDeleteValueForm form = new planetDeleteValueForm(request);

		if (isValiedPlanetDeleteValueForm(form) == true) {
			try {
				Connection con = DriverManager.getConnection(
						db_data.getDB_URL(), 
						db_data.getDB_ID(),
						db_data.getDB_PASSWORD());
				Statement stmt = con.createStatement();
				
				String str_sql = getSQLStatement_delete(form);
				stmt.executeQuery(str_sql);
				stmt.close();
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		RequestDispatcher rd = request.getServletContext().getRequestDispatcher(nextPageURL);
		request.setAttribute("id", userId);
		request.setAttribute("failmsg", this.ValidFormMsg);
		
		try {
			rd.forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void searchPlanet(HttpServletRequest request, HttpServletResponse response){
		String nextPageURL = "/managePlanet.jsp";
		String userId = request.getParameter("id");		
		planetSearchForm form = new planetSearchForm(request);
		String searchSQLstatement = null;
		
		if (isValidPlanetSearchFrom(form) == true) {
			searchSQLstatement = getSQLStatement_select_all();
		}
		
		RequestDispatcher rd = request.getServletContext().getRequestDispatcher(nextPageURL);
		request.setAttribute("id", userId);
		request.setAttribute("failmsg", this.ValidFormMsg);
		
		request.setAttribute("searchSQLstatement", searchSQLstatement);
		request.setAttribute("searchOption", form.optionName);
		request.setAttribute("searchKey", form.searchKey);
		
		try {
			rd.forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	public void changePlanet(HttpServletRequest request, HttpServletResponse response){
		DB_data db_data = new DB_data();
		String nextPageURL = "/managePlanet.jsp";
		String userId = request.getParameter("id");		
		planetChangeValueForm form = new planetChangeValueForm(request);

		if (isValiedplanetChangeValueForm(form) == true) {
			try {
				Connection con = DriverManager.getConnection(
						db_data.getDB_URL(), 
						db_data.getDB_ID(),
						db_data.getDB_PASSWORD());
				Statement stmt = con.createStatement();
				
				String str_sql = getSQLStatement_changeValue(form);
				stmt.executeQuery(str_sql);
				stmt.close();
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		RequestDispatcher rd = request.getServletContext().getRequestDispatcher(nextPageURL);
		request.setAttribute("id", userId);
		request.setAttribute("failmsg", this.ValidFormMsg);
		
		try {
			rd.forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
}
