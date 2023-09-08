package com.api.messengerservice.exceptions;



public class InvalidCreateEntityException extends RuntimeException{
    public InvalidCreateEntityException(String message){
        super(message);
    }
}
