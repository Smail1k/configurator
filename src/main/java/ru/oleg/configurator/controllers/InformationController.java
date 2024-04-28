package ru.oleg.configurator.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.oleg.configurator.domain.information.dto.Information;
import ru.oleg.configurator.domain.information.InformationService;

import java.io.IOException;

@RestController
@RequestMapping("/system")
@AllArgsConstructor
public class InformationController {
    private final InformationService informationService;

    @GetMapping()
    public ResponseEntity<Information> getInfoSystem(){
        return ResponseEntity.ok(informationService.getInformation());
    }
}
