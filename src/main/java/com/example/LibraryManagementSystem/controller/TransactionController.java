package com.example.LibraryManagementSystem.controller;

import com.example.LibraryManagementSystem.dto.TransactionRequest;
import com.example.LibraryManagementSystem.exceptions.TransactionException;
import com.example.LibraryManagementSystem.model.Transaction;
import com.example.LibraryManagementSystem.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transaction")
public class TransactionController {
    TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/issue")
    public ResponseEntity<Transaction> issueBook(@RequestBody @Valid TransactionRequest request){
        /*
        try{
            Transaction transaction = transactionService.issueBook(request);
            return new ResponseEntity<>(transaction, HttpStatus.CREATED);
        }catch (TransactionException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
         */

        // handling exception directly using ControllerAdvice
        Transaction transaction = transactionService.issueBook(request);
        return new ResponseEntity<>(transaction, HttpStatus.CREATED);
    }

    @PutMapping("/return")
    public ResponseEntity<Integer> returnBook(@RequestBody @Valid TransactionRequest request){
        Integer settlementAmount = transactionService.returnBook(request);
        return new ResponseEntity<>(settlementAmount, HttpStatus.OK);
    }
}
