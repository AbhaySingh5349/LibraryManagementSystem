package com.example.LibraryManagementSystem.repository;

import com.example.LibraryManagementSystem.model.Book;
import com.example.LibraryManagementSystem.model.Transaction;
import com.example.LibraryManagementSystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    // tertiary dependency queries (in user we have field email & in book we have bookNum)
    Transaction findByUserEmailAndBookBookNum(String userEmail, String bookNum);

    Transaction findByUserAndBook(User user, Book book);
}
