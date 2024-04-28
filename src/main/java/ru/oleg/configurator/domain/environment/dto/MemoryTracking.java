package ru.oleg.configurator.domain.environment.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemoryTracking {
    private Boolean EnableLowMemoryWarnings;
    private String memorizeOpenDocuments;
    private Boolean swapSection;
}
