package pl.adamciesla.zad1.config;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class SchemaInitializer {
    public void initialize() {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("schema.sql");

        if (inputStream == null) {
            throw new RuntimeException("Nie znaleziono pliku schema");
        }

        StringBuilder sqlBuilder = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            String line;

            while ((line = reader.readLine()) != null) {
                sqlBuilder.append(line).append("\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String[] sqlStatements = sqlBuilder.toString().split(";");

        try (Connection connection = DatabaseConfig.getConnection();
             Statement statement = connection.createStatement()) {

            for (String sql : sqlStatements) {
                if (!sql.trim().isEmpty()) {
                    statement.execute(sql);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
