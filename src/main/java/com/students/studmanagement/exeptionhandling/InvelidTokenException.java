package com.students.studmanagement.exeptionhandling;

public class InvelidTokenException extends RuntimeException{
    public InvelidTokenException(String message){
        super(message);
    }
}
