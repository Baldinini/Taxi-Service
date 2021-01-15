package mate.academy.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import mate.academy.exception.DataProcessingException;

public class ConnectionUtil {
    private static final String ROOT = "root";
    private static final String PASSWORD = "1234";

    public static Connection getConnection() {
        Properties dbProperties = new Properties();
        dbProperties.put("user", ROOT);
        dbProperties.put("password", PASSWORD);
        String url = "jdbc:mysql://localhost:3306/taxi_service?serverTimezone=UTC";

        try {
            return DriverManager.getConnection(url, dbProperties);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't established the connection to DB", e);
        }
    }
}
