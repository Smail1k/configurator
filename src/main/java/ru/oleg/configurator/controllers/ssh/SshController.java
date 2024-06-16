package ru.oleg.configurator.controllers.ssh;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.oleg.configurator.domain.ssh.SshService;
import ru.oleg.configurator.domain.ssh.dto.SshConfig;
import ru.oleg.configurator.domain.ssh.dto.UpdateParameterIn;

@RestController
@RequestMapping("/ssh")
@AllArgsConstructor
public class SshController {
    private final SshService sshService;

    @GetMapping
    public ResponseEntity<SshConfig> getConfigSSH() {
        return ResponseEntity.ok(sshService.getConfigSSH());
    }

    @PatchMapping
    public ResponseEntity<?> updateConfigSSH(@RequestBody UpdateParameterIn updateParameterIn) {
        sshService.updateConfigSSH(updateParameterIn);
        return ResponseEntity.ok().build();
    }
}
