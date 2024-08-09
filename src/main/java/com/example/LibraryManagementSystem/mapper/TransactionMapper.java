package com.example.LibraryManagementSystem.mapper;

import com.example.LibraryManagementSystem.dto.BookDTO;
import com.example.LibraryManagementSystem.dto.TransactionDTO;
import com.example.LibraryManagementSystem.dto.UserDTO;
import com.example.LibraryManagementSystem.model.Transaction;
import lombok.experimental.UtilityClass;

@UtilityClass // automatically inserts "private constructor" & methods as "static" so that we don't have to create its instance
public class TransactionMapper {
    public TransactionDTO mapTransactionDTO(Transaction transaction){
        return TransactionDTO.builder()
                .id(transaction.getId())
                .transactionId(transaction.getTransactionId())
                .book(BookDTO.builder()
                        .id(transaction.getBook().getId())
                        .bookTitle(transaction.getBook().getBookTitle())
                        .bookNum(transaction.getBook().getBookNum())
                        .build())
                .user(UserDTO.builder()
                        .id(transaction.getUser().getId())
                        .name(transaction.getUser().getName())
                        .email(transaction.getUser().getEmail())
                        .phoneNum(transaction.getUser().getPhoneNum())
                        .address(transaction.getUser().getAddress())
                        .userType(transaction.getUser().getUserType().name())
                        .userStatus(transaction.getUser().getUserStatus().name())
                        .build())
                .transactionStatus(transaction.getTransactionStatus())
                .settlementAmount(transaction.getSettlementAmount())
                .createdOn(transaction.getCreatedOn())
                .updatedOn(transaction.getUpdatedOn())
                .build();
    }
}
