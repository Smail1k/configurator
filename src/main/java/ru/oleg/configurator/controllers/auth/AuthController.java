package ru.oleg.configurator.controllers.auth;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.oleg.configurator.service.auth.AuthService;
import ru.oleg.configurator.service.auth.dto.JwtRequest;
import ru.oleg.configurator.service.auth.dto.RefreshAccessTokenIn;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
@Tag(name = "Авторизация")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(final @RequestBody JwtRequest loginIn) {
        return ResponseEntity.ok(authService.login(loginIn));
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(final @RequestBody RefreshAccessTokenIn refreshAccessTokenIn) {
        return ResponseEntity.ok(authService.refresh(refreshAccessTokenIn));
    }
}
