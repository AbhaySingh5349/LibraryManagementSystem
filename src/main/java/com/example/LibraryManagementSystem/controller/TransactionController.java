package com.example.LibraryManagementSystem.controller;

import com.example.LibraryManagementSystem.dto.TransactionDTO;
import com.example.LibraryManagementSystem.dto.TransactionRequest;
import com.example.LibraryManagementSystem.exceptions.TransactionException;
import com.example.LibraryManagementSystem.model.Transaction;
import com.example.LibraryManagementSystem.model.User;
import com.example.LibraryManagementSystem.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

// any transaction exception will be handled through "ControllerExceptionAdvice class"

@RestController
@RequestMapping("/transaction")
public class TransactionController {
    TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/issue")
    public ResponseEntity<TransactionDTO> issueBook(@RequestBody @Valid TransactionRequest request){
        // exception handling (but better way is to use "ControllerAdvice" to avoid using try-catch redundantly
        /*
        try{
            TransactionDTO transaction = transactionService.issueBook(request);
            return new ResponseEntity<>(transaction, HttpStatus.CREATED);
        }catch (TransactionException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
         */

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user =  (User) authentication.getPrincipal();

        // check if book is issued by currently logged-in user only
        if(!user.getEmail().equals(request.getUserEmail())){
            throw new TransactionException("you cannot issue book to some other user");
        }

        // handling exception directly using ControllerAdvice
        TransactionDTO transaction = transactionService.issueBook(request);
        return new ResponseEntity<>(transaction, HttpStatus.CREATED);
    }

    @PutMapping("/return")
    public ResponseEntity<Integer> returnBook(@RequestBody @Valid TransactionRequest request){
        Integer settlementAmount = transactionService.returnBook(request);
        return new ResponseEntity<>(settlementAmount, HttpStatus.OK);
    }
}
