package com.example.LibraryManagementSystem.service;

import com.example.LibraryManagementSystem.model.Author;
import com.example.LibraryManagementSystem.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorService {
    AuthorRepository authorRepository;

    @Autowired
    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public Author getAuthorByEmail(String email){
        return authorRepository.findByEmail(email);
    }

    public Author addAuthor(Author author){
        return authorRepository.save(author);
    }
}
