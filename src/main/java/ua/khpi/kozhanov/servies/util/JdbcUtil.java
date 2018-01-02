package ua.khpi.kozhanov.servies.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by PC on 09.12.2017.
 */

public class JdbcUtil {

    private static final String DRIVER = "com.mysql.jdbc.Driver";
    private static final String URL_AMENDED = "jdbc:mysql://mysql:3306/internet_shop?serverTimezone=UTC&useSSL=false";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    private static Connection connection = null;

    private JdbcUtil() {
    }

    public static Connection getConnection(){

        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(URL_AMENDED, USER, PASSWORD);

        }catch(SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("ERROR FROM JDBC METHOD");
            e.printStackTrace();
        }
        return connection;
    }
}
