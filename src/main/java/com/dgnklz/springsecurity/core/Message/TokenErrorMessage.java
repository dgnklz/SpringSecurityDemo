package com.dgnklz.springsecurity.core.Message;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TokenErrorMessage {
    private int statusCode;
    private String message;
}
