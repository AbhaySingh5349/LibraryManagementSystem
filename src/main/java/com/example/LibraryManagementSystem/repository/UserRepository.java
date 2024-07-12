package com.example.LibraryManagementSystem.repository;

import com.example.LibraryManagementSystem.enums.UserType;
import com.example.LibraryManagementSystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmail(String email);

    User findByEmailAndUserType(String email, UserType userType);
}
