package com.example.LibraryManagementSystem.service;

import com.example.LibraryManagementSystem.dto.AddBookRequest;
import com.example.LibraryManagementSystem.enums.BookType;
import com.example.LibraryManagementSystem.mapper.AuthorMapper;
import com.example.LibraryManagementSystem.mapper.BookMapper;
import com.example.LibraryManagementSystem.model.Author;
import com.example.LibraryManagementSystem.model.Book;
import com.example.LibraryManagementSystem.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public Book getBookByBookNum(String bookNum) {
        return bookRepository.findByBookNum(bookNum);
    }

    public void updateBook(Book book) {
        // save method is capable of both update and inserting new entry
        bookRepository.save(book);
    }

    public List<Book> getBooks(String title, BookType type) {
        return bookRepository.findBooksByFilters(title, type);
    }
}
