package kz.iitu.springlab.service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import kz.iitu.springlab.domain.Book;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    private final Map<Long, Book> books = new ConcurrentHashMap<>();
    private final AtomicLong sequence = new AtomicLong();

    public BookService() {
        create(new Book(null, "Effective Java", "Joshua Bloch", 2018));
        create(new Book(null, "Spring in Action", "Craig Walls", 2022));
        create(new Book(null, "Clean Code", "Robert Martin", 2008));
    }

    public List<Book> findAll() {
        return books.values().stream()
                .sorted(Comparator.comparing(Book::id))
                .toList();
    }

    public Optional<Book> findById(long id) {
        return Optional.ofNullable(books.get(id));
    }

    public Book create(Book book) {
        Book saved = book.withId(sequence.incrementAndGet());
        books.put(saved.id(), saved);
        return saved;
    }
}
