package com.example.LibraryManagementSystem.repository;

import com.example.LibraryManagementSystem.enums.BookType;
import com.example.LibraryManagementSystem.model.Book;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

// whenever we deal with "criteria queries", we have to directly interact with "entity manager" as we can't use JpaRepository
// now we can extend JpaRepository interface with "CustomBookRepository"

@Repository
public class CustomBookRepositoryImpl implements CustomBookRepository{
    EntityManager entityManager;

    @Autowired
    public CustomBookRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Book> findBooksByFilters(String title, BookType type) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder(); // builder pattern for custom query
        CriteriaQuery<Book> criteriaQuery = criteriaBuilder.createQuery(Book.class); // creating query with multiple where clauses

        Root<Book> root = criteriaQuery.from(Book.class); // select * from book

        List<Predicate> predicates = new ArrayList<>(); // where clause

        if(title != null && !title.isEmpty()){
            // operator , operand, value
            Predicate titlePredicate = criteriaBuilder.like(root.get("bookTitle"), "%" + title + "%");
            predicates.add(titlePredicate);
        }

        if(type != null){
            Predicate typePredicate = criteriaBuilder.equal(root.get("bookType"), type);
            predicates.add(typePredicate);
        }

        Predicate orPredicate = criteriaBuilder.or(predicates.toArray(new Predicate[0]));

        criteriaQuery.select(root).where(orPredicate);

        return entityManager.createQuery(criteriaQuery).getResultList();
    }
}
