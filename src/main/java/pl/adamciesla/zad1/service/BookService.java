package pl.adamciesla.zad1.service;
import pl.adamciesla.zad1.model.Book;
import java.util.List;
import java.util.Optional;
public interface BookService {

    List<Book> listAll();

    List<Book> searchByTitle(String title);

    List<Book> searchByAuthor(String author);

    Book add(String title, String author);

    boolean remove(long id);

    Optional<Book> edit(long id, String newTitle, String newAuthor);

    List<Book> searchByPublishYear(int year);

    List<Book> listAllSortedByTitle();
}
