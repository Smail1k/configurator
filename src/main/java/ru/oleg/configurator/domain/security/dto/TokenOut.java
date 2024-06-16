package ru.oleg.configurator.domain.security.dto;

public record TokenOut(String token, Long expires) {
}
