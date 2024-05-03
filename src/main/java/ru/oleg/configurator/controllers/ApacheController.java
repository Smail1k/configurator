package ru.oleg.configurator.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.oleg.configurator.domain.apache.ApacheService;
import ru.oleg.configurator.domain.apache.dto.ConfigApache;


@RestController
@RequestMapping("/apache")
@AllArgsConstructor
public class ApacheController {
    private final ApacheService apacheService;

    @GetMapping
    public ResponseEntity<ConfigApache> getConfigApache() {
        return ResponseEntity.ok(apacheService.getConfigApache());
    }

    @PostMapping
    public ResponseEntity<ConfigApache> updateConfigApache(@RequestBody ConfigApache configApache) {
        return ResponseEntity.ok(apacheService.updateConfigApache(configApache));
    }

}
