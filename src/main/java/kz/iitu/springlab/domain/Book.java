package kz.iitu.springlab.domain;

public record Book(Long id, String title, String author, int year) {

    public Book withId(Long newId) {
        return new Book(newId, title, author, year);
    }
}
