package com.students.studmanagement.exeptionhandling;

import com.students.studmanagement.response.ResponseHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GblobalEceptionHandler {
    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<Object> handleResourceNotFoundException(DataNotFoundException ex) {
        return ResponseHandler.responseEntity(
                ex.getMessage(),
                "unsuccessful",
                false,
                HttpStatus.NOT_FOUND
        );
    }
    @ExceptionHandler(USerNotExist.class)
    public ResponseEntity<Object> handleResourceNotFoundException(USerNotExist ex) {
        return ResponseHandler.responseEntity(
                ex.getMessage(),
                "unsuccessful",
                false,
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(InvelidTokenException.class)
    public ResponseEntity<Object> handleResourceNotFoundException(InvelidTokenException ex) {
        return ResponseHandler.responseEntity(
                ex.getMessage(),
                "unsuccessful",
                false,
                HttpStatus.NOT_FOUND
        );
    }
}
