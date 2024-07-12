package com.example.LibraryManagementSystem.service;

import com.example.LibraryManagementSystem.dto.TransactionRequest;
import com.example.LibraryManagementSystem.enums.TransactionStatus;
import com.example.LibraryManagementSystem.enums.UserStatus;
import com.example.LibraryManagementSystem.enums.UserType;
import com.example.LibraryManagementSystem.exceptions.TransactionException;
import com.example.LibraryManagementSystem.model.Book;
import com.example.LibraryManagementSystem.model.Transaction;
import com.example.LibraryManagementSystem.model.User;
import com.example.LibraryManagementSystem.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TransactionService {
    UserService userService;
    BookService bookService;
    TransactionRepository transactionRepository;

    @Autowired
    public TransactionService(UserService userService, BookService bookService, TransactionRepository transactionRepository) {
        this.userService = userService;
        this.bookService = bookService;
        this.transactionRepository = transactionRepository;
    }

    public Transaction issueBook(TransactionRequest request) {
        User user = fetchUser(request);
        Book book = fetchBook(request);

        return executeTransaction(user, book);
    }

    @Transactional // since we are making updates, so need to have transaction security
    private Transaction executeTransaction(User user, Book book){
        Transaction transaction = Transaction.builder().
                book(book).
                user(user).
                settlementAmount(-book.getSecurityAmount()).
                transactionId((UUID.randomUUID().toString()).substring(0,15)).
                transactionStatus(TransactionStatus.ISSUED).
                build();

        book.setUser(user);
        bookService.updateBook(book);

        return transactionRepository.save(transaction);
    }

    private User fetchUser(TransactionRequest request){
        User user = userService.fetchUserByEmail(request.getUserEmail());
        if(user == null){
            throw new TransactionException("user not found");
        }
        if(!user.getUserType().equals(UserType.STUDENT)){
            throw new TransactionException("user is not of type Student");
        }
        if(user.getUserStatus().equals(UserStatus.BLOCKED)){
            throw new TransactionException("user is blocked");
        }
        return user;
    }

    private Book fetchBook(TransactionRequest request){
        Book book = bookService.getBookByBookNum(request.getBookNum());
        if(book == null){
            throw new TransactionException("book not found");
        }
        if(book.getUser() != null){
            throw new TransactionException("book is already issued");
        }
        return book;
    }
}
