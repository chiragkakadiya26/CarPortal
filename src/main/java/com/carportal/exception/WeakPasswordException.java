package com.carportal.exception;

public class WeakPasswordException extends  RuntimeException {

    public WeakPasswordException(String message) {
        super(message);
    }
}
