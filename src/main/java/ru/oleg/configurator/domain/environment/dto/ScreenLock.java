package ru.oleg.configurator.domain.environment.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScreenLock {
    private Boolean checkboxAbsenceOfActivityDuring;
    private String countAbsenceOfActivityDuring;
    private Boolean exitingStandbyMode;
    private String countUnlockWithoutPassword;
}
