package pl.adamciesla.zad1.app;
import pl.adamciesla.zad1.repository.BookRepository;
import pl.adamciesla.zad1.repository.MemoryBookRepository;
import pl.adamciesla.zad1.repository.MemoryUserRepository;
import pl.adamciesla.zad1.repository.UserRepository;
import pl.adamciesla.zad1.security.BCryptPasswordHasher;
import pl.adamciesla.zad1.security.PasswordHasher;
import pl.adamciesla.zad1.service.BookService;
import pl.adamciesla.zad1.service.BookServiceImpl;
import pl.adamciesla.zad1.service.LoginService;
import pl.adamciesla.zad1.service.LoginServiceImpl;
import pl.adamciesla.zad1.ui.ConsoleUI;

public class Main {

    public static void main(String[] args) {

        PasswordHasher passwordHasher = new BCryptPasswordHasher();

        UserRepository userRepository = new MemoryUserRepository(passwordHasher);
        BookRepository bookRepository = new MemoryBookRepository();

        LoginService loginService = new LoginServiceImpl(userRepository, passwordHasher);
        BookService bookService = new BookServiceImpl(bookRepository);

        ConsoleUI consoleUI = new ConsoleUI(loginService, bookService);
        consoleUI.start();
    }
}
