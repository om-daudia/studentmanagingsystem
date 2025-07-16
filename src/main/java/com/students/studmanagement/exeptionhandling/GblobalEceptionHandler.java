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
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

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
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        String paramName = ex.getName();  // e.g. "id"
        String value = ex.getValue() != null ? ex.getValue().toString() : "null";

        // Create a descriptive message
        String message = String.format("Invalid value '%s' for parameter '%s'", value, paramName);

        // Throw your custom exception
        ApplicationException appEx = new ApplicationException(message, HttpStatus.BAD_REQUEST);

        // Reuse your ApplicationException handler logic
        ErrorResponse error = new ErrorResponse(
                appEx.getMessage(),
                appEx.getStatusCode().name(),
                appEx.getStatusCode().value()
        );
        return new ResponseEntity<>(error, appEx.getStatusCode());
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneric(Exception ex) {
        ErrorResponse error = new ErrorResponse(
                "Invalid request",
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
