package ru.oleg.configurator.domain.user.dto;

public enum Role {
    ADMIN, USER;

    public static Role parseValue(final String value) {
        return value.equals("True") ? Role.ADMIN: Role.USER;
    }
}
