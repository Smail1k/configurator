package ru.oleg.configurator.service.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RefreshAccessTokenIn(@NotNull @NotBlank String accessToken, @NotNull @NotBlank String refreshToken) {
}