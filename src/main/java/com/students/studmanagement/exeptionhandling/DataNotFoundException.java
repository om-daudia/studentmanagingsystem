package com.students.studmanagement.exeptionhandling;

import org.springframework.http.HttpStatus;

public class DataNotFoundException extends RuntimeException{

    public DataNotFoundException(String message) {
        super(message);
    }

}
