package com.gestionSalleCefim.Group3.exceptions;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.MethodNotAllowedException;

import java.lang.reflect.InvocationTargetException;

@RestControllerAdvice
public class ExceptionHandlerControllerAdvice {
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ExceptionMessage> nullPointerExceptionHandler(HttpServletRequest request, NullPointerException exception) {
        ExceptionMessage exceptionMessage = new ExceptionMessage(request.getRequestURI(), exception.getStackTrace()[0].toString(), exception.getClass().getName());
        return new ResponseEntity<>(exceptionMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ExceptionMessage> entityNotFoundExceptionHandler(HttpServletRequest request, EntityNotFoundException exception) {
        ExceptionMessage exceptionMessage = new ExceptionMessage(request.getRequestURI(), exception.getMessage(), exception.getClass().getName());
        return new ResponseEntity<>(exceptionMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ExceptionMessage> constraintViolationExceptionHandler(HttpServletRequest request, ConstraintViolationException exception) {
        ExceptionMessage exceptionMessage = new ExceptionMessage(request.getRequestURI(), exception.getMessage(), exception.getClass().getName());
        return new ResponseEntity<>(exceptionMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(JpaSystemException.class)
    public ResponseEntity<ExceptionMessage> jpaSystemExceptionHandler(HttpServletRequest request, JpaSystemException exception) {
        ExceptionMessage exceptionMessage = new ExceptionMessage(request.getRequestURI(), exception.getMessage(), exception.getClass().getName());
        return new ResponseEntity<>(exceptionMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NoSuchMethodException.class)
    public ResponseEntity<ExceptionMessage> noSuchMethodExceptionHandler(HttpServletRequest request, NoSuchMethodException exception) {
        ExceptionMessage exceptionMessage = new ExceptionMessage(request.getRequestURI(), exception.getMessage(), exception.getClass().getName());
        return new ResponseEntity<>(exceptionMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(InvocationTargetException.class)
    public ResponseEntity<ExceptionMessage> invocationTargetExceptionHandler(HttpServletRequest request, InvocationTargetException exception) {
        ExceptionMessage exceptionMessage = new ExceptionMessage(request.getRequestURI(), exception.getMessage(), exception.getClass().getName());
        return new ResponseEntity<>(exceptionMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(IllegalAccessException.class)
    public ResponseEntity<ExceptionMessage> illegalAccessExceptionHandler(HttpServletRequest request, IllegalAccessException exception) {
        ExceptionMessage exceptionMessage = new ExceptionMessage(request.getRequestURI(), exception.getMessage(), exception.getClass().getName());
        return new ResponseEntity<>(exceptionMessage, HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS);
    }

    @ExceptionHandler(MethodNotAllowedException.class)
    public ResponseEntity<ExceptionMessage> methodNotAllowedExceptionHandler(HttpServletRequest request, MethodNotAllowedException exception) {
        ExceptionMessage exceptionMessage = new ExceptionMessage(request.getRequestURI(), exception.getMessage(), exception.getClass().getName());
        return new ResponseEntity<>(exceptionMessage, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(InvalidEntityException.class)
    public ResponseEntity<ExceptionMessage> invalidEntityExceptionHandler(HttpServletRequest request, InvalidEntityException exception) {
        ExceptionMessage exceptionMessage = new ExceptionMessage(request.getRequestURI(), exception.getMessage(), exception.getClass().getName());
        return new ResponseEntity<>(exceptionMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntityAlreadyExistsException.class)
    public ResponseEntity<ExceptionMessage> entityAlreadyExistsExceptionHandler(HttpServletRequest request, EntityAlreadyExistsException exception) {
        ExceptionMessage exceptionMessage = new ExceptionMessage(request.getRequestURI(), exception.getMessage(), exception.getClass().getName());
        return new ResponseEntity<>(exceptionMessage, HttpStatus.CONFLICT);
    }
}
