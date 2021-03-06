package com.palonskiy.exceptions;

public class NoResultException extends RuntimeException {
    public NoResultException() {
        super("Can not find any entities");
    }
}
