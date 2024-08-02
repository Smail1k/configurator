package ru.oleg.configurator.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Getter
@Setter
public class JwtAuthenticationToken extends JwtAuthentication implements CredentialsContainer {

    private boolean authenticated;
    private UserDetails userDetails;
    private Object details;
    private Object credentials;
    private Collection<? extends GrantedAuthority> authorities;

    private Long userId;
    private String altUsername;

    public JwtAuthenticationToken(final long userId,
                                  final String altUsername,
                                  final Collection<? extends GrantedAuthority> authorities,
                                  final UserDetails userDetails) {
        this.userId = userId;
        this.altUsername = altUsername;
        this.authorities = authorities;
        this.userDetails = userDetails;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public Object getCredentials() {
        return credentials;
    }

    @Override
    public Object getDetails() {
        return details;
    }

    @Override
    public Object getPrincipal() {
        return userDetails;
    }

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    @Override
    public void setAuthenticated(final boolean isAuthenticated) throws IllegalArgumentException {
        this.authenticated = isAuthenticated;
    }

    @Override
    public String getName() {
        return userDetails.getUsername();
    }

    @Override
    public void eraseCredentials() {
        this.credentials = null;
    }

    @Override
    public long getUserId() {
        return userId == null ? -1 : userId;
    }

    @Override
    public String toString() {
        return "JwtAuthenticationToken{" +
                "authenticated=" + authenticated +
                ", userDetails=" + userDetails +
                ", details=" + details +
                ", credentials=" + credentials +
                ", authorities=" + authorities +
                ", userId=" + userId +
                ", altUsername='" + altUsername + '\'' +
                '}';
    }
}
