package sample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static DBConnection instance = new DBConnection();

    private DBConnection(){

    }

    public static DBConnection getInstance(){
        return instance;
    }

    public static Connection getConnection() throws SQLException {

        String database =  "spotify";
        String userName = "root";
        String password = "";

        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3308/" + database + "?serverTimezone=America/New_York", userName, password);

        return conn;
    }
}
