package ru.oleg.configurator.security.dto;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public record JwtClaimOut(Long userId, String altUsername, Collection<? extends GrantedAuthority> authorities) {
}
