package com.students.studmanagement.exeptionhandling;

public class USerNotExist extends RuntimeException{
    public USerNotExist(String message){
        super(message);
    }
}
