package tomcat_server;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//create table cargo(
//weight numeric(10,2),

//create table cargo_owner(
//credit numeric(10,4),

//create table cargo_ship(
//capacity numeric(10,2),
//velocity numeric(10,2),
//max_flight_distance numeric(10,2),
//cost numeric(10,2),

public class cargoOwnerMainPage {
	String ValidFormMsg = null;
	String nextPageURL = "/cargoOwnerMainPage.jsp";
	String user_id = "";
	String CargoOwner_id = "";
	float credit = 0;
	public cargoOwnerMainPage(HttpServletRequest request, HttpServletResponse response){
		this.user_id = request.getParameter("id");
		String select = String.format("%s", tableMetaData.cargo_owner_ID) ;
		String from = String.format("%s", tableMetaData.cargo_ownerTableName) ;
		String where = String.format("%s = '%s' ", tableMetaData.user_ID, user_id) ;
		String SQLStatement = String.format("select %s from %s where %s",
				select,
				from,
				where
				);
		myDBQuery query = new myDBQuery();
		ArrayList< ArrayList<String> > result = query.getResultQuery(SQLStatement);
		
		this.CargoOwner_id = result.get(1).get(0);
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
	
	
	
	private ArrayList<String> getPlanetIDList(){
		ArrayList<String> ret = new ArrayList<String>();
		myDBQuery query = new myDBQuery();
		String SQLStatement = String.format("select %s from %s",
				tableMetaData.planet_ID,
				tableMetaData.planetTableName
				);
		ArrayList<ArrayList<String> > result = query.getResultQuery(SQLStatement); 
		
		for(int i=1 ; i<result.size(); i++){
			ret.add(result.get(i).get(0));
		}		
		return ret;
	}
	public String printSelectOption_destination(){
		String selectTagName = tableMetaData.destination;
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
	
	private ArrayList<String> getCargo_ship_idList(){
		ArrayList<String> ret = new ArrayList<String>();
		myDBQuery query = new myDBQuery();
		String SQLStatement = String.format("select %s from %s",
				tableMetaData.cargo_ship_id,
				tableMetaData.cargo_shipTableName
				);
		ArrayList<ArrayList<String> > result = query.getResultQuery(SQLStatement); 
		
		for(int i=1 ; i<result.size(); i++){
			ret.add(result.get(i).get(0));
		}		
		return ret;
	}
	public String printSelectOption_cargo_ship_id(){
		String selectTagName = tableMetaData.cargo_ship_id;
		String SelectTagFormat = "<select name = \"%s\">  %s </select>";
		String strOptionTag = "";
		
		ArrayList<String> optionList  = getCargo_ship_idList();
		if (optionList != null){
			for (int i =0; i< optionList.size(); i++){
				String optionFormat = String.format("<option> %s </option>", optionList.get(i));
				strOptionTag += optionFormat;
			}
		}
		String ret = String.format(SelectTagFormat, selectTagName, strOptionTag); 
		
		return ret;
	}
	
	
	private String getSQLStatement_select_all() {
		String SQLFormat ="select cargo.cargo_id, cargo.cargo_owner_ID, cargo.weight,"
				+ " cargo.destination, cargo.cargo_ship_id, cargo.cargo_state, planet.할인및할증률 , cargo_ship.cost,"
				+ " planet.할인및할증률 * cargo_ship.cost * cargo.weight as \"예상 비용\" "
				+ " from cargo, planet, cargo_ship "
				+ " where cargo.destination = planet_ID and cargo.cargo_ship_id = cargo_ship.cargo_ship_id"
				+ " and cargo.cargo_owner_ID = '%s' ";
		String SQLStatement = String.format(SQLFormat, this.CargoOwner_id);
		
		return SQLStatement;
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
	

	
	
	//TODO
	private String makeUpdateSetClause(myForm form){
		String weight = String.format("weight = '%s'", form.weight );
		String destination = String.format("destination = '%s'", form.destination );
		String cargo_ship_id = String.format("cargo_ship_id = '%s'", form.cargo_ship_id );

		ArrayList<String> setClauseList = new ArrayList<String>();

		if(form.weight.length() !=0){
			setClauseList.add(weight);
		}
		if(form.destination.length() !=0){
			setClauseList.add(destination);
		}
		if(form.cargo_ship_id.length() !=0){
			setClauseList.add(cargo_ship_id);
		}
		
		String setClause = setClauseList.get(0);
		for(int i = 1 ; i < setClauseList.size(); i++){
			setClause += ", "+ setClauseList.get(i); 	
		}
		
		return setClause;
	}
	
	
	//TODO
	private String getSQLStatement_updateValue(myForm form) {
		String SQLFormat = "update %s set %s where %s";
		
		String whereClause =  String.format("cargo_id = '%s'", form.cargo_id );
		String setClause = makeUpdateSetClause(form);
		
		
		String ret = String.format(SQLFormat, tableMetaData.cargoTableName, setClause, whereClause);
		return ret;
	}
	
	
	//TODO
	private boolean isValiedUpdateValueForm(myForm form){
		boolean isChanged = false;
		
		if (form.cargo_id.length() == 0){
			this.ValidFormMsg = "no cargo_id";
			return false;
		}
		if(form.cargo_id.length() > tableMetaData.MAXFEILDSIZE_cargo_id){
			this.ValidFormMsg = "Too long cargo_id";
			return false;
		}
		
		String SQLFormat = "select %s from %s where %s";
		String select = String.format("%s , %s", 
				tableMetaData.cargo_id,
				tableMetaData.cargo_state);
		
		String from = String.format("%s", tableMetaData.cargoTableName);
		String where = String.format("%s = '%s' and %s = '%s'", 
				tableMetaData.cargo_id, form.cargo_id,
				tableMetaData.cargo_owner_ID, this.CargoOwner_id);
		
		String SQLStatement = String.format(SQLFormat, select, from, where);
		myDBQuery query = new myDBQuery();
		ArrayList< ArrayList <String>> result = query.getResultQuery(SQLStatement);
		
		if (result.size() == 1){
			this.ValidFormMsg = "cargo_id 존재하지않음";
			return false;
		}
		String state = result.get(1).get(1);
		
		if (  state.equals(tableMetaData.cargo_state_delivery_complete) 
			||state.equals(tableMetaData.cargo_state_delivery) ){
			this.ValidFormMsg = "수정 할 수 없는 화물";
			return false;
		}
		
		if (form.weight.length() != 0 ){
			try {
				Float.parseFloat(form.weight);
			} catch (Exception e) {
				this.ValidFormMsg = "weight is not number";
				return false;
			}
			isChanged = true;
		}		

		if(isChanged == true)
			return true;
		else 
			return false;
	}


	public void update(HttpServletRequest request, HttpServletResponse response){
		String userId = request.getParameter("id");		
		myForm form = new myForm(request);

		if (isValiedUpdateValueForm(form) == true) {
			String SQLStatement = getSQLStatement_updateValue(form);
		
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
	
	
	
	private String getSQLStatement_insert_value(myForm form){		
		String SQLFormat = "insert into %s values( %s )";
		String cargo_state = tableMetaData.cargo_state_waiting_confirm;
		String insertValue = String.format(" '%s', '%s', %s, '%s', '%s', '%s'",
				form.cargo_id, 				
				this.CargoOwner_id, 	
				form.weight, 	
				form.destination,				
				form.cargo_ship_id,
				cargo_state
				);
		
		String ret = String.format(SQLFormat, tableMetaData.cargoTableName, insertValue);
		return ret;
	}
	
	private boolean isValidRegisterForm(myForm form) {
		this.ValidFormMsg = null;
			
		try {
			Float.parseFloat(form.weight);
		} catch (Exception e) {
			this.ValidFormMsg = "weight is not number";
			return false;
		}
		if (form.weight.length() == 0){
			this.ValidFormMsg = "no weight";
			return false;
		}

		return true;
	}
	
	public void register(HttpServletRequest request, HttpServletResponse response){
		String userId = request.getParameter("id");		
		myForm form = new myForm(request);
		
		if (isValidRegisterForm(form) == true) {
			form.cargo_id = generateNewId("cargo");
			String SQLStatement = getSQLStatement_insert_value(form);
			
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

	
	
	
	//TODO 돈 환불??
	private boolean isValiedDeleteValueForm( myForm form ){
		this.ValidFormMsg = null;
		
		if (form.cargo_id.length() > tableMetaData.MAXFEILDSIZE_cargo_id){
			ValidFormMsg = "too long cargo_id";
			return false;
		}
		else if (form.cargo_id.length() == 0){
			ValidFormMsg = "no cargo_id";
			return false;
		}
		
		String SQLFormat = "select %s from %s where %s";
		String select = String.format("%s , %s", 
				tableMetaData.cargo_id,
				tableMetaData.cargo_state);
		String from = String.format("%s", tableMetaData.cargoTableName);
		String where = String.format("%s = '%s' and %s = '%s'", 
				tableMetaData.cargo_id, form.cargo_id,
				tableMetaData.cargo_owner_ID, this.CargoOwner_id);
		
		String SQLStatement = String.format(SQLFormat, select, from, where);
		myDBQuery query = new myDBQuery();
		ArrayList< ArrayList <String>> result = query.getResultQuery(SQLStatement);
		
		String state = result.get(1).get(1);
		if (state.equals(tableMetaData.cargo_state_delivery) ){
			this.ValidFormMsg = "운송중인 화물은 삭제 불가";
			return false;
		}
		
		
		
		return true;
	}
	
	private String getSQLStatement_delete( myForm form ){
		String whereClause = String.format("%s ='%s'", tableMetaData.cargo_id, form.cargo_id);		
		String SQLFormat = "delete from %s where %s";
		String ret = String.format(SQLFormat, tableMetaData.cargoTableName, whereClause) ;		
		return ret;
	}
	
	public void delete(HttpServletRequest request, HttpServletResponse response){
		String userId = request.getParameter("id");		
		myForm form = new myForm(request);

		if (isValiedDeleteValueForm(form) == true) {
			String SQLStatement = getSQLStatement_delete(form);
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
	
	
	private boolean isValidConfirmForm(myForm form){
		if (form.cargo_id.length() == 0){
			this.ValidFormMsg = "no cargo_id";
			return false;
		}
		if(form.cargo_id.length() > tableMetaData.MAXFEILDSIZE_cargo_id){
			this.ValidFormMsg = "Too long cargo_id";
			return false;
		}
		
		String SQLFormat = "select %s from %s where %s";
		String select = String.format("%s , %s", 
				tableMetaData.cargo_id,
				tableMetaData.cargo_state);
		
		String from = String.format("%s", tableMetaData.cargoTableName);
		String where = String.format("%s = '%s' and %s = '%s'", 
				tableMetaData.cargo_id, form.cargo_id,
				tableMetaData.cargo_owner_ID, this.CargoOwner_id);
		
		String SQLStatement = String.format(SQLFormat, select, from, where);
		myDBQuery query = new myDBQuery();
		ArrayList< ArrayList <String>> result = query.getResultQuery(SQLStatement);
		
		if (result.size() == 1){
			this.ValidFormMsg = "cargo_id 존재하지않음";
			return false;
		}
		String state = result.get(1).get(1);
		
		if (state.equals(tableMetaData.cargo_state_waiting_confirm) == false){
			this.ValidFormMsg = "등록 확인을 할 수 없는 화물";
			return false;
		}
		
		//돈 검사 \"예상 비용\"

		float totalCost = getTotalCost(form);
		if(totalCost > this.credit){
			this.ValidFormMsg = "돈 없어 못해";
			return false;
		}
		
		return true;
	}
	
	private String  getSQLStatement_Confirm(myForm form){
		String SQLFormat = "update %s set %s where %s";
		String Confirm = tableMetaData.cargo_state_waiting_delivery;
		String setClause = String.format("%s = '%s'", tableMetaData.cargo_state, Confirm);
		String whereClause =String.format("%s = '%s'", tableMetaData.cargo_id, form.cargo_id);
		String ret = String.format(SQLFormat, tableMetaData.cargoTableName, setClause, whereClause);
		return ret ;
	}
	
	private float getTotalCost(myForm form ){
		String subSql = getSQLStatement_select_all();
		String SQLStatement = String.format("select \"예상 비용\" from ( %s ) where cargo_id = '%s'", 
				subSql, form.cargo_id);
		myDBQuery query = new myDBQuery();
		ArrayList< ArrayList <String>> result = query.getResultQuery(SQLStatement);
		
		String temp = result.get(1).get(0);
		float totalCost = Float.parseFloat(temp);
		
		return totalCost;
	}
	//TODO 돈 빠져나가기
	public void confirm(HttpServletRequest request, HttpServletResponse response){
		String userId = request.getParameter("id");		
		myForm form = new myForm(request);
		
		if (isValidConfirmForm(form) == true) {
			String SQLStatement = getSQLStatement_Confirm(form);
			
			myDBQuery query = new myDBQuery();
			query.justQuery(SQLStatement);

			//돈처리
			float totalcost = getTotalCost(form);
			String updateValue = Float.toString(this.credit - totalcost);
			
			SQLStatement = String.format("update %s "
					+ "set credit = %s "
					+ "where cargo_owner_ID = '%s'",
					tableMetaData.cargo_ownerTableName,
					updateValue,
					this.CargoOwner_id
					);
			
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
	
	public String printCargoOwnerData(){
		String format_CargoOwnerData = "CargoOwner id: %s <br>예치금  = %s <br> 등급 : %s <br> company_name : %s <br>";
		
		String SQLFormat = "select %s from %s where %s";
		String select = String.format("%s, %s, %s, %s",
				tableMetaData.cargo_owner_ID,
				tableMetaData.credit,
				tableMetaData.grade,
				tableMetaData.company_name
				);
		
		
		String from = String.format("%s", tableMetaData.cargo_ownerTableName);
		String where = String.format("%s = '%s' ", 
				tableMetaData.cargo_owner_ID, this.CargoOwner_id);
		
		String SQLStatement = String.format(SQLFormat, select, from, where);
		myDBQuery query = new myDBQuery();
		ArrayList< ArrayList <String>> result = query.getResultQuery(SQLStatement);
		
		ArrayList <String> tuple = result.get(1);
		String temp = tuple.get(1);
		this.credit = Float.parseFloat(temp); 
		String ret = String.format(format_CargoOwnerData,
				tuple.get(0),
				tuple.get(1),
				tuple.get(2),
				tuple.get(3)
				);
				
		return ret;
	}
		
	

	public void searchCheepCargoShip(HttpServletRequest request, HttpServletResponse response){
		String userId = request.getParameter("id");		
		myForm form = new myForm(request);
		String SQLStatement;
		

		
		String SQLFormat = "select cargo_ship.cargo_ship_id, cargo_ship.cargo_ship_name, cargo_ship.cost "
				+ "from cargo, cargo_ship "
				+ "where cargo.destination = cargo_ship.cargo_ship_destination "
				+ "and cargo.cargo_id = '%s' "
				+ "order by cost";
		

		SQLStatement = String.format(SQLFormat, form.cargo_id);
		
		RequestDispatcher rd = request.getServletContext().getRequestDispatcher(this.nextPageURL);
		request.setAttribute("id", userId);
		request.setAttribute("failmsg", this.ValidFormMsg);
		request.setAttribute("SQL_searchCheepCargoShip", SQLStatement);
		request.setAttribute("searchCheepCargoShip_cargo_id", form.cargo_id);
		
		try {
			rd.forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//TODO
	private float getCargoShipCapacity(String cargo_ship_id){
		float ret =0;
		String searchSQLstatement = String.format("select capacity from cargo_ship "
				+ "where cargo_ship_id = '%s'",
				cargo_ship_id);
		
		myDBQuery q = new myDBQuery();
		ArrayList<  ArrayList<String> > result = q.getResultQuery(searchSQLstatement);

		String temp = result.get(1).get(0);
		ret = Float.parseFloat(temp);
		
		return ret;
	}
	
	private float getTotalCargoWeight(String cargo_ship_id){
		float ret = 0;
		String searchSQLstatement = String.format("select weight from shipped_cargo "
				+ "where cargo_ship_id = '%s'",
				cargo_ship_id);
		
		myDBQuery q = new myDBQuery();
		ArrayList<  ArrayList<String> > result = q.getResultQuery(searchSQLstatement);
		
		for(int i =1; i<result.size(); i++){
			String temp  = result.get(i).get(0);
			float f= Float.parseFloat(temp);
			ret += f;			
		}
		
		return ret;
	}

	private float getCargoWeight(String cargo_id){
		float ret = 0;
		String searchSQLstatement = String.format("select weight from cargo "
				+ "where cargo_id = '%s'",
				cargo_id);
		
		myDBQuery q = new myDBQuery();
		ArrayList<  ArrayList<String> > result = q.getResultQuery(searchSQLstatement);
		if (result.size() == 1){
			return -1;
		}
		
		String temp = result.get(1).get(0);
		ret = Float.parseFloat(temp);
		
		return ret;
	}
	
	private boolean isOk_searchCheepCargoShip(ArrayList <String> list, String cargo_id ){
		String cargoShip_id = list.get(0);
		
		float TotalCargoWeight = getTotalCargoWeight(cargoShip_id);
		float capacity = getCargoShipCapacity(cargoShip_id);
		float weight = getCargoWeight(cargo_id);
		
		if(TotalCargoWeight + weight <= capacity){
			return true;
		}
		return false;
	}
	
	private ArrayList< ArrayList <String>> parse_searchCheepCargoShip( ArrayList< ArrayList <String> > list,
			String cargo_id ){
		ArrayList< ArrayList <String>> result =  new ArrayList< ArrayList <String>>();
		result.add(list.get(0));
		
		for(int i =1; i<list.size(); i++){
			if(isOk_searchCheepCargoShip (list.get(i), cargo_id)){
				result.add(list.get(i));
			}
		}
		
		return result;
	}
	
	public String print_searchCheepCargoShip(HttpServletRequest request, HttpServletResponse response){
		String SQLStatement = (String) request.getAttribute("SQL_searchCheepCargoShip");
		String cargo_id = (String) request.getAttribute("searchCheepCargoShip_cargo_id");
		if(SQLStatement == null){
			return "";
		}
		
		myDBQuery query = new myDBQuery();
		ArrayList< ArrayList <String>> result = query.getResultQuery(SQLStatement);
		ArrayList< ArrayList <String>> afterParse = parse_searchCheepCargoShip(result, cargo_id);
		
		String ret = printTable(afterParse);				
		return ret;
	}
		
	
	
}
	

	
	
	




