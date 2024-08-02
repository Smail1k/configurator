package ru.oleg.configurator.service.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

public record JwtRequest(@NotNull @Size(max = 128) @Email @NotBlank String username,
                      @NotNull @Size(max = 128) @NotBlank String password) implements Serializable {
}

