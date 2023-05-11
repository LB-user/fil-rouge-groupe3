package com.gestionSalleCefim.Group3.exceptions;

/**
 * Exception thrown when an invalid entity is passed to the save method.
 */
public class InvalidEntityException extends Exception {
    public InvalidEntityException(String message, Throwable cause) {
        super(message, cause);
    }
}
