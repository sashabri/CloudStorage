package com.example.cloudstorage.advice;

import com.example.cloudstorage.controller.entities.ErrorResponse;
import com.example.cloudstorage.exception.*;
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

    @ExceptionHandler(InvalidBodyException.class)
    public ResponseEntity<ErrorResponse> invalidBodyHandler(InvalidBodyException e) {
        return new ResponseEntity<>(
                new ErrorResponse(e.toString(), HttpStatus.BAD_REQUEST.value()),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(InputParamIsNullException.class)
    public ResponseEntity<ErrorResponse> inputParamIsNullHandler(InputParamIsNullException e) {
        return new ResponseEntity<>(
                new ErrorResponse(e.toString(), HttpStatus.BAD_REQUEST.value()),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(UserIsNullException.class)
    public ResponseEntity<ErrorResponse> userIsNullHandler(UserIsNullException e) {
        return new ResponseEntity<>(
                new ErrorResponse(e.toString(), HttpStatus.BAD_REQUEST.value()),
                HttpStatus.BAD_REQUEST
        );
    }
}
