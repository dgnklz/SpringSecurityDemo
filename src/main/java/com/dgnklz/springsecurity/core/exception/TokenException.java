package com.dgnklz.springsecurity.core.exception;

public class TokenException extends RuntimeException {
    public TokenException(String message) {
        super(message);
    }
}