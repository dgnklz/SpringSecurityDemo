package com.dgnklz.springsecurity.core.Message;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
<<<<<<<< HEAD:src/main/java/com/dgnklz/springsecurity/core/Message/ResponseMessage.java
public class ResponseMessage {
    private String message;
========
@Getter
@Setter
public class RefreshTokenRequest {
    private String token;
>>>>>>>> origin/master:src/main/java/com/dgnklz/springsecurity/business/dto/RefreshTokenRequest.java
}
