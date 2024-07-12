package com.example.LibraryManagementSystem.mapper;

import com.example.LibraryManagementSystem.dto.AddUserRequest;
import com.example.LibraryManagementSystem.enums.UserStatus;
import com.example.LibraryManagementSystem.model.User;
import lombok.experimental.UtilityClass;

@UtilityClass // automatically inserts "private constructor" & methods as "static" so that we don't have to create its instance
public class UserMapper {
    public User mapRequestUser(AddUserRequest request){
        return User.builder().
                name(request.getUserName()).
                email(request.getEmail()).
                phoneNum(request.getPhoneNum()).
                address(request.getAddress()).
                userStatus(UserStatus.ACTIVE).
                build();
    }
}
