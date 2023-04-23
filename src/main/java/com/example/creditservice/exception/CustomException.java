package com.example.creditservice.exception;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
public class CustomException extends RuntimeException {
    String code;
    String message;

    public CustomException(String code, String message) {
        log.error("Exception: " + code);
        this.code = code;
        this.message = message;
    }
}
