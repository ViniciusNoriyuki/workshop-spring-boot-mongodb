package com.noriyuki.workshopmongo.services.exception;

public class AuthorizationException extends RuntimeException {
    private final long serialVersionUID = 1L;

    public AuthorizationException(String msg) {
        super(msg);
    }

    public AuthorizationException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
