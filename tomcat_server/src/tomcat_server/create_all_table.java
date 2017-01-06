package tomcat_server;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;


//import java.util.Scanner;

public class create_all_table {
	static String fileName = "SQL_statement_create_all_table";
	static String raw_create_table_statement = "";
	
	static void loadSQL_statement(){
        BufferedReader br = null;        
        InputStreamReader isr = null;    
        FileInputStream fis = null;        
        File file = new File(fileName);
        String line = "";
         
        try {
            fis = new FileInputStream(file);
            isr = new InputStreamReader(fis, "UTF-8");
            br = new BufferedReader(isr);
            while( (line = br.readLine()) != null) {
            	raw_create_table_statement += line + "\n";
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
		try{
			Connection con = DriverManager.getConnection(db_data.getDB_URL(), db_data.getDB_ID(),db_data.getDB_PASSWORD());
			Statement stmt = con.createStatement();		
			
			System.out.println(raw_create_table_statement);
			stmt.executeUpdate(raw_create_table_statement);	
			System.out.println("success create table");
			
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
		create_table();
		
		return; 
	}

}


