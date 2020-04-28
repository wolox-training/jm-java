package com.wolox.training.controllers;

import com.wolox.training.exceptions.BookIdMismatchException;
import com.wolox.training.exceptions.BookNotFoundException;
import com.wolox.training.models.Book;
import com.wolox.training.repositories.BookRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/api/books")
@Api(tags = "Books")
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @GetMapping("/greeting")
    public ModelAndView greeting (@RequestParam(name="name", required=false, defaultValue="Stranger") final String name, Model model) {
        model.addAttribute("name", name);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("greeting");
        return modelAndView;
    }

    @GetMapping
    @ApiOperation(value = "Return an index for Books", response = Iterable.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Book List retrieved"),
        @ApiResponse(code = 400, message = "Malformed request"),
        @ApiResponse(code = 401, message = "Unauthorized access"),
        @ApiResponse(code = 404, message = "Resource not found"),
        @ApiResponse(code = 500, message = "Internal server error")
    })
    public Iterable<Book> findAll() {
        return bookRepository.findAll();
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Given an ID shows associated Book", response = Book.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Book found"),
        @ApiResponse(code = 400, message = "Malformed request"),
        @ApiResponse(code = 401, message = "Unauthorized access"),
        @ApiResponse(code = 404, message = "Resource not found"),
        @ApiResponse(code = 500, message = "Internal server error")
    })
    public Book findOne(@ApiParam(value = "ID of the book", required = true) @PathVariable final Long id) {
        return bookRepository.findById(id).orElseThrow(BookNotFoundException::new);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Creates a book based on given params", response = Book.class)
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "Book created"),
        @ApiResponse(code = 400, message = "Malformed request"),
        @ApiResponse(code = 401, message = "Unauthorized access"),
        @ApiResponse(code = 404, message = "Resource not found"),
        @ApiResponse(code = 500, message = "Internal server error")
    })
    public Book create(@ApiParam(value = "Attributes of the book", required = true) @RequestBody final Book book) {
        return bookRepository.save(book);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Given an ID, deletes the associated Book")
    @ApiResponses(value = {
        @ApiResponse(code = 204, message = "Book deleted"),
        @ApiResponse(code = 400, message = "Malformed request"),
        @ApiResponse(code = 401, message = "Unauthorized access"),
        @ApiResponse(code = 404, message = "Resource not found"),
        @ApiResponse(code = 500, message = "Internal server error")
    })
    public void delete(@ApiParam(value = "ID of the book to delete", required = true) @PathVariable final Long id) {
        bookRepository.findById(id).orElseThrow(BookNotFoundException::new);
        bookRepository.deleteById(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @ApiOperation(value = "Updates a book based on given params", response = Book.class)
    @ApiResponses(value = {
        @ApiResponse(code = 202, message = "Book updated"),
        @ApiResponse(code = 400, message = "Malformed request"),
        @ApiResponse(code = 401, message = "Unauthorized access"),
        @ApiResponse(code = 404, message = "Resource not found"),
        @ApiResponse(code = 500, message = "Internal server error")
    })
    public Book updateBook(@ApiParam(value = "Book attributes", required = true) @RequestBody final Book book,
        @ApiParam(value = "ID of the book to update", required = true) @PathVariable final Long id) {
        if (book.getId() != id) {
            throw new BookIdMismatchException();
        }
        bookRepository.findById(id).orElseThrow(BookNotFoundException::new);
        return bookRepository.save(book);
    }

    @GetMapping("/author/{bookAuthor}")
    @ApiOperation(value = "Given an author's name, returns the associated book", response = Book.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Book found"),
        @ApiResponse(code = 400, message = "Malformed request"),
        @ApiResponse(code = 401, message = "Unauthorized access"),
        @ApiResponse(code = 404, message = "Resource not found"),
        @ApiResponse(code = 500, message = "Internal server error")
    })
    public Book findTopByAuthor(@ApiParam(value = "Author's name to fetch", required = true) @PathVariable final String bookAuthor) {
        return bookRepository.findTopByAuthor(bookAuthor).orElseThrow(BookNotFoundException::new);
    }

}
