package pl.adamciesla.zad1.repository;
import pl.adamciesla.zad1.model.Book;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MemoryBookRepository implements BookRepository {

    private List<Book> books = new ArrayList<>();
    private long nextId = 1;

    @Override
    public List<Book> findAll() {
        return books;
    }
    @Override
    public Optional<Book> findById(long id) {
        for (Book book : books) {
            if (book.getId() == id) {
                return Optional.of(book);
            }
        }
        return Optional.empty();
    }
    @Override
    public List<Book> findByTitleContains(String title) {
        List<Book> result = new ArrayList<>();
        for (Book book : books) {
            if (book.getTitle().toLowerCase().contains(title.toLowerCase())) {
                result.add(book);
            }
        }
        return result;
    }
    @Override
    public List<Book> findByAuthorContains(String author) {
        List<Book> result = new ArrayList<>();
        for (Book book : books) {
            if (book.getAuthor().toLowerCase().contains(author.toLowerCase())) {
                result.add(book);
            }
        }
        return result;
    }
    @Override
    public Book save(Book book) {
        if (book.getId() == 0) {
            Book newBook = new Book(nextId++, book.getTitle(), book.getAuthor());
            books.add(newBook);
            return newBook;
        } else {
            deleteById(book.getId());
            books.add(book);
            return book;
        }
    }
    @Override
    public boolean deleteById(long id) {
        return books.removeIf(book -> book.getId() == id);
    }
}