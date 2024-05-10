package com.students.management.system.exception;

public class GenericAuthenticationException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public GenericAuthenticationException(String msg) {
        super(msg);
    }
}
