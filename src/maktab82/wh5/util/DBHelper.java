package maktab82.wh5.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBHelper {
    public final static String URL = "jdbc:postgresql://127.0.0.1:5432/myDB";
    public final static String USER = "postgres";
    public final static String PASSWORD = "110110";

    private Connection connection;

    public Connection getConnection() {

        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
