package ru.oleg.configurator.domain.user.dto;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@RequiredArgsConstructor
public enum Role implements GrantedAuthority {

    ADMIN("ADMIN"),
    USER("USER");

    private final String vale;

    @Override
    public String getAuthority() {
        return vale;
    }

    public static Role parseValue(final String value) {
        return value.equals("True") ? Role.ADMIN: Role.USER;
    }

}
