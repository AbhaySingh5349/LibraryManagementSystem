package com.example.LibraryManagementSystem.repository;

import com.example.LibraryManagementSystem.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
}
