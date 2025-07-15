package com.students.studmanagement.exeptionhandling;

import com.students.studmanagement.response.ErrorResponse;
import org.springframework.http.HttpStatus;

public class ApplicationException extends RuntimeException{
    private HttpStatus statusCode;

    public ApplicationException(String message, HttpStatus statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public HttpStatus getStatusCode() {
        return statusCode;
    }


}

