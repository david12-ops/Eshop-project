package com.example.exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class UserPermissionException extends RuntimeException {
    public UserPermissionException() {
        super("User does not have permission to perform this action");
    }
}
