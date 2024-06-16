package ru.oleg.configurator.controllers.nginx;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.oleg.configurator.domain.nginx.NginxService;
import ru.oleg.configurator.domain.nginx.dto.NginxConfig;

@RestController
@RequestMapping("/nginx")
@AllArgsConstructor
public class NginxController {
    private final NginxService nginxService;

    @GetMapping
    public ResponseEntity<NginxConfig> getConfigNginx() {
        return ResponseEntity.ok(nginxService.getConfigNginx());
    }

    @PostMapping
    public ResponseEntity<NginxConfig> updateConfigNginx(@RequestBody NginxConfig configNginx) {
        return ResponseEntity.ok(nginxService.updateConfigNginx(configNginx));
    }
}
