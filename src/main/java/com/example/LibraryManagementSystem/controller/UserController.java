package com.example.LibraryManagementSystem.controller;

import com.example.LibraryManagementSystem.dto.AddBookRequest;
import com.example.LibraryManagementSystem.dto.AddUserRequest;
import com.example.LibraryManagementSystem.model.Book;
import com.example.LibraryManagementSystem.model.User;
import com.example.LibraryManagementSystem.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/student")
    public ResponseEntity<User> addStudent(@RequestBody @Valid AddUserRequest request){
       User user = userService.addStudent(request);
       return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PostMapping("/admin")
    public ResponseEntity<User> addAdmin(@RequestBody @Valid AddUserRequest request){
        User user = userService.addAdmin(request);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }
}
