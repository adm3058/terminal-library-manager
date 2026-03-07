package pl.adamciesla.zad1.repository;
import pl.adamciesla.zad1.model.Book;
import java.util.List;
import java.util.Optional;


public interface BookRepository {

    List<Book> findAll();

    Optional<Book> findById(long id);

    List<Book> findByTitleContains(String title);

    List<Book> findByAuthorContains(String author);

    Book save(Book book);

    boolean deleteById(long id);

    List<Book> findByPublishYear(int year);

    List<Book> findAllOrderByTitle();
}
