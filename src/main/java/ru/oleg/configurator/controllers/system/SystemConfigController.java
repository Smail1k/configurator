package ru.oleg.configurator.controllers.system;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.oleg.configurator.service.system.dto.SystemConfig;
import ru.oleg.configurator.service.system.SystemConfigService;

@RestController
@RequestMapping("/system")
@AllArgsConstructor
@Tag(name = "System")
public class SystemConfigController {
    private final SystemConfigService informationService;

    @GetMapping()
    public ResponseEntity<SystemConfig> getInfoSystem(){
        return ResponseEntity.ok(informationService.getInformation());
    }
}
