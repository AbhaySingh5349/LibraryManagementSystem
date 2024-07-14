package com.example.LibraryManagementSystem.service;

import com.example.LibraryManagementSystem.model.Author;
import com.example.LibraryManagementSystem.repository.AuthorRepository;
import com.example.LibraryManagementSystem.repository.RedisRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AuthorService {
    AuthorRepository authorRepository;
    RedisRepository redisRepository;

    @Autowired
    public AuthorService(AuthorRepository authorRepository, RedisRepository redisRepository) {
        this.authorRepository = authorRepository;
        this.redisRepository = redisRepository;
    }

    // Read-Through mechanism
    public Author getAuthorByEmail(String email){
        Author author = redisRepository.getAuthorByEmail(email);
        if(author != null){
            log.info("Author: " + email + " fetched from redis");
            return author;
        }

        author = authorRepository.findByEmail(email);
        if(author != null){
            redisRepository.saveAuthorToRedis(author);
            log.info("Author: " + email + " fetched from Db and save to redis");
        }

        return author;
    }

    // Write-Through mechanism
    public Author addAuthor(Author author){
        Author savedAuthor = authorRepository.save(author);

        redisRepository.saveAuthorToRedis(savedAuthor);

        log.info("new author: " + author.getEmail() + " saved to Db and redis");

        return savedAuthor;
    }
}
