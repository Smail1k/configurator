package ru.oleg.configurator.service.auth;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import ru.oleg.configurator.security.UserDetailsImpl;
import ru.oleg.configurator.security.dto.TokenOut;
import ru.oleg.configurator.service.auth.dto.JwtRequest;
import ru.oleg.configurator.service.auth.dto.AccessOut;
import ru.oleg.configurator.security.JwtProvider;
import ru.oleg.configurator.service.auth.dto.RefreshAccessTokenIn;
import ru.oleg.configurator.service.auth.dto.RefreshOut;
import ru.oleg.configurator.service.user.UserService;
import ru.oleg.configurator.service.user.dto.Role;
import ru.oleg.configurator.service.user.dto.User;

import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AuthService {

    private final UserService userService;
    private final JwtProvider jwtProvider;
    private final UserDetailsService userDetailsService;

    public RefreshOut login(@NonNull final JwtRequest authRequest) throws AuthenticationException {
        final User user = userService.getUserByUsername(authRequest.username());
        if (user==null) throw new AuthenticationException("Пользователь не найден") {};

        if (user.getPassword().equals(authRequest.password())) {
            final UserDetailsImpl userDetails = new UserDetailsImpl(user);
            final TokenOut accessToken = jwtProvider.generateAccessToken(userDetails);
            final TokenOut refreshToken = jwtProvider.generateRefreshToken(userDetails);
            return new RefreshOut(refreshToken.token(), refreshToken.expires(),
                new AccessOut(user.getId(),
                    user.getUsername(),
                    user.getRoles()
                            .stream()
                            .map(Role::getAuthority)
                            .collect(Collectors.toSet()),
                    accessToken.token(), accessToken.expires())
            );
        } else {
            throw new AuthenticationException("Неправильный пароль") {};
        }
    }

    public AccessOut refresh(final RefreshAccessTokenIn refreshAccessTokenIn) {
        if (!jwtProvider.validateRefreshToken(refreshAccessTokenIn.refreshToken()) ||
                !jwtProvider.validateExpiredAccessToken(refreshAccessTokenIn.accessToken())) {
            throw new RuntimeException();
        }

        final UserDetailsImpl userDetails = (UserDetailsImpl) userDetailsService.loadUserByUsername(
                jwtProvider.extractUsernameRefreshClaim(refreshAccessTokenIn.refreshToken())
        );
        final TokenOut accessToken = jwtProvider.generateAccessToken(userDetails);
        return new AccessOut(userDetails.user().getId(),
                userDetails.user().getUsername(),
                userDetails.user().getRoles()
                        .stream()
                        .map(Role::getAuthority)
                        .collect(Collectors.toSet()),
                accessToken.token(), accessToken.expires()
        );
    }

}
