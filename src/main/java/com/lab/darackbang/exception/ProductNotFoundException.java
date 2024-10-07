package com.lab.darackbang.exception;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException(String message) {
        super(message);
    }

    public ProductNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}