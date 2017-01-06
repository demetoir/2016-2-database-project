package tomcat_server;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.media.jfxmediaimpl.MarkerStateListener;

public class crewMainPage {

	String ValidFormMsg = null;
	String nextPageURL = "/crewMainPage.jsp";
	String user_id = "";
	String crew_id = "";
	String space_cargo_ship_id = "";
	String destination = "";
	
	float capacity = 0;
	float totalCargoWeight = 0;
	
	
	public crewMainPage(HttpServletRequest request, HttpServletResponse response){
		this.user_id = request.getParameter("id");
		String select = String.format("%s, %s", 
				tableMetaData.crew_ID, 
				tableMetaData.space_cargo_ship_id) ;
		
		String from = String.format("%s", tableMetaData.crewTableName) ;
		String where = String.format("%s = '%s' ", tableMetaData.user_ID, user_id) ;
		String SQLStatement = String.format("select %s from %s where %s",
				select,
				from,
				where
				);
		myDBQuery query = new myDBQuery();
		ArrayList< ArrayList<String> > result = query.getResultQuery(SQLStatement);
		
		this.crew_id = result.get(1).get(0);
		this.space_cargo_ship_id = result.get(1).get(1);
		
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
		String SQLFormat = "select %s from %s where %s";
		String selectClase = "*";
		
		String fromClause = String.format("%s", tableMetaData.cargoTableName);
		
		String whereClause = String.format("%s = '%s'",
				tableMetaData.cargo_owner_ID,
				this.crew_id
				);
				
		String SQLStatement = String.format(SQLFormat,
											selectClase,
											fromClause,
											whereClause);
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
	
	
	
	public String printcrewData(){
		String format_crewData = "crew data <br>"
				+ "crew id: %s <br> "
				+ "space_cargo_ship_id : %s <br> "
				+ "position : %s <br> "
				+ "role : %s <br>";
		
		String SQLFormat = "select %s from %s where %s";
		String select = String.format("%s, %s, %s, %s",
				tableMetaData.crew_ID,
				tableMetaData.space_cargo_ship_id,
				tableMetaData.position,
				tableMetaData.role
				);
		
		String from = String.format("%s", tableMetaData.crewTableName);
		String where = String.format("%s = '%s' ", 
				tableMetaData.crew_ID, this.crew_id);
		
		String SQLStatement = String.format(SQLFormat, select, from, where);
		myDBQuery query = new myDBQuery();
		ArrayList< ArrayList <String>> result = query.getResultQuery(SQLStatement);
		
		ArrayList <String> tuple = result.get(1);
		
		
		String ret = String.format(format_crewData,
				tuple.get(0),
				tuple.get(1),
				tuple.get(2),
				tuple.get(3)
				);
				
		return ret;
	}
		
	public String printCargoShipData(){
		if(this.space_cargo_ship_id.equals(tableMetaData.not_in_space_cargo_ship_id)){
			return "소속 우주 화물선 정보 없음<br>";
		}
		
		String SQLFormat = "select %s from %s where %s";
		String select = String.format("*");
		String from = String.format("%s", tableMetaData.cargo_shipTableName);
		String where = String.format("%s = '%s' ", 
				tableMetaData.cargo_ship_id, this.space_cargo_ship_id);
		String SQLStatement = String.format(SQLFormat, select, from, where);
		myDBQuery query = new myDBQuery();
		ArrayList< ArrayList <String>> result = query.getResultQuery(SQLStatement);
		
		ArrayList <String> tuple = result.get(1);
		
		String format_CargoShipData = "CargoShipData<br>"
				+ "cargo_ship_id : %s <br>"
				+ "cargo_ship_name : %s <br>"
				+ "cargo_ship_grade : %s <br>"
				+ "capacity : %s <br>"
				+ "velocity : %s <br>"
				+ "max_flight_distance : %s <br>"
				+ "cargo_ship_state : %s <br>"
				+ "cargo_ship_destination : %s <br>"
				+ "cost : %s <br>";
		String ret = String.format(format_CargoShipData,
				tuple.get(0),
				tuple.get(1),
				tuple.get(2),
				tuple.get(3),
				tuple.get(4),
				tuple.get(5),
				tuple.get(6),
				tuple.get(7),
				tuple.get(8)
				);

		this.capacity = Float.parseFloat(tuple.get(3));
		
		
		this.destination = tuple.get(7);
		
		
		return ret;
	}
		
	public String printNecessary_item(){
		if(this.space_cargo_ship_id.equals(tableMetaData.not_in_space_cargo_ship_id)){
			return "소속 우주 화물선 정보 없음<br>";
		}
		
		String ret = "";
		String searchSQLstatement = String.format("select * from necessary_item where cargo_ship_id = '%s'",
				this.space_cargo_ship_id);
		myDBQuery q = new myDBQuery();
		
		ArrayList< ArrayList<String> > List = q.getResultQuery(searchSQLstatement); 				
		ret = printTable(List);		
		
		return ret;
	
	}
	
	public String printShipAble_cargo(){
		if(this.space_cargo_ship_id.equals(tableMetaData.not_in_space_cargo_ship_id)){
			return "소속 우주 화물선 정보 없음<br>";
		}
		
		String ret = "";
		String searchSQLstatement = String.format("select * from cargo "
				+ "where destination = '%s'"
				+ "and cargo_state = '%s' "
				+ "and cargo_ship_id = '%s'",
				this.destination,
				tableMetaData.cargo_state_waiting_delivery,
				this.space_cargo_ship_id);

		myDBQuery q = new myDBQuery();
		
		ArrayList< ArrayList<String> > List = q.getResultQuery(searchSQLstatement); 			
		ret = printTable(List);		
		
		return ret;
	
	}
	
	public String printShipped_cargo(){
		if(this.space_cargo_ship_id.equals(tableMetaData.not_in_space_cargo_ship_id)){
			return "소속 우주 화물선 정보 없음<br>";
		}
		
		String ret = "";
		String searchSQLstatement = String.format("select * from shipped_cargo where cargo_ship_id = '%s'",
				this.space_cargo_ship_id);

		myDBQuery q = new myDBQuery();
		
		ArrayList< ArrayList<String> > List = q.getResultQuery(searchSQLstatement);
		
		this.totalCargoWeight = 0;
		for(int i =1; i<List.size(); i++){
			String temp = List.get(i).get(2);
			float f = Float.parseFloat(temp);
			this.totalCargoWeight += f;
		}
		
		ret = printTable(List);		
		return ret;
	
	}
	
	public String printAllCargo(){
		String ret = "";
		String searchSQLstatement = String.format("select * from cargo ");
		myDBQuery q = new myDBQuery();
		
		ArrayList< ArrayList<String> > List = q.getResultQuery(searchSQLstatement); 				
		ret = printTable(List);		
		return ret;
	}

	

	private boolean isValidUnshipCargoForm(myForm form){
		if(form.cargo_id.length() == 0){
			this.ValidFormMsg = "no cargo_id";
			return false;
		}
		if(form.cargo_id.length() > tableMetaData.MAXFEILDSIZE_cargo_id){
			this.ValidFormMsg = "no cargo_id";
			return false;
		}
		
		String format = "select * from shipped_cargo "
				+ "where cargo_ship_id = '%s' "
				+ "and cargo_id = '%s'";
		String searchSQLstatement = String.format(format,
				this.space_cargo_ship_id,
				form.cargo_id);
		
		myDBQuery q = new myDBQuery();
		ArrayList<  ArrayList<String> > result = q.getResultQuery(searchSQLstatement);	
		
		if (result.size() == 1){
			this.ValidFormMsg = "선적되지 않는 화물 화물";
			return false;
		}
		
		
		return true;
	}
	private String getSQLStatement_unshipCargo(myForm form){
				String format = "delete from shipped_cargo "
				+ "where cargo_ship_id = '%s' "
				+ "and cargo_id = '%s'";
		String SQLstatement = String.format(format,
				this.space_cargo_ship_id,
				form.cargo_id);
		return SQLstatement;
	}
	
	public void unshipCargo(HttpServletRequest request, HttpServletResponse response){
		String userId = request.getParameter("id");		
		myForm form = new myForm(request);
		
		if (isValidUnshipCargoForm(form) ) {
			String SQLStatement = getSQLStatement_unshipCargo(form);
			
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
	
	
	private String getCargoShipDestinaion(String cargo_ship_id){
		String ret = "";
		String searchSQLstatement = String.format("select cargo_ship_destination from cargo_ship "
				+ "where cargo_ship_id = '%s'",
				cargo_ship_id);
		
		myDBQuery q = new myDBQuery();
		ArrayList<  ArrayList<String> > result = q.getResultQuery(searchSQLstatement);
		if (result.size() == 1){
			return null;
		}
		
		ret = result.get(1).get(0);
		
		return ret;
	}
	
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
	
	private String getCargoState(String cargo_id){
		String ret = null;
		String searchSQLstatement = String.format("select cargo_state from cargo "
				+ "where cargo_id = '%s'",
				cargo_id);
		
		myDBQuery q = new myDBQuery();
		ArrayList<  ArrayList<String> > result = q.getResultQuery(searchSQLstatement);

		ret = result.get(1).get(0);
		
		return ret;
	}
	private boolean isValidshipCargoForm(myForm form){
		if(form.cargo_id.length() == 0){
			this.ValidFormMsg = "no cargo_id";
			return false;
		}
		if(form.cargo_id.length() > tableMetaData.MAXFEILDSIZE_cargo_id){
			this.ValidFormMsg = "no cargo_id";
			return false;
		}
		
		String format = "select * from shipped_cargo "
				+ "where cargo_ship_id = '%s' "
				+ "and cargo_id = '%s'";
		String searchSQLstatement = String.format(format,
				this.space_cargo_ship_id,
				form.cargo_id);
		
		myDBQuery q = new myDBQuery();
		ArrayList<  ArrayList<String> > result = q.getResultQuery(searchSQLstatement);	
		
		if (result.size() > 1){
			this.ValidFormMsg = "이미 선적된 화물";
			return false;
		}
		
		String destination = getCargoShipDestinaion(this.space_cargo_ship_id);
		searchSQLstatement = String.format("select * from cargo "
				+ "where destination = '%s'"
				+ "and cargo_id = '%s'",
				destination,
				form.cargo_id);
		
		q = new myDBQuery();
		result = q.getResultQuery(searchSQLstatement);	
		
		if (result.size() == 1){
			this.ValidFormMsg = "선적할 수 없는 화물, 목적지 불일치";
			return false;
		}
		
		float weight = getCargoWeight(form.cargo_id);
		float capacity = getCargoShipCapacity(this.space_cargo_ship_id);
		float totalCargoWeight = getTotalCargoWeight(this.space_cargo_ship_id);
		
		if (weight + totalCargoWeight > capacity){
			this.ValidFormMsg = "용량 초과";
			return false;
		}
		
		
		String cargoState = getCargoState(form.cargo_id);
		if(cargoState.equals(tableMetaData.cargo_state_waiting_delivery) == false){
			this.ValidFormMsg = "선적할 수 없는 화물, 화주의 확인이 필요함";
			return false;
		}
		
		return true;
	}
	private String getSQLStatement_shipCargo(myForm form){
		String format = "insert into %s values('%s', '%s', %s)";
		float f = getCargoWeight(form.cargo_id);
		String weight = Float.toString(f);
		
		String SQLstatement = String.format(format,
				tableMetaData.shipped_cargoTablename,
				this.space_cargo_ship_id,
				form.cargo_id,
				weight);
		return SQLstatement;
	}
	
	public void shipCargo(HttpServletRequest request, HttpServletResponse response){
		String userId = request.getParameter("id");		
		myForm form = new myForm(request);
		
		if (isValidshipCargoForm(form) ) {
			String SQLStatement = getSQLStatement_shipCargo(form);
			
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
	
	public String printCargoShipCapacityStatus(){ 
		float capacity = getCargoShipCapacity(this.space_cargo_ship_id);
		float totalCargoWeight = getTotalCargoWeight(this.space_cargo_ship_id);
		String format = "%f / %f <br>";
		return String.format(format, totalCargoWeight, capacity);
	}
	
	
	private void updateCargoShip_state(String space_cargo_ship_id, String state){
		String SQLStatement = String.format("update cargo_ship "
				+ "set cargo_ship_state = '%s' "
				+ "where cargo_ship_id = '%s'",
				state,
				this.space_cargo_ship_id)
				;
		
		myDBQuery query = new myDBQuery();
		query.justQuery(SQLStatement);
	}
	
	private String getCargoShip_id(String crew_id) {
		String SQLStatement = String.format("select space_cargo_ship_id "
				+ "from crew "
				+ "where crew_ID = '%s'",
				crew_id);
		myDBQuery q = new myDBQuery();
		ArrayList< ArrayList<String> > result = q.getResultQuery(SQLStatement);
		String ret =result.get(1).get(0);
		return ret;
	}
	
	
	//TODO update cargo
	private void updateCargo_state(String space_cargo_ship_id, String state ){
		String SQLStatement = String.format("select cargo_id from shipped_cargo "
				+ "where cargo_ship_id = '%s'",
				this.space_cargo_ship_id);
		
		myDBQuery query = new myDBQuery();
		ArrayList< ArrayList<String > > result = query.getResultQuery(SQLStatement);
		
		for(int i =0; i<result.size(); i++){
			SQLStatement = String.format("update cargo "
					+ "set cargo_state = '%s' "
					+ "where cargo_id = '%s'",
					state,
					result.get(i).get(0)
					);
			query.justQuery(SQLStatement);
		}
		
	}
	
	public void setCargoShipReady(HttpServletRequest request, HttpServletResponse response){
		String userId = request.getParameter("id");		
		this.space_cargo_ship_id = getCargoShip_id(this.crew_id);
		
		updateCargoShip_state(this.space_cargo_ship_id, tableMetaData.cargo_ship_state_ready);
		
		updateCargo_state(this.space_cargo_ship_id, tableMetaData.cargo_state_waiting_delivery);
		
		
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
	
	
	public void setCargoShipDelivering (HttpServletRequest request, HttpServletResponse response){
		String userId = request.getParameter("id");		
		this.space_cargo_ship_id = getCargoShip_id(this.crew_id);
		
		updateCargoShip_state(this.space_cargo_ship_id, tableMetaData.cargo_ship_state_delivering);
		
		updateCargo_state(this.space_cargo_ship_id, tableMetaData.cargo_state_delivery);

		
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
	


	//TODO update log 
	private void updateDelivery_log(String space_cargo_ship_id){
		String SQLFormat = "select cargo_id from shipped_cargo  where cargo_ship_id= '%s'";
		String SQLStatement = String.format(SQLFormat, space_cargo_ship_id);
		String SQLStatement2; 
		String SQLFormat2 ="select cargo.cargo_owner_ID, cargo.cargo_ship_id, cargo.weight,"
				+ " planet.할인및할증률 * cargo_ship.cost * cargo.weight as \"예상 비용\" "
				+ " from cargo, planet, cargo_ship "
				+ " where cargo.destination = planet_ID and cargo.cargo_ship_id = cargo_ship.cargo_ship_id"
				+ " and cargo.cargo_id = '%s' ";
		
		
		
		myDBQuery query = new myDBQuery();
		ArrayList< ArrayList< String > > result = query.getResultQuery(SQLStatement);
		
		ArrayList< ArrayList< String > > result2; 
		
		for(int i = 1; i<result.size(); i++){
			SQLFormat = "insert into delivery_log values('%s', '%s', '%s', %s , %s )";
			String cargo_id = result.get(i).get(0);
			
			SQLStatement2 = String.format(SQLFormat2, cargo_id);
			result2 = query.getResultQuery(SQLStatement2);
			
			SQLStatement = String.format(SQLFormat, 
					generateNewId("log"),
					result2.get(1).get(0),
					result2.get(1).get(1),
					result2.get(1).get(2),
					result2.get(1).get(3)
					);
			query.justQuery(SQLStatement);
		}		
	}
	
	//TODO update shipped_cargo
	private void updateShipped_cargo(String space_cargo_ship_id){
		String SQLStatement = String.format("delete from shipped_cargo "
				+ "where cargo_ship_id = '%s'",
				this.space_cargo_ship_id);
		
		myDBQuery query = new myDBQuery();
		query.justQuery(SQLStatement);
	}
	
	public void setCargoShipDeliveryComplete(HttpServletRequest request, HttpServletResponse response){
		String userId = request.getParameter("id");		
		this.space_cargo_ship_id = getCargoShip_id(this.crew_id);

		updateCargoShip_state(this.space_cargo_ship_id, tableMetaData.cargo_ship_state_delivery_complete);
		
		updateCargo_state(this.space_cargo_ship_id, tableMetaData.cargo_state_delivery_complete);
		
		updateDelivery_log(this.space_cargo_ship_id);
		
		updateShipped_cargo(this.space_cargo_ship_id);
		
		
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
	
	boolean isValid_requsetNecessary_item(myForm form){
		if (form.cargo_ship_id  == null){
			ValidFormMsg = "소속된 화물선이 없는 선원 ";
			return false;
		}
		if (form.cargo_ship_id.length() == 0 ){
			ValidFormMsg = "no cargo_ship_id";
			return false;
		}
		if (form.cargo_ship_id.length() > tableMetaData.MAXFEILDSIZE_cargo_ship_id ){
			ValidFormMsg = "too long cargo_ship_id";
			return false;
		}
		
		if (form.item_name.length() == 0 ){
			ValidFormMsg = "no item_name";
			return false;
		}
		if (form.item_name.length() > tableMetaData.MAXFEILDSIZE_item_name ){
			ValidFormMsg = "too long item_name";
			return false;
		}
		
		if (form.amount.length() == 0 ){
			ValidFormMsg = "no amount";
			return false;
		}
		
		try {
			Float.parseFloat(form.amount);
		} catch (Exception e) {
			ValidFormMsg = "amount 숫자 아님";
			return false;
		}
		
		return true;
	}
	public void requsetNecessary_item(HttpServletRequest request, HttpServletResponse response){
		String userId = request.getParameter("id");
		myForm form = new myForm(request);
		form.cargo_ship_id = this.getCargoShip_id(userId);
		
		if (isValid_requsetNecessary_item(form)){
			String SQLStatement = String.format("insert into necessary_item values('%s', '%s', %s)",
					form.cargo_ship_id,
					form.item_name,
					form.amount);
			
			this.space_cargo_ship_id = getCargoShip_id(this.crew_id);
			myDBQuery q = new myDBQuery();
			q.justQuery(SQLStatement);
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
	
	
	
	
	
}
	

	
	
	












