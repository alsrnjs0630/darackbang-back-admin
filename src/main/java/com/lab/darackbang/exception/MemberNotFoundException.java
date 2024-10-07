package com.lab.darackbang.exception;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MemberNotFoundException extends RuntimeException {

    public MemberNotFoundException(String message) {
        super(message);
    }

    public MemberNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}