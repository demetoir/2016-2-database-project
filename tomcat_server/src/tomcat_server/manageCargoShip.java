package tomcat_server;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class manageCargoShip {
	String ValidFormMsg = null;
	String nextPageURL = "/manageCargoShip.jsp";
	String TableName = "cargo_ship";
	TableMetaData tableMetaData = new TableMetaData();
	
	private String generateNewId(cargoShipRegisterForm form) {
		
		return form.tuple.cargo_ship_name +"_"+ System.currentTimeMillis();
	}
	
	
	public String printFailMsg(HttpServletRequest request){
		String msg = (String) request.getAttribute("failmsg") ;
		if (msg == null) msg = "";
		return msg;
	}

	public class cargoShipTuple {
		String cargo_ship_id;
		String cargo_ship_name;
		String cargo_ship_grade;
		String cargo_ship_state;
		String cargo_ship_destination;		
		String capacity;
		String velocity;
		String max_flight_distance;
		String cost;
	}
	
	public class cargoShipRegisterForm{
		cargoShipTuple tuple = new cargoShipTuple();
		cargoShipRegisterForm(HttpServletRequest request){
			this.tuple.cargo_ship_id = null;
			this.tuple.cargo_ship_name = request.getParameter("cargo_ship_name");
			this.tuple.cargo_ship_grade = request.getParameter("cargo_ship_grade");
			this.tuple.cargo_ship_state = tableMetaData.cargo_ship_state_ready;	
			this.tuple.capacity = request.getParameter("capacity");
			this.tuple.velocity = request.getParameter("velocity");
			this.tuple.max_flight_distance = request.getParameter("max_flight_distance");
			this.tuple.cost = request.getParameter("cost");
		}
	}
	
	public class cargoShipDeleteValueForm{
		cargoShipTuple tuple = new cargoShipTuple();
		cargoShipDeleteValueForm(HttpServletRequest request){
			this.tuple.cargo_ship_id = request.getParameter("cargo_ship_id");
		}		
	}
	
	public class cargoShipUpdateValueForm{
		cargoShipTuple tuple = new cargoShipTuple();
		
		cargoShipUpdateValueForm(HttpServletRequest request){
			this.tuple.cargo_ship_id = request.getParameter("cargo_ship_id");
			this.tuple.cargo_ship_name = request.getParameter("cargo_ship_name");
			this.tuple.cargo_ship_grade = request.getParameter("cargo_ship_grade");
			this.tuple.cargo_ship_state = request.getParameter("cargo_ship_state");	
			this.tuple.capacity = request.getParameter("capacity");
			this.tuple.velocity = request.getParameter("velocity");
			this.tuple.max_flight_distance = request.getParameter("max_flight_distance");
			this.tuple.cost = request.getParameter("cost");
		}
	}
	
	public class cargoShipSearchForm{
		String searchKey;
		String optionName;
		cargoShipSearchForm(HttpServletRequest request){
			this.searchKey = request.getParameter("search key");
			this.optionName = request.getParameter("search option");
		}
	}
	
	
	ArrayList<cargoShipTuple> tupleList;
	ArrayList<cargoShipTuple> searchList;
	
	private String getSQLStatement_select_all() {
		String ret = String.format("select * from %s",this.TableName);
		return ret;
	}
	private String getSQLStatement_insert_value(cargoShipRegisterForm form){
		String SQLFormat = "insert into %s values( "
				+ "'%s', "
				+ "'%s', "
				+ "'%s', "
				
				+ "%s, "
				+ "%s, "
				+ "%s, "
				
				+ "'%s', "
				+ "%s, "
				
				+ "%s)";
		String ret = String.format(SQLFormat,
				this.TableName,
				form.tuple.cargo_ship_id, 
				form.tuple.cargo_ship_name, 
				form.tuple.cargo_ship_grade, 
				
				form.tuple.capacity, 
				form.tuple.velocity, 
				form.tuple.max_flight_distance, 
				tableMetaData.cargo_ship_state_ready,
				"null", 
				form.tuple.cost 
				);
		return ret;
	}
	private String getSQLStatement_delete(cargoShipDeleteValueForm form){
		String SQLFormat = "delete from %s where %s='%s'";
		String ret = String.format(SQLFormat,
				this.TableName,
				"cargo_ship_id",
				form.tuple.cargo_ship_id
				);
		
		return ret;
	}
	
	private String makeUpdateSetClause(cargoShipUpdateValueForm form){
		String cargo_ship_name = String.format("cargo_ship_name = '%s'", form.tuple.cargo_ship_name );
		String cargo_ship_grade = String.format("cargo_ship_grade = '%s'", form.tuple.cargo_ship_grade );
		String cargo_ship_state = String.format("cargo_ship_state = '%s'", form.tuple.cargo_ship_state );
		String capacity = String.format("capacity = %s", form.tuple.capacity);
		String velocity = String.format("velocity = %s", form.tuple.velocity);
		String max_flight_distance = String.format("max_flight_distance = %s", form.tuple.max_flight_distance);	
		String cost = String.format("cost = %s ", form.tuple.cost);
		
		ArrayList<String> setClauseList = new ArrayList<String>();
		
		if (form.tuple.cargo_ship_name.length() != 0 ){
			setClauseList.add(cargo_ship_name);		
		}
		if(form.tuple.cargo_ship_grade.length() != 0){
			setClauseList.add(cargo_ship_grade);
		}
		if (form.tuple.cargo_ship_state.length() != 0){
			setClauseList.add(cargo_ship_state);
		}
		if(form.tuple.capacity.length() !=0){
			setClauseList.add(capacity);
		}
		if(form.tuple.velocity.length() != 0 ){
			setClauseList.add(velocity);
		}
		if(form.tuple.max_flight_distance.length() !=0 ){
			setClauseList.add(max_flight_distance);
		}
		if(form.tuple.cost.length() !=0){
			setClauseList.add(cost);
		}
		
		String setClause = setClauseList.get(0);
		for(int i = 1 ; i < setClauseList.size(); i++){
			setClause += ", "+ setClauseList.get(i); 	
		}
		
		return setClause;
	}
	private String makeUpdateWhereCaluse(cargoShipUpdateValueForm form){
		return String.format("cargo_ship_id = '%s'", form.tuple.cargo_ship_id );
	}
	private String getSQLStatement_changeValue(cargoShipUpdateValueForm form) {
		String SQLFormat = "update %s set %s where %s";
		
		String whereClause = makeUpdateWhereCaluse(form);
		String setClause = makeUpdateSetClause(form);
		
		String ret = String.format(SQLFormat, this.TableName, setClause, whereClause);
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
	
	private boolean isValidRegisterForm(cargoShipRegisterForm form) {
		ValidFormMsg = null;
		
		if (form.tuple.cargo_ship_name.length() > 50) {
			ValidFormMsg = "Too Long cargo_ship_name";
			return false;
		}
		else if (form.tuple.cargo_ship_name.length() == 0){
			ValidFormMsg = "no cargo_ship_name";
			return false;
		}
		
		if (form.tuple.cargo_ship_grade.length() > 50) {
			ValidFormMsg = "Too Long cargo_ship_grade";
			return false;
		}
		else if(form.tuple.cargo_ship_grade.length() == 0){
			ValidFormMsg = "no cargo_ship_grade";
			return false;
		}
		
		try {
			Float.parseFloat(form.tuple.capacity);
		} catch (Exception e) {
			ValidFormMsg = "capacity는 숫자가 아님";
			return false;
		}
		if(form.tuple.capacity.length() == 0){
			ValidFormMsg = "no capacity";
			return false;
		}
		
		try {
			Float.parseFloat(form.tuple.velocity);
		} catch (Exception e) {
			ValidFormMsg = "velocity는 숫자가 아님";
			return false;
		}
		if(form.tuple.velocity.length() == 0){
			ValidFormMsg = "no velocity";
			return false;
		}
		
		try {
			Float.parseFloat(form.tuple.max_flight_distance);
		} catch (Exception e) {
			ValidFormMsg = "max_flight_distance는 숫자가 아님";
			return false;
		}
		if(form.tuple.max_flight_distance.length() == 0){
			ValidFormMsg = "no max_flight_distance";
			return false;
		}
		
		if (form.tuple.cargo_ship_state.length() > 20){
			ValidFormMsg = "Too Long cargo_ship_grade";
			return false;
		}
		else if(form.tuple.cargo_ship_state.length() == 0){
			ValidFormMsg = "no cargo_ship_grade";
			return false;
		}
					
		try {
			Float.parseFloat(form.tuple.cost);
		} catch (Exception e) {
			ValidFormMsg = "cost는 숫자가 아님";
			return false;
		}
		if(form.tuple.cost.length() == 0){
			ValidFormMsg = "no cost";
			return false;
		}
		return true;
	}
	
	private boolean isValiedDeleteValueForm( cargoShipDeleteValueForm form ){
		boolean ret = true;
		this.ValidFormMsg = null;
		if (form.tuple.cargo_ship_id.length() > 50){
			ValidFormMsg = "too long planet id";
			ret = false;
		}
		else if (form.tuple.cargo_ship_id.length() == 50){
			ValidFormMsg = "no planet id";
			ret = false;
		}
		
		return ret;
	}
	
	private boolean isValiedUpdateValueForm( cargoShipUpdateValueForm form){
		boolean isChanged = false;
		
		if (form.tuple.cargo_ship_id.length() != 0){
			if (form.tuple.cargo_ship_id.length() > 40){
				ValidFormMsg = "Too Long cargo_ship_id";
				return false;
			}
		}
		else if(form.tuple.cargo_ship_id.length() == 0){
			ValidFormMsg = "cargo_ship_id 입력 되지않음";
			return false;
		}
		
		if(form.tuple.cargo_ship_name.length() != 0){
			isChanged = true;
			if (form.tuple.cargo_ship_name.length() > 40) {
				ValidFormMsg = "Too Long cargo_ship_name";
				return false;
			}
		}
		
		if(form.tuple.cargo_ship_grade.length() != 0){
			isChanged = true;
			if (form.tuple.cargo_ship_grade.length() > 40) {
				ValidFormMsg = "Too Long Planet Type";
				return false;
			}
		}
		
		if(form.tuple.capacity.length() != 0){
			try {
				Float.parseFloat(form.tuple.capacity);
			} catch (Exception e) {
				ValidFormMsg = "capacity는 숫자가 아님";
				return false;
			}
			isChanged = true;
		}
		
		if(form.tuple.velocity.length() != 0){
			try {
				Float.parseFloat(form.tuple.velocity);
			} catch (Exception e) {
				ValidFormMsg = "velocity는 숫자가 아님";
				return false;
			}
			isChanged = true;
		}
		if(form.tuple.max_flight_distance.length() != 0){
			isChanged = true;
			try {
				Float.parseFloat(form.tuple.max_flight_distance);
			} catch (Exception e) {
				ValidFormMsg = "max_flight_distance는 숫자가 아님";
				return false;
			}
		}
		
		if(form.tuple.cargo_ship_state.length() !=0){
			isChanged = true;
			if (form.tuple.cargo_ship_state.length() > 20){
				ValidFormMsg = "Too long cargo_ship_state";
				return false;
			}			
		}
		
		if(form.tuple.cost.length() != 0){
			try {
				Float.parseFloat(form.tuple.cost);
			} catch (Exception e) {
				ValidFormMsg = "cost은 숫자가 아님";
				return false;
			}
			isChanged = true;
		}
		
		if(isChanged == true)
			return true;
		else 
			return false;
	}
	
//	create table cargo_ship(
//	cargo_ship_id varchar(50),
//	cargo_ship_name varchar(50),
//	cargo_ship_grade varchar(20),
//	capacity numeric(10,2),
//	velocity numeric(10,2),
//	max_flight_distance numeric(10,2),
//	cargo_ship_state varchar(20),
//	cargo_ship_destination varchar(40),
//	cost numeric(10,2),
//	primary key (cargo_ship_id),
//	foreign key(cargo_ship_destination) references planet
//	on delete set null
	//)
	//;		
	
	private boolean isValidSearchFrom( cargoShipSearchForm form){
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
		cargoShipRegisterForm form = new cargoShipRegisterForm(request);
		
		if (isValidRegisterForm(form) == true) {
			form.tuple.cargo_ship_id = generateNewId(form);
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
	
	public void delete(HttpServletRequest request, HttpServletResponse response){
		String userId = request.getParameter("id");		
		cargoShipDeleteValueForm form = new cargoShipDeleteValueForm(request);

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
	
	public void search(HttpServletRequest request, HttpServletResponse response){
		this.nextPageURL = "/manageCargoShip.jsp";
		String userId = request.getParameter("id");		
		cargoShipSearchForm form = new cargoShipSearchForm(request);
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
		cargoShipUpdateValueForm form = new cargoShipUpdateValueForm(request);

		if (isValiedUpdateValueForm(form) == true) {
			String SQLStatement = getSQLStatement_changeValue(form);
			
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

	

	
	
	private ArrayList<String> getCargoShipList(){
		ArrayList<String> cargoShipList = new ArrayList<String>();
		String SQLFormat = "select %s from %s where %s";
		String selectClause = "cargo_ship_id";
		String fromClause = this.TableName;
		String whereClause = "cargo_ship_state = 'ready' ";
		String SQLStatement = String.format(SQLFormat, selectClause, fromClause, whereClause);
		
		myDBQuery query = new myDBQuery();
		ArrayList< ArrayList<String> >  result = query.getResultQuery(SQLStatement);
		
		for(int i = 1; i<result.size(); i++){
			cargoShipList.add(result.get(i).get(0));
		}
		return cargoShipList;
	}
	public String printSelectOption_cargoShip(){
		String SelectOptionFormat = " <select name = \"%s\">  %s </select> ";
		String selectOptionName = "cargo_ship_id";
		String strOption = "";
		
		ArrayList<String> optionList  = getCargoShipList();
		
		for (int i =0; i< optionList.size(); i++){
			String optionFormat = String.format("<option> %s </option>", optionList.get(i));
			strOption += optionFormat;
		}
		
		String ret = String.format(SelectOptionFormat, selectOptionName, strOption); 
		
		return ret;
	}
		
	
	private ArrayList<String> getDestinaionList(){
		ArrayList<String> cargoShipList = new ArrayList<String>();
		String SQLFormat = "select %s from %s";
		String selectClause = "planet_id";
		String FromClause = "planet";
		String SQLStatement = String.format(SQLFormat, selectClause, FromClause);
		myDBQuery query = new myDBQuery();
		ArrayList< ArrayList<String> >  result = query.getResultQuery(SQLStatement);
		
		for(int i=1; i<result.size(); i++){
			cargoShipList.add(result.get(i).get(0));
		}
		return cargoShipList;
	}
	public String printSelectOption_destination(){
		String SelectTagFormat = "<select name = \"%s\">  %s </select>";
		String selectTagName = "cargo_ship_destination";
		String strOptionTag = "";
		
		ArrayList<String> optionList  = getDestinaionList();
		
		for (int i =0; i< optionList.size(); i++){
			String optionFormat = String.format("<option> %s </option>", optionList.get(i));
			strOptionTag += optionFormat;
		}
		
		String ret = String.format(SelectTagFormat, selectTagName, strOptionTag); 
		
		return ret;
	}

	

	public class updateCargoShipDestinationForm{
		cargoShipTuple tuple = new cargoShipTuple();
		updateCargoShipDestinationForm(HttpServletRequest request){
			this.tuple.cargo_ship_id = request.getParameter("cargo_ship_id");
			this.tuple.cargo_ship_destination = request.getParameter("cargo_ship_destination");
		}
	}
	private float getMax_flight_distance(String cargo_ship_id){
		float ret  = 0;
		String SQLFormat = "select max_flight_distance from cargo_ship where cargo_ship_id = '%s'";
		String str_sql = String.format(SQLFormat, cargo_ship_id);
		
		myDBQuery query = new myDBQuery();
		ArrayList< ArrayList<String> > result = query.getResultQuery(str_sql);
		String temp = result.get(1).get(0);
		ret = Float.parseFloat(temp);
		
		return ret;
	}
	private float getPlanet_distance(String planet_ID){
		float ret  = 0;
		String SQLFormat = "select distance from planet where planet_ID = '%s'";
		String str_sql = String.format(SQLFormat, planet_ID);
		
		myDBQuery query = new myDBQuery();
		ArrayList< ArrayList<String> > result = query.getResultQuery(str_sql);
		
		String temp = result.get(1).get(0);
		ret = Float.parseFloat(temp);
		
		return ret;
	}
	
	private String getSQLStatement_updateCargoShipDestination(updateCargoShipDestinationForm form){
		String SQLFormat = "update %s set %s where %s";
		String ret = null;
		
		String setClause = String.format("%s = '%s' ", "cargo_ship_destination" , form.tuple.cargo_ship_destination);
		String setWhereClause = String.format("%s = '%s'", "cargo_ship_id", form.tuple.cargo_ship_id);
		ret = String.format(SQLFormat, this.TableName, setClause, setWhereClause );
		
		return ret;
	}
	
	private boolean isValidupdateCargoShipDestinationForm(updateCargoShipDestinationForm form){
		float Max_flight_distance = getMax_flight_distance(form.tuple.cargo_ship_id);
		float planet_distance = getPlanet_distance(form.tuple.cargo_ship_destination);
		if (Max_flight_distance < planet_distance) {
			this.ValidFormMsg = " planet_distance is bigger than Max_flight_distance";
			return false;
		}

		return true;
	}
	
	public void updateCargoshipDestination(HttpServletRequest request, HttpServletResponse response){
		String userId = request.getParameter("id");		
		updateCargoShipDestinationForm form = new updateCargoShipDestinationForm(request);
		
		if(isValidupdateCargoShipDestinationForm(form)){
			String SQLStatement = getSQLStatement_updateCargoShipDestination(form);
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
		
}
	

	
	
	

