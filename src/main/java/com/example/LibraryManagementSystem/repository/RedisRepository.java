package com.example.LibraryManagementSystem.repository;

import com.example.LibraryManagementSystem.model.Author;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@FieldDefaults(level = AccessLevel.PRIVATE) // all non-static fields will have "private" attached
public class RedisRepository {
    RedisTemplate redisTemplate;

    final String AUTHOR_KEY = "author:";

    @Autowired
    public RedisRepository(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void saveAuthorToRedis(Author author){
        redisTemplate.opsForValue().set(AUTHOR_KEY.concat(author.getEmail()), author);
    }

    public Author getAuthorByEmail(String email){
        // since redis values are stored as Generic Object, so we need to downcast our values;
        return (Author) redisTemplate.opsForValue().get(AUTHOR_KEY.concat(email));
    }
}
