package ru.oleg.configurator.domain.manager.dto.window.behavior;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Additionally {
    private Boolean afterOnHoverWithDelay;
    private String delayAfterHover;
    private String placingNewWindow;
    private Boolean allowSaveGeometry;
    private Boolean hideServiceWindow;
    private String BehaviorVirtualDesktops;
}
