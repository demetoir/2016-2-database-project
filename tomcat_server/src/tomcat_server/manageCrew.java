package tomcat_server;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class manageCrew {
	String ValidFormMsg = null;
	String nextPageURL = "/manageCrew.jsp";
	
	private class SQL_STATEMENT_FORMAT_SET{
		String SELECT_ALL = "select %s form %s where %s";
	}
	
	private TableMetaData tableMetaData = new TableMetaData();  
	
	
	private String generateNewId(String name) {
		return name +"_"+ System.currentTimeMillis();
	}
	public String printFailMsg(HttpServletRequest request){
		String msg = (String) request.getAttribute("failmsg") ;
		if (msg == null) msg = "";
		return msg;
	}
	
	public class Form {
		String crew_ID;
		String user_ID;
		String space_cargo_ship_id;
		String position;
		String role;		
		String password;
		String name;
		String user_type;
		String phone_number;
		String email_address;
		String address;
		String searchKey;
		String optionName;
		Form(HttpServletRequest request){
			this.space_cargo_ship_id 	= request.getParameter("space_cargo_ship_id");
			this.position 				= request.getParameter("position");
			this.role 					= request.getParameter("role");
			this.password				= request.getParameter("password");
			this.name					= request.getParameter("name");
			this.user_type				= request.getParameter("user_type");
			this.phone_number			= request.getParameter("phone_number");
			this.email_address			= request.getParameter("email_address");
			this.address				= request.getParameter("address");
			this.crew_ID 				= request.getParameter("crew_ID");
			this.searchKey 				= request.getParameter("search key");
			this.optionName 			= request.getParameter("search option");
			this.user_ID 				= request.getParameter("user_ID");
		}
	}

	private String getSQLStatement_select_all() {
		String SQLFormat = "select %s from %s where %s";
		String selectClase = "*";
		String joinAttribute = "user_ID";
		String fromClause = String.format("%s, %s", 
				tableMetaData.crewTableName, 
				tableMetaData.userTableName);
		
		String whereClause = String.format("%s.%s = %s.%s",
				tableMetaData.crewTableName,
				joinAttribute,
				tableMetaData.userTableName,
				joinAttribute
				);
				
		String SQLStatement = String.format(SQLFormat,
											selectClase,
											fromClause,
											whereClause);
		return SQLStatement;
	}
		
	private String getSQLStatement_insert_value_Crew(Form form){		
		String SQLFormat = "insert into %s values( %s )";
		String space_cargo_ship_id = "null"; 
		String insertValue = String.format("'%s', '%s', %s, '%s', '%s'",
				form.crew_ID, 				
				form.user_ID, 	
				space_cargo_ship_id, 	
				form.position,				
				form.role			
				);
		
		String ret = String.format(SQLFormat, tableMetaData.crewTableName, insertValue);
		return ret;
	}

	private String getSQLStatement_insert_value_userTable(Form form){	
		String SQLFormat = "insert into %s values( %s )";
		String user_type = "선원";
		String password = "1234"; 
		String insertValue = String.format("'%s', '%s', '%s', '%s', '%s', '%s', '%s'",
				form.user_ID, 				
				password, 	
				form.name, 	
				user_type,				
				form.phone_number,
				form.email_address,
				form.address
				);
		
		String ret = String.format(SQLFormat, tableMetaData.userTableName, insertValue);
		return ret;
	}
		
	
	private String getSQLStatement_delete_crew( Form form ){
		String whereClause = String.format("user_ID ='%s'", form.user_ID);	
		String SQLFormat = "delete from %s where %s";
		String ret = String.format(SQLFormat, tableMetaData.crewTableName, whereClause) ;		
		return ret;
	}
	
	private String getSQLStatement_delete_userTable( Form form ){
		String whereClause = String.format("user_ID ='%s'", form.user_ID);		
		String SQLFormat = "delete from %s where %s";
		String ret = String.format(SQLFormat, tableMetaData.userTableName, whereClause) ;		
		return ret;
	}
	
	private String makeUpdateSetClause_userTable(Form form){
		
		String name = String.format("name = '%s'", form.name );
		String password = String.format("password = '%s'", form.password );
		String phone_number = String.format("phone_number = '%s'", form.phone_number );
		String email_address = String.format("email_address = '%s'", form.email_address );
		String address = String.format("address = '%s'", form.address );

		ArrayList<String> setClauseList = new ArrayList<String>();

		if(form.name.length() !=0){
			setClauseList.add(name);
		}
		if(form.password.length() !=0){
			setClauseList.add(password);
		}
		if(form.phone_number.length() !=0){
			setClauseList.add(phone_number);
		}
		if(form.email_address.length() !=0){
			setClauseList.add(email_address);
		}
		if(form.address.length() !=0){
			setClauseList.add(address);
		}
		
		
		String setClause = setClauseList.get(0);
		for(int i = 1 ; i < setClauseList.size(); i++){
			setClause += ", "+ setClauseList.get(i); 	
		}
		
		return setClause;
	}
	
	private String makeUpdateSetClause_crew(Form form){
		String position = String.format("position = '%s'", form.position );
		String role = String.format("role = '%s'", form.role );

		ArrayList<String> setClauseList = new ArrayList<String>();
		
		if(form.position.length() !=0){
			setClauseList.add(position);
		}
		if(form.role.length() !=0){
			setClauseList.add(role);
		}
		
		String setClause = setClauseList.get(0);
		for(int i = 1 ; i < setClauseList.size(); i++){
			setClause += ", "+ setClauseList.get(i); 	
		}
		
		return setClause;
	}
	
	private String makeUpdateWhereCaluse_crew(Form form){
		return String.format("crew_ID = '%s'", form.crew_ID );
	}
	private String makeUpdateWhereCaluse_userTable(Form form){
		return String.format("user_ID = '%s'", form.user_ID );
	}
	private String getSQLStatement_changeValue_crew(Form form) {
		String SQLFormat = "update %s set %s where %s";
		
		String whereClause = makeUpdateWhereCaluse_crew(form);
		String setClause = makeUpdateSetClause_crew(form);
		
		String ret = String.format(SQLFormat, tableMetaData.crewTableName, setClause, whereClause);
		return ret;
	}
	
	private String getSQLStatement_changeValue_userTable(Form form) {
		String SQLFormat = "update %s set %s where %s";
		
		String whereClause = makeUpdateWhereCaluse_userTable(form);
		String setClause = makeUpdateSetClause_userTable(form);
		
		String ret = String.format(SQLFormat, tableMetaData.userTableName, setClause, whereClause);
		return ret;
	}
	
	private String printTable(ArrayList< ArrayList<String> > tupleList){
		String tableTagFormat = " <table border=\"1\"> %s </table>";
		String tableTagContents = "";
		
		for (int i = 0; i < tupleList.size(); i++){
			String trTagFromat = "<tr> %s </tr>";
			String trTagContents ="";
			
			for(int j =0; j< tupleList.get(i).size(); j++){
				String thTagFormat="<th>%s</th>";
				String thTag = String.format(thTagFormat, tupleList.get(i).get(j));
				trTagContents += thTag;
			}
			String trTag = String.format(trTagFromat, trTagContents);
			tableTagContents += trTag;
		}
		
		return String.format(tableTagFormat, tableTagContents);
	}
	
	private boolean isSearchData(ArrayList<String> tuple, ArrayList<String> metaData, String searchKey, String option ){
		String upperOtion = option.toUpperCase();
		for(int i = 0; i< metaData.size(); i++){
			if(metaData.get(i).equals(upperOtion) && tuple.get(i).contains(searchKey) ){
				return true;
			}
		}
		
		return false;
	}
	
	private ArrayList< ArrayList<String> > parseList (ArrayList< ArrayList<String> > list, String SearchKey, String option){
		ArrayList< ArrayList<String> >  ret = new ArrayList< ArrayList<String> > ();
		ArrayList<String> metaData = list.get(0);
		ret.add(metaData);
		
		for(int i = 1; i<list.size(); i++){
			ArrayList<String> tuple = list.get(i);
			if(isSearchData(tuple, metaData, SearchKey, option)){
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
		
		ArrayList< ArrayList<String> > List = makeTupleList(searchSQLstatement); 				
		ArrayList< ArrayList<String> > afterParseList = parseList(List, searchKey, searchOption);		
		ret = printTable(afterParseList);		
		
		return ret;
	}
	public String printAllTableTupleList(){
		String ret = "";
		String SQLStatement = getSQLStatement_select_all();
		ArrayList< ArrayList<String> > List = makeTupleList(SQLStatement); 
		ret = printTable(List);		
		return ret;
	}
	public ArrayList< ArrayList<String> >makeTupleList(String SQLStatment) {
		String SQLStatement = getSQLStatement_select_all();
		myDBQuery query = new myDBQuery();
		ArrayList< ArrayList<String> > result = query.getResultQuery(SQLStatement);
		
		return result;
	}
	
	private boolean isValidRegisterForm(Form form) {
		ValidFormMsg = null;
		if (form.name.length() > tableMetaData.MAXFEILDSIZE_name) {
			ValidFormMsg = "Too Long name";
			return false;
		}
		else if (form.name.length() == 0){
			ValidFormMsg = "no name";
			return false;
		}
		
		if (form.position.length() > tableMetaData.MAXFEILDSIZE_position) {
			ValidFormMsg = "Too Long position";
			return false;
		}
		else if(form.position.length() == 0){
			ValidFormMsg = "no position";
			return false;
		}
		
		if (form.role.length() > tableMetaData.MAXFEILDSIZE_role) {
			ValidFormMsg = "Too Long role";
			return false;
		}
		else if(form.role.length() == 0){
			ValidFormMsg = "no role";
			return false;
		}
		
		if (form.phone_number.length() > tableMetaData.MAXFEILDSIZE_phone_number) {
			ValidFormMsg = "Too Long phone_number";
			return false;
		}
		else if(form.phone_number.length() == 0){
			ValidFormMsg = "no phone_number";
			return false;
		}
		
		
		if (form.email_address.length() > tableMetaData.MAXFEILDSIZE_email_address) {
			ValidFormMsg = "Too Long email_address";
			return false;
		}
		else if(form.email_address.length() == 0){
			ValidFormMsg = "no email_address";
			return false;
		}
		
		if (form.address.length() > tableMetaData.MAXFEILDSIZE_address) {
			ValidFormMsg = "Too Long address";
			return false;
		}
		else if(form.address.length() == 0){
			ValidFormMsg = "no address";
			return false;
		}
		return true;
	}
	
	private boolean isValiedDeleteValueForm( Form form ){
		boolean ret = true;
		this.ValidFormMsg = null;
		
		
		if (form.crew_ID.length() > tableMetaData.MAXFEILDSIZE_crew_ID){
			ValidFormMsg = "too long crew_ID";
			ret = false;
		}
		else if (form.crew_ID.length() == 0){
			ValidFormMsg = "no crew_ID";
			ret = false;
		}
		
		return ret;
	}
	
	private boolean isValiedUpdateValueForm_crew( Form form){
		boolean isChanged = false;
		
		if (form.crew_ID.length() != 0){
			if (form.crew_ID.length() > tableMetaData.MAXFEILDSIZE_crew_ID){
				ValidFormMsg = "Too Long crew_ID";
				return false;
			}
		}
		else {
			ValidFormMsg = "crew_ID 입력 되지않음";
			return false;
		}
		
		if(form.position.length() != 0){
			isChanged = true;
			if (form.position.length()  > tableMetaData.MAXFEILDSIZE_position) {
				ValidFormMsg = "Too Long position";
				return false;
			}
		}
		
		if(form.role.length() != 0){
			isChanged = true;
			if (form.role.length()  > tableMetaData.MAXFEILDSIZE_role) {
				ValidFormMsg = "Too Long role";
				return false;
			}
		}
		if(isChanged == true)
			return true;
		else 
			return false;
	}
	
	private boolean isValiedUpdateValueForm_userTable( Form form){
		boolean isChanged = false;
		
		if (form.user_ID.length() != 0){
			if (form.user_ID.length() > tableMetaData.MAXFEILDSIZE_user_ID){
				ValidFormMsg = "Too Long user_ID";
				return false;
			}
		}
		else {
			ValidFormMsg = "user_ID 입력 되지않음";
			return false;
		}
		
		if(form.name.length() != 0){
			isChanged = true;
			if (form.name.length()  > tableMetaData.MAXFEILDSIZE_name) {
				ValidFormMsg = "Too Long name";
				return false;
			}
		}
		
		if(form.password.length() != 0){
			isChanged = true;
			if (form.password.length()  > tableMetaData.MAXFEILDSIZE_password) {
				ValidFormMsg = "Too Long password";
				return false;
			}
		}
		
		if(form.phone_number.length() != 0){
			isChanged = true;
			if (form.phone_number.length()  > tableMetaData.MAXFEILDSIZE_phone_number) {
				ValidFormMsg = "Too Long phone_number";
				return false;
			}
		}
		if(form.email_address.length() != 0){
			isChanged = true;
			if (form.email_address.length()  > tableMetaData.MAXFEILDSIZE_email_address) {
				ValidFormMsg = "Too Long email_address";
				return false;
			}
		}
		if(form.address.length() != 0){
			isChanged = true;
			if (form.address.length()  > tableMetaData.MAXFEILDSIZE_address) {
				ValidFormMsg = "Too Long address";
				return false;
			}
		}
		
		if(isChanged == true)
			return true;
		else 
			return false;
	}

	private boolean isValidSearchFrom( Form form){
		boolean ret = true;
		if(form.searchKey.length() > 50){
			ValidFormMsg = "TOO long searchKey";
			return false;
		}
		if (form.searchKey.length() == 0 || form.searchKey == null){
			ValidFormMsg = "no search key";
			return false;
		}
		
		return ret;
	}
	
	public void register(HttpServletRequest request, HttpServletResponse response){
		String userId = request.getParameter("id");		
		Form form = new Form(request);
		
		if (isValidRegisterForm(form) == true) {
			form.user_ID = generateNewId(form.name);
			form.crew_ID = generateNewId(form.name);
			
			String SQLStatement1 = getSQLStatement_insert_value_userTable(form);
			String SQLStatement2 = getSQLStatement_insert_value_Crew(form);
			
			myDBQuery query1 = new myDBQuery();
			query1.justQuery(SQLStatement1);
			myDBQuery query2 = new myDBQuery();
			query2.justQuery(SQLStatement2);
		}
		
		RequestDispatcher rd = request.getServletContext().getRequestDispatcher(this.nextPageURL);
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
	
	private String get_user_idBy_Crew_id(String Crew_id){
		String SQLStatement = String.format("select user_id from crew where Crew_id = '%s'", Crew_id); 
		myDBQuery query = new myDBQuery();
		ArrayList< ArrayList <String> > result = query.getResultQuery(SQLStatement);
		String user_id = result.get(1).get(0);
		
		return user_id;
	}
	public void delete(HttpServletRequest request, HttpServletResponse response){
		String userId = request.getParameter("id");		
		Form form = new Form(request);

		if (isValiedDeleteValueForm(form) == true) {
			//find user_id
			form.user_ID = get_user_idBy_Crew_id(form.crew_ID);
			String SQLStatement1 = getSQLStatement_delete_userTable(form);
			String SQLStatement2 = getSQLStatement_delete_crew(form);
			
			myDBQuery query1 = new myDBQuery();
			query1.justQuery(SQLStatement1);
			myDBQuery query2 = new myDBQuery();
			query2.justQuery(SQLStatement2);
		}
		
		RequestDispatcher rd = request.getServletContext().getRequestDispatcher(this.nextPageURL);
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
	
	public void search(HttpServletRequest request, HttpServletResponse response){
		String userId = request.getParameter("id");		
		Form form = new Form(request);
		String searchSQLstatement = null;
		
		if (isValidSearchFrom(form) == true) {
			searchSQLstatement = getSQLStatement_select_all();
		}
		
		RequestDispatcher rd = request.getServletContext().getRequestDispatcher(this.nextPageURL);
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
	
	public void update(HttpServletRequest request, HttpServletResponse response){
		String userId = request.getParameter("id");		
		Form form = new Form(request);
		form.user_ID = get_user_idBy_Crew_id(form.crew_ID);

		if (isValiedUpdateValueForm_crew(form) == true) {
			String SQLStatement = getSQLStatement_changeValue_crew(form);
		
			myDBQuery query = new myDBQuery();
			query.justQuery(SQLStatement);
		}
		if (isValiedUpdateValueForm_userTable(form) == true) {
			String SQLStatement = getSQLStatement_changeValue_userTable(form);
			myDBQuery query = new myDBQuery();
			query.justQuery(SQLStatement);
		}
		
		RequestDispatcher rd = request.getServletContext().getRequestDispatcher(this.nextPageURL);
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
	
	public  ArrayList<String> getCrewIDList(){
		ArrayList<String> ret = new ArrayList<String>();
		String SQLStatement = String.format("select %s from %s", tableMetaData.crew_ID, tableMetaData.crewTableName);
		
		myDBQuery query = new myDBQuery();
		ArrayList < ArrayList<String> > result = query.getResultQuery(SQLStatement);
		for(int i = 1; i<result.size(); i++){
			ret.add(result.get(i).get(0));
		}
		return ret;
	}
	public String printSelectOption_crewID(){
		String selectTagName = "crew_ID";
		String SelectTagFormat = "<select name = \"%s\">  %s </select>";
		String strOptionTag = "";
		
		ArrayList<String> optionList  = getCrewIDList();
		if (optionList != null){
			for (int i =0; i< optionList.size(); i++){
				String optionFormat = String.format("<option> %s </option>", optionList.get(i));
				strOptionTag += optionFormat;
			}
		}
		String ret = String.format(SelectTagFormat, selectTagName, strOptionTag); 
		
		return ret;
	}
	
	public  ArrayList<String> getcargoShipIDList(){
		ArrayList<String> ret = new ArrayList<String>();
		String SQLStatement = String.format("select %s from %s", tableMetaData.cargo_ship_id, tableMetaData.cargo_shipTableName);
		
		myDBQuery query = new myDBQuery();
		ArrayList < ArrayList<String> > result = query.getResultQuery(SQLStatement);
		for(int i = 1; i<result.size(); i++){
			ret.add(result.get(i).get(0));
		}
		return ret;
	}
	public String printSelectOption_cargoShipID(){
		String selectTagName = "space_cargo_ship_id";
		String SelectTagFormat = "<select name = \"%s\">  %s </select>";
		String strOptionTag = "";
		
		ArrayList<String> optionList  = getcargoShipIDList();
		if (optionList != null){
			for (int i =0; i< optionList.size(); i++){
				String optionFormat = String.format("<option> %s </option>", optionList.get(i));
				strOptionTag += optionFormat;
			}
		}
		String ret = String.format(SelectTagFormat, selectTagName, strOptionTag); 
		
		return ret;
	}
	
	private String getSQLStatement_setCrewToCargoShip(Form form){
		String SQLFormat = "update %s set %s where %s";
		String setClause = String.format("%s='%s'", tableMetaData.space_cargo_ship_id, form.space_cargo_ship_id );
		String whereClause = String.format("%s='%s'", tableMetaData.crew_ID, form.crew_ID );
		
		String ret = String.format(SQLFormat, 
				tableMetaData.crewTableName, 
				setClause,
				whereClause
				);
		return ret;
	}
	
	public void setCrewToCargoShip(HttpServletRequest request, HttpServletResponse response){
		String userId = request.getParameter("id");		
		Form form = new Form(request);

		String SQLStatement = getSQLStatement_setCrewToCargoShip(form);
		
		myDBQuery query = new myDBQuery();
		query.justQuery(SQLStatement);
		
		RequestDispatcher rd = request.getServletContext().getRequestDispatcher(this.nextPageURL);
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
	

	
	
	




