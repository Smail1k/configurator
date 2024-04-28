package ru.oleg.configurator.domain.environment.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MainParameters {
    private Boolean tooltipsHoveringOverMouse;
    private Boolean showNotificationsAdjustingDevices;
    private String animationSpeed;
    private String clickFolderFile;
    private String clickScrollBar;
    private String touchMode;
}
