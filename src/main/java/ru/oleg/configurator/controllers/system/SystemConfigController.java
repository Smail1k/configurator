package ru.oleg.configurator.controllers.system;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.oleg.configurator.domain.system.dto.SystemConfig;
import ru.oleg.configurator.domain.system.SystemConfigService;

@RestController
@RequestMapping("/system")
@AllArgsConstructor
public class SystemConfigController {
    private final SystemConfigService informationService;

    @GetMapping()
    public ResponseEntity<SystemConfig> getInfoSystem(){
        return ResponseEntity.ok(informationService.getInformation());
    }
}
