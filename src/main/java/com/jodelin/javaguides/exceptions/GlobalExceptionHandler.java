package com.jodelin.javaguides.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
@ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleRessourceNotFoung(
            ResourceNotFoundException exception,
            WebRequest webRequest ){

ErrorDetails errorDetails= new ErrorDetails(
        new Date(),
        exception.getMessage(),webRequest.getDescription(false));
        return  new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BlogAPIException.class)
    public ResponseEntity<ErrorDetails> handleApiException(
            BlogAPIException exception,
            WebRequest webRequest ){

        ErrorDetails errorDetails= new ErrorDetails(
                new Date(),
                exception.getMessage(),webRequest.getDescription(false));
        return  new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorDetails> handleAccessDenied(
            AccessDeniedException exception,
            WebRequest webRequest ){

        ErrorDetails errorDetails= new ErrorDetails(
                new Date(),
                exception.getMessage(),webRequest.getDescription(false));
        return  new ResponseEntity<>(errorDetails, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleExceptions(
            Exception exception,
            WebRequest webRequest ){

        ErrorDetails errorDetails= new ErrorDetails(
                new Date(),
                exception.getMessage(),webRequest.getDescription(false));
        return  new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }



}
