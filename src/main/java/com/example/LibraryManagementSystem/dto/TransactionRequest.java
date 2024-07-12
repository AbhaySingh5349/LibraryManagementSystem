package com.example.LibraryManagementSystem.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE) // all non-static fields will have "private" attached
public class TransactionRequest {
    @NotBlank(message = "book number is mandatory")
    String bookNum;

    @NotBlank(message = "user email is mandatory")
    String userEmail;
}
