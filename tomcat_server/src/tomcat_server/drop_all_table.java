package tomcat_server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;


public class drop_all_table {
	static String fileName = "SQL_statement_drop_all_table";
	static String raw_statement = "";
	static ArrayList<String> statementList = new ArrayList<String>();
	
	static void loadSQL_statement(){
        BufferedReader br = null;        
        InputStreamReader isr = null;    
        FileInputStream fis = null;        
        String line = "";
        String path = drop_all_table.class.getResource("").getPath();        
        System.out.println(path); 
        String filePath = path + "\\" + fileName;
        File file = new File(filePath);
        String tempLine = "";
        
        try {
            fis = new FileInputStream(file);
            isr = new InputStreamReader(fis, "UTF-8");
            br = new BufferedReader(isr);
            while( (line = br.readLine()) != null) {
            	if(line.equals(";")){
            		statementList.add(tempLine);
            	}
            	else {
            		tempLine += line + "\n";
            	}
            }
             
        } catch (FileNotFoundException e) {
            e.printStackTrace();
             
        } catch (Exception e) {
            e.printStackTrace();
             
        } finally {
 
            try {
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
             
            try {
                isr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
             
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
             
        }        
	}
	
	static boolean create_table(){		
		DB_data db_data = new DB_data();
		loadSQL_statement();
		try{
			Connection con = DriverManager.getConnection(db_data.getDB_URL(), db_data.getDB_ID(),db_data.getDB_PASSWORD());
			Statement stmt = con.createStatement();		
			
			for(int i =0; i<statementList.size(); i++ ){
				System.out.println(statementList.get(i));
				stmt.executeUpdate(statementList.get(i));	
				System.out.println("success drop table");
			}
			
			stmt.close();
			con.close();
		}
		catch(Exception e){
				e.printStackTrace();
				return false;
		}		
		return true;
	}
	
	public static void main(String[] args) {	
		
		
		return; 
	}

	
}
