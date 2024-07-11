package com.example.LibraryManagementSystem.model;

import com.example.LibraryManagementSystem.enums.BookType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.List;

@Data // getters n setters
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity // telling hibernate that table will exist in DB
@FieldDefaults(level = AccessLevel.PRIVATE) // all non-static fields will have "private" attached
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(length = 30)
    String bookTitle;

    @Column(length = 10, unique = true, nullable = false)
    String bookNum;

    int securityAmount;

    @Enumerated(value = EnumType.STRING)
    BookType bookType;

    // many books can be written by 1 author
    @ManyToOne
    @JoinColumn
    Author author; // default join col name: author_id (id being PK of Author class)

    // many books can be issued by 1 user
    @ManyToOne
    User user;

    // over the tine book can be part of different transactions
    @OneToMany(mappedBy = "book")
    List<Transaction> transactions;

    // timestamp of location where DB instance is running
    @CreationTimestamp(source = SourceType.DB)
    Date createdOn;

    @UpdateTimestamp
    Date updatedOn;
}
