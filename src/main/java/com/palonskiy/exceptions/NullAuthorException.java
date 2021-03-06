package com.palonskiy.exceptions;

public class NullAuthorException extends RuntimeException {
    public NullAuthorException() {
        super("Author can not be null");
    }
}
