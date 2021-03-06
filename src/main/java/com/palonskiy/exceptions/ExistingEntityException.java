package com.palonskiy.exceptions;

public class ExistingEntityException extends RuntimeException {
    public ExistingEntityException() {
        super("Entity is already exist");
    }
}
