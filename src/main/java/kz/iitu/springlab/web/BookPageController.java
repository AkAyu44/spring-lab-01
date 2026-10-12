package kz.iitu.springlab.web;

import jakarta.validation.Valid;
import kz.iitu.springlab.domain.Book;
import kz.iitu.springlab.domain.Genre;
import kz.iitu.springlab.service.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/books")
public class BookPageController {

    private final BookService service;

    public BookPageController(BookService service) {
        this.service = service;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("books", service.findAll());
        return "books/list";
    }

    @GetMapping("/{id}")
    public String details(@PathVariable long id, Model model) {
        model.addAttribute("book", service.findById(id));   // may throw BookNotFoundException
        return "books/details";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("form", new BookForm());
        model.addAttribute("genres", Genre.values());
        return "books/form";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute("form") BookForm form,
                         BindingResult binding,          // immediately after!
                         Model model,
                         RedirectAttributes redirect) {

        if (binding.hasErrors()) {
            model.addAttribute("genres", Genre.values());   // the model again
            return "books/form";
        }

        Book saved = service.create(new Book(null, form.getTitle(),
                form.getAuthor(), form.getYear(), form.getGenre(),
                form.isAvailable(), form.getPublishedOn()));

        // the text itself lives in the bundle: book.added=Book «{0}» has been added
        redirect.addFlashAttribute("addedTitle", saved.title());
        return "redirect:/books";
    }
}
