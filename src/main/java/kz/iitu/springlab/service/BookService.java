package kz.iitu.springlab.service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import kz.iitu.springlab.domain.Book;
import kz.iitu.springlab.domain.Genre;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    private final Map<Long, Book> books = new ConcurrentHashMap<>();
    private final AtomicLong sequence = new AtomicLong();

    public BookService() {
        create(new Book(null, "Effective Java", "Joshua Bloch", 2018,
                Genre.PROGRAMMING, true, LocalDate.of(2017, 12, 27)));
        create(new Book(null, "Spring in Action", "Craig Walls", 2022,
                Genre.PROGRAMMING, true, LocalDate.of(2022, 1, 4)));
        create(new Book(null, "Clean Code", "Robert Martin", 2008,
                Genre.PROGRAMMING, false, LocalDate.of(2008, 8, 1)));
    }

    public List<Book> findAll() {
        return books.values().stream()
                .sorted(Comparator.comparing(Book::id))
                .toList();
    }

    public Book findById(long id) {
        Book book = books.get(id);
        if (book == null) {
            throw new BookNotFoundException(id);
        }
        return book;
    }

    public Book create(Book book) {
        Book saved = book.withId(sequence.incrementAndGet());
        books.put(saved.id(), saved);
        return saved;
    }
}
