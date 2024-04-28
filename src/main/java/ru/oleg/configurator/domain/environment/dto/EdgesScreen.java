package ru.oleg.configurator.domain.environment.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EdgesScreen {
    private Boolean movingToTopScreen;
    private Boolean movingToRightLeftEdgeScreen;
    private Boolean activeWindowUnwrap;
    private String heightWindowZone;
    private String changeDesktopMovingMouse;
    private String delayBeforeReactionMovingMouse;
    private String delayBeforeRepeatedReaction;
}
