package ru.oleg.configurator.controllers.user;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.oleg.configurator.security.JwtAuthentication;
import ru.oleg.configurator.service.user.dto.UserIn;
import ru.oleg.configurator.service.user.dto.User;
import ru.oleg.configurator.service.user.UserService;

import java.util.List;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
@Tag(name = "User")
public class UserController {
    private final UserService userService;

    @GetMapping("")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PostMapping("")
    public ResponseEntity<User> createUser(final @RequestBody UserIn userIn) {
        return ResponseEntity.ok(userService.createUser(userIn));
    }

    @PatchMapping("")
    public ResponseEntity<User> updateUser(final @RequestBody UserIn userIn,
                                           final @NotNull JwtAuthentication jwtAuthentication) {
        return ResponseEntity.ok(userService.updateUser(userIn, jwtAuthentication));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(final @PathVariable long id,
                                        final @NotNull JwtAuthentication jwtAuthentication) {
        userService.deleteUser(id, jwtAuthentication);
        return ResponseEntity.ok().build();
    }
}
