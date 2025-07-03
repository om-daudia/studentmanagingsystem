package com.students.studmanagement.response;

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

//    public static ResponseEntity<Object> responseEntity(Object object,String msg, HttpStatus httpStatus)
//    {
//        Map<String, Object> response = new HashMap<>();
//        response.put("data",object);
//        response.put("message",msg);
//        response.put("HttpStatus",httpStatus);
//
//        return new ResponseEntity<>(response,httpStatus);
//    }
}
