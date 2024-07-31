package ru.oleg.configurator.domain.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.validation.constraints.NotNull;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import ru.oleg.configurator.domain.security.dto.JwtAuthority;
import ru.oleg.configurator.domain.security.dto.JwtClaimOut;
import ru.oleg.configurator.domain.security.dto.TokenOut;
import ru.oleg.configurator.domain.user.dto.Role;
import ru.oleg.configurator.domain.user.dto.User;

import javax.crypto.SecretKey;
import java.security.Key;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtProvider {

    private final SecretKey jwtAccessSecret;
    private final SecretKey jwtRefreshSecret;
    private final long expirationAccessTime;
    private final long expirationRefreshTime;

    public JwtProvider(
            @Value("${jwt.secret.access}") String jwtAccessSecret,
            @Value("${jwt.secret.refresh}") String jwtRefreshSecret,
            final @Value("${jwt.expiration.access}") long expirationAccessTime,
            final @Value("${jwt.expiration.refresh}") long expirationRefreshTime
    ) {
        this.jwtAccessSecret = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtAccessSecret));
        this.jwtRefreshSecret = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtRefreshSecret));
        this.expirationAccessTime = expirationAccessTime;
        this.expirationRefreshTime = expirationRefreshTime;
    }

    public TokenOut generateAccessToken(final @NonNull UserDetails userDetails) {
        final User user = ((UserDetailsImpl) userDetails).user();
        return new TokenOut(generateToken(userDetails, jwtAccessSecret, expirationAccessTime)
                .claim("userId", user.getId())
                .claim("Username", user.getUsername())
                .claim("authorities", user.getRole()
                        .stream()
                        .map(Role::getAuthority)
                        .collect(Collectors.joining(", ")))
                .compact(), expirationAccessTime);
    }

    public TokenOut generateRefreshToken(final @NonNull UserDetails userDetails) {
        return new TokenOut(generateToken(userDetails, jwtRefreshSecret, expirationRefreshTime)
                .compact(), expirationRefreshTime);
    }

    private JwtBuilder generateToken(final @NonNull UserDetails userDetails,
                                     final SecretKey jwtSecret,
                                     final long expirationTime) {
        final LocalDateTime now = LocalDateTime.now();
        final Instant accessExpirationInstant = now
                .plusSeconds(expirationTime)
                .atZone(ZoneId.systemDefault())
                .toInstant();
        final Date accessExpiration = Date.from(accessExpirationInstant);
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setExpiration(accessExpiration)
                .signWith(jwtSecret);
    }

    public boolean validateAccessToken(@NonNull String accessToken) {
        return validateToken(accessToken, jwtAccessSecret);
    }

    public boolean validateRefreshToken(@NonNull String refreshToken) {
        return validateToken(refreshToken, jwtRefreshSecret);
    }

    private boolean validateToken(@NonNull String token, @NonNull Key secret) {
        try {
            JwtParser parser = Jwts.parser()
                    .setSigningKey(secret)
                    .build();
            parser.parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException expEx) {
            log.error("Token expired", expEx);
        } catch (UnsupportedJwtException unsEx) {
            log.error("Unsupported jwt", unsEx);
        } catch (MalformedJwtException mjEx) {
            log.error("Malformed jwt", mjEx);
        } catch (SignatureException sEx) {
            log.error("Invalid signature", sEx);
        } catch (Exception e) {
            log.error("Invalid token", e);
        }
        return false;
    }

    public String extractUsernameAccessClaim(final @NotNull String token) {
        return extractAccessClaim(token, Claims::getSubject);
    }

    public String extractUsernameRefreshClaim(final @NotNull String token) {
        return extractRefreshClaim(token, Claims::getSubject);
    }

    public JwtClaimOut extractJwtAuthAccessClaims(final @NotNull String token) {
        final Claims claims = extractClaims(token, jwtAccessSecret);
        return new JwtClaimOut(claims.get("userId", Long.class),
                claims.get("altUsername", String.class),
                Arrays.stream(claims.get("authorities", String.class)
                                .split(", "))
                        .map(JwtAuthority::new)
                        .collect(Collectors.toSet()));
    }

    private <T> T extractAccessClaim(final @NotNull String token, final Function<Claims, T> claimsResolver) {
        final Claims claims = extractClaims(token, jwtAccessSecret);
        return claimsResolver.apply(claims);
    }

    private <T> T extractRefreshClaim(final @NotNull String token, final Function<Claims, T> claimsResolver) {
        final Claims claims = extractClaims(token, jwtRefreshSecret);
        return claimsResolver.apply(claims);
    }

    private Claims extractClaims(final @NotNull String token, final @NotNull Key secret) {
        return Jwts.parser()
                .setSigningKey(secret)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public Claims getAccessClaims(@NonNull String token) {
        return getClaims(token, jwtAccessSecret);
    }

    public Claims getRefreshClaims(@NonNull String token) {
        return getClaims(token, jwtRefreshSecret);
    }

    private Claims getClaims(@NonNull String token, @NonNull Key secret) {
        return Jwts.parser()
                .setSigningKey(secret)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

}
