package com.students.studmanagement.exeptionhandling;

import com.students.studmanagement.response.ResponseHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GblobalEceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAllExceptions(Exception ex){
        return ResponseHandler.responseEntity(
                ex.getMessage(),
                false,
                HttpStatus.NOT_FOUND
        );
    }
}
