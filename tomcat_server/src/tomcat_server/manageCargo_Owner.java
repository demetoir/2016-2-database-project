package tomcat_server;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



public class manageCargo_Owner {

	String ValidFormMsg = null;
	String nextPageURL = "/manageCargo_Owner.jsp";
	
	private TableMetaData tableMetaData = new TableMetaData();  

	
	private String generateNewId(String name) {
		return name +"_"+ System.currentTimeMillis();
	}
	public String printFailMsg(HttpServletRequest request){
		String msg = (String) request.getAttribute("failmsg") ;
		if (msg == null) msg = "";
		return msg;
	}

	private String getSQLStatement_select_all() {
		String SQLFormat = "select %s from %s where %s";
		String selectClase = "*";
		String joinAttribute = "user_ID";
		String fromClause = String.format("%s, %s", 
				tableMetaData.cargo_ownerTableName, 
				tableMetaData.userTableName);
		
		String whereClause = String.format("%s.%s = %s.%s",
				tableMetaData.cargo_ownerTableName,
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
		
	
	//user_ID varchar(50),
	//password varchar(50),
	//name varchar(50),
	//user_type varchar(50),
	//phone_number varchar(50),
	//email_address varchar(100),
	//address varchar(300),
	//


	private String getSQLStatement_insert_value_Cargo_Owner(myForm form){		
		String SQLFormat = "insert into %s values( %s )";
		String credit = "0";
		String insertValue = String.format("'%s', '%s', '%s', '%s', '%s', %s",
				form.cargo_owner_ID, 				
				form.user_ID, 	
				form.planet_id, 	
				form.company_name,				
				form.grade,
				credit
				);
		
		String ret = String.format(SQLFormat, tableMetaData.cargo_ownerTableName, insertValue);
		return ret;
	}

	private String getSQLStatement_insert_value_userTable(myForm form){	
		String SQLFormat = "insert into %s values( %s )";
		String user_type = "화주";
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
		
	
	private String getSQLStatement_delete_Cargo_Owner( myForm form ){
		String whereClause = String.format("user_ID ='%s'", form.user_ID);	
		String SQLFormat = "delete from %s where %s";
		String ret = String.format(SQLFormat, tableMetaData.cargo_ownerTableName, whereClause) ;		
		return ret;
	}
	
	private String getSQLStatement_delete_userTable( myForm form ){
		String whereClause = String.format("user_ID ='%s'", form.user_ID);		
		String SQLFormat = "delete from %s where %s";
		String ret = String.format(SQLFormat, tableMetaData.userTableName, whereClause) ;		
		return ret;
	}

	
	private String makeUpdateSetClause_userTable(myForm form){
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
	

	
	private String makeUpdateSetClause_Cargo_Owner(myForm form){
		String company_name = String.format("company_name = '%s'", form.grade );
		String grade = String.format("grade = '%s'", form.grade );
		String planet_id = String.format("planet_id = '%s'", form.planet_id );
		String credit = String.format("credit = '%s'", form.credit );

		ArrayList<String> setClauseList = new ArrayList<String>();
		
		if(form.company_name.length() !=0){
			setClauseList.add(company_name);
		}
		if(form.grade.length() !=0){
			setClauseList.add(grade);
		}
		if(form.planet_id.length() !=0){
			setClauseList.add(planet_id);
		}
		if(form.credit.length() !=0){
			setClauseList.add(credit);
		}
		
		String setClause = setClauseList.get(0);
		for(int i = 1 ; i < setClauseList.size(); i++){
			setClause += ", "+ setClauseList.get(i); 	
		}
		
		return setClause;
	}
	
	private String makeUpdateWhereCaluse_Cargo_Owner(myForm form){
		return String.format("cargo_owner_ID = '%s'", form.cargo_owner_ID );
	}
	private String makeUpdateWhereCaluse_userTable(myForm form){
		return String.format("user_ID = '%s'", form.user_ID );
	}
	
	private String getSQLStatement_changeValue_Cargo_Owner(myForm form) {
		String SQLFormat = "update %s set %s where %s";
		
		String whereClause = makeUpdateWhereCaluse_Cargo_Owner(form);
		String setClause = makeUpdateSetClause_Cargo_Owner(form);
		
		String ret = String.format(SQLFormat, tableMetaData.cargo_ownerTableName, setClause, whereClause);
		return ret;
	}
	
	private String getSQLStatement_changeValue_userTable(myForm form) {
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
	
	
	private boolean isValidRegisterForm(myForm form) {
		ValidFormMsg = null;
		if (form.name.length() > tableMetaData.MAXFEILDSIZE_name) {
			ValidFormMsg = "Too Long name";
			return false;
		}
		else if (form.name.length() == 0){
			ValidFormMsg = "no name";
			return false;
		}
		
		if (form.company_name.length() > tableMetaData.MAXFEILDSIZE_company_name) {
			ValidFormMsg = "Too Long company_name";
			return false;
		}
		else if(form.company_name.length() == 0){
			ValidFormMsg = "no company_name";
			return false;
		}
		
		if (form.grade.length() > tableMetaData.MAXFEILDSIZE_grade) {
			ValidFormMsg = "Too Long grade";
			return false;
		}
		else if(form.grade.length() == 0){
			ValidFormMsg = "no grade";
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
	
	private boolean isValiedDeleteValueForm( myForm form ){
		boolean ret = true;
		this.ValidFormMsg = null;
		
		
		if (form.cargo_owner_ID.length() > tableMetaData.MAXFEILDSIZE_cargo_owner_ID){
			ValidFormMsg = "too long cargo_owner_ID";
			ret = false;
		}
		else if (form.cargo_owner_ID.length() == 0){
			ValidFormMsg = "no cargo_owner_ID";
			ret = false;
		}
		
		return ret;
	}
	
	private boolean isValiedUpdateValueForm_Cargo_Owner( myForm form){
		boolean isChanged = false;
		
		if (form.cargo_owner_ID.length() != 0){
			if (form.cargo_owner_ID.length() > tableMetaData.MAXFEILDSIZE_cargo_owner_ID){
				ValidFormMsg = "Too Long cargo_owner_ID";
				return false;
			}
		}
		else {
			ValidFormMsg = "cargo_owner_ID 입력 되지않음";
			return false;
		}
		
		if(form.company_name.length() != 0){
			isChanged = true;
			if (form.company_name.length()  > tableMetaData.MAXFEILDSIZE_company_name) {
				ValidFormMsg = "Too Long company_name";
				return false;
			}
		}
		
		if(form.grade.length() != 0){
			isChanged = true;
			if (form.grade.length()  > tableMetaData.MAXFEILDSIZE_grade) {
				ValidFormMsg = "Too Long grade";
				return false;
			}
		}
		
		if(form.credit.length() != 0){
			isChanged = true;
			try {
				Float.parseFloat(form.credit);
			} catch (Exception e) {
				ValidFormMsg = " credit 숫자 아님";
				return false;
			}
		}
		
		if(isChanged == true)
			return true;
		else 
			return false;
	}
	
	private boolean isValiedUpdateValueForm_userTable( myForm form){
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

	private boolean isValidSearchFrom( myForm form){
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
		myForm form = new myForm(request);
		
		if (isValidRegisterForm(form) == true) {
			form.user_ID = generateNewId(form.name);
			form.cargo_owner_ID = generateNewId(form.name);
			
			String SQLStatement1 = getSQLStatement_insert_value_userTable(form);
			String SQLStatement2 = getSQLStatement_insert_value_Cargo_Owner(form);
			
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
	
	private String get_user_idBy_cargo_owner_ID(String cargo_owner_ID){
		String SQLStatement = String.format("select user_id from cargo_owner where cargo_owner_ID = '%s'", cargo_owner_ID); 
		myDBQuery query = new myDBQuery();
		ArrayList< ArrayList <String> > result = query.getResultQuery(SQLStatement);
		String user_id = result.get(1).get(0);
		
		return user_id;
	}
	public void delete(HttpServletRequest request, HttpServletResponse response){
		String userId = request.getParameter("id");		
		myForm form = new myForm(request);

		if (isValiedDeleteValueForm(form) == true) {
			//find user_id
			form.user_ID = get_user_idBy_cargo_owner_ID(form.cargo_owner_ID);
			String SQLStatement1 = getSQLStatement_delete_userTable(form);
			String SQLStatement2 = getSQLStatement_delete_Cargo_Owner(form);
			
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
		myForm form = new myForm(request);
		String searchSQLstatement = null;
		
		if (isValidSearchFrom(form) == true) {
			searchSQLstatement = getSQLStatement_select_all();
		}
		
		RequestDispatcher rd = request.getServletContext().getRequestDispatcher(this.nextPageURL);
		request.setAttribute("id", userId);
		request.setAttribute("failmsg", this.ValidFormMsg);
		
		request.setAttribute("searchSQLstatement", searchSQLstatement);
		request.setAttribute("searchOption", form.searchOption);
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
		myForm form = new myForm(request);
		form.user_ID = get_user_idBy_cargo_owner_ID(form.cargo_owner_ID);

		if (isValiedUpdateValueForm_Cargo_Owner(form) == true) {
			String SQLStatement = getSQLStatement_changeValue_Cargo_Owner(form);
		
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
	
	
	
	public  ArrayList<String> getPlanetIDList(){
		ArrayList<String> ret = new ArrayList<String>();
		String SQLStatement = String.format("select %s from %s", tableMetaData.planet_id, tableMetaData.planetTableName);
		
		myDBQuery query = new myDBQuery();
		ArrayList < ArrayList<String> > result = query.getResultQuery(SQLStatement);
		for(int i = 1; i<result.size(); i++){
			ret.add(result.get(i).get(0));
		}
		return ret;
	}
	
	public String printSelectOption_PlanetID(){
		String selectTagName = "planet_id";
		String SelectTagFormat = "<select name = \"%s\">  %s </select>";
		String strOptionTag = "";
		
		ArrayList<String> optionList  = getPlanetIDList();
		if (optionList != null){
			for (int i =0; i< optionList.size(); i++){
				String optionFormat = String.format("<option> %s </option>", optionList.get(i));
				strOptionTag += optionFormat;
			}
		}
		String ret = String.format(SelectTagFormat, selectTagName, strOptionTag); 
		
		return ret;
	}
	
	
		
}
	

	
	
	




