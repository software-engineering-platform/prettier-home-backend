package com.ph.exception.customs;

import org.springframework.web.bind.annotation.ResponseStatus;


public class ImageException extends RuntimeException {
    public ImageException(String message) {
        super(message);
    }

    public ImageException(String message, Throwable cause) {
        super(message, cause);
    }
}
