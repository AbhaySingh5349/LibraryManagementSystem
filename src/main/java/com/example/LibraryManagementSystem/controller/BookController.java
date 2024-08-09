package com.example.LibraryManagementSystem.controller;

import com.example.LibraryManagementSystem.annotations.LogAnnotation;
import com.example.LibraryManagementSystem.dto.AddBookRequest;
import com.example.LibraryManagementSystem.enums.BookType;
import com.example.LibraryManagementSystem.model.Book;
import com.example.LibraryManagementSystem.service.BookService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book") // base URL for controller
public class BookController {
    private static final Logger log = LoggerFactory.getLogger(BookController.class);
    BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    // if any validation fails, we get "400 Bad Request err"
    @PostMapping
    public ResponseEntity<Book> addBook(@RequestBody @Valid AddBookRequest request){
        Book book = bookService.addBook(request);
        return new ResponseEntity<>(book, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    @LogAnnotation
    public ResponseEntity<List<Book>> getBooks(@RequestParam(value = "title", required = false) String title,
                                               @RequestParam(value = "type", required = false) BookType type){
        log.info("in BOOK CONTROLLER");
        List<Book> books = bookService.getBooks(title, type);
        return new ResponseEntity<>(books, HttpStatus.OK);
    }
}
