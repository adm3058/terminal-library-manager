package pl.adamciesla.zad1.ui;
import pl.adamciesla.zad1.model.Book;
import pl.adamciesla.zad1.model.Role;
import pl.adamciesla.zad1.model.User;
import pl.adamciesla.zad1.service.BookService;
import pl.adamciesla.zad1.service.LoginService;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class ConsoleUI {

    private LoginService loginService;
    private BookService bookService;
    private Scanner scanner = new Scanner(System.in);

    public ConsoleUI(LoginService loginService, BookService bookService) {
        this.loginService = loginService;
        this.bookService = bookService;
    }

    public void start() {
        System.out.println("Menedżer Biblioteki");

        User user = loginLoop();
        System.out.println("Zalogowano jako: " + user.getUsername());

        if (user.getRole() == Role.ADMIN) {
            adminMenu();
        } else {
            userMenu();
        }
    }

    private User loginLoop() {
        while (true) {
            System.out.print("Login: ");
            String username = scanner.nextLine();

            System.out.print("Hasło: ");
            String password = scanner.nextLine();

            Optional<User> userOptional = loginService.login(username, password);

            if (userOptional.isPresent()) {
                return userOptional.get();
            }

            System.out.println("Błędny login lub hasło.\n");
        }
    }

    private void userMenu() {
        while (true) {
            System.out.println("\n MENU USER");
            System.out.println("1. Lista książek");
            System.out.println("2. Szukaj po tytule");
            System.out.println("3. Szukaj po autorze");
            System.out.println("0. Wyjście");
            System.out.print("Wybór: ");

            String choice = scanner.nextLine();

            if (choice.equals("1")) {
                printBooks(bookService.listAll());
            } else if (choice.equals("2")) {
                System.out.print("Podaj fragment tytułu: ");
                String title = scanner.nextLine();
                printBooks(bookService.searchByTitle(title));
            } else if (choice.equals("3")) {
                System.out.print("Podaj fragment autora: ");
                String author = scanner.nextLine();
                printBooks(bookService.searchByAuthor(author));
            } else if (choice.equals("0")) {
                return;
            } else {
                System.out.println("Nieznana opcja.");
            }
        }
    }

    private void adminMenu() {
        while (true) {
            System.out.println("\n MENU ADMIN");
            System.out.println("1. Lista książek");
            System.out.println("2. Szukaj po tytule");
            System.out.println("3. Szukaj po autorze");
            System.out.println("4. Dodaj książkę");
            System.out.println("5. Usuń książkę");
            System.out.println("6. Edytuj książkę");
            System.out.println("0. Wyjście");
            System.out.print("Wybór: ");

            String choice = scanner.nextLine();

            if (choice.equals("1")) {
                printBooks(bookService.listAll());
            } else if (choice.equals("2")) {
                System.out.print("Podaj fragment tytułu: ");
                String title = scanner.nextLine();
                printBooks(bookService.searchByTitle(title));
            } else if (choice.equals("3")) {
                System.out.print("Podaj fragment autora: ");
                String author = scanner.nextLine();
                printBooks(bookService.searchByAuthor(author));
            } else if (choice.equals("4")) {
                addBookFlow();
            } else if (choice.equals("5")) {
                removeBookFlow();
            } else if (choice.equals("6")) {
                editBookFlow();
            } else if (choice.equals("0")) {
                return;
            } else {
                System.out.println("Nieznana opcja.");
            }
        }
    }

    private void addBookFlow() {
        System.out.print("Tytuł: ");
        String title = scanner.nextLine();

        System.out.print("Autor: ");
        String author = scanner.nextLine();

        Book added = bookService.add(title, author);
        System.out.println("Dodano: " + added.getId());
    }

    private void removeBookFlow() {
        System.out.print("Podaj ID do usunięcia: ");
        long id = parseLong(scanner.nextLine());

        if (id == -1) {
            System.out.println("Złe ID.");
            return;
        }

        boolean removed = bookService.remove(id);
        if (removed) {
            System.out.println("Usunięto.");
        } else {
            System.out.println("Nie znaleziono książki.");
        }
    }

    private void editBookFlow() {
        System.out.print("Podaj ID do edycji: ");
        long id = parseLong(scanner.nextLine());

        if (id == -1) {
            System.out.println("Złe ID.");
            return;
        }

        System.out.print("Nowy tytuł: ");
        String newTitle = scanner.nextLine();

        System.out.print("Nowy autor: ");
        String newAuthor = scanner.nextLine();

        Optional<Book> edited = bookService.edit(id, newTitle, newAuthor);

        if (edited.isPresent()) {
            System.out.println("Zmieniono.");
        } else {
            System.out.println("Nie znaleziono książki.");
        }
    }

    private void printBooks(List<Book> books) {
        if (books.isEmpty()) {
            System.out.println("(brak wyników)");
            return;
        }

        System.out.println("\n ID|TYTUŁ|AUTOR");
        for (Book book : books) {
            System.out.println(book.getId() + " | " + book.getTitle() + " | " + book.getAuthor());
        }
    }

    private long parseLong(String text) {
        try {
            return Long.parseLong(text.trim());
        } catch (Exception e) {
            return -1;
        }
    }
}