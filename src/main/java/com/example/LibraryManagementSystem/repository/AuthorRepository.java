package com.example.LibraryManagementSystem.repository;

import com.example.LibraryManagementSystem.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer> {
    // at compile time "hibernate" won't validate this query and it will be directly executed by SQL
//    @Query(name = "select * from author where email = :email", nativeQuery = true)
//    Author fetchAuthorByEmailByNativeQuery(String email);

    // query managed by "hibernate" (findBy<PARAMETER_NAME>)
    Author findByEmail(String email);
}
