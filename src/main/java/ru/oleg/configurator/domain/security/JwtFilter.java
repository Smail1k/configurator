package ru.oleg.configurator.domain.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.oleg.configurator.domain.security.dto.JwtClaimOut;

import java.io.IOException;
import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private static final String AUTHORIZATION = "Authorization";

    private final JwtProvider jwtProvider;
    private final UserDetailsService userDetailsService;

    @Override
    public void doFilterInternal(@NonNull final HttpServletRequest request,
                                 @NonNull final HttpServletResponse response,
                                 @NonNull final FilterChain fc)
            throws IOException, ServletException {
        final String token = getTokenFromRequest(request);
        if (token != null) {
            if (jwtProvider.validateAccessToken(token)) {
                final String username = jwtProvider.extractUsernameAccessClaim(token);
                final JwtClaimOut jwtClaimOut = jwtProvider.extractJwtAuthAccessClaims(token);
                final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                final Authentication jwtAuthentication = new JwtAuthenticationToken(jwtClaimOut.userId(),
                        jwtClaimOut.altUsername(),
                        jwtClaimOut.authorities(),
                        userDetails);
                jwtAuthentication.setAuthenticated(true);
                SecurityContextHolder.getContext().setAuthentication(jwtAuthentication);

            } else {
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                response.getOutputStream().print("{ \"timestamp\": \"" + LocalDateTime.now() + "\",\n" +
                        "\"status\": \"" + HttpStatus.UNAUTHORIZED.value() + "\",\n" +
                        "\"error\": \"" + HttpStatus.UNAUTHORIZED.getReasonPhrase() + "\",\n" +
                        "\"message\": \"Access token expired\",\n" +
                        "\"path\": \"" + request.getRequestURI() + "\"}");
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                return;
            }
        }
        fc.doFilter(request, response);
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        final String bearer = request.getHeader(AUTHORIZATION);
        if (StringUtils.hasText(bearer) && bearer.startsWith("Bearer ")) {
            return bearer.substring(7);
        }
        return null;
    }

}
