package ru.oleg.configurator.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.oleg.configurator.domain.environment.EnvironmentService;
import ru.oleg.configurator.domain.environment.dto.*;

@RestController
@RequestMapping("/environment")
@AllArgsConstructor
public class EnvironmentController {
    private final EnvironmentService environmentService;

    @GetMapping("/mainParameters")
    public ResponseEntity<MainParameters> getMainParameters() {
        return ResponseEntity.ok(environmentService.getMainParameters());
    }

    @PatchMapping("/mainParameters")
    public ResponseEntity<MainParameters> updateMainParameters(@RequestBody MainParameters mainParameters) {
        return ResponseEntity.ok(environmentService.updateMainParameters(mainParameters));
    }

    @GetMapping("/edgesScreen")
    public ResponseEntity<EdgesScreen> getEdgesScreen() {
        return ResponseEntity.ok(environmentService.getEdgesScreen());
    }

    @PatchMapping("/edgesScreen")
    public ResponseEntity<EdgesScreen> updateEdgesScreen(@RequestBody EdgesScreen edgesScreen) {
        return ResponseEntity.ok(environmentService.updateEdgesScreen(edgesScreen));
    }

    @GetMapping("/screenLock")
    public ResponseEntity<ScreenLock> getScreenLock() {
        return ResponseEntity.ok(environmentService.getScreenLock());
    }

    @PatchMapping("/screenLock")
    public ResponseEntity<ScreenLock> updateScreenLock(@RequestBody ScreenLock screenLock) {
        return ResponseEntity.ok(environmentService.updateScreenLock(screenLock));
    }

    @GetMapping("/recentFiles")
    public ResponseEntity<RecentFiles> getRecentFiles() {
        return ResponseEntity.ok(environmentService.getRecentFiles());
    }

    @PatchMapping("/recentFiles")
    public ResponseEntity<RecentFiles> updateRecentFiles(@RequestBody RecentFiles recentFiles) {
        return ResponseEntity.ok(environmentService.updateRecentFiles(recentFiles));
    }

    @GetMapping("/memoryTracking")
    public ResponseEntity<MemoryTracking> getMemoryTracking() {
        return ResponseEntity.ok(environmentService.getMemoryTracking());
    }

    @PatchMapping("/memoryTracking")
    public ResponseEntity<MemoryTracking> updateMemoryTracking(@RequestBody MemoryTracking memoryTracking) {
        return ResponseEntity.ok(environmentService.updateMemoryTracking(memoryTracking));
    }
}
