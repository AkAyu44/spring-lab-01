package kz.iitu.springlab.domain;

import java.time.LocalDate;

public record Book(Long id, String title, String author, int year,
                   Genre genre, boolean available, LocalDate publishedOn) {

    public Book(Long id, String title, String author, int year) {
        this(id, title, author, year, null, true, null);
    }

    public Book withId(Long newId) {
        return new Book(newId, title, author, year, genre, available, publishedOn);
    }
}
