package ru.oleg.configurator.domain.auth;

import io.jsonwebtoken.Claims;
import jakarta.security.auth.message.AuthException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.oleg.configurator.domain.auth.dto.JwtRequest;
import ru.oleg.configurator.domain.auth.dto.JwtResponse;
import ru.oleg.configurator.domain.security.JwtAuthentication;
import ru.oleg.configurator.domain.security.JwtProvider;
import ru.oleg.configurator.domain.user.UserService;
import ru.oleg.configurator.domain.user.dto.User;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final Map<String, String> refreshStorage = new HashMap<>();
    private final JwtProvider jwtProvider;

    public JwtResponse login(@NonNull JwtRequest authRequest) throws AuthException {
        final User user = userService.getUserByUsername(authRequest.getLogin());
        if (user==null) throw new AuthException("Пользователь не найден");

        if (user.getPassword().equals(authRequest.getPassword())) {
            final String accessToken = String.valueOf(jwtProvider.generateAccessToken((UserDetails) user));
            final String refreshToken = String.valueOf(jwtProvider.generateRefreshToken((UserDetails) user));
            refreshStorage.put(user.getUsername(), refreshToken);
            return new JwtResponse(accessToken, refreshToken);
        } else {
            throw new AuthException("Неправильный пароль");
        }
    }

    public JwtResponse getAccessToken(@NonNull String refreshToken) throws AuthException {
        if (jwtProvider.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            final String login = claims.getSubject();
            final String saveRefreshToken = refreshStorage.get(login);
            if (saveRefreshToken != null && saveRefreshToken.equals(refreshToken)) {
                final User user = userService.getUserByUsername(login);
                if (user==null) throw new AuthException("Пользователь не найден");
                final String accessToken = String.valueOf(jwtProvider.generateAccessToken((UserDetails) user));
                return new JwtResponse(accessToken, null);
            }
        }
        return new JwtResponse(null, null);
    }

    public JwtResponse refresh(@NonNull String refreshToken) throws AuthException {
        if (jwtProvider.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            final String login = claims.getSubject();
            final String saveRefreshToken = refreshStorage.get(login);
            if (saveRefreshToken != null && saveRefreshToken.equals(refreshToken)) {
                final User user = userService.getUserByUsername(login);
                if (user==null) throw new AuthException("Пользователь не найден");
                final String accessToken = String.valueOf(jwtProvider.generateAccessToken((UserDetails) user));
                final String newRefreshToken = String.valueOf(jwtProvider.generateRefreshToken((UserDetails) user));
                refreshStorage.put(user.getUsername(), newRefreshToken);
                return new JwtResponse(accessToken, newRefreshToken);
            }
        }
        throw new AuthException("Невалидный JWT токен");
    }

    public JwtAuthentication getAuthInfo() {
        return (JwtAuthentication) SecurityContextHolder.getContext().getAuthentication();
    }

}
