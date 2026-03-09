package pl.adamciesla.zad1.repository;
import pl.adamciesla.zad1.model.Loan;
import java.util.List;
import java.util.Optional;

public interface LoanRepository {

    Loan save(Loan loan);

    List<Loan> findByUserId(long userId);

    Optional<Loan> findActiveLoanByBookId(long bookId);

    Optional<Loan> findActiveLoanByUserIdAndBookId(long userId, long bookId);

    boolean returnBook(long loanId);
}