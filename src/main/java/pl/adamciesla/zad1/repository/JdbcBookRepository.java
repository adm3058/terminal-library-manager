package pl.adamciesla.zad1.repository;
import pl.adamciesla.zad1.config.DatabaseConfig;
import pl.adamciesla.zad1.model.Book;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcBookRepository implements BookRepository {

    @Override
    public List<Book> findAll() {
        List<Book> books = new ArrayList<>();

        String sql = "SELECT * FROM books";

        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {

                Book book = new Book(
                        resultSet.getLong("id"),
                        resultSet.getString("title"),
                        resultSet.getString("author"),
                        resultSet.getObject("publish_year", Integer.class),
                        resultSet.getString("isbn"),
                        resultSet.getBoolean("available")
                );

                books.add(book);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return books;
    }
    @Override
    public Optional<Book> findById(long id) {

        String sql = "SELECT * FROM books WHERE id = ?";

        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Book book = new Book(
                        resultSet.getLong("id"),
                        resultSet.getString("title"),
                        resultSet.getString("author"),
                        resultSet.getObject("publish_year", Integer.class),
                        resultSet.getString("isbn"),
                        resultSet.getBoolean("available")
                );

                return Optional.of(book);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return Optional.empty();
    }

    @Override
    public List<Book> findByTitleContains(String title) {
        List<Book> books = new ArrayList<>();

        String sql = "SELECT * FROM books WHERE LOWER(title) LIKE ?";

        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, "%" + title.toLowerCase() + "%");

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                Book book = new Book(
                        resultSet.getLong("id"),
                        resultSet.getString("title"),
                        resultSet.getString("author"),
                        resultSet.getObject("publish_year", Integer.class),
                        resultSet.getString("isbn"),
                        resultSet.getBoolean("available")
                );

                books.add(book);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return books;
    }

    @Override
    public List<Book> findByAuthorContains(String author) {
        List<Book> books = new ArrayList<>();

        String sql = "SELECT * FROM books WHERE LOWER(author) LIKE ?";

        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, "%" + author.toLowerCase() + "%");

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                Book book = new Book(
                        resultSet.getLong("id"),
                        resultSet.getString("title"),
                        resultSet.getString("author"),
                        resultSet.getObject("publish_year", Integer.class),
                        resultSet.getString("isbn"),
                        resultSet.getBoolean("available")
                );

                books.add(book);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return books;
    }

    @Override
    public Book save(Book book) {

        String sql = "INSERT INTO books(title, author, publish_year, isbn, available) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, book.getTitle());
            statement.setString(2, book.getAuthor());
            statement.setObject(3, book.getPublishYear());
            statement.setString(4, book.getIsbn());
            statement.setBoolean(5, book.isAvailable());

            statement.executeUpdate();

            ResultSet keys = statement.getGeneratedKeys();

            if (keys.next()) {
                book.setId(keys.getLong(1));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return book;
    }

    @Override
    public boolean deleteById(long id) {

        String sql = "DELETE FROM books WHERE id = ?";

        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public List<Book> findByPublishYear(int year) {
        List<Book> books = new ArrayList<>();

        String sql = "SELECT * FROM books WHERE publish_year = ?";

        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, year);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Book book = new Book(
                        resultSet.getLong("id"),
                        resultSet.getString("title"),
                        resultSet.getString("author"),
                        resultSet.getObject("publish_year", Integer.class),
                        resultSet.getString("isbn"),
                        resultSet.getBoolean("available")
                );

                books.add(book);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return books;
    }

    @Override
    public List<Book> findAllOrderByTitle() {
        List<Book> books = new ArrayList<>();

        String sql = "SELECT * FROM books ORDER BY title ASC";

        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Book book = new Book(
                        resultSet.getLong("id"),
                        resultSet.getString("title"),
                        resultSet.getString("author"),
                        resultSet.getObject("publish_year", Integer.class),
                        resultSet.getString("isbn"),
                        resultSet.getBoolean("available")
                );

                books.add(book);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return books;
    }
}