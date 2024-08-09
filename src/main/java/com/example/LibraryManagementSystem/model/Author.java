package com.example.LibraryManagementSystem.model;

import com.example.LibraryManagementSystem.enums.UserStatus;
import com.example.LibraryManagementSystem.enums.UserType;
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
public class Author implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(length = 30)
    String name;

    @Column(unique = true, nullable = false, length = 50)
    String email;

    // we want a bidirectional relationship between author & book i.e fetch list of books for author also but also avoid additional table creation (author_books)
    // so we have to use mapping by field name of author mentioned in Book table
    // List<Book> will not be stored in actual MySQL table but maintained by hibernate
    // "fetch = FetchType.EAGER" to avoid error "could not write json failed to lazily initialize a collection of role could not initialize proxy"
    @OneToMany(mappedBy = "author", fetch = FetchType.EAGER)
    @JsonIgnoreProperties(value = {"author"}) // for bidirectional relationships, it avoids "infinite nesting" , "user", "transactions"
    List<Book> books;

    // timestamp of location where DB instance is running
    @CreationTimestamp(source = SourceType.DB)
    Date createdOn;

    @UpdateTimestamp
    Date updatedOn;
}
