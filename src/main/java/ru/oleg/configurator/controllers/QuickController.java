package ru.oleg.configurator.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.oleg.configurator.domain.quick.Quick;
import ru.oleg.configurator.domain.quick.QuickService;

@RestController
@RequestMapping("/quick")
@AllArgsConstructor
public class QuickController {
    private final QuickService quickService;

    @GetMapping
    public ResponseEntity<Quick> getQuick() {
        return ResponseEntity.ok(quickService.getQuick());
    }

    @PatchMapping
    public ResponseEntity<Quick> updateQuick(@RequestBody Quick quick) {
        return ResponseEntity.ok(quickService.updateQuick(quick));
    }
}
