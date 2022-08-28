package util;

import java.sql.Connection;
import java.util.Scanner;

public class ApplicationConstant {
    private static Connection connection = new DBHelper().getConnection();
    private static Scanner scanner = new Scanner(System.in);
    public static Scanner getScanner() {
        return scanner;
    }
    public static Connection getConnection() {
        return connection;
    }
}
