package ru.oleg.configurator.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.oleg.configurator.domain.manager.ManagerService;
import ru.oleg.configurator.domain.manager.dto.kwin.Kwin;
import ru.oleg.configurator.domain.manager.dto.window.behavior.*;
import ru.oleg.configurator.domain.manager.dto.window.switching.MainAlternative;

@RestController
@RequestMapping("/manager")
@AllArgsConstructor
public class ManagerController {
    private final ManagerService managerService;

    @GetMapping("/focus")
    public ResponseEntity<Focus> getFocus() {
        return ResponseEntity.ok(managerService.getFocus());
    }

    @PatchMapping("/focus")
    public ResponseEntity<Focus> updateFocus(@RequestBody Focus focus) {
        return ResponseEntity.ok(managerService.updateFocus(focus));
    }

    @GetMapping("/headerActions")
    public ResponseEntity<HeaderAction> getHeaderActions() {
        return ResponseEntity.ok(managerService.getHeaderAction());
    }

    @PatchMapping("/headerActions")
    public ResponseEntity<HeaderAction> updateHeaderActions(@RequestBody HeaderAction headerAction) {
        return ResponseEntity.ok(managerService.updateHeaderAction(headerAction));
    }

    @GetMapping("/windowActions")
    public ResponseEntity<WindowAction> getWindowAction() {
        return ResponseEntity.ok(managerService.getWindowAction());
    }

    @PatchMapping("/windowAction")
    public ResponseEntity<WindowAction> updateWindowAction(@RequestBody WindowAction windowAction) {
        return ResponseEntity.ok(managerService.updateWindowAction(windowAction));
    }

    @GetMapping("/moving")
    public ResponseEntity<Moving> getMoving() {
        return ResponseEntity.ok(managerService.getMoving());
    }

    @PatchMapping("/moving")
    public ResponseEntity<Moving> updateMoving(@RequestBody Moving moving) {
        return ResponseEntity.ok(managerService.updateMoving(moving));
    }

    @GetMapping("/additionally")
    public ResponseEntity<Additionally> getAdditionally() {
        return ResponseEntity.ok(managerService.getAdditionally());
    }

    @PatchMapping("/additionally")
    public ResponseEntity<Additionally> updateAdditionally(@RequestBody Additionally additionally) {
        return ResponseEntity.ok(managerService.updateAdditionally(additionally));
    }

    @GetMapping("/mainAlternative")
    public ResponseEntity<MainAlternative> getMainAlternative() {
        return ResponseEntity.ok(managerService.getMainAlternative());
    }

    @PatchMapping("/mainAlternative")
    public ResponseEntity<MainAlternative> updateMainAlternative(@RequestBody MainAlternative mainAlternative) {
        return ResponseEntity.ok(managerService.updateMainAlternative(mainAlternative));
    }

    @GetMapping("/kwin")
    public ResponseEntity<Kwin> getKwin() {
        return ResponseEntity.ok(managerService.getKwin());
    }

    @PatchMapping("/kwin")
    public ResponseEntity<Kwin> updateKwin(@RequestBody Kwin kwin) {
        return ResponseEntity.ok(managerService.updateKwin(kwin));
    }
}
