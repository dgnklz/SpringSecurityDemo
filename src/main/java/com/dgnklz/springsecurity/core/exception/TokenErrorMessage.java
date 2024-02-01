package com.dgnklz.springsecurity.core.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@Getter
@AllArgsConstructor
public class TokenErrorMessage {
    private int statusCode;
    private String message;
}
