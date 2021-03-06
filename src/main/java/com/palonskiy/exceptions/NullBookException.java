package com.palonskiy.exceptions;

public class NullBookException extends RuntimeException {
    public NullBookException() {
        super("Book can not be null");
    }
}
