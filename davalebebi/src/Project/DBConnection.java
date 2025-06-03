package Project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String url = "jdbc:sqlserver://DESKTOP-U7HF9BH;databaseName=Gayidvebi;encrypt=true;trustServerCertificate=true";
    private static final String user = "sa";
    private static final String password = "Wadi1@.,";

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(url, user, password);
            
        } catch (SQLException e) {
            System.out.println("კავშირის შეცდომა: " + e.getMessage());
            return null; 
        }
    }
}
