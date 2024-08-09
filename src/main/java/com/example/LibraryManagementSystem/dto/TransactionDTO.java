package com.example.LibraryManagementSystem.dto;

import com.example.LibraryManagementSystem.enums.TransactionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionDTO {

    private int id;
    private String transactionId;
    private BookDTO book;
    private UserDTO user;
    private TransactionStatus transactionStatus;
    private int settlementAmount;
    private Date createdOn;
    private Date updatedOn;
}
