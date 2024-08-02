package ru.oleg.configurator.service.auth.dto;

public record RefreshOut(String refreshToken, Long expires, AccessOut login) {
}
