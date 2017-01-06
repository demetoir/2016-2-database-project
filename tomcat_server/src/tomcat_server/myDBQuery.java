package tomcat_server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;

public class myDBQuery {
	
	public ArrayList< ArrayList<String> > getResultQuery(String SQLStatement){
		ArrayList< ArrayList<String> > resultList = new ArrayList< ArrayList<String> >();
		DB_data db_data = new DB_data();
		ResultSet res = null;
		ResultSetMetaData resultMeta = null;
		
		try {
			Connection con = DriverManager.getConnection(
					db_data.getDB_URL(), 
					db_data.getDB_ID(),
					db_data.getDB_PASSWORD());
			Statement stmt = con.createStatement();
			
			res = stmt.executeQuery(SQLStatement);
			
			resultMeta = res.getMetaData();
			
			ArrayList<String>metaTuple = new ArrayList<String>();
			int colSize = resultMeta.getColumnCount();
			for(int i =1; i<=colSize; i++){
				metaTuple.add(resultMeta.getColumnName(i));
			}
			resultList.add(metaTuple);
			
			
			while (res.next()){
				ArrayList<String> tuple = new ArrayList<String>();
				for(int i =1; i<= colSize ; i++){
					tuple.add(res.getString(i));
				}
				resultList.add(tuple);
			}
			
			stmt.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultList;
	}
	
	public void justQuery(String SQLStatement){
		DB_data db_data = new DB_data();
		try {
			Connection con = DriverManager.getConnection(
					db_data.getDB_URL(), 
					db_data.getDB_ID(),
					db_data.getDB_PASSWORD());
			Statement stmt = con.createStatement();
			
			stmt.executeQuery(SQLStatement);

			stmt.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
