package pl.adamciesla.zad1.app;
import pl.adamciesla.zad1.repository.BookRepository;
import pl.adamciesla.zad1.repository.JdbcBookRepository;
import pl.adamciesla.zad1.repository.JdbcUserRepository;
import pl.adamciesla.zad1.repository.UserRepository;
import pl.adamciesla.zad1.security.BCryptPasswordHasher;
import pl.adamciesla.zad1.security.PasswordHasher;
import pl.adamciesla.zad1.service.BookService;
import pl.adamciesla.zad1.service.BookServiceImpl;
import pl.adamciesla.zad1.service.LoginService;
import pl.adamciesla.zad1.service.LoginServiceImpl;
import pl.adamciesla.zad1.ui.ConsoleUI;
import pl.adamciesla.zad1.config.SchemaInitializer;
import pl.adamciesla.zad1.repository.LoanRepository;
import pl.adamciesla.zad1.repository.JdbcLoanRepository;
import pl.adamciesla.zad1.service.LoanService;
import pl.adamciesla.zad1.service.LoanServiceImpl;
import pl.adamciesla.zad1.service.StatsService;
import pl.adamciesla.zad1.service.StatsServiceImpl;

public class Main {

    public static void main(String[] args) {

        SchemaInitializer schemaInitializer = new SchemaInitializer();
        schemaInitializer.initialize();

        PasswordHasher passwordHasher = new BCryptPasswordHasher();

        UserRepository userRepository = new JdbcUserRepository();
        BookRepository bookRepository = new JdbcBookRepository();
        LoanRepository loanRepository = new JdbcLoanRepository();

        LoginService loginService = new LoginServiceImpl(userRepository, passwordHasher);
        BookService bookService = new BookServiceImpl(bookRepository);
        LoanService loanService = new LoanServiceImpl(loanRepository,bookRepository);
        StatsService statsService = new StatsServiceImpl();

        ConsoleUI consoleUI = new ConsoleUI(loginService, bookService,loanService,statsService);
        consoleUI.start();
    }
}
