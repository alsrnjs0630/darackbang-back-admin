package com.lab.darackbang.exception;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RoleAccessDeniedException extends RuntimeException {

    public RoleAccessDeniedException(String message) {
        super(message);
    }

    public RoleAccessDeniedException(String message, Throwable cause) {
        super(message, cause);
    }
}