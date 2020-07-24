package transaction.replicator.config.databases;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * JDBC Connection Class
 * It returns a connection object, after a driver class and credentials are supplied
 */
public class JDBCConnection {
    public static Connection getConnection(String url, String userName, String password, String driverClass) {
        Connection connection = null;
        try {
            Class.forName(driverClass);
            connection = DriverManager.getConnection(url, userName, password);
        } catch (Exception e) {
            System.out.println("Exception : " + e);
        }
        return connection;
    }
}
