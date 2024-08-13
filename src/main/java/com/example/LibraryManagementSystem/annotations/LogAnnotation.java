package com.example.LibraryManagementSystem.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// we have used @LogAnnotation for "getBooks" method in Book Controller
// in "custom aspect" we have defined what needs to be done for methods we used this "LogAnnotation" on

// where exactly we want to apply this annotation
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LogAnnotation {

}
