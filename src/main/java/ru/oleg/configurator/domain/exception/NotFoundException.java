package ru.oleg.configurator.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No such resource")
public class NotFoundException extends RuntimeException {

    public NotFoundException() {

    }

    public NotFoundException(final String message) {
        super(message);
    }

}
