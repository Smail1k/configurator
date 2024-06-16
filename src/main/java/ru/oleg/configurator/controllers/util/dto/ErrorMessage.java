package ru.oleg.configurator.controllers.util.dto;

import java.time.LocalDateTime;

public record ErrorMessage(LocalDateTime timestamp,
                           int status,
                           String error,
                           String message,
                           String path) {
    @Override
    public String toString() {
        return "{" +
                "timestamp=" + timestamp +
                ", status=" + status +
                ", error='" + error + '\'' +
                ", message='" + message + '\'' +
                ", path='" + path + '\'' +
                '}';
    }
}
