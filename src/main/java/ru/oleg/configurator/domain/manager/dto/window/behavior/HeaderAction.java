package ru.oleg.configurator.domain.manager.dto.window.behavior;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HeaderAction {
    private String doubleClick;
    private String mouseWheel;
    private String borderClickLeftButtonActive;
    private String borderClickLeftButtonInactive;
    private String borderClickMediumButtonActive;
    private String borderClickMediumButtonInActive;
    private String borderClickRightButtonActive;
    private String borderClickRightButtonInActive;
    private String unfoldingClickLeftButton;
    private String unfoldingClickMediumButton;
    private String unfoldingClickRightButton;
}
