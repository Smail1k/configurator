package ru.oleg.configurator.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.oleg.configurator.domain.nginx.NginxService;
import ru.oleg.configurator.domain.nginx.dto.ConfigNginx;

@RestController
@RequestMapping("/nginx")
@AllArgsConstructor
public class NginxController {
    private final NginxService nginxService;

    @GetMapping
    public ResponseEntity<ConfigNginx> getConfigNginx() {
        return ResponseEntity.ok(nginxService.getConfigNginx());
    }

    @PostMapping
    public ResponseEntity<ConfigNginx> updateConfigNginx(@RequestBody ConfigNginx configNginx) {
        return ResponseEntity.ok(nginxService.updateConfigNginx(configNginx));
    }
}
