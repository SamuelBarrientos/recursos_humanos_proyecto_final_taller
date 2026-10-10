
package main.java.com.magcorporation.kinal.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static Connection connection;

    private DatabaseConnection() {}

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(Credentials.URL, Credentials.USER, Credentials.PASSWORD);
            } catch (ClassNotFoundException e) {
                throw new SQLException("mysql-connector-j driver no encontrado", e);
            }
        }
        return connection;
    }
}