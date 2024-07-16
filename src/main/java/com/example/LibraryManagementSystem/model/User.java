package com.example.LibraryManagementSystem.model;

import com.example.LibraryManagementSystem.enums.UserStatus;
import com.example.LibraryManagementSystem.enums.UserType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

// all model classes whichever we plan to save in redis have to implement Serializable otherwise data will not persist in redis

@Data // getters n setters
@AllArgsConstructor
@NoArgsConstructor
@Builder // helps in creating instance
@Entity // telling hibernate that table will exist in DB
@FieldDefaults(level = AccessLevel.PRIVATE) // all non-static fields will have "private" attached
public class User implements Serializable, UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(length = 30)
    String name;

    @Column(unique = true, nullable = false, length = 50)
    String email;

    String password;

    String authorities;

    @Column(unique = true, length = 10)
    String phoneNum;

    String address;

    @Enumerated(value = EnumType.STRING)
    UserType userType;

    @Enumerated(value = EnumType.STRING)
    UserStatus userStatus;

    // we want a bidirectional relationship between user & book but also avoid additional table creation (user_books)
    // so we have to use mapping by field name of user mentioned in Book table
    // List<Book> will not be stored in actual MySQL table but maintained by hibernate
    @OneToMany(mappedBy = "user")
    @JsonIgnoreProperties(value = {"user", "author", "createdOn", "updatedOn"}) // for bidirectional relationships, it avoids "infinite nesting"
    List<Book> books;

    // over the tine user can be part of different transactions
    @OneToMany(mappedBy = "user")
    @JsonIgnoreProperties(value = {"user", "book", "createdOn", "updatedOn"}) // for bidirectional relationships, it avoids "infinite nesting"
    List<Transaction> transactions;

    // timestamp of location where DB instance is running
    @CreationTimestamp(source = SourceType.DB)
    Date createdOn;

    @UpdateTimestamp
    Date updatedOn;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.stream(authorities.split(",")).
                map(authority -> new SimpleGrantedAuthority(authority)).
                collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return email; // unique identifier for user (can be phone num also)
    }
}
