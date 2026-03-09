package pl.adamciesla.zad1.service;
import pl.adamciesla.zad1.model.Book;
import pl.adamciesla.zad1.model.Loan;
import pl.adamciesla.zad1.repository.BookRepository;
import pl.adamciesla.zad1.repository.LoanRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class LoanServiceImpl implements LoanService {

    private LoanRepository loanRepository;
    private BookRepository bookRepository;

    public LoanServiceImpl(LoanRepository loanRepository, BookRepository bookRepository) {
        this.loanRepository = loanRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public boolean borrowBook(long userId, long bookId) {
        Optional<Book> bookOptional = bookRepository.findById(bookId);

        if (bookOptional.isEmpty()) {
            return false;
        }

        Optional<Loan> activeLoan = loanRepository.findActiveLoanByBookId(bookId);

        if (activeLoan.isPresent()) {
            return false;
        }

        Loan loan = new Loan(0, userId, bookId, LocalDate.now(), null);
        loanRepository.save(loan);

        return true;
    }

    @Override
    public List<Loan> getUserLoans(long userId) {
        return loanRepository.findByUserId(userId);
    }

    @Override
    public boolean returnBook(long userId, long bookId) {
        Optional<Loan> loanOptional = loanRepository.findActiveLoanByUserIdAndBookId(userId, bookId);

        if (loanOptional.isEmpty()) {
            return false;
        }
        return loanRepository.returnBook(loanOptional.get().getId());
    }
}