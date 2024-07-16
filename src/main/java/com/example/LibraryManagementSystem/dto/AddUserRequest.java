package com.example.LibraryManagementSystem.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE) // all non-static fields will have "private" attached
public class AddUserRequest {
    @NotBlank(message = "user name should not be blank")
    String userName;

    @NotBlank(message = "user email should not be blank")
    String email;

    @NotBlank(message = "user password should not be blank")
    String password;

    String phoneNum;

    String address;
}
