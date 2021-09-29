package com.noriyuki.workshopmongo.services.exception;

public class ObjectNotFoundException extends RuntimeException {
    private final long serialVersionUID = 1L;

    public ObjectNotFoundException(String msg) {
        super(msg);
    }
}
