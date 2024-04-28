package ru.oleg.configurator.domain.manager.dto.window.behavior;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class WindowAction {
    private String clickLeftButtonInInactive;
    private String clickMediumButtonInInactive;
    private String clickRightButtonInInactive;
    private String mouseWheelButtonInInactive;
    private String additionalKey;
    private String additionalKeyWithClickLeftButton;
    private String additionalKeyWithClickMediumButton;
    private String additionalKeyWithClickRightButton;
    private String additionalKeyWithMouseWheelButton;
}
