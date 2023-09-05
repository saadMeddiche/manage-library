// JDBC 
// Source : https://www.youtube.com/watch?v=3OrEsC-QjUA 
package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Db {

    public static Connection makeConnection() {

        String url = "jdbc:mysql://localhost:3306/library";
        String username = "root";
        String password = "";

        Connection connection = null;

        try {
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("Connection has been established !!");
        } catch (SQLException e) {
            System.out.println("Something went wrong with the connection !!");
        }

        return connection;
    }
}
