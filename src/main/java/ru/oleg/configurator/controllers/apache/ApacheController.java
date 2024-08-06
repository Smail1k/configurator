package ru.oleg.configurator.controllers.apache;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.oleg.configurator.service.apache.ApacheService;
import ru.oleg.configurator.service.apache.dto.ApacheConfig;


@RestController
@RequestMapping("/apache")
@AllArgsConstructor
@Tag(name = "Apache")
public class ApacheController {
    private final ApacheService apacheService;

    @GetMapping
    public ResponseEntity<ApacheConfig> getConfigApache() {
        return ResponseEntity.ok(apacheService.getConfigApache());
    }

    @PatchMapping
    public ResponseEntity<ApacheConfig> updateConfigApache(final @RequestBody ApacheConfig configApache) {
        return ResponseEntity.ok(apacheService.updateConfigApache(configApache));
    }

}
