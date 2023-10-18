package com.myprojects.myportfolio.clients.utils;

import com.myprojects.myportfolio.clients.general.messages.IMessage;
import com.myprojects.myportfolio.clients.general.messages.Message;
import com.myprojects.myportfolio.clients.general.messages.MessageResource;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@ControllerAdvice
@Slf4j
public class ExceptionHandlerAdvice {

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<MessageResource<?>> handleUnauthorizedException(UnauthorizedException e) {
        log.error(e.getMessage(), e);
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(new MessageResource<>(null, new Message(e.getMessage(), IMessage.Level.ERROR)));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<MessageResource<?>> handleAccessDeniedException(AccessDeniedException e) {
        log.error(e.getMessage(), e);
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(new MessageResource<>(null, new Message(e.getMessage(), IMessage.Level.ERROR)));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<MessageResource<?>> handleBadCredentialsException(BadCredentialsException e) {
        log.error(e.getMessage(), e);
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new MessageResource<>(null, new Message(e.getMessage(), IMessage.Level.ERROR)));
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<MessageResource<?>> handleExpiredJwtException(ExpiredJwtException e) {
        log.error(e.getMessage(), e);
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new MessageResource<>(null, new Message(e.getMessage(), IMessage.Level.ERROR)));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<MessageResource<?>> handleException(Exception e) {
        log.error(e.getMessage(), e);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new MessageResource<>(null, new Message(e.getMessage(), IMessage.Level.ERROR)));
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<MessageResource<?>> handleNoSuchElementException(NoSuchElementException e) {
        log.error(e.getMessage());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new MessageResource<>(null, new Message(e.getMessage(), IMessage.Level.ERROR)));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<MessageResource<?>> handleEntityNotFoundException(EntityNotFoundException e) {
        log.error(e.getMessage());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new MessageResource<>(null, new Message(e.getMessage(), IMessage.Level.ERROR)));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<MessageResource<?>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        BindingResult result = e.getBindingResult();
        List<Message> errors = new ArrayList<>();
        result.getAllErrors();
        if (!result.getAllErrors().isEmpty()) {
            errors = result.getAllErrors().stream().map(error -> new Message(error.getDefaultMessage(), IMessage.Level.ERROR)).collect(Collectors.toList());
        }

        log.error("Validation Error {}", errors);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new MessageResource<>(null, errors));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<MessageResource<?>> handleException(DataIntegrityViolationException e) {
        String message = e.getCause() != null && e.getCause().getCause() != null ? e.getCause().getCause().getMessage() : e.getMessage();
        log.error(message, e);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new MessageResource<>(null, new Message(message, IMessage.Level.ERROR)));
    }

}
