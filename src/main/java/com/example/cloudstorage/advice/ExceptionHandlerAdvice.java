package com.example.cloudstorage.advice;

import com.example.cloudstorage.controller.entities.ErrorResponse;
import com.example.cloudstorage.exception.InternalServerErrorException;
import com.example.cloudstorage.exception.InvalidDataException;
import com.example.cloudstorage.exception.UnauthorisedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerAdvice {
    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<ErrorResponse> invalidHeaderHandler(MissingRequestHeaderException e) {
        return new ResponseEntity<>(
                new ErrorResponse(e.toString(), HttpStatus.BAD_REQUEST.value()),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(InvalidDataException.class)
    public ResponseEntity<ErrorResponse> invalidDataHandler(InvalidDataException e) {
        return new ResponseEntity<>(
                new ErrorResponse(e.toString(), HttpStatus.BAD_REQUEST.value()),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(InternalServerErrorException.class)
    public ResponseEntity<ErrorResponse> internalHandler(InternalServerErrorException e) {
        return new ResponseEntity<>(
                new ErrorResponse(
                        e.toString(),
                        HttpStatus.INTERNAL_SERVER_ERROR.value()
                ),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @ExceptionHandler(UnauthorisedException.class)
    public ResponseEntity<ErrorResponse> unauthorisedHandler(UnauthorisedException e) {
        return new ResponseEntity<>(
                new ErrorResponse(
                        e.toString(),
                        HttpStatus.UNAUTHORIZED.value()
                ),
                HttpStatus.UNAUTHORIZED
        );
    }
}
