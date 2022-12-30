package com.example.basic.controllers;

import com.example.objects.common.ExceptionDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

public class AbstractController {

    @ExceptionHandler({Exception.class})
    public ResponseEntity<ExceptionDto> handleInvalidRegisterDataException(Exception e) {
        return buildErrorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public static ResponseEntity<ExceptionDto> buildErrorResponse(String msg, HttpStatus status) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        ExceptionDto response = new ExceptionDto();

        response.setStatus(status.value());
        response.setMessage(msg);
        response.setTimeStamp(LocalDateTime.now());

        return ResponseEntity.status(status).headers(httpHeaders).body(response);
    }

}
