package com.studentmanagement.common.exceptionhandling;

import com.studentmanagement.common.response.ErrorResponse;
import com.studentmanagement.common.response.ResponseHandler;
import jakarta.transaction.SystemException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.server.ResponseStatusException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<Object> handleApplicationException(ApplicationException ex) {
        ErrorResponse error = new ErrorResponse(
                ex.getMessage(),
                HttpStatus.valueOf(ex.getStatusCode().value()).toString(),
                ex.getStatusCode().value()
        );
        return ResponseHandler.responseEntity(error, ex.getStatusCode());
    }
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Object> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        String paramName = ex.getName();
        String value = ex.getValue() != null ? ex.getValue().toString() : "null";
        String message = String.format("Invalid value '%s' for parameter '%s'", value, paramName);
        ApplicationException appEx = new ApplicationException(message, HttpStatus.BAD_REQUEST);
        ErrorResponse error = new ErrorResponse(
                appEx.getMessage(),
                appEx.getStatusCode().name(),
                appEx.getStatusCode().value()
        );
        return ResponseHandler.responseEntity(error, appEx.getStatusCode());
    }
    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<Object> handleHttpClientErrorException(HttpClientErrorException ex) {
        ErrorResponse error = new ErrorResponse(
                ex.getResponseBodyAsString(),
                ex.getStatusCode().toString(),
                ex.getStatusCode().value()
        );
        return ResponseHandler.responseEntity(error, HttpStatus.valueOf(ex.getStatusCode().value()));
    }
    @ExceptionHandler(SystemException.class)
    public ResponseEntity<Object> handleSystemException(SystemException ex) {
        ErrorResponse error = new ErrorResponse(
                "internal error",
                HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                HttpStatus.INTERNAL_SERVER_ERROR.value()
        );
        return ResponseHandler.responseEntity(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception ex) {
        ErrorResponse error = new ErrorResponse(
                "something went wrong",
                HttpStatus.INTERNAL_SERVER_ERROR.name(),
                HttpStatus.INTERNAL_SERVER_ERROR.value()
        );
        return ResponseHandler.responseEntity(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
