package com.example.LibraryManagementSystem.mapper;

import com.example.LibraryManagementSystem.dto.AddBookRequest;
import com.example.LibraryManagementSystem.model.Book;
import lombok.experimental.UtilityClass;

@UtilityClass // automatically inserts "private constructor" & methods as "static" so that we don't have to create its instance
public class BookMapper {
    public Book mapRequestBook(AddBookRequest request){
        return Book.builder().
                bookTitle(request.getBookTitle()).
                bookNum(request.getBookNum()).
                securityAmount(request.getSecurityAmount()).
                bookType(request.getBookType()).
                build();
    }
}
