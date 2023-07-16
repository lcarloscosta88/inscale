package com.inscale.test.exception;

public class PersonException extends RuntimeException {
    private static final long serialVersionUID = -2950540919256203656L;

    public PersonException(String message) {
        super(message);
    }
}
