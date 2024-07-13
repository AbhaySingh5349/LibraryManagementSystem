package com.example.LibraryManagementSystem.service;

import com.example.LibraryManagementSystem.dto.TransactionRequest;
import com.example.LibraryManagementSystem.enums.UserType;
import com.example.LibraryManagementSystem.exceptions.TransactionException;
import com.example.LibraryManagementSystem.model.Transaction;
import com.example.LibraryManagementSystem.model.User;
import com.example.LibraryManagementSystem.repository.TransactionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {
    @Mock
    UserService userService;

    @Mock
    BookService bookService;

    @Mock
    TransactionRepository transactionRepository;

    @InjectMocks
    TransactionService transactionService;

    // executed before each method of test case
    @BeforeEach
    public void setup(){
        System.out.println("In BeforeEach for TransactionServiceTest");

        // to access private fields of class, we use Reflection property
        ReflectionTestUtils.setField(transactionService, "validDays", 14);
        ReflectionTestUtils.setField(transactionService, "finePerDay", 1);
    }

    // used with static methods only (executed once when class is created)
    @BeforeAll
    public static void dummy(){
        System.out.println("In BeforeAll for TransactionServiceTest");
    }

    @Test
    public void calculateFine_WithinValidDays_ReturnsCorrectAmount(){
        Transaction transaction = Transaction.builder().createdOn(new Date()).settlementAmount(-200).build();

        int amount = transactionService.calculateFine(transaction);

        Assertions.assertEquals(-200, amount);
//        Assertions.assertThrows(TransactionException.class, () -> transactionService.calculateFine(transaction));
    }

    @Test
    public void calculateFine_InValidDays_ReturnsCorrectAmount() throws ParseException {
        Date date = new SimpleDateFormat("yyyy-MM-dd").parse("2024-01-01");
        Transaction transaction = Transaction.builder().createdOn(date).settlementAmount(-200).build();

        int amount = transactionService.calculateFine(transaction);

        Assertions.assertEquals(-20, amount);
    }

    @Test
    public void fetchUser_InvalidStudent_ReturnsCorrectStudent(){
        User user = User.builder().
                id(123).
                email("abc@gmail.com").
                userType(UserType.ADMIN).
                build();

        Mockito.when(userService.fetchUserByEmail("abc@gmail.com")).thenReturn(user);

        TransactionRequest request = TransactionRequest.builder().userEmail("abc@gmail.com").build();

        Assertions.assertThrows(TransactionException.class, () -> transactionService.fetchUser(request));
    }

    @Test
    public void fetchUser_ValidStudent_ReturnsCorrectStudent(){
        User user = User.builder().
                id(123).
                email("abc@gmail.com").
                userType(UserType.STUDENT).
                build();

        Mockito.when(userService.fetchUserByEmail("abc@gmail.com")).thenReturn(user);

        TransactionRequest request = TransactionRequest.builder().userEmail("abc@gmail.com").build();
        User returnedUser = transactionService.fetchUser(request);

        Assertions.assertEquals(user, returnedUser);
    }
}
