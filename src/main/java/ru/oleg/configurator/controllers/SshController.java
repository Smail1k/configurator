package ru.oleg.configurator.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.oleg.configurator.domain.ssh.SshService;
import ru.oleg.configurator.domain.ssh.dto.ConfigSSH;

@RestController
@RequestMapping("/ssh")
@AllArgsConstructor
public class SshController {
    private final SshService sshService;

    @GetMapping
    public ResponseEntity<ConfigSSH> getConfigSSH() {
        return ResponseEntity.ok(sshService.getConfigSSH());
    }

    @PostMapping
    public ResponseEntity<ConfigSSH> updateConfigSSH(@RequestBody ConfigSSH configSSH) {
        return ResponseEntity.ok(sshService.updateConfigSSH(configSSH));
    }
}
