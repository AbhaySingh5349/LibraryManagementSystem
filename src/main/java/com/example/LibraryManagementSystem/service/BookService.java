package com.example.LibraryManagementSystem.service;

import com.example.LibraryManagementSystem.dto.AddBookRequest;
import com.example.LibraryManagementSystem.mapper.AuthorMapper;
import com.example.LibraryManagementSystem.mapper.BookMapper;
import com.example.LibraryManagementSystem.model.Author;
import com.example.LibraryManagementSystem.model.Book;
import com.example.LibraryManagementSystem.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookService {
    AuthorService authorService;
    BookRepository bookRepository;

    @Autowired
    public BookService(AuthorService authorService, BookRepository bookRepository) {
        this.authorService = authorService;
        this.bookRepository = bookRepository;
    }

    public Book addBook(AddBookRequest request) {
        Author authorFromDb = authorService.getAuthorByEmail(request.getAuthorEmail());

        if(authorFromDb == null){
            // add author to repo
            authorFromDb = AuthorMapper.mapBookToAuthor(request);
            authorFromDb = authorService.addAuthor(authorFromDb);
        }

        Book book = BookMapper.mapRequestBook(request);
        book.setAuthor(authorFromDb);

        return bookRepository.save(book);
    }
}
