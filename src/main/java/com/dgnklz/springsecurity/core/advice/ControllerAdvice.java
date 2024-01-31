package com.dgnklz.springsecurity.core.advice;

import com.dgnklz.springsecurity.core.exception.TokenErrorMessage;
import com.dgnklz.springsecurity.core.exception.TokenException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {
    @ExceptionHandler(value = TokenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public TokenErrorMessage handleTokenException(TokenException exception) {
        return new TokenErrorMessage(
                HttpStatus.FORBIDDEN.value(),
                exception.getMessage());
    }
}
