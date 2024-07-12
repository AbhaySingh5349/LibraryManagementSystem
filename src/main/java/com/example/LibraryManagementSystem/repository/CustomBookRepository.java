package com.example.LibraryManagementSystem.repository;

import com.example.LibraryManagementSystem.enums.BookType;
import com.example.LibraryManagementSystem.model.Book;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomBookRepository {
    List<Book> findBooksByFilters(String title, BookType type);
}
