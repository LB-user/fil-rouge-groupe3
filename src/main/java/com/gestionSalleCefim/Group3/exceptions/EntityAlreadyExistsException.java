package com.gestionSalleCefim.Group3.exceptions;

/**
 * Exception thrown when an entity with the same ID already exists in the database.
 */
public class EntityAlreadyExistsException extends Exception {
    public EntityAlreadyExistsException(String message) {
        super(message);
    }
}
