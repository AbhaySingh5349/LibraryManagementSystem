package com.example.LibraryManagementSystem.mapper;

import com.example.LibraryManagementSystem.dto.AddBookRequest;
import com.example.LibraryManagementSystem.model.Author;
import lombok.experimental.UtilityClass;

@UtilityClass // automatically inserts "private constructor" & methods as "static" so that we don't have to create its instance
public class AuthorMapper {
    public Author mapBookToAuthor(AddBookRequest request){
        return Author.builder().
                name(request.getAuthorName()).
                email(request.getAuthorEmail()).
                build();
    }
}
