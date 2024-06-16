package ru.oleg.configurator.controllers.user;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.oleg.configurator.domain.user.dto.UserOut;
import ru.oleg.configurator.domain.user.UserService;

import java.util.List;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserOut>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PostMapping
    public ResponseEntity<UserOut> createUser(@RequestBody UserOut user) {
        return ResponseEntity.ok(userService.createUser(user));
    }

    @PatchMapping("/me")
    public ResponseEntity<UserOut> updateUser(@RequestBody UserOut user) {
        return ResponseEntity.ok(userService.updateMe(user));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserOut> updateUser(@PathVariable Long id, @RequestBody UserOut user) {
        return ResponseEntity.ok(userService.updateUser(id, user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        userService.deleteUserByUsername(id);
        return ResponseEntity.ok().build();
    }
}
