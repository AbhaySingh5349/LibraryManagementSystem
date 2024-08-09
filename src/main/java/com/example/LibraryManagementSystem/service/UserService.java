package com.example.LibraryManagementSystem.service;

import com.example.LibraryManagementSystem.dto.AddUserRequest;
import com.example.LibraryManagementSystem.enums.UserType;
import com.example.LibraryManagementSystem.mapper.UserMapper;
import com.example.LibraryManagementSystem.model.User;
import com.example.LibraryManagementSystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
    UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // if we will try to save duplicate user, we get "500 internal server error" since we have constraint on model (DB layer)
    public User addStudent(AddUserRequest request) {
        User user = UserMapper.mapRequestUser(request);
        user.setUserType(UserType.STUDENT);
        user.setAuthorities("STUDENT");

        return userRepository.save(user);
    }

    public User addAdmin(AddUserRequest request) {
        User user = UserMapper.mapRequestUser(request);
        user.setUserType(UserType.ADMIN);
        user.setAuthorities("ADMIN");

        return userRepository.save(user);
    }

    public User fetchUserByEmail(String email){
        return userRepository.findByEmail(email);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);

        if(user == null){
            throw new UsernameNotFoundException(username.concat(" user name not found"));
        }

        return user;
    }
}
