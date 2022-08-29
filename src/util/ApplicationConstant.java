package util;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class ApplicationConstant {
    private static DBHelper dbHelper = new DBHelper();
    private static Connection connection = dbHelper.getConnection();
    private static Scanner scanner = new Scanner(System.in);
    public static Scanner getScanner() {
        return scanner;
    }
    public static Connection getConnection() {
        return connection;
    }
    public static void closeConnection(){
        dbHelper.closeConnection();
    }
}
