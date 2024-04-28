package ru.oleg.configurator.domain.manager.dto.window.behavior;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Focus {
    private String activateWindow;
    private String delaySwitchingFocus;
    private String levelPreventingChangeFocus;
    private Boolean clickOpenActiveWindow;
    private Boolean pullOutHover;
    private String delayHoverToFocus;
}
