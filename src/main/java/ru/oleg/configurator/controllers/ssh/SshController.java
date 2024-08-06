package ru.oleg.configurator.controllers.ssh;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.oleg.configurator.service.ssh.SshService;
import ru.oleg.configurator.service.ssh.dto.SshConfig;
import ru.oleg.configurator.service.utils.dto.UpdateParameterIn;

@RestController
@RequestMapping("/ssh")
@AllArgsConstructor
@Tag(name = "Ssh")
public class SshController {
    private final SshService sshService;

    @GetMapping
    public ResponseEntity<SshConfig> getConfigSSH() {
        return ResponseEntity.ok(sshService.getConfigSSH());
    }

    @PatchMapping
    public ResponseEntity<?> updateConfigSSH(final @RequestBody UpdateParameterIn updateParameterIn) {
        sshService.updateConfigSSH(updateParameterIn);
        return ResponseEntity.ok().build();
    }
}
