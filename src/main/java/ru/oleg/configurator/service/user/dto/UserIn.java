package ru.oleg.configurator.service.user.dto;

import jakarta.validation.constraints.NotNull;

import java.util.Set;

public record UserIn(@NotNull String username,
                     String fullName,
                     String password,
                     @NotNull Set<Role> roles,
                     @NotNull boolean isAutoIn){
}
