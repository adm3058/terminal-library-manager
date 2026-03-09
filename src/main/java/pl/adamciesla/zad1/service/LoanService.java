package pl.adamciesla.zad1.service;
import pl.adamciesla.zad1.model.Loan;
import java.util.List;

public interface LoanService {

    boolean borrowBook(long userId, long bookId);

    List<Loan> getUserLoans(long userId);

    boolean returnBook(long userId, long bookId);
}
