package com.example.LibraryManagementSystem.model;

import com.example.LibraryManagementSystem.enums.TransactionStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.util.Date;

// all model classes whichever we plan to save in redis have to implement Serializable otherwise data will not persist in redis

// transaction:
// - user exists, type is STUDENT, status is not blocked
// - book exists, not already issued
// - create transaction -> map user with book -> update book status as issued -> save transaction

@Data // getters n setters
@AllArgsConstructor
@NoArgsConstructor
@Builder // helps in creating instance
@Entity // telling hibernate that table will exist in DB
@FieldDefaults(level = AccessLevel.PRIVATE) // all non-static fields will have "private" attached
public class Transaction implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(length = 16, unique = true, nullable = false)
    String transactionId;

    // multiple transactions can be done for a book
    @ManyToOne
    Book book;

    // multiple transactions can be done for a user
    @ManyToOne
    User user;

    @Enumerated(value = EnumType.STRING)
    TransactionStatus transactionStatus;

    int settlementAmount;

    // timestamp of location where DB instance is running
    @CreationTimestamp(source = SourceType.DB)
    Date createdOn;

    @UpdateTimestamp
    Date updatedOn;
}
