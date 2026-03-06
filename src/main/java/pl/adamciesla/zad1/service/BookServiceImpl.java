package pl.adamciesla.zad1.service;
import pl.adamciesla.zad1.model.Book;
import pl.adamciesla.zad1.repository.BookRepository;
import java.util.List;
import java.util.Optional;

public class BookServiceImpl implements BookService {

    private BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }
    @Override
    public List<Book> listAll() {
        return bookRepository.findAll();
    }
    @Override
    public List<Book> searchByTitle(String title) {
        return bookRepository.findByTitleContains(title);
    }
    @Override
    public List<Book> searchByAuthor(String author) {
        return bookRepository.findByAuthorContains(author);
    }
    @Override
    public Book add(String title, String author) {
        return bookRepository.save(new Book(0, title, author));
    }
    @Override
    public boolean remove(long id) {
        return bookRepository.deleteById(id);
    }
    @Override
    public Optional<Book> edit(long id, String newTitle, String newAuthor) {
        Optional<Book> bookOptional = bookRepository.findById(id);

        if (bookOptional.isPresent()) {
            Book updated = new Book(id, newTitle, newAuthor);
            Book saved = bookRepository.save(updated);
            return Optional.of(saved);
        }
        return Optional.empty();
    }
}