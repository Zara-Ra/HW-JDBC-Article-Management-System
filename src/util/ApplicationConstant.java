package util;

import java.sql.Connection;

public class ApplicationConstant {

    private static Connection connection = new DBHelper().getConnection();

    public static Connection getConnection() {
        return connection;
    }
}


