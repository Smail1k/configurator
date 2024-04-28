package ru.oleg.configurator.domain.manager.dto.window.switching;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MainAlternative {
    private Boolean showSelectedWindow;
    private Boolean checkboxTypeVisualSwitchingWindow;
    private String typeVisualSwitchingWindow;
    private String combinationKeySwitchWindowFront;
    private String combinationKeySwitchWindowBack;
    private String combinationKeySwitchWindowCurrentAppFront;
    private String combinationKeySwitchWindowCurrentAppBack;
    private String sort;
    private Boolean enableShowDesktop;
    private Boolean oneWindowPerApp;
    private Boolean arrangeRollUpWindowAfterUnfolding;
    private Boolean checkboxDesktop;
    private String radioDesktop;
    private Boolean checkboxRoom;
    private String radioRoom;
    private Boolean checkboxVisibility;
    private String radioVisibility;
}
