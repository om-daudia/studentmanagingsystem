package com.students.studmanagement.common.exceptionhandling;

import com.students.studmanagement.common.response.ErrorResponse;
import com.students.studmanagement.common.response.ResponseHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<Object> handleRuntime(ApplicationException ex) {
        ErrorResponse error = new ErrorResponse(
                ex.getMessage(),
                HttpStatus.valueOf(ex.getStatusCode().value()).toString(),
                ex.getStatusCode().value()
        );
        return ResponseHandler.responseEntity(error, ex.getStatusCode());
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
