package com.example.creditservice.util;

import com.example.creditservice.exception.CustomException;
import com.example.creditservice.model.error.CustomError;
import com.example.creditservice.model.response.DataResponseError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class OrderControllerAdvice {
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<DataResponseError> handleException(CustomException e) {
        DataResponseError dataResponseError = new DataResponseError(
                new CustomError(
                        e.getCode(),
                        e.getMessage()
                )
        );
        return new ResponseEntity<>(dataResponseError, HttpStatus.BAD_REQUEST);
    }
}
