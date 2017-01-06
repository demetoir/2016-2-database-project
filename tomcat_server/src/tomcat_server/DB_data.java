package tomcat_server;
public class DB_data {
    private static String DB_URL = "jdbc:oracle:thin:@localhost:1521:orcl";
    private static String DB_ID = "system";
    private static String DB_PASSWORD = "1234QWer";
    public String getDB_URL(){
        return DB_URL;
    }

    public String  getDB_ID(){
        return DB_ID;
    }

    public String getDB_PASSWORD(){
        return DB_PASSWORD;
    }

}
