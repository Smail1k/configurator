package ru.oleg.configurator.domain.system.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SystemConfig {
    private String deviceName;
    private String ramMemory;
    private String cpu;
    private String graphics;
    private String capacityDisk;
    private String osName;
    private String osType;
    private String environmentVersion;
    private String interfaceWindow;
    private String virtualization;
}
