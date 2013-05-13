package com.aciertoteam.mail.exceptions;

/**
 * @author Bogdan Nechyporenko
 */
public class EmptyMessageBodyException extends RuntimeException {

    public EmptyMessageBodyException() {
        super();
    }

    public EmptyMessageBodyException(String message) {
        super(message);
    }

    public EmptyMessageBodyException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmptyMessageBodyException(Throwable cause) {
        super(cause);
    }
}
