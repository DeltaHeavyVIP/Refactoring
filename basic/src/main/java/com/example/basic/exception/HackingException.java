package com.example.basic.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class HackingException extends RuntimeException {
    public HackingException(String msg) {
        super(msg);
    }
}
