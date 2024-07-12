package com.example.LibraryManagementSystem.repository;

import com.example.LibraryManagementSystem.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Integer> {
    Book findByBookNum(String bookNum);
}
