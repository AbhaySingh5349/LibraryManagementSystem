package com.example.LibraryManagementSystem.service;

import com.example.LibraryManagementSystem.dto.AddUserRequest;
import com.example.LibraryManagementSystem.enums.UserType;
import com.example.LibraryManagementSystem.mapper.UserMapper;
import com.example.LibraryManagementSystem.model.User;
import com.example.LibraryManagementSystem.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User addStudent(AddUserRequest request) {
        User user = UserMapper.mapRequestUser(request);
        user.setUserType(UserType.STUDENT);

        return userRepository.save(user);
    }

    public User addAdmin(AddUserRequest request) {
        User user = UserMapper.mapRequestUser(request);
        user.setUserType(UserType.ADMIN);

        return userRepository.save(user);
    }

    public User fetchUserByEmail(String email){
        return userRepository.findByEmail(email);
    }
}
