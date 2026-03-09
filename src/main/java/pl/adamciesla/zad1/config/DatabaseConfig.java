package pl.adamciesla.zad1.config;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;


public class DatabaseConfig {

    private static final String URL = "jdbc:h2:./librarydb";
    private static final String USER = "sa";
    private static final String PASSWORD = "";

    public static Connection getConnection() throws SQLException{
        return DriverManager.getConnection(URL,USER,PASSWORD);
    }
}
