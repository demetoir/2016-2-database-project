package tomcat_server;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class statisticPage {

	String ValidFormMsg = null;
	String nextPageURL = "/cargoOwnerMainPage.jsp";
	String user_id = "";
	String CargoOwner_id = "";
	float credit = 0;
	
	private TableMetaData tableMetaData = new TableMetaData();  
	
	

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
	

	

	public String printTotalIncome(){
		String SQLStatement = "select sum(totalcost) from delivery_log "; 

		myDBQuery query = new myDBQuery();
		ArrayList< ArrayList<String> > result = query.getResultQuery(SQLStatement);
		String ret = result.get(1).get(0);
		
		return ret;
	}
	
	public String printStaticCargoOwner(){
		ArrayList< ArrayList<String> > table = new ArrayList< ArrayList<String> >(); 
		ArrayList<String>  list = new ArrayList<String>();
		list.add("È­ÁÖ id");
		list.add("ÃÑ ¿î¼Û·®");
		list.add("ÃÑ ÁöºÒ¾×");
		table.add(list);
		
		String SQLStatement = "select cargo_owner_ID from cargo_owner"; 
		myDBQuery query = new myDBQuery();
		ArrayList< ArrayList<String> > result = query.getResultQuery(SQLStatement);
		
		for(int i = 1; i <result.size(); i++){
			ArrayList< ArrayList<String> > result2;
			String SQLStatement2;
			String val;
			list = new ArrayList<String>();
			list.add( result.get(i).get(0) );
			
			SQLStatement2 = String.format("select sum(weight) from delivery_log where owner_id = '%s'",
					result.get(i).get(0));
			result2 = query.getResultQuery(SQLStatement2);
			if(result2.get(1).get(0) == null) val = "0";
			else val = result2.get(1).get(0);
			list.add( val);
			
			SQLStatement2 = String.format("select sum(totalcost) from delivery_log where owner_id = '%s'",
					result.get(i).get(0));
			result2 = query.getResultQuery(SQLStatement2);
			if(result2.get(1).get(0) == null) val = "0";
			else val = result2.get(1).get(0);
			list.add( val);
			
			table.add(list);
		}
		
		return printTable(table);
	}
	
	public String printStaticCargoShip(){
		ArrayList< ArrayList<String> > table = new ArrayList< ArrayList<String> >(); 
		ArrayList<String>  list = new ArrayList<String>();
		list.add("¼ö¼Û¼± id");
		list.add("ÃÑ ¿î¼Û·®");
		list.add("ÃÑ ¼öÀÔ");
		table.add(list);
		
		String SQLStatement = "select cargo_ship_id from cargo_ship"; 
		myDBQuery query = new myDBQuery();
		ArrayList< ArrayList<String> > result = query.getResultQuery(SQLStatement);
		
		for(int i = 1; i <result.size(); i++){
			ArrayList< ArrayList<String> > result2;
			String SQLStatement2;
			String val;
			list = new ArrayList<String>();
			list.add( result.get(i).get(0) );
			
			SQLStatement2 = String.format("select sum(weight) from delivery_log where cargo_ship_id = '%s'",
					result.get(i).get(0));
			result2 = query.getResultQuery(SQLStatement2);
			if(result2.get(1).get(0) == null) val = "0";
			else val = result2.get(1).get(0);
			list.add( val);
			
			SQLStatement2 = String.format("select sum(totalcost) from delivery_log where cargo_ship_id = '%s'",
					result.get(i).get(0));
			result2 = query.getResultQuery(SQLStatement2);
			if(result2.get(1).get(0) == null) val = "0";
			else val = result2.get(1).get(0);
			
			list.add( val);
			
			table.add(list);
		}
		
		return printTable(table);
	}
	
	
	public String printLog(){
		String SQLStatement = "select * from delivery_log";
		myDBQuery q = new myDBQuery();
		ArrayList< ArrayList<String> >  result = q.getResultQuery(SQLStatement);
		
		return printTable(result);
	}
	
}
	

		
		
		




