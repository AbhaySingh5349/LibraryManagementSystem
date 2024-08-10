package com.example.LibraryManagementSystem.service;

import com.example.LibraryManagementSystem.dto.TransactionDTO;
import com.example.LibraryManagementSystem.dto.TransactionRequest;
import com.example.LibraryManagementSystem.enums.TransactionStatus;
import com.example.LibraryManagementSystem.enums.UserStatus;
import com.example.LibraryManagementSystem.enums.UserType;
import com.example.LibraryManagementSystem.exceptions.TransactionException;
import com.example.LibraryManagementSystem.mapper.TransactionMapper;
import com.example.LibraryManagementSystem.model.Book;
import com.example.LibraryManagementSystem.model.Transaction;
import com.example.LibraryManagementSystem.model.User;
import com.example.LibraryManagementSystem.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE) // all non-static fields will have "private" attached
public class TransactionService {
    final UserService userService;
    final BookService bookService;
    final TransactionRepository transactionRepository;

    // to fetch key-value from "application.properties"
    @Value("${book.maximum.validity}")
    int validDays;

    @Value("${book.fine.per.day}")
    int finePerDay;

    @Autowired
    public TransactionService(UserService userService, BookService bookService, TransactionRepository transactionRepository) {
        this.userService = userService;
        this.bookService = bookService;
        this.transactionRepository = transactionRepository;
    }

    public TransactionDTO issueBook(TransactionRequest request) {
        User user = fetchUser(request);
        if(user.getUserStatus().equals(UserStatus.BLOCKED)){
            throw new TransactionException("user is blocked");
        }

        Book book = fetchBook(request);
        if(book.getUser() != null){
            throw new TransactionException("book is already issued");
        }

        return executeIssueTransaction(user, book);
    }

    public Integer returnBook(TransactionRequest request) {
        User user = fetchUser(request);
        Book book = fetchBook(request);

        if(book.getUser() != user){
            throw new TransactionException("book is not issued by current user");
        }

        Transaction transaction = transactionRepository.findByUserEmailAndBookBookNum(request.getUserEmail(), request.getBookNum());

        return executeReturnTransaction(transaction, book);
    }

    @Transactional // since we are making updates, so need to have transaction security
    private TransactionDTO executeIssueTransaction(User user, Book book){
        Transaction transaction = Transaction.builder().
                book(book).
                user(user).
                settlementAmount(-book.getSecurityAmount()).
                transactionId((UUID.randomUUID().toString()).substring(0,15)).
                transactionStatus(TransactionStatus.ISSUED).
                build();

        book.setUser(user);
        bookService.updateBook(book);

        Transaction savedTransaction = transactionRepository.save(transaction);

        return TransactionMapper.mapTransactionDTO(savedTransaction);
    }

    @Transactional
    private Integer executeReturnTransaction(Transaction transaction, Book book){
        int amount = calculateFine(transaction);

        book.setUser(null);
        bookService.updateBook(book);

        transactionRepository.save(transaction);

        return amount;
    }

    public User fetchUser(TransactionRequest request){
        User user = userService.fetchUserByEmail(request.getUserEmail());
        if(user == null){
            throw new TransactionException("user not found");
        }
        if(!user.getUserType().equals(UserType.STUDENT)){
            throw new TransactionException("user is not of type Student");
        }
        return user;
    }

    private Book fetchBook(TransactionRequest request){
        Book book = bookService.getBookByBookNum(request.getBookNum());
        if(book == null){
            throw new TransactionException("book not found");
        }
        return book;
    }

    public int calculateFine(Transaction transaction){
        long issueDate = transaction.getCreatedOn().getTime();
        long returnDate = System.currentTimeMillis();

        long timeDifference = returnDate - issueDate;

        long days = TimeUnit.MILLISECONDS.toDays(timeDifference);

        int amount = 0;
        if(days > validDays){
            // add fine
            int fine = (int) ((days-validDays)*finePerDay);

            amount = fine - Math.abs(transaction.getSettlementAmount());
            transaction.setSettlementAmount(-fine);

            transaction.setTransactionStatus(TransactionStatus.FINED);
        }else {
            transaction.setTransactionStatus(TransactionStatus.RETURNED);
            amount = transaction.getSettlementAmount();
            transaction.setSettlementAmount(0);
        }

        return amount;
    }
}
