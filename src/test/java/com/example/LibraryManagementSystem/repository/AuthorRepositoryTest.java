package com.example.LibraryManagementSystem.repository;

import com.example.LibraryManagementSystem.model.Author;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class AuthorRepositoryTest {
    @Autowired
    AuthorRepository authorRepository;

    @BeforeEach
    public void setup(){
        Author author = Author.builder().name("a1").email("a1@gmail.com").build();
        authorRepository.save(author);
    }

    @Test
    public void fetchByEmail_ValidAuthor_ReturnsAuthor(){
        Author author = authorRepository.findByEmail("a1@gmail.com");

        Assertions.assertEquals("a1", author.getName());
    }

    @Test
    public void fetchByEmail_InvalidAuthor_ReturnsNull(){
        Assertions.assertNull(authorRepository.findByEmail("dummy@gmail.com"));
    }
}
