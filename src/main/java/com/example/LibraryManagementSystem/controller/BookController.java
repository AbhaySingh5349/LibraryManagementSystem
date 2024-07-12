package com.example.LibraryManagementSystem.controller;

import com.example.LibraryManagementSystem.dto.AddBookRequest;
import com.example.LibraryManagementSystem.model.Book;
import com.example.LibraryManagementSystem.service.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/book") // base URL for controller
public class BookController {
    BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    public ResponseEntity<Book> addBook(@RequestBody @Valid AddBookRequest request){
        Book book = bookService.addBook(request);
        return new ResponseEntity<>(book, HttpStatus.CREATED);
    }

}
