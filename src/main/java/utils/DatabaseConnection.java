package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase para connectar el base de datos
 * @author zhihan
 */
public class DatabaseConnection {
    private static final String JDBC_URL = "jdbc:derby://localhost:1527/isdcm";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASSWORD = "root";

    static {
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al cargar el driver JDBC");
        }
    }
    
    /**
     * Connectar la base de datos
     * @return Connexión
     * @throws SQLException Error de base de datos
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
    }
}
