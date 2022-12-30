package com.example.basic.controllers;

import com.example.basic.exception.HackingException;
import com.example.basic.exception.InsufficientFundsException;
import com.example.basic.exception.InternalServerException;
import com.example.basic.exception.ResourceNotFoundException;
import com.example.objects.common.ExceptionDto;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.NonUniqueResultException;
import java.time.LocalDateTime;

public class AbstractController {

    @ExceptionHandler({NonUniqueResultException.class, IncorrectResultSizeDataAccessException.class})
    public ResponseEntity<ExceptionDto> handleInvalidRegisterDataException(Exception e) {
        return buildErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({UsernameNotFoundException.class, AuthenticationException.class})
    public ResponseEntity<ExceptionDto> handleInvalidAuthorizationDataException(Exception e) {
        return buildErrorResponse(e.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<ExceptionDto> handleInvalidEmailException(Exception e) {
        return buildErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({HackingException.class})
    public ResponseEntity<ExceptionDto> handleHackingException(Exception e) {
        return buildErrorResponse(e.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler({InternalServerException.class})
    public ResponseEntity<ExceptionDto> handleInternalServerException(Exception e) {
        return buildErrorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({ResourceNotFoundException.class})
    public ResponseEntity<ExceptionDto> handleResourceNotFoundException(Exception e) {
        return buildErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({InsufficientFundsException.class})
    public ResponseEntity<ExceptionDto> handleInsufficientFundsException(Exception e) {
        return buildErrorResponse(e.getMessage(), HttpStatus.PAYMENT_REQUIRED);
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
