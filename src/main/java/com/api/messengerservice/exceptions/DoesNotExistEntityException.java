package com.api.messengerservice.exceptions;

public class DoesNotExistEntityException extends NullPointerException{

    public DoesNotExistEntityException(String message) {
        super(message);
    }
}
