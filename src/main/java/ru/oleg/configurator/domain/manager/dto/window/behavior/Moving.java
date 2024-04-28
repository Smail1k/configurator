package ru.oleg.configurator.domain.manager.dto.window.behavior;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Moving {
    private String borderBindingZone;
    private String windowsBindingZone;
    private String centerBindingZone;
    private Boolean onlyOverlapping;
}
