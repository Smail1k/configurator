package ru.oleg.configurator.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Conflict with existing resource")
public class ConflictException extends RuntimeException {
    public ConflictException() {
    }

    public ConflictException(final String message) {
        super(message);
    }
}
