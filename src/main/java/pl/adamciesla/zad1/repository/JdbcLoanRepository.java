package pl.adamciesla.zad1.repository;
import pl.adamciesla.zad1.config.DatabaseConfig;
import pl.adamciesla.zad1.model.Loan;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcLoanRepository implements LoanRepository {

    @Override
    public Loan save(Loan loan) {

        String sql = "INSERT INTO loans(user_id, book_id, loan_date, return_date) VALUES (?, ?, ?, ?)";

        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setLong(1, loan.getUserId());
            statement.setLong(2, loan.getBookId());
            statement.setDate(3, Date.valueOf(loan.getLoanDate()));
            statement.setDate(4, loan.getReturnDate() != null ? Date.valueOf(loan.getReturnDate()) : null);

            statement.executeUpdate();

            ResultSet keys = statement.getGeneratedKeys();

            if (keys.next()) {
                loan.setId(keys.getLong(1));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return loan;
    }

    @Override
    public List<Loan> findByUserId(long userId) {

        List<Loan> loans = new ArrayList<>();

        String sql = "SELECT * FROM loans WHERE user_id = ?";

        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, userId);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                Loan loan = new Loan(
                        resultSet.getLong("id"),
                        resultSet.getLong("user_id"),
                        resultSet.getLong("book_id"),
                        resultSet.getDate("loan_date").toLocalDate(),
                        resultSet.getDate("return_date") != null ? resultSet.getDate("return_date").toLocalDate() : null
                );

                loans.add(loan);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return loans;
    }

    @Override
    public Optional<Loan> findActiveLoanByBookId(long bookId) {

        String sql = "SELECT * FROM loans WHERE book_id = ? AND return_date IS NULL";

        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, bookId);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {

                Loan loan = new Loan(
                        resultSet.getLong("id"),
                        resultSet.getLong("user_id"),
                        resultSet.getLong("book_id"),
                        resultSet.getDate("loan_date").toLocalDate(),
                        null
                );

                return Optional.of(loan);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return Optional.empty();
    }

    @Override
    public Optional<Loan> findActiveLoanByUserIdAndBookId(long userId, long bookId) {

        String sql = "SELECT * FROM loans WHERE user_id = ? AND book_id = ? AND return_date IS NULL";

        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, userId);
            statement.setLong(2, bookId);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {

                Loan loan = new Loan(
                        resultSet.getLong("id"),
                        resultSet.getLong("user_id"),
                        resultSet.getLong("book_id"),
                        resultSet.getDate("loan_date").toLocalDate(),
                        null
                );

                return Optional.of(loan);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return Optional.empty();
    }

    @Override
    public boolean returnBook(long loanId) {

        String sql = "UPDATE loans SET return_date = ? WHERE id = ?";

        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setDate(1, Date.valueOf(LocalDate.now()));
            statement.setLong(2, loanId);

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}