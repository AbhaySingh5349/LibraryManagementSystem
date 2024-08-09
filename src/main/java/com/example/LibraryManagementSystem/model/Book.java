package com.example.LibraryManagementSystem.model;

import com.example.LibraryManagementSystem.enums.BookType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

// all model classes whichever we plan to save in redis have to implement Serializable otherwise data will not persist in redis

@Data // getters n setters
@AllArgsConstructor
@NoArgsConstructor
@Builder // helps in creating instance
@Entity // telling hibernate that table will exist in DB
@FieldDefaults(level = AccessLevel.PRIVATE) // all non-static fields will have "private" attached
public class Book implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(length = 30, unique = true, nullable = false)
    String bookTitle;

    @Column(length = 10, unique = true, nullable = false)
    String bookNum;

    int securityAmount;

    @Enumerated(value = EnumType.STRING)
    BookType bookType;

    // many books can be written by 1 author
    @ManyToOne
    @JoinColumn // optional annotation to override properties of join col
    @JsonIgnoreProperties(value = {"books", "createdOn", "updatedOn"})
    Author author; // default join col name: author_id (id being PK of Author class)

    // many books can be issued by 1 user
    @ManyToOne
    User user;

    // over the time book can be part of different transactions
    // "fetch = FetchType.EAGER" to avoid error "could not write json failed to lazily initialize a collection of role could not initialize proxy"
    @OneToMany(mappedBy = "book", fetch = FetchType.EAGER)
    @JsonIgnoreProperties(value = {"book", "user", "createdOn", "updatedOn"}) // for bidirectional relationships, it avoids "infinite nesting"
    List<Transaction> transactions;

    // timestamp of location where DB instance is running
    @CreationTimestamp(source = SourceType.DB)
    Date createdOn;

    @UpdateTimestamp
    Date updatedOn;
}
