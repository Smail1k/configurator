package ru.oleg.configurator.domain.notification.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Notification {
    private Boolean duplicatingImageExternalScreen;
    private String combinationKeysNotDisturbMode;
    private Boolean criticalShowInNotDisturbMode;
    private Boolean commonOutputOverFullscreenWindows;
    private Boolean nonPriorityShowInNotDisturbMode;
    private Boolean nonPriorityShowInHistory;
    private String location;
    private String selectedLocation;
    private String delayAfterHover;
    private Boolean showProgressOnTaskbar;
    private Boolean showProgressInNotification;
    private Boolean dontClosePopUpWindowDuringExecution;
    private Boolean showCounterNotificationOnTaskbar;
}
