package ru.oleg.configurator.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.oleg.configurator.domain.application.ApplicationService;
import ru.oleg.configurator.domain.application.dto.Application;
import ru.oleg.configurator.domain.application.dto.Player;
import ru.oleg.configurator.domain.application.dto.Way;

@RestController
@RequestMapping("/applications")
@AllArgsConstructor
public class ApplicationController {
    private final ApplicationService applicationService;

    @GetMapping("/application")
    public ResponseEntity<Application> getApplication() {
        return ResponseEntity.ok(applicationService.getApplication());
    }

    @PatchMapping("/application")
    public ResponseEntity<Application> updateApplication(@RequestBody Application application) {
        return ResponseEntity.ok(applicationService.updateApplication(application));
    }

    @GetMapping("/ways")
    public ResponseEntity<Way> getWays() {
        return ResponseEntity.ok(applicationService.getWay());
    }

    @PatchMapping("/ways")
    public ResponseEntity<Way> updateWay(@RequestBody Way way) {
        return ResponseEntity.ok(applicationService.updateWay(way));
    }

    @GetMapping("/players")
    public ResponseEntity<Player> getPlayers() {
        return ResponseEntity.ok(applicationService.getPlayer());
    }

    @PatchMapping("/players")
    public ResponseEntity<Player> updatePlayer(@RequestBody Player player) {
        return ResponseEntity.ok(applicationService.updatePlayer(player));
    }
}

