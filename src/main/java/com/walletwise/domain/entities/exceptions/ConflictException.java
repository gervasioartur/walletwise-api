package com.walletwise.domain.entities.exceptions;

public class ConflictException extends  RuntimeException{
    public ConflictException(String message) {
        super(message);
    }
}
