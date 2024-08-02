package ru.oleg.configurator.controllers.nginx;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.oleg.configurator.service.nginx.NginxService;
import ru.oleg.configurator.service.nginx.dto.NginxConfig;

@RestController
@RequestMapping("/nginx")
@AllArgsConstructor
@Tag(name = "Nginx")
public class NginxController {
    private final NginxService nginxService;

    @GetMapping
    public ResponseEntity<NginxConfig> getConfigNginx() {
        return ResponseEntity.ok(nginxService.getConfigNginx());
    }

    @PatchMapping
    public ResponseEntity<NginxConfig> updateConfigNginx(@RequestBody NginxConfig configNginx) {
        return ResponseEntity.ok(nginxService.updateConfigNginx(configNginx));
    }
}
