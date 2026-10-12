package kz.iitu.springlab.web;

import kz.iitu.springlab.service.BookNotFoundException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Error pages for the @Controller classes. @RestController is itself
 * meta-annotated with @Controller, so this scope matches the REST controllers
 * too; the advice is therefore ordered after ApiExceptionHandler.
 */
@ControllerAdvice(annotations = Controller.class)
@Order(Ordered.LOWEST_PRECEDENCE)
public class PageExceptionHandler {

    @ExceptionHandler(BookNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNotFound(BookNotFoundException ex, Model model) {
        model.addAttribute("bookId", ex.getId());
        model.addAttribute("message", ex.getMessage());
        return "error/not-found";          // our own template
    }
}
