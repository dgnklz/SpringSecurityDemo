package com.dgnklz.springsecurity.exception;

public class TokenException extends RuntimeException {
    public TokenException(String message) {
        super(message);
    }
}