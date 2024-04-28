package ru.oleg.configurator.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.oleg.configurator.domain.appearance.dto.Appearance;
import ru.oleg.configurator.domain.appearance.AppearanceService;

@RestController
@RequestMapping("/appearance")
@AllArgsConstructor
public class AppearanceController {
    private final AppearanceService appearanceService;


    @GetMapping
    public ResponseEntity<Appearance> getAppearance() {
        return ResponseEntity.ok(appearanceService.getAppearance());
    }

    @PatchMapping
    public ResponseEntity<Appearance> updateAppearance(@RequestBody Appearance appearance) {
        return ResponseEntity.ok(appearanceService.updateAppearance(appearance));
    }
}
