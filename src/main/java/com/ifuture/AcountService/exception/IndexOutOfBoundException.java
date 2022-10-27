package com.ifuture.AcountService.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@ResponseStatus(NOT_FOUND)
public class IndexOutOfBoundException extends RuntimeException{

    public IndexOutOfBoundException(String objectType, String criteria, Object value) {
        super(String.format("%s size = %s is largest then table size = %s", objectType, criteria, value));
    }
}
