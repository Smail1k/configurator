package ru.oleg.configurator.domain.application.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Application {
    private String webBrowser;
    private String fileManager;
    private String emailClient;
    private String terminalEmulator;
    private String map;
    private String dialer;
}
