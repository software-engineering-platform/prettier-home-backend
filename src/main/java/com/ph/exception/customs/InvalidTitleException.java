package com.ph.exception.customs;

public class InvalidTitleException extends RuntimeException {
    public InvalidTitleException(String message) {
        super(message);
    }
    public InvalidTitleException(String message, Throwable cause) {
        super(message, cause);
    }
}
