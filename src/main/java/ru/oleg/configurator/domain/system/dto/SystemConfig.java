package ru.oleg.configurator.domain.system.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SystemConfig {
    private String deviceName;
    private String ramInfo;
    private String cpuInfo;
    private String graphicsInfo;
    private String romInfo;
    private String osName;
    private String osType;
    private String environmentVersion;
    private String windowInterface;
    private String virtualization;
}
