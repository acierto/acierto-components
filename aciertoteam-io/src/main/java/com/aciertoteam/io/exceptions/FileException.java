package com.aciertoteam.io.exceptions;

/**
 * @author Bogdan Nechyporenko
 */
public class FileException extends RuntimeException {

    public FileException() {
        super();    
    }

    public FileException(String message) {
        super(message);    
    }

    public FileException(String message, Throwable cause) {
        super(message, cause);    
    }

    public FileException(Throwable cause) {
        super(cause);    
    }
}
