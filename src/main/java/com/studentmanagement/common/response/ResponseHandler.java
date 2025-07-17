package com.studentmanagement.common.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class ResponseHandler {
    public static ResponseEntity<Object> responseEntity(Object object, String msg,boolean flag, HttpStatus httpStatus)
    {
        Map<String, Object> response = new HashMap<>();
        response.put("data",object);
        response.put("message",msg);
        response.put("isSuccess",flag);
        response.put("httpStatus",httpStatus);

        return new ResponseEntity<>(response,httpStatus);
    }

    public static ResponseEntity<Object> responseEntity(String msg,boolean flag, HttpStatus httpStatus)
    {
        Map<String, Object> response = new HashMap<>();
        response.put("message",msg);
        response.put("isSuccess",flag);
        response.put("httpStatus",httpStatus);

        return new ResponseEntity<>(response,httpStatus);
    }
    public static ResponseEntity<Object> responseEntity(String msg, int code, HttpStatus httpStatus)
    {
        Map<String, Object> response = new HashMap<>();
        response.put("message",msg);
        response.put("httpStatus",httpStatus);
        response.put("httpStatusCode",code);

        return new ResponseEntity<>(response,httpStatus);
    }
    public static ResponseEntity<Object> responseEntity(Object object, HttpStatus httpStatus){
        return new ResponseEntity<>(object,httpStatus);
    }
}
