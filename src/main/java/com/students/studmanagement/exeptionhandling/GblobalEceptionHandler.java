package com.students.studmanagement.exeptionhandling;

import com.students.studmanagement.response.ErrorResponse;
import com.students.studmanagement.response.ResponseHandler;
import org.springframework.context.ApplicationContextException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GblobalEceptionHandler {
    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ErrorResponse> handleRuntime(ApplicationException ex) {
        ErrorResponse error = new ErrorResponse(
                ex.getMessage(),
                HttpStatus.valueOf(ex.getStatusCode().value()).toString(),
                ex.getStatusCode().value()
        );
        return new ResponseEntity<>(error, ex.getStatusCode());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneric(Exception ex) {
        ErrorResponse error = new ErrorResponse(
                "Something went wrong",
                HttpStatus.BAD_REQUEST.name(),
                HttpStatus.BAD_REQUEST.value()
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<Object> handleAllExceptions(Exception ex){
//        return ResponseHandler.responseEntity(
//                ex.getMessage(),
//                false,
//                HttpStatus.NOT_FOUND
//        );
//    }
}
