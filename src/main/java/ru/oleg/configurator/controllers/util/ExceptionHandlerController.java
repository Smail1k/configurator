package ru.oleg.configurator.controllers.util;

import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.oleg.configurator.controllers.util.dto.ErrorMessage;
import ru.oleg.configurator.exception.*;
import ru.oleg.configurator.security.JwtAuthentication;

import java.time.LocalDateTime;
import java.util.Objects;

@RestControllerAdvice
public class ExceptionHandlerController {
    private static final Logger LOGGER = LogManager.getLogger(ExceptionHandlerController.class);

    private void logError(final Exception exception,
                          final JwtAuthentication authentication,
                          final ErrorMessage errorMessage) {
        String message = exception.getClass().getSimpleName() + ":" +
                errorMessage.toString();
        if (authentication != null) {
            message += ";\nUserInfo:" + authentication.toString();
        }
        LOGGER.error(message, exception);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorMessage> notFoundException(final NotFoundException exception,
                                                          final HttpServletRequest request,
                                                          final JwtAuthentication authentication) {
        final ErrorMessage message = new ErrorMessage(LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                exception.getMessage(),
                request.getRequestURI());
        logError(exception, authentication, message);
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(message);
    }

    @ExceptionHandler({ForbiddenException.class, BadCredentialsException.class})
    public ResponseEntity<ErrorMessage> forbiddenException(final ForbiddenException exception,
                                                           final HttpServletRequest request,
                                                           final JwtAuthentication authentication) {
        final ErrorMessage message = new ErrorMessage(LocalDateTime.now(),
                HttpStatus.FORBIDDEN.value(),
                HttpStatus.FORBIDDEN.getReasonPhrase(),
                exception.getMessage(),
                request.getRequestURI());
        logError(exception, authentication, message);
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(message);
    }

    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<ErrorMessage> alreadyExistsException(final AlreadyExistsException exception,
                                                               final HttpServletRequest request,
                                                               final JwtAuthentication authentication) {
        final ErrorMessage message = new ErrorMessage(LocalDateTime.now(),
                HttpStatus.CONFLICT.value(),
                HttpStatus.CONFLICT.getReasonPhrase(),
                exception.getMessage(),
                request.getRequestURI());
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(message);
    }

    @ExceptionHandler(UnprocessableEntityException.class)
    public ResponseEntity<ErrorMessage> unprocessableEntityException(final UnprocessableEntityException exception,
                                                                     final HttpServletRequest request,
                                                                     final JwtAuthentication authentication) {
        final ErrorMessage message = new ErrorMessage(LocalDateTime.now(),
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                HttpStatus.UNPROCESSABLE_ENTITY.getReasonPhrase(),
                exception.getMessage(),
                request.getRequestURI());
        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(message);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> methodArgumentNotValidException(final MethodArgumentNotValidException exception,
                                                                        final HttpServletRequest request,
                                                                        final JwtAuthentication authentication) {
        final ErrorMessage message = new ErrorMessage(LocalDateTime.now(),
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                HttpStatus.UNPROCESSABLE_ENTITY.getReasonPhrase(),
                Objects.requireNonNull(exception.getAllErrors().get(0).getDefaultMessage()),
                request.getRequestURI());
        logError(exception, authentication, message);
        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(message);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ErrorMessage> methodArgumentNotValidException(final ConflictException exception,
                                                                        final HttpServletRequest request,
                                                                        final JwtAuthentication authentication) {
        final ErrorMessage message = new ErrorMessage(LocalDateTime.now(),
                HttpStatus.CONFLICT.value(),
                HttpStatus.CONFLICT.getReasonPhrase(),
                exception.getMessage(),
                request.getRequestURI());
        logError(exception, authentication, message);
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(message);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> unhandledException(final Exception exception,
                                                           final HttpServletRequest request,
                                                           final JwtAuthentication authentication) {
        final ErrorMessage message = new ErrorMessage(LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                exception.getMessage(),
                request.getRequestURI());
        logError(exception, authentication, message);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(message);
    }
}
