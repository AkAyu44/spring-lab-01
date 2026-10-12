package kz.iitu.springlab.api;

import java.util.List;

import kz.iitu.springlab.domain.Book;
import kz.iitu.springlab.service.BookService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/books")
public class BookRestController {

    private final BookService service;

    public BookRestController(BookService service) {
        this.service = service;
    }

    @GetMapping
    public List<Book> all() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Book one(@PathVariable long id) {
        return service.findById(id);       // BookNotFoundException -> ApiExceptionHandler
    }
}
