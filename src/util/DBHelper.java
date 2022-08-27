package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBHelper {
    private static final String DB_URL = "jdbc:postgresql://localhost/Maktab";
    private static final String USER = "postgres";
    private static final String PASS = "zara12";

    private Connection connection;

    public Connection getConnection() {

        try {
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
