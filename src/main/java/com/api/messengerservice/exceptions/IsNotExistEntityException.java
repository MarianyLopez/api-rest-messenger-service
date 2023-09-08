package com.api.messengerservice.exceptions;

public class IsNotExistEntityException extends NullPointerException{

    public IsNotExistEntityException(String message) {
        super(message);
    }
}
