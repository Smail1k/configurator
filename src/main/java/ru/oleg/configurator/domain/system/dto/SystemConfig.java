package ru.oleg.configurator.domain.system.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
