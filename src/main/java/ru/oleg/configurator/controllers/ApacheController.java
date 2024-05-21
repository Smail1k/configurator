package ru.oleg.configurator.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.oleg.configurator.domain.apache.ApacheService;
import ru.oleg.configurator.domain.apache.dto.ApacheConfig;


@RestController
@RequestMapping("/apache")
@AllArgsConstructor
public class ApacheController {
    private final ApacheService apacheService;

    @GetMapping
    public ResponseEntity<ApacheConfig> getConfigApache() {
        return ResponseEntity.ok(apacheService.getConfigApache());
    }

    @PostMapping
    public ResponseEntity<ApacheConfig> updateConfigApache(@RequestBody ApacheConfig configApache) {
        return ResponseEntity.ok(apacheService.updateConfigApache(configApache));
    }

}
