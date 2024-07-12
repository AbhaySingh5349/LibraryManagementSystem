package com.example.LibraryManagementSystem.dto;

import com.example.LibraryManagementSystem.enums.BookType;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE) // all non-static fields will have "private" attached
public class AddBookRequest {
    @NotBlank(message = "book title should not be blank")
    String bookTitle;

    @NotBlank(message = "book num should not be blank")
    String bookNum;

    @Positive(message = "security amount should not be negative")
    int securityAmount;

    @NotNull(message = "book title should not be null")
    BookType bookType;

    @NotBlank(message = "author name should not be blank")
    String authorName;

    @NotBlank(message = "author email should not be blank")
    String authorEmail;
}
