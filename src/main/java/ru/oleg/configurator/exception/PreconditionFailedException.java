package ru.oleg.configurator.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.PRECONDITION_FAILED, reason = "Precondition failed")
public class PreconditionFailedException extends RuntimeException {
    public PreconditionFailedException() {
    }

    public PreconditionFailedException(final String message) {
        super(message);
    }
}
