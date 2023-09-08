package com.api.messengerservice.exceptions;

public class InvalidEmployeeTypeException extends RuntimeException {
    public InvalidEmployeeTypeException(String message){
        super(message);
    }
}
